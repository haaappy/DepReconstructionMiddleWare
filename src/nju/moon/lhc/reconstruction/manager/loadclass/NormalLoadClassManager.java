package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class NormalLoadClassManager extends LoadClassManager {

	@Override
	public void loadAllClass(){
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();		
		for (File f: deploymentSet){
			for (File classFile: f.listFiles()){
				if (classFile.isFile() && classFile.getName().endsWith(".class")){
					loadClassByDeployment(f.getName(), classFile.getName());
				}
			}
		}
	}
	
	@Override
	public Class<?> loadClassByDeployment(String deploymentName, String className){
		HashMap<String, DeploymentNode> nodeMap = MiddleWareConfig.getInstance().getDepManager().getNodeMap();
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
	
	@Override
	public void loadClassByDeploymentNameSet(HashSet<String> nameSet){
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();		
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
