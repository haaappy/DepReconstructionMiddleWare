package nju.moon.lhc.reconstructon.main;

import nju.moon.lhc.reconstruction.DependencyManager;
import nju.moon.lhc.reconstruction.LoadClassManager;
import nju.moon.lhc.reconstruction.VFSManager;
import nju.moon.lhc.reconstructon.util.XMLReader;


public class MiddleWareConfig {

	private String curMiddleWareHome;
	private int curDeploymentWay;
	private String defaultMiddleWareHome;
	private int defaultDeploymentWay;
	
	private boolean isRunning;
	
	public static final String DEFAULT_HOME = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	public static final int DEPLOY_WAY_NORMAL = 1;
	public static final int DEPLOY_WAY_JAR = 2;
	
	
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
		initConfig(MiddleWareConfig.DEFAULT_HOME);
	}
	
	private MiddleWareConfig(String curHome){
		initConfig(curHome);	
	}
	
	private void initConfig(String curHome){
		String[] configResults = XMLReader.readAllInfoByConfigXMLFile(curHome);
		if (configResults != null && configResults.length == 4){
			setCurMiddleWareHome(configResults[0]);
			if (configResults[1].equals("Normal")){
				setCurDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
			}
			else{
				setCurDeploymentWay(MiddleWareConfig.DEPLOY_WAY_JAR);
			}
			setDefaultMiddleWareHome(configResults[2]);
			
			// TODO  the managers need to change 
			// the way deals with how to new the object
			
			// TODO how about isRunning??
			depManager = new DependencyManager();
			lcManager = new LoadClassManager();
			vfsManager = new VFSManager();
			
			if (configResults[3].endsWith("Normal")){
				setDefaultDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
			}
			else{
				setDefaultDeploymentWay(MiddleWareConfig.DEPLOY_WAY_JAR);
			}
			
		}
		else{
			System.out.println("reading config.xml is error in MiddleWareConfig!!");
		}
	}

	public String getCurMiddleWareHome() {
		return curMiddleWareHome;
	}

	public void setCurMiddleWareHome(String curMiddleWareHome) {
		this.curMiddleWareHome = curMiddleWareHome;
	}

	public int getCurDeploymentWay() {
		return curDeploymentWay;
	}

	public void setCurDeploymentWay(int curDeploymentWay) {
		this.curDeploymentWay = curDeploymentWay;
	}

	public String getDefaultMiddleWareHome() {
		return defaultMiddleWareHome;
	}

	public void setDefaultMiddleWareHome(String defaultMiddleWareHome) {
		this.defaultMiddleWareHome = defaultMiddleWareHome;
	}

	public int getDefaultDeploymentWay() {
		return defaultDeploymentWay;
	}

	public void setDefaultDeploymentWay(int defaultDeploymentWay) {
		this.defaultDeploymentWay = defaultDeploymentWay;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
}
