package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;


import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;



abstract public class VFSManager extends AbstractVFSManager{

	protected HashMap<String, HashSet<String>> xmlDependencyInfoMap;

	
	public VFSManager(String rootDir){
		super(rootDir);
		new PollingThread().start();
	}
	
	protected class PollingThread extends Thread{
		@Override
		public void run(){
			try{
				while(MiddleWareConfig.getInstance().getIsRunning()){
					Thread.sleep(MiddleWareConfig.getInstance().getCurPollingTime());
					
					long t1 = System.nanoTime();
					
					System.out.println("The time is: " + new Date().toString() + " and scan is start!");
					MiddleWareMain.application.addTextAreaConsole("The time is: " + new Date().toString() + " and scan is start!");
					
					// get the remove file names
					HashSet<String> rmvSet = getRemoveFileName();
					// remove action if the file does not exsist
					removeAction(rmvSet);
									
					// get the update file names
					HashSet<String> updateSet = getUpdateFileName();
					// update action if the file updates 
					updateAction(updateSet);  // addAction in the updateAction
					
					// reconstruct dependency!!!!  
					if (updateSet.size()!=0){
						reconstructDependency(updateSet);
						// load the new version class
						//LoadClassManager.getInstance().loadClassByDeploymentNameSet(updateSet);
						
						if (((DependencyManager)MiddleWareConfig.getInstance().getDepManager()).isCircleDependency(xmlDependencyInfoMap) 
								&& MiddleWareConfig.getInstance().getCurClassLoaderWay() != MiddleWareConfig.CIRCLE_EXT_RECONSTRUCTION_CLASSLOADER){
							System.out.println("Warning!!! The circle exists in the dependency!!\n Please use CircleExtReconstructionClassLoader!");
							MiddleWareMain.application.addTextAreaConsole("Warning!!! The circle exists in the dependency!!\n Please use CircleExtReconstructionClassLoader!");
						}
						
						// execute	
						updateExcuteBySet(updateSet);
						
					}									
					
					/*
					if (updateSet.size() != 0){
						MiddleWareMain.executeMainMethodByNode("A3");
					}
					*/
					
					System.out.println("The time is: " + new Date().toString() + " and scan is over!");
					MiddleWareMain.application.addTextAreaConsole("The time is: " + new Date().toString() + " and scan is over!");
					System.out.println("Update Time Circle: " + (System.nanoTime() - t1));
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
	//abstract protected HashSet<String> readInfoByXMLFile(String filePathName, String tagInfo);	
	
	
	//*************  important  *********************************
	// reconstruct the dependency before the load class and copy the main class's class loader
	protected void reconstructDependency(HashSet<String> newSet){
		HashSet<String> alreadySet = new HashSet<String>();
		for (String dependencyName: newSet){
			reconstructDependencyByName(dependencyName, newSet, alreadySet);
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
	protected void reconstructDependencyByName(String dependencyName, HashSet<String> newSet, HashSet<String> alreadySet){
		for (String nodeName: xmlDependencyInfoMap.keySet()){
			for (String depName: xmlDependencyInfoMap.get(nodeName)){
				if (depName.equals(dependencyName)){
					((DependencyManager)MiddleWareConfig.getInstance().getDepManager()).reconstructDependencyByTwoNode(nodeName, depName);
					if (!newSet.contains(nodeName) && !alreadySet.contains(nodeName)){
						alreadySet.add(nodeName);
						reconstructDependencyByName(nodeName, newSet, alreadySet);   // recursion for reconstructing dependency
					}
					
				}
			}
		}
	}
	
	
	// the action that vfs remove the file 
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
		((DependencyManager)MiddleWareConfig.getInstance().getDepManager()).modifyDeploymentNodeByRmvDepMap(rmvDepMap);		
	}
		
	// get the file that disappear in the OSGI-TEST-DEPLOYMENT
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
		
	}
		
	// the action that vfs update the file
	protected void updateAction(HashSet<String> updateSet){
		// first remove the old version
		removeAction(updateSet);
		// second add the new version
		// add the add file names in the update set
		updateSet.addAll(getAddFileName());				
		// add action if the file adds and update		
		addAction(updateSet);		
	}
		
		
	//  get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	@Override
	protected HashSet<String> getAllChangedFileName(){
		return null;
	}
	
		
	// get the update File in the OSGI-TEST-DEPLOYMENT
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
	protected HashSet<String> getAddFileName(){
		HashSet<String> addFileSet = getAllChangedFileName();
		addFileSet.removeAll(getUpdateFileName());   // remove update file set and the rest is add file set
		return addFileSet;
	}
	
}
