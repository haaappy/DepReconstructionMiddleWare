package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class JarLoadClassManager extends LoadClassManager {

	@Override
	public void loadAllClass() {
		// TODO Auto-generated method stub
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();
		for (File f: deploymentSet){
			loadJarFile(f);
		}
	}

	@Override
	public Class<?> loadClassByDeployment(String deploymentName, String className) {
		// TODO Auto-generated method stub
		HashMap<String, DeploymentNode> nodeMap = ((DependencyManager)(MiddleWareConfig.getInstance().getDepManager())).getNodeMap();
		DeploymentNode curNode = nodeMap.get(deploymentName);
		ClassLoader cl = curNode.getClassLoader();
		
		
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
		// TODO Auto-generated method stub
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();		
		for (File f: deploymentSet){
			if (nameSet.contains(f.getName())){
				loadJarFile(f);		 
			}
		}
	}

	private void loadJarFile(File f) {
		try{
			ZipFile zf = new ZipFile(f.getPath());
			for (Enumeration<?> entries = zf.entries(); entries.hasMoreElements();) { 
				ZipEntry ze = (ZipEntry)entries.nextElement();
				String zipEntryName = ze.getName();
				if (!ze.isDirectory() && zipEntryName.indexOf(".class")>0){
					loadClassByDeployment(f.getName(), zipEntryName.replace(File.separatorChar, '.'));
				}  
			} 
		}
		catch(Exception e){
			System.out.println("Exception in JarLoadClassManager in loadClassByDeploymentNameSet");
			e.printStackTrace();
		}
	}

}
