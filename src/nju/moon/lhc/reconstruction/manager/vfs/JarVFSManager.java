package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

public class JarVFSManager extends VFSManager {

	public JarVFSManager(String rootDir){
		super(rootDir);
	}
	
	@Override
	protected void initDeploymentSet() {
		// TODO Auto-generated method stub
		deploymentSet = new HashSet<File>();
		File file = new File(rootDir);
		for (File f: file.listFiles()){
			if (f.getName().lastIndexOf(".jar") > 0){
				deploymentSet.add(f);
			}
		}	
	}

	@Override
	protected void readAllXMLInfo() {
		// TODO Auto-generated method stub
		xmlDependencyInfoMap = new HashMap<String, HashSet<String>>();
		xmlMainClassInfoMap = new HashMap<String, HashSet<String>>();
		for (File f: deploymentSet){
			String jarName = f.getName();
			if (jarName.lastIndexOf(".jar") > 0){
				String filePathName = jarName.substring(0, jarName.lastIndexOf(".jar")) + ".xml";
				xmlDependencyInfoMap.put(f.getName(), XMLReader.readInfoByXMLFileFromJar(f.getPath(), filePathName, XMLFinalField.DEPENDENCY));
				xmlMainClassInfoMap.put(f.getName(), XMLReader.readInfoByXMLFileFromJar(f.getPath(), filePathName, XMLFinalField.MAIN_ClASS));
			}
		}
	}


	@Override
	protected void removeAction(HashSet<String> rmvSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected HashSet<String> getRemoveFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, HashSet<String>> getRemoveDependency(
			HashSet<String> rmvSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addAction(HashSet<String> addSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateAction(HashSet<String> updateSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected HashSet<String> getAllChangedFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashSet<String> getUpdateFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashSet<String> getAddFileName() {
		// TODO Auto-generated method stub
		return null;
	}

}
