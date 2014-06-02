package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.classloader.AdaptDepClassLoader;
import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.main.MiddleWareMain;
import nju.moon.lhc.reconstruction.util.XMLFinalField;
import nju.moon.lhc.reconstruction.util.XMLReader;

import nju.moon.lhc.reconstruction.manager.dependency.AdaptDependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

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
					
					// get the remove file names and do action
					HashSet<String> rmvSet = getRemoveFileName();
					removeAction(rmvSet);
			
					// get the update file names and do action
					HashSet<String> updateSet = getUpdateFileName();
					updateAction(updateSet);
					
					// get the add file names and do action
					HashSet<String> addSet = getAddFileName();
					addAction(addSet);
					
					
					// execute TODO ****************  2 cases
					addSet.addAll(updateSet);
					for (String nodeName: addSet){
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
	
	
	// TODO the action that vfs remove the file 
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
	
		// delete mainMap
		for (String rmvStr: rmvSet){
			xmlMainClassInfoMap.remove(rmvStr);
		}
		
		// remove the relation and repository
		((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).removeDeploymentNodeBySet(rmvSet);
		
	}
		
		
	// the action that vfs add the file
	@Override
	protected void addAction(HashSet<String> addSet){
		((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).addNewDeploymentNodeBySet(addSet);
	}
		
	// TODO the action that vfs update the file
	@Override
	protected void updateAction(HashSet<String> updateSet){	
		if (MiddleWareConfig.getInstance().getCurClassLoaderWay() == MiddleWareConfig.ADAPT_DEP_CLASSLOADER){
			// *** important to reconstruct  ****
			((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).updateDeploymentNodeBySet(updateSet);
		}
		else{  // *** important for AdaptExtDepClassloader
			((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).validDeploymentNodeBySet(updateSet);
		}		
	}
		
		
	//  get the update or add File in the OSGI-TEST-DEPLOYMENT  (modify time is changed)
	@Override
	protected HashSet<String> getAllChangedFileName(){
		return null;
	}

	
}
