package nju.moon.lhc.reconstruction;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import nju.moon.lhc.reconstructon.main.MiddleWareMain;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



public class VFSManager {

	public static final String ROOT_DIR = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	
	private HashSet<File> deploymentSet;
	private HashMap<String, HashSet<String>> xmlDependencyInfoMap;
	private HashMap<String, HashSet<String>> xmlMainClassInfoMap;
	
	
	private static class SingletonHolder{
		private static final VFSManager INSTANCE = new VFSManager();
	}
	
	public static final VFSManager getInstance(){
		return SingletonHolder.INSTANCE;
	}
	
	public VFSManager(){
		initDeploymentSet();
		readAllXMLInfo();
		new PollingThread().start();
	}
	
	private class PollingThread extends Thread{
		@Override
		public void run(){
			try{
				while(true){
					Thread.sleep(5000);
					
					System.out.println("The time is: " + new Date().toString() + " and scan is start!");
					
					// get the remove file names
					HashSet<String> rmvSet = getRemoveFileName();
					// remove action if the file does not exsist
					removeAction(rmvSet);
									
					// get the update file names
					HashSet<String> updateSet = getUpdateFileName();
					// update action if the file updates 
					updateAction(updateSet);  // addAction in the updateAction
					
					// reconstruct dependency!!!!  
					reconstructDependency(updateSet);					
					// load the new version class
					//LoadClassManager.getInstance().loadClassByDeploymentNameSet(updateSet);
					// execute
					for (String nodeName: updateSet){
						for (String mainNodeName: readInvokeNodeXMLFile(ROOT_DIR + nodeName + "/" + nodeName + ".xml")){
							MiddleWareMain.executeMainMethodByNode(mainNodeName);
						}	
					}
					
					System.out.println("The time is: " + new Date().toString() + " and scan is over!");
				}
			}
			catch(Exception e){
				e.printStackTrace();		
			}
		}
		

	}
	
	public HashMap<String, HashSet<String>> getXMLDependencyInfoMap(){
		return xmlDependencyInfoMap;
	}
	
	public HashMap<String, HashSet<String>> getXMLMainClassInfoMap(){
		return xmlMainClassInfoMap;
	}
	
	public HashSet<File> getDeploymentSet(){
		return deploymentSet;
	}
	
	private void initDeploymentSet(){
		deploymentSet = new HashSet<File>();
		File file = new File(ROOT_DIR);
		for (File f: file.listFiles()){
			if (f.isDirectory()){
				deploymentSet.add(f);
			}
		}	
	}
	
	private void readAllXMLInfo(){
		xmlDependencyInfoMap = new HashMap<String, HashSet<String>>();
		xmlMainClassInfoMap = new HashMap<String, HashSet<String>>();
		for (File f: deploymentSet){
			String filePathName = ROOT_DIR + f.getName() + "/" + f.getName() + ".xml";
			// HashSet<String> dependencySet = readDependencyXMLFile(filePathName);
			xmlDependencyInfoMap.put(f.getName(), readDependencyXMLFile(filePathName));
			xmlMainClassInfoMap.put(f.getName(), readMainClassXMLFile(filePathName));
		}
	}
	
	// if there is not xml file in it, return hash set which size is 0
	private HashSet<String> readInfoByXMLFile(String filePathName, String tagInfo){
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
	
	
	//*************  important  *********************************
	// reconstruct the dependency before the load class and copy the main class's class loader
	private void reconstructDependency(HashSet<String> newSet){
		for (String dependencyName: newSet){
			reconstructDependencyByName(dependencyName);
		}
		// copy the main class's class loader
//		for (String nodeName: newSet){
//			String xmlPathName = rootDir + nodeName + "/" + nodeName + ".xml";
//			HashSet<String> mainInvokeNodeSet = readInvokeNodeXMLFile(xmlPathName);
//			for (String mainNodeName: mainInvokeNodeSet){
//				DependencyManager.getInstance().reconstructMainClassLoader(mainNodeName);
//			}
//		}
		
	}
	
	// *************  important  *********************************
	// reconstruct the dependency before the load class
	public void reconstructDependencyByName(String dependencyName){
		for (String nodeName: xmlDependencyInfoMap.keySet()){
			for (String depName: xmlDependencyInfoMap.get(nodeName)){
				if (depName.equals(dependencyName)){
					DependencyManager.getInstance().reconstructDependencyByTwoNode(nodeName, depName);
					reconstructDependencyByName(nodeName);   // recursion for reconstructing dependency
				}
			}
		}
	}
	
	
	// the action that vfs remove the file 
	private void removeAction(HashSet<String> rmvSet){   	
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
		DependencyManager.getInstance().modifyDeploymentNodeByRmvDepMap(rmvDepMap);		
	}
	
	// get the file that disappear in the OSGI-TEST-DEPLOYMENT
	private HashSet<String> getRemoveFileName(){
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
	private HashMap<String, HashSet<String>> getRemoveDependency(HashSet<String> rmvSet){
		HashMap<String, HashSet<String>> rmvDepMap = new HashMap<String, HashSet<String>>();
		for (String rmvName: rmvSet){
			rmvDepMap.put(rmvName, xmlDependencyInfoMap.get(rmvName));
		}
		return rmvDepMap;
	}
	
	// the action that vfs add the file
	private void addAction(HashSet<String> addSet){
		// add the 'deploymentSet' and the 'xmlDependencyInfoMap', 'xmlMainClassInfoMap' and the deploymentNode
		HashMap<String, HashSet<String>> addDepMap = new HashMap<String, HashSet<String>>();
		HashMap<String, HashSet<String>> addMainMap = new HashMap<String, HashSet<String>>();
		
		for (String fileName: addSet){
			deploymentSet.add(new File(ROOT_DIR + fileName));
			String xmlFilePathName = ROOT_DIR + fileName + "/" + fileName + ".xml";
			HashSet<String> parentSet = readDependencyXMLFile(xmlFilePathName);
			addDepMap.put(fileName, parentSet);
			HashSet<String> mainSet = readMainClassXMLFile(xmlFilePathName);
			addMainMap.put(fileName, mainSet);
		}
		xmlDependencyInfoMap.putAll(addDepMap);
		xmlMainClassInfoMap.putAll(addMainMap);
		// add the new nodeMap and the dependency
		DependencyManager.getInstance().addDeploymentNodeByAddDepMap(addDepMap);			
	}
		
	// the action that vfs update the file
	private void updateAction(HashSet<String> updateSet){
		// first remove the old version
		removeAction(updateSet);
		// second add the new version
		// add the add file names in the update set
		updateSet.addAll(getAddFileName());				
		// add action if the file adds and update		
		addAction(updateSet);		
	}
	
	
	// get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	private HashSet<String> getAllChangedFileName(){
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
	private HashSet<String> getUpdateFileName(){
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
	private HashSet<String> getAddFileName(){
		HashSet<String> addFileSet = getAllChangedFileName();
		addFileSet.removeAll(getUpdateFileName());   // remove update file set and the rest is add file set
		return addFileSet;
	}
	
	
	// if there is not xml file in it, return hash set which size is 0
	private HashSet<String> readDependencyXMLFile(String filePathName){
		return readInfoByXMLFile(filePathName, "Dependency");
	}
	
	// if there is not xml file in it, return hash set which size is 0
	private HashSet<String> readMainClassXMLFile(String filePathName){
		return readInfoByXMLFile(filePathName, "MainClass");
	}
	
	// if there is not xml file in it, return hash set which size is 0
	private HashSet<String> readInvokeNodeXMLFile(String filePathName){
		return readInfoByXMLFile(filePathName, "InvokeMainNode");
	}
}
