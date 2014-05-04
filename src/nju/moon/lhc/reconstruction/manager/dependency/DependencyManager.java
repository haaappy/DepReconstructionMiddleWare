package nju.moon.lhc.reconstruction.manager.dependency;

import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.classloader.ReconstructionClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.vfs.VFSManager;


public class DependencyManager extends AbstractDependencyManager{

	private DeploymentNode systemRoot;  // System class loader
	
	public DependencyManager(){
		super();
		systemRoot = new DeploymentNode();
	}		
	
	public boolean isCircleDependency(HashMap<String, HashSet<String>> xmlInfoMap){
		
		HashMap<String, HashSet<String>> reverseMap = new HashMap<String, HashSet<String>>();
		for(String key: xmlInfoMap.keySet()){
			reverseMap.put(key, new HashSet<String>());
		}
			
		for (String key: xmlInfoMap.keySet()){
			for (String str: xmlInfoMap.get(key)){
				HashSet<String> hs = reverseMap.get(str);
				hs.add(key);
				reverseMap.put(str, hs);
			}
		}
		
		while (isInDegreeZero(reverseMap)){
			
		}
		if (reverseMap.isEmpty()){
			return false;
		}
		else{
			return true;
		}
	}	
	
	public boolean isInDegreeZero(HashMap<String, HashSet<String>> reverseMap){
		HashSet<String> rmSet = new HashSet<String>();
		for (String key: reverseMap.keySet()){
			if (reverseMap.get(key).size() == 0){
				rmSet.add(key);
			}
		}		
		if (rmSet.size() == 0){
			return false;
		}
		else{
			for (String key: reverseMap.keySet()){
				reverseMap.get(key).removeAll(rmSet);
			}
			for (String rm: rmSet){
				reverseMap.remove(rm);
			}
			return true;
		}
	}
	
	// test methode
		public static void main(String[] arg){
			HashMap<String, HashSet<String>> map = new HashMap<String, HashSet<String>>();
			HashSet<String> hs = new HashSet<String>(); hs.add("B");hs.add("C");
			map.put("A", hs);
			HashSet<String> hss = new HashSet<String>(); hss.add("C");hss.add("A");
			map.put("B", hss);
			map.put("C", new HashSet<String>());
			
			DependencyManager d = new DependencyManager();
			System.out.println(d.isCircleDependency(map));
		}
		
	
	// create the dependencyGraph when init the system
	public void createDependencyGraph(){
		HashMap<String,HashSet<String>> xmlInfoMap = ((VFSManager)(MiddleWareConfig.getInstance().getVfsManager())).getXMLDependencyInfoMap();
		if (isCircleDependency(xmlInfoMap)){
			System.out.println("Warning!!! The circle exists in the dependency!!\n Please use CircleExtReconstructionClassLoader!");
		}
		
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
			// replace //node.setClassLoader(new ReconstructionClassLoader(nodeName));
			node.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(nodeName));
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
		// replace //curNode.setClassLoader(new ReconstructionClassLoader((ReconstructionClassLoader)curNode.getClassLoader()));
		curNode.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByCl((ReconstructionClassLoader)curNode.getClassLoader()));
	}
	
	public void reconstructMainClassLoader(String mainNodeName){
		DeploymentNode mainNode = nodeMap.get(mainNodeName);
		// replace //mainNode.setClassLoader(new ReconstructionClassLoader((ReconstructionClassLoader)mainNode.getClassLoader()));
		mainNode.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByCl((ReconstructionClassLoader)mainNode.getClassLoader()));
	}
	
}
