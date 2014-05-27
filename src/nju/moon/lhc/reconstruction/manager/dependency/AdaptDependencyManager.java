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
		loadedNodeRepository.remove(node.getNodeName());
	}
	
	// TODO  remove Action. need to check carefully! important method to reconstruct dependency
	public void reconstructDependency(HashSet<String> rmvSet){
		for (String rmvStr: rmvSet){
			reconstructByDepNode(rmvStr, rmvSet);
		}
		
	}
	
	public void reconstructByDepNode(String depInverseClName, HashSet<String> rmvSet){
		DeploymentNode node = loadedNodeRepository.get(depInverseClName);
		if (node != null){
			AdaptDepClassLoader nodeClassLoader = (AdaptDepClassLoader) node.getClassLoader();
			// cut Dep Relation
			for (ClassLoader depCl: nodeClassLoader.getDepClassLoaders()){
				String depClName = ((AdaptDepClassLoader)depCl).getClassLoaderName();
				if (!rmvSet.contains(depClName) && !reconstructNodeSet.contains(depClName)){
					((AdaptDepClassLoader)loadedNodeRepository.get(depClName).getClassLoader()).removeDepInverseByName(depInverseClName);
				}			
			}
			
			// find DepInverse Relation
			for (ClassLoader depInverseCl: nodeClassLoader.getDepInverseClassLoaders()){
				String newDepInverseClName = ((AdaptDepClassLoader)depInverseCl).getClassLoaderName();
				if (!rmvSet.contains(newDepInverseClName) && !reconstructNodeSet.contains(newDepInverseClName)){
					reconstructNodeSet.add(newDepInverseClName);
					reconstructByDepNode(newDepInverseClName, rmvSet);
				}
			}
			
			nodeClassLoader.getDepClassLoaders().clear();
			nodeClassLoader.getDepInverseClassLoaders().clear();
			// remove the repository nodes
			node.setClassLoader(null);
			removeLoadedDeploymentNode(node);
		}	
	}
	
	// addAction
	public void addNewDeploymentNode(HashSet<String> addSet){
		reconstructNodeSet.addAll(addSet);
		MiddleWareConfig.getInstance().getLcManager().loadClassByDeploymentNameSet(reconstructNodeSet);
		reconstructNodeSet.clear();
	}
	
	public ArrayList<ClassLoader> getAllRepositoryClassLoaders(){
		ArrayList<ClassLoader> loadedClassLoaderList = new ArrayList<ClassLoader>();
		for (DeploymentNode node : loadedNodeRepository.values()){
			loadedClassLoaderList.add(node.getClassLoader());
		}
		return loadedClassLoaderList;
	}

}
