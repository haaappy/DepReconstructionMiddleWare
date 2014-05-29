package nju.moon.lhc.reconstruction.manager.dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;

public class AdaptDependencyManager extends AbstractDependencyManager{

	private HashMap<String, DeploymentNode> loadedNodeRepository;
	
	private HashSet<String> reconstructNodeSet;
	
	public AdaptDependencyManager(){
		super();
		loadedNodeRepository = nodeMap;    // map to store loaded node
		reconstructNodeSet = new HashSet<String>();
	}
	
	public HashMap<String, DeploymentNode> getLoadedNodeResporitory(){
		return loadedNodeRepository;
	}
	
	
	public void addLoadedDeploymentNode(DeploymentNode node){
		loadedNodeRepository.put(node.getNodeName(), node);
	}
	
	public void removeLoadedDeploymentNode(DeploymentNode node){
		AdaptDepClassLoader nodeClassLoader = (AdaptDepClassLoader) node.getClassLoader();
		nodeClassLoader.getDepClassLoaders().clear();
		nodeClassLoader.getDepInverseClassLoaders().clear();		
		node.setClassLoader(null);
		loadedNodeRepository.remove(node.getNodeName());
	}
	
	// TODO  remove Action. need to check carefully! important method to reconstruct dependency
	public void updateDeploymentNodeBySet(HashSet<String> updateSet){
		for (String updateStr: updateSet){
			reconstructByDepNode(updateStr, updateSet);
			reconstructNodeSet.add(updateStr);
		}
		
	}
	
	public void reconstructByDepNode(String depInverseClName, HashSet<String> updateSet){
		DeploymentNode node = loadedNodeRepository.get(depInverseClName);
		if (node != null){
			AdaptDepClassLoader nodeClassLoader = (AdaptDepClassLoader) node.getClassLoader();
			// cut Dep Relation
			for (ClassLoader depCl: nodeClassLoader.getDepClassLoaders()){
				String depClName = ((AdaptDepClassLoader)depCl).getClassLoaderName();
				if (!updateSet.contains(depClName) && !reconstructNodeSet.contains(depClName)){
					((AdaptDepClassLoader)loadedNodeRepository.get(depClName).getClassLoader()).removeDepInverseByName(depInverseClName);
				}			
			}
			
			// find DepInverse Relation
			for (ClassLoader depInverseCl: nodeClassLoader.getDepInverseClassLoaders()){
				String newDepInverseClName = ((AdaptDepClassLoader)depInverseCl).getClassLoaderName();
				if (!updateSet.contains(newDepInverseClName) && !reconstructNodeSet.contains(newDepInverseClName)){
					reconstructNodeSet.add(newDepInverseClName);
					reconstructByDepNode(newDepInverseClName, updateSet);
				}
			}
			
			// remove the repository nodes			
			removeLoadedDeploymentNode(node);
		}	
	}
	
	// addAction
	public void addNewDeploymentNodeBySet(HashSet<String> addSet){
		reconstructNodeSet.addAll(addSet);
		MiddleWareConfig.getInstance().getLcManager().loadClassByDeploymentNameSet(reconstructNodeSet);
		reconstructNodeSet.clear();
	}
	
	
	// TODO removeAction
	public void removeDeploymentNodeBySet(HashSet<String> rmvSet){
		for (String nodeName: rmvSet){
			DeploymentNode node = loadedNodeRepository.get(nodeName);
			if (node != null){
				AdaptDepClassLoader nodeClassLoader = (AdaptDepClassLoader) node.getClassLoader();
				
				// cut Dep Relation
				for (ClassLoader depCl: nodeClassLoader.getDepClassLoaders()){
					String depClName = ((AdaptDepClassLoader)depCl).getClassLoaderName();
					((AdaptDepClassLoader)loadedNodeRepository.get(depClName).getClassLoader()).removeDepInverseByName(nodeName);						
				}
							
				// find DepInverse Relation
				for (ClassLoader depInverseCl: nodeClassLoader.getDepInverseClassLoaders()){
					String depInverseClName = ((AdaptDepClassLoader)depInverseCl).getClassLoaderName();
					((AdaptDepClassLoader)loadedNodeRepository.get(depInverseClName).getClassLoader()).removeDepInverseByName(nodeName);
				}
			}
			// total delete node
			removeLoadedDeploymentNode(node);		
		}
	}
	
	public ArrayList<ClassLoader> getAllRepositoryClassLoaders(){
		ArrayList<ClassLoader> loadedClassLoaderList = new ArrayList<ClassLoader>();
		for (DeploymentNode node : loadedNodeRepository.values()){
			loadedClassLoaderList.add(node.getClassLoader());
		}
		return loadedClassLoaderList;
	}

}
