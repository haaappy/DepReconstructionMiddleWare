package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

import nju.moon.lhc.reconstruction.manager.dependency.AdaptDependencyManager;

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
					// get the update file names
					HashSet<String> updateSet = getUpdateFileName();
					
					rmvSet.addAll(updateSet);
					
					// remove action if the file does not exsist
					removeAction(rmvSet);   // include the update old version
					
					
					// update action if the file updates 
					updateAction(updateSet);  // addAction in the updateAction
					
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
	
		// *** important to reconstruct  ****
		((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).reconstructDependency(rmvSet);
		
	}
		
		
	// the action that vfs add the file
	@Override
	protected void addAction(HashSet<String> addSet){
		((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).addNewDeploymentNode(addSet);
	}
		
	// the action that vfs update the file
	@Override
	protected void updateAction(HashSet<String> updateSet){
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

	
}
