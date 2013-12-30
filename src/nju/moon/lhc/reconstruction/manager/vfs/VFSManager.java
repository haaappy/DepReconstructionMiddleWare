package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;



abstract public class VFSManager {

	public static final String ROOT_DIR = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	
	protected HashSet<File> deploymentSet;
	protected HashMap<String, HashSet<String>> xmlDependencyInfoMap;
	protected HashMap<String, HashSet<String>> xmlMainClassInfoMap;
	
	
//	private static class SingletonHolder{
//		private static final VFSManager INSTANCE = new VFSManager();
//	}
//	
//	public static final VFSManager getInstance(){
//		return SingletonHolder.INSTANCE;
//	}
	
	public VFSManager(){
		initDeploymentSet();
		readAllXMLInfo();
		new PollingThread().start();
	}
	
	protected class PollingThread extends Thread{
		@Override
		public void run(){
			try{
				while(MiddleWareConfig.getInstance().getIsRunning()){
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
						for (String mainNodeName: XMLReader.readInfoByXMLFile(ROOT_DIR + nodeName + "/" + nodeName + ".xml", XMLFinalField.INVOKE_MAIN_NODE)){
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
	
	abstract protected void initDeploymentSet();
	
	abstract protected void readAllXMLInfo();
	
	// if there is not xml file in it, return hash set which size is 0
	abstract protected HashSet<String> readInfoByXMLFile(String filePathName, String tagInfo);	
	
	
	//*************  important  *********************************
	// reconstruct the dependency before the load class and copy the main class's class loader
	protected void reconstructDependency(HashSet<String> newSet){
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
	protected void reconstructDependencyByName(String dependencyName){
		for (String nodeName: xmlDependencyInfoMap.keySet()){
			for (String depName: xmlDependencyInfoMap.get(nodeName)){
				if (depName.equals(dependencyName)){
					MiddleWareConfig.getInstance().getDepManager().reconstructDependencyByTwoNode(nodeName, depName);
					reconstructDependencyByName(nodeName);   // recursion for reconstructing dependency
				}
			}
		}
	}
	
	
	// the action that vfs remove the file 
	abstract protected void removeAction(HashSet<String> rmvSet);
	
	// get the file that disappear in the OSGI-TEST-DEPLOYMENT
	abstract protected HashSet<String> getRemoveFileName();
	
	// get the remove dependency from xmlInfoMap
	abstract protected HashMap<String, HashSet<String>> getRemoveDependency(HashSet<String> rmvSet);
	
	// the action that vfs add the file
	abstract protected void addAction(HashSet<String> addSet);
		
	// the action that vfs update the file
	abstract protected void updateAction(HashSet<String> updateSet);
	
	
	// get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	abstract protected HashSet<String> getAllChangedFileName();
	
	// get the update File in the OSGI-TEST-DEPLOYMENT
	abstract protected HashSet<String> getUpdateFileName();
		
	// get the add File in the OSGI-TEST-DEPLOYMENT
	abstract protected HashSet<String> getAddFileName();
	
	
//	// if there is not xml file in it, return hash set which size is 0
//	private HashSet<String> readDependencyXMLFile(String filePathName){
//		return readInfoByXMLFile(filePathName, "Dependency");
//	}
//	
//	// if there is not xml file in it, return hash set which size is 0
//	private HashSet<String> readMainClassXMLFile(String filePathName){
//		return readInfoByXMLFile(filePathName, "MainClass");
//	}
//	
//	// if there is not xml file in it, return hash set which size is 0
//	private HashSet<String> readInvokeNodeXMLFile(String filePathName){
//		return readInfoByXMLFile(filePathName, "InvokeMainNode");
//	}
}
