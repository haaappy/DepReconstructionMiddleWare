package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.AdaptDependencyManager;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

public class AdaptJarVFSManager extends AdaptVFSManager {

	public AdaptJarVFSManager(String rootDir) {
		super(rootDir);
	}

	@Override
	public void initDeploymentSet() {
		deploymentSet = new HashSet<File>();
		File file = new File(rootDir);
		for (File f: file.listFiles()){
			if (f.getName().lastIndexOf(".jar") > 0){
				deploymentSet.add(f);
			}
		}
	}

	@Override
	public void readAllXMLInfo() {
		//xmlDependencyInfoMap = new HashMap<String, HashSet<String>>();
		xmlMainClassInfoMap = new HashMap<String, HashSet<String>>();
		for (File f: deploymentSet){
			String jarName = f.getName();
			if (jarName.lastIndexOf(".jar") > 0){
				String filePathName = jarName.substring(0, jarName.lastIndexOf(".jar")) + ".xml";
				//xmlDependencyInfoMap.put(f.getName(), XMLReader.readInfoByXMLFileFromJar(f.getPath(), filePathName, XMLFinalField.DEPENDENCY));
				xmlMainClassInfoMap.put(f.getName(), XMLReader.readInfoByXMLFileFromJar(f.getPath(), filePathName, XMLFinalField.MAIN_ClASS));
			}
		}
	}

	@Override
	protected void addAction(HashSet<String> addSet) {
		// TODO Auto-generated method stub
		// add the 'deploymentSet' and the 'xmlDependencyInfoMap', 'xmlMainClassInfoMap' and the deploymentNode
		//HashMap<String, HashSet<String>> addDepMap = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> addMainMap = new HashMap<String, HashSet<String>>();
		
		for (String fileName: addSet){
			deploymentSet.add(new File(rootDir + fileName));
			
			String filePathName = fileName.substring(0, fileName.lastIndexOf(".jar")) + ".xml";
			//HashSet<String> parentSet = XMLReader.readInfoByXMLFileFromJar(rootDir + fileName, filePathName, XMLFinalField.DEPENDENCY);
			//addDepMap.put(fileName, parentSet);
			HashSet<String> mainSet = XMLReader.readInfoByXMLFileFromJar(rootDir + fileName, filePathName, XMLFinalField.MAIN_ClASS);
			addMainMap.put(fileName, mainSet);
			
		}
		//xmlDependencyInfoMap.putAll(addDepMap);
		xmlMainClassInfoMap.putAll(addMainMap);
		// add the new nodeMap and the dependency
		
		// TODO do sth about add action between DepManager
		//MiddleWareConfig.getInstance().getDepManager().addDeploymentNodeByAddDepMap(addDepMap);			
		super.addAction(addSet);
	}


	@Override
	protected HashSet<String> getAllChangedFileName() {
		File file = new File(rootDir);
		File[] files = file.listFiles();
		HashSet<String> changedFileSet = new HashSet<String>();
		for (File f: files){
			if (!f.isDirectory() && f.getName().indexOf(".jar")>0 && 
					f.lastModified() + MiddleWareConfig.getInstance().getCurPollingTime() > System.currentTimeMillis()){
				changedFileSet.add(f.getName());
			}
		}
		return changedFileSet;
	}


}
