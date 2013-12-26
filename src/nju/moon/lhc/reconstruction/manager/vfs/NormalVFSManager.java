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
	
	public NormalVFSManager(){
		super();
	}
	
	@Override
	protected void initDeploymentSet(){
		deploymentSet = new HashSet<File>();
		File file = new File(ROOT_DIR);
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
			String filePathName = ROOT_DIR + f.getName() + "/" + f.getName() + ".xml";
			// HashSet<String> dependencySet = readDependencyXMLFile(filePathName);
			xmlDependencyInfoMap.put(f.getName(), XMLReader.readInfoByXMLFile(filePathName, XMLFinalField.DEPENDENCY));
			xmlMainClassInfoMap.put(f.getName(), XMLReader.readInfoByXMLFile(filePathName, XMLFinalField.MAIN_ClASS));
		}
	}
	
	// if there is not xml file in it, return hash set which size is 0
	@Override
	protected HashSet<String> readInfoByXMLFile(String filePathName, String tagInfo){
		HashSet<String> result = new HashSet<String>();
		try{		
			File xmlFile = new File(filePathName);
			if (!xmlFile.exists()){
				System.out.println("ERORR! "+ filePathName + " XML file does not exisit!");
				return result;
			}
			else{			
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				NodeList nl = doc.getElementsByTagName(tagInfo);
				for (int i=0; i<nl.getLength(); i++){
					//System.out.println(doc.getElementsByTagName("Dependency").item(i).getTextContent());
					result.add(doc.getElementsByTagName(tagInfo).item(i).getTextContent());
				}
				return result;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}	
	
	
	// the action that vfs remove the file 
	@Override
	protected void removeAction(HashSet<String> rmvSet){   	
		// remove the 'deploymetSet'
		HashSet<File> delSet = new HashSet<File>();
		for (File f: deploymentSet){
			if (rmvSet.contains(f.getName())){
				delSet.add(f);
			}
		}
		deploymentSet.removeAll(delSet);
		// get the remove dependency
		HashMap<String, HashSet<String>> rmvDepMap = getRemoveDependency(rmvSet);
		// remove the 'xmlDependencyInfoMap' and 'xmlMainClassInfoMap'
		for (String rmvDepName: rmvDepMap.keySet()){
			xmlDependencyInfoMap.remove(rmvDepName);
			xmlMainClassInfoMap.remove(rmvDepName);
		}
		// rmvDepList  ==>   key: A    value  [B, C]     remove the child named A in  B and C   finally remove A
		MiddleWareConfig.getInstance().getDepManager().modifyDeploymentNodeByRmvDepMap(rmvDepMap);		
	}
	
	// get the file that disappear in the OSGI-TEST-DEPLOYMENT
	@Override
	protected HashSet<String> getRemoveFileName(){
		HashSet<String> rmvSet = new HashSet<String>();		
		for (File f: deploymentSet){
			String path =  f.getPath();
			if (new File(path).exists() == false){
				rmvSet.add(f.getName());
			}
		}
		return rmvSet;
	}
	
	// get the remove dependency from xmlInfoMap
	@Override
	protected HashMap<String, HashSet<String>> getRemoveDependency(HashSet<String> rmvSet){
		HashMap<String, HashSet<String>> rmvDepMap = new HashMap<String, HashSet<String>>();
		for (String rmvName: rmvSet){
			rmvDepMap.put(rmvName, xmlDependencyInfoMap.get(rmvName));
		}
		return rmvDepMap;
	}
	
	// the action that vfs add the file
	@Override
	protected void addAction(HashSet<String> addSet){
		// add the 'deploymentSet' and the 'xmlDependencyInfoMap', 'xmlMainClassInfoMap' and the deploymentNode
		HashMap<String, HashSet<String>> addDepMap = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> addMainMap = new HashMap<String, HashSet<String>>();
		
		for (String fileName: addSet){
			deploymentSet.add(new File(ROOT_DIR + fileName));
			String xmlFilePathName = ROOT_DIR + fileName + "/" + fileName + ".xml";
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
		
	// the action that vfs update the file
	@Override
	protected void updateAction(HashSet<String> updateSet){
		// first remove the old version
		removeAction(updateSet);
		// second add the new version
		// add the add file names in the update set
		updateSet.addAll(getAddFileName());				
		// add action if the file adds and update		
		addAction(updateSet);		
	}
	
	
	// get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	@Override
	protected HashSet<String> getAllChangedFileName(){
		File file = new File(ROOT_DIR);
		File[] files = file.listFiles();
		HashSet<String> changedFileSet = new HashSet<String>();
		for (File f: files){
			if (f.isDirectory() && f.lastModified() + 6000 > System.currentTimeMillis()){
				changedFileSet.add(f.getName());
			}
		}
		return changedFileSet;
	}
	
	// get the update File in the OSGI-TEST-DEPLOYMENT
	@Override
	protected HashSet<String> getUpdateFileName(){
		HashSet<String> updateFileSet = new HashSet<String>();
		for (String fileName: getAllChangedFileName()){
			for (File file: deploymentSet){
				if (file.getName().equals(fileName)){
					updateFileSet.add(fileName);
				}
			}
		}
		return updateFileSet;
	}
		
	// get the add File in the OSGI-TEST-DEPLOYMENT
	@Override
	protected HashSet<String> getAddFileName(){
		HashSet<String> addFileSet = getAllChangedFileName();
		addFileSet.removeAll(getUpdateFileName());   // remove update file set and the rest is add file set
		return addFileSet;
	}

	
	
}
