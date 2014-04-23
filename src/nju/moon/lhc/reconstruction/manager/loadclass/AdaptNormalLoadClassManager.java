package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class AdaptNormalLoadClassManager extends AdaptLoadClassManager {

	@Override
	public void loadAllClass() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<?> loadClassByDeployment(String deploymentName,
			String className) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loadClassByDeploymentNameSet(HashSet<String> nameSet) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prepareQueue(HashSet<File> deploymentSet) {
		for (File file: deploymentSet){
			String fileName = file.getName();
			DeploymentNode node = new DeploymentNode(fileName);
			node.setClassLoader(new AdaptDepClassLoader(MiddleWareConfig.getInstance().getCurMiddleWareHome(), fileName));		
			for (File classFile: file.listFiles()){
				if (classFile.isFile() && classFile.getName().endsWith(".class")){
					addALoadingClass(node, changeClassNameFormat(classFile.getName()));
				}
			}					
		}	
	}

	@Override
	protected String changeClassNameFormat(String className) {
		return className.substring(0, className.lastIndexOf('.'));
	}

}
