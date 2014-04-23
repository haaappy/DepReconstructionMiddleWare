package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

public abstract class AdaptVFSManager extends AbstractVFSManager{
	
	public AdaptVFSManager(String rootDir){
		super(rootDir);
		new PollingThread().start();
	}
	
	protected class PollingThread extends Thread{
		@Override
		public void run(){
			try{
				while(MiddleWareConfig.getInstance().getIsRunning()){
					Thread.sleep(MiddleWareConfig.getInstance().getCurPollingTime());
					
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
					
					
					
					
			/*		
					// reconstruct dependency!!!!  
					reconstructDependency(updateSet);					
					// load the new version class
					//LoadClassManager.getInstance().loadClassByDeploymentNameSet(updateSet);
					
					
					
					if (MiddleWareConfig.getInstance().getDepManager().isCircleDependency(xmlDependencyInfoMap)){
						System.out.println("Warning!!! The circle exists in the dependency!!\n Please use CircleExtReconstructionClassLoader!");
						MiddleWareMain.application.addTextAreaConsole("Warning!!! The circle exists in the dependency!!\n Please use CircleExtReconstructionClassLoader!");
					}
					
		    */	
					
					// execute
					for (String nodeName: updateSet){
						for (String mainNodeName: XMLReader.readInfoByXMLFile(rootDir + nodeName + "/" + nodeName + ".xml", XMLFinalField.INVOKE_MAIN_NODE)){
							MiddleWareMain.executeMainMethodByNode(mainNodeName);
						}	
					}
					
					System.out.println("The time is: " + new Date().toString() + " and scan is over!");
					MiddleWareMain.application.addTextAreaConsole("The time is: " + new Date().toString() + " and scan is over!");
				}
			}
			catch(Exception e){
				e.printStackTrace();		
			}
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
	
		/*
		// TODO if remove sth in the depleymentSet
		// get the remove dependency
		HashMap<String, HashSet<String>> rmvDepMap = getRemoveDependency(rmvSet);
		// remove the 'xmlDependencyInfoMap' and 'xmlMainClassInfoMap'
		for (String rmvDepName: rmvDepMap.keySet()){
			//xmlDependencyInfoMap.remove(rmvDepName);
			xmlMainClassInfoMap.remove(rmvDepName);
		}
		// rmvDepList  ==>   key: A    value  [B, C]     remove the child named A in  B and C   finally remove A
		MiddleWareConfig.getInstance().getDepManager().modifyDeploymentNodeByRmvDepMap(rmvDepMap);		
	*/
	// TODO do the remove things about adpatDepManager
	
	}
	
	/*
	// get the remove dependency from xmlInfoMap
	protected HashMap<String, HashSet<String>> getRemoveDependency(HashSet<String> rmvSet){
		HashMap<String, HashSet<String>> rmvDepMap = new HashMap<String, HashSet<String>>();
		for (String rmvName: rmvSet){
			rmvDepMap.put(rmvName, xmlDependencyInfoMap.get(rmvName));
		}
		return rmvDepMap;
	}
	*/
		
		
	// the action that vfs add the file
	@Override
	abstract protected void addAction(HashSet<String> addSet);
		
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
		
		
	//  get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	@Override
	abstract protected HashSet<String> getAllChangedFileName();

	
}
