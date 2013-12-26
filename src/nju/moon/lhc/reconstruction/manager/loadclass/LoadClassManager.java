package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;
import nju.moon.lhc.reconstruction.manager.vfs.VFSManager;



abstract public class LoadClassManager {
//	private static class SingletonHolder{
//		private static final LoadClassManager INSTANCE = new LoadClassManager();
//	}
//	
//	public static final LoadClassManager getInstance(){
//		return SingletonHolder.INSTANCE;
//	}
	
	public LoadClassManager(){
		
	}
	
	abstract public void loadAllClass();
	
	abstract public Class<?> loadClassByDeployment(String deploymentName, String className);
	
	abstract public void loadClassByDeploymentNameSet(HashSet<String> nameSet);
}
