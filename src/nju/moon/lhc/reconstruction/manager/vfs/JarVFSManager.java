package nju.moon.lhc.reconstruction.manager.vfs;

import java.util.HashMap;
import java.util.HashSet;

public class JarVFSManager extends VFSManager {

	public JarVFSManager(){
		super();
	}
	
	@Override
	protected void initDeploymentSet() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readAllXMLInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	protected HashSet<String> readInfoByXMLFile(String filePathName,
			String tagInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void removeAction(HashSet<String> rmvSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected HashSet<String> getRemoveFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashMap<String, HashSet<String>> getRemoveDependency(
			HashSet<String> rmvSet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addAction(HashSet<String> addSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateAction(HashSet<String> updateSet) {
		// TODO Auto-generated method stub

	}

	@Override
	protected HashSet<String> getAllChangedFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashSet<String> getUpdateFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected HashSet<String> getAddFileName() {
		// TODO Auto-generated method stub
		return null;
	}

}
