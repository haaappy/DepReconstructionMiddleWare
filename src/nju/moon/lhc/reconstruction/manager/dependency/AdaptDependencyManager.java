package nju.moon.lhc.reconstruction.manager.dependency;

import java.util.ArrayList;
import java.util.HashSet;

public class AdaptDependencyManager {

	private HashSet<DeploymentNode> loadedNodeRepository;
	
	public AdaptDependencyManager(){
		loadedNodeRepository = new HashSet<DeploymentNode>();
	}
	
	public HashSet<DeploymentNode> getLoadedNodeResporitory(){
		return loadedNodeRepository;
	}
	
	
	public void addLoadedDeploymentNode(DeploymentNode node){
		loadedNodeRepository.add(node);
	}
	
	public void removeLoadedDeploymentNode(DeploymentNode node){
		loadedNodeRepository.remove(node);
	}
	
	public void createLoadedRepository(){
		
	}
	
	
	// update the repository
	public void updateLoadedrepository(HashSet<String> rmvSet){
		for (String rmvStr: rmvSet){
			DeploymentNode node = new DeploymentNode(rmvStr);
			removeLoadedDeploymentNode(node);
		}
	}
	
	
	public ArrayList<ClassLoader> getAllRepositoryClassLoaders(){
		ArrayList<ClassLoader> loadedClassLoaderList = new ArrayList<ClassLoader>();
		for (DeploymentNode node : loadedNodeRepository){
			loadedClassLoaderList.add(node.getClassLoader());
		}
		return loadedClassLoaderList;
	}

}
