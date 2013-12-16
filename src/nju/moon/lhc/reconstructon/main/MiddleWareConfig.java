package nju.moon.lhc.reconstructon.main;

import nju.moon.lhc.reconstruction.DependencyManager;
import nju.moon.lhc.reconstruction.LoadClassManager;
import nju.moon.lhc.reconstruction.VFSManager;


public class MiddleWareConfig {

	private String curMiddleWareHome;
	private int curDeploymentWay;
	private String defaultMiddleWareHome;
	private int defaultDeploymentWay;
	
	private boolean isRunning;
	
	private static final String DEFAULT_HOME = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	private static final int DEPLOY_WAY_NORMAL = 1;
	private static final int DEPLOY_WAY_JAR = 2;
	
	
	private DependencyManager depManager;
	private LoadClassManager lcManager;
	private VFSManager vfsManager;
			
	private volatile static MiddleWareConfig singleton;

	public static MiddleWareConfig getInstance(){
		if(singleton == null){
			synchronized(MiddleWareConfig.class){
				if(singleton == null){
					singleton = new MiddleWareConfig();
				}
			}
		}
		return singleton;
	}
	
	public static MiddleWareConfig getNewInstance(String curHome){
		if(singleton == null){
			synchronized(MiddleWareConfig.class){
				if(singleton == null){
					singleton = new MiddleWareConfig(curHome);
				}
			}
		}
		else{
			singleton = new MiddleWareConfig(curHome);
		}
		return singleton;
	}
	
	private MiddleWareConfig(){
		// use default
		depManager = new DependencyManager();
		lcManager = new LoadClassManager();
		vfsManager = new VFSManager();
	}
	
	private MiddleWareConfig(String curHome){
		//TODO read the config.xml in curHome
		//TODO set the home and way
		// the way deals with how to new the object
		depManager = new DependencyManager();
		lcManager = new LoadClassManager();
		vfsManager = new VFSManager();
	}
}
