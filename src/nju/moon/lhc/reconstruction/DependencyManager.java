package nju.moon.lhc.reconstruction;

import java.util.HashMap;
import java.util.HashSet;


public class DependencyManager {

	private DeploymentNode systemRoot;  // System class loader
	private HashMap<String, DeploymentNode> nodeMap;  // store all the node
	
	private static class SingletonHolder{
		private static final DependencyManager INSTANCE = new DependencyManager();
	}
	
	public static final DependencyManager getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	public DependencyManager(){
		systemRoot = new DeploymentNode();
		nodeMap = new HashMap<String, DeploymentNode>();
	}
	
	public HashMap<String, DeploymentNode> getNodeMap(){
		return nodeMap;
	}
	
	
	// create the dependencyGraph when init the system
	public void createDependencyGraph(){
		HashMap<String,HashSet<String>> xmlInfoMap = VFSManager.getInstance().getXMLDependencyInfoMap();
		addDeploymentNodeByAddDepMap(xmlInfoMap);
	}
	
	// modify the deploymentNode in nodeMap by the rmvDepMap
	public void modifyDeploymentNodeByRmvDepMap(HashMap<String, HashSet<String>> rmvDepMap){
		// remove the children Node
		for (String key: rmvDepMap.keySet()){
			if (rmvDepMap.get(key).size() == 0){  // parent is systemRoot
				systemRoot.removeChildByName(key);
			}
			else{
				for (String parent: rmvDepMap.get(key)){
					nodeMap.get(parent).removeChildByName(key);
				}
			}		
		}
		// remove itself
		for (String key: rmvDepMap.keySet()){
			nodeMap.remove(key);
		}
	}
	
	// add the deploymentNode in nodeMap by the addDepMap
	public void addDeploymentNodeByAddDepMap(HashMap<String, HashSet<String>> addDepMap){
		// add the new deploymentNode
		for (String nodeName: addDepMap.keySet()){
			DeploymentNode node = new DeploymentNode(nodeName);
			node.setClassLoader(new ReconstructionClassLoader(nodeName));
			nodeMap.put(nodeName, node);
		}
		
		// add the dependency and classloader in deploymentNode
		for (String nodeName: addDepMap.keySet()){
			HashSet<String> depSet = addDepMap.get(nodeName);
			DeploymentNode curNode = nodeMap.get(nodeName);
			if (depSet.size() == 0){			
				curNode.setAFather(systemRoot);
				systemRoot.setAChild(curNode);
				((ReconstructionClassLoader)curNode.getClassLoader()).addParentByClassLoader(ClassLoader.getSystemClassLoader());
				
			}
			else{
				for (String depName: addDepMap.get(nodeName)){			
					DeploymentNode depNode = nodeMap.get(depName);
					depNode.setAChild(curNode);
					curNode.setAFather(depNode);
					((ReconstructionClassLoader)curNode.getClassLoader()).addParentByClassLoader(depNode.getClassLoader());
				}
			}		
		}
	}
	
	// reconstruct dependency by two node  from vfsManager polling thread before load class
	public void reconstructDependencyByTwoNode(String nodeName, String depName){
		DeploymentNode curNode = nodeMap.get(nodeName);
		DeploymentNode depNode = nodeMap.get(depName);
		
		depNode.removeChildByName(curNode.getNodeName());
		depNode.setAChild(curNode);
		curNode.removeFatherByName(depNode.getNodeName());
		curNode.setAFather(depNode);
		((ReconstructionClassLoader)curNode.getClassLoader()).removeParentByName(depName);
		((ReconstructionClassLoader)curNode.getClassLoader()).addParentByClassLoader(depNode.getClassLoader());
		// import !!!   change the classloader, but the really classloader is the same.
		curNode.setClassLoader(new ReconstructionClassLoader((ReconstructionClassLoader)curNode.getClassLoader()));
	}
	
	public void reconstructMainClassLoader(String mainNodeName){
		DeploymentNode mainNode = nodeMap.get(mainNodeName);
		mainNode.setClassLoader(new ReconstructionClassLoader((ReconstructionClassLoader)mainNode.getClassLoader()));
	}
	
}
