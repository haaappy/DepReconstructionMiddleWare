package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.classloader.AdaptExtDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class AdaptNormalLoadClassManager extends AdaptLoadClassManager {

	@Override
	protected void prepareQueue(HashSet<File> deploymentSet) {
		for (File f: deploymentSet){
			prepareAdeploymentByFile(f);				
		}	
	}

	@Override
	protected String changeClassNameFormat(String className) {
		String formatName = className.substring(0, className.lastIndexOf(".class"));
		return formatName;
	}
	
	@Override
	public Class<?> loadClassByDeployment(String deploymentName, String className) {
		// TODO Auto-generated method stub
		HashMap<String, DeploymentNode> nodeMap = MiddleWareConfig.getInstance().getDepManager().getNodeMap();
		DeploymentNode curNode = nodeMap.get(deploymentName);
		ClassLoader cl = curNode.getClassLoader();
		
		if (cl instanceof AdaptExtDepClassLoader){
			if (((AdaptExtDepClassLoader)cl).getValid() == false){
				curNode.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(deploymentName));
				cl = curNode.getClassLoader();
			}
		}
		
		try {
			return cl.loadClass(className.substring(0, className.lastIndexOf('.')));    // load the XXXX   for example： NodeAMain
			// System.out.println("success load "+className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();		
		}
		return null;
	}

	@Override
	public void loadClassByDeploymentNameSet(HashSet<String> nameSet) {
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();		
		for (File f: deploymentSet){
			if (nameSet.contains(f.getName())){
				prepareAdeploymentByFile(f);
			}
		}
		tryLoadingFromQueue();   // load the new classes
	}

	@Override
	protected void prepareAdeploymentByFile(File f) {
		DeploymentNode node = new DeploymentNode(f.getName());
		node.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(f.getName()));
		for (File classFile: f.listFiles()){
			if (classFile.isFile() && classFile.getName().endsWith(".class")){
				addALoadingClass(node, changeClassNameFormat(classFile.getName()));
			}
		}
	}

}
