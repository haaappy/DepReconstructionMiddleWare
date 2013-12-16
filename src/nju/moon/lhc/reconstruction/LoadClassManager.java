package nju.moon.lhc.reconstruction;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;



public class LoadClassManager {
	private static class SingletonHolder{
		private static final LoadClassManager INSTANCE = new LoadClassManager();
	}
	
	public static final LoadClassManager getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	public LoadClassManager(){
		
	}
	
	public void loadAllClass(){
		HashSet<File> deploymentSet = VFSManager.getInstance().getDeploymentSet();		
		for (File f: deploymentSet){
			for (File classFile: f.listFiles()){
				if (classFile.isFile() && classFile.getName().endsWith(".class")){
					loadClassByDeployment(f.getName(), classFile.getName());
				}
			}
		}
	}
	
	public Class<?> loadClassByDeployment(String deploymentName, String className){
		HashMap<String, DeploymentNode> nodeMap = DependencyManager.getInstance().getNodeMap();
		DeploymentNode curNode = nodeMap.get(deploymentName);
		ClassLoader cl = curNode.getClassLoader();
		try {
			return cl.loadClass(className.substring(0, className.lastIndexOf('.')));    // load the XXXX   for example： NodeAMain
			// System.out.println("success load "+className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();		
		}
		return null;
	}
	
	public void loadClassByDeploymentNameSet(HashSet<String> nameSet){
		HashSet<File> deploymentSet = VFSManager.getInstance().getDeploymentSet();		
		for (File f: deploymentSet){
			if (nameSet.contains(f.getName())){
				for (File classFile: f.listFiles()){
					if (classFile.isFile() && classFile.getName().endsWith(".class")){
						loadClassByDeployment(f.getName(), classFile.getName());
					}
				}
			}
		}
	}
}
