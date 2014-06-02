package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.classloader.AdaptExtDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class AdaptJarLoadClassManager extends AdaptLoadClassManager {

	@Override
	protected void prepareQueue(HashSet<File> deploymentSet) {
		for (File f: deploymentSet){
			prepareAdeploymentByFile(f);
		}	
	}

	@Override
	protected String changeClassNameFormat(String className) {
		return className.replace(File.separatorChar, '.');
	}
	
	@Override
	public Class<?> loadClassByDeployment(String deploymentName, String className) {
		// TODO Auto-generated method stub
		HashMap<String, DeploymentNode> nodeMap = MiddleWareConfig.getInstance().getDepManager().getNodeMap();
		DeploymentNode curNode = nodeMap.get(deploymentName);
		
		ClassLoader cl = curNode.getClassLoader();
		if (cl instanceof AdaptExtDepClassLoader){
			if (((AdaptExtDepClassLoader)cl).getValid()){
				curNode.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(deploymentName));
				cl = curNode.getClassLoader();
			}
		}
		
		
		try {
			return cl.loadClass(className.substring(0, className.lastIndexOf(".class")));    // load the XXXX   for example：  safd.fdg.NodeAMain
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
		try{
			ZipFile zf = new ZipFile(f.getPath());
			DeploymentNode node = new DeploymentNode(f.getName());
			node.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(f.getName()));
			for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) { 
				ZipEntry ze = (ZipEntry)entries.nextElement();
				String zipEntryName = ze.getName();
				if (!ze.isDirectory() && zipEntryName.indexOf(".class")>0){			
					addALoadingClass(node, changeClassNameFormat(zipEntryName));
				}  
			} 
		}
		catch(Exception e){
			System.out.println("Exception in AdaptJarLoadClassManager in loadAdeploymentByFile");
			e.printStackTrace();
		}		
	}

}
