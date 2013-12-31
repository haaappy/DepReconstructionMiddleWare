package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class NormalVFSManager extends VFSManager {
	
	public NormalVFSManager(String rootDir){
		super(rootDir);
	}
	
	@Override
	protected void initDeploymentSet(){
		deploymentSet = new HashSet<File>();
		File file = new File(rootDir);
		for (File f: file.listFiles()){
			if (f.isDirectory()){
				deploymentSet.add(f);
			}
		}	
	}
	
	@Override
	protected void readAllXMLInfo(){
		xmlDependencyInfoMap = new HashMap<String, HashSet<String>>();
		xmlMainClassInfoMap = new HashMap<String, HashSet<String>>();
		for (File f: deploymentSet){
			String filePathName = rootDir + f.getName() + "/" + f.getName() + ".xml";
			// HashSet<String> dependencySet = readDependencyXMLFile(filePathName);
			xmlDependencyInfoMap.put(f.getName(), XMLReader.readInfoByXMLFile(filePathName, XMLFinalField.DEPENDENCY));
			xmlMainClassInfoMap.put(f.getName(), XMLReader.readInfoByXMLFile(filePathName, XMLFinalField.MAIN_ClASS));
		}
	}

	// get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	@Override
	protected HashSet<String> getAllChangedFileName(){
		File file = new File(rootDir);
		File[] files = file.listFiles();
		HashSet<String> changedFileSet = new HashSet<String>();
		for (File f: files){
			if (f.isDirectory() && f.lastModified() + 6000 > System.currentTimeMillis()){
				changedFileSet.add(f.getName());
			}
		}
		return changedFileSet;
	}
	
	
	// the action that vfs add the file
	@Override
	protected void addAction(HashSet<String> addSet){
		// add the 'deploymentSet' and the 'xmlDependencyInfoMap', 'xmlMainClassInfoMap' and the deploymentNode
		HashMap<String, HashSet<String>> addDepMap = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> addMainMap = new HashMap<String, HashSet<String>>();
		
		for (String fileName: addSet){
			deploymentSet.add(new File(rootDir + fileName));
			String xmlFilePathName = rootDir + fileName + "/" + fileName + ".xml";
			HashSet<String> parentSet = XMLReader.readInfoByXMLFile(xmlFilePathName, XMLFinalField.DEPENDENCY);
			addDepMap.put(fileName, parentSet);
			HashSet<String> mainSet = XMLReader.readInfoByXMLFile(xmlFilePathName, XMLFinalField.MAIN_ClASS);
			addMainMap.put(fileName, mainSet);
		}
		xmlDependencyInfoMap.putAll(addDepMap);
		xmlMainClassInfoMap.putAll(addMainMap);
		// add the new nodeMap and the dependency
		MiddleWareConfig.getInstance().getDepManager().addDeploymentNodeByAddDepMap(addDepMap);			
	}
}
