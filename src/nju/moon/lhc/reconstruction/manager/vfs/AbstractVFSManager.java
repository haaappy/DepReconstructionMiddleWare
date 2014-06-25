package nju.moon.lhc.reconstruction.manager.vfs;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;



public abstract class AbstractVFSManager {
	
	protected String rootDir = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";	
	protected HashSet<File> deploymentSet;
	protected HashMap<String, HashSet<String>> xmlMainClassInfoMap;
	
	public AbstractVFSManager(String rootDir){
		this.rootDir = rootDir;
		initDeploymentSet();
		readAllXMLInfo();
	}
	
	
	abstract protected void initDeploymentSet();	
	abstract protected void readAllXMLInfo();

	
	abstract protected void removeAction(HashSet<String> rmvSet);
	abstract protected void addAction(HashSet<String> addSet);
	abstract protected void updateAction(HashSet<String> updateSet);
	
	abstract protected HashSet<String> getAllChangedFileName();
	
	abstract protected void updateExcuteBySet(HashSet<String> changeSet);
	
	
	
	public HashMap<String, HashSet<String>> getXMLMainClassInfoMap(){
		return xmlMainClassInfoMap;
	}
	
	public HashSet<File> getDeploymentSet(){
		return deploymentSet;
	}
	
	// three methods of get FileNames
	// Including add, remove and update
	
	protected HashSet<String> getAddFileName(){
		HashSet<String> addFileSet = getAllChangedFileName();
		addFileSet.removeAll(getUpdateFileName());   // remove update file set and the rest is add file set
		return addFileSet;
	}
	
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
	
}
