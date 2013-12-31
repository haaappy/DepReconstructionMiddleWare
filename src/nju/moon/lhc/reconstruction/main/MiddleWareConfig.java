package nju.moon.lhc.reconstruction.main;

import nju.moon.lhc.reconstruction.classloader.ExtReconstructionClassLoader;
import nju.moon.lhc.reconstruction.classloader.ReconstructionClassLoader;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.manager.loadclass.JarLoadClassManager;
import nju.moon.lhc.reconstruction.manager.loadclass.LoadClassManager;
import nju.moon.lhc.reconstruction.manager.loadclass.NormalLoadClassManager;
import nju.moon.lhc.reconstruction.manager.vfs.JarVFSManager;
import nju.moon.lhc.reconstruction.manager.vfs.NormalVFSManager;
import nju.moon.lhc.reconstruction.manager.vfs.VFSManager;
import nju.moon.lhc.reconstruction.util.XMLReader;


public class MiddleWareConfig {

	private String curMiddleWareHome;
	private int curDeploymentWay;
	private int curClassLoader;
	private String defaultMiddleWareHome;
	private int defaultDeploymentWay;
	private int DefaultClassLoader;
	
	private boolean isRunning;
	
	public static final String DEFAULT_HOME = "/home/happy/JBOSS/OSGI-TEST-DEPLOYMENT/";
	public static final int DEPLOY_WAY_NORMAL = 1;
	public static final int DEPLOY_WAY_JAR = 2;
	public static final int RECONSTRUCTION_CLASSLOADER = 3;
	public static final int EXT_RECONSTRUCTION_CLASSLOADER = 4;
	
	
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
		String[] configResults = XMLReader.readAllInfoByConfigXMLFile(curHome + "config.xml");
		if (configResults != null && configResults.length == 6){
			setCurMiddleWareHome(configResults[0]);
			setCurDeploymentWayByStr(configResults[1]);
			setCurClassLoaderByStr(configResults[2]);
			setDefaultMiddleWareHome(configResults[3]);
			setDefaultDeploymentWayByStr(configResults[4]);
			setDefaultClassLoaderByStr(configResults[5]);
			
			setManagersByCurDeploymentWay(configResults[1]);
			
			setIsRunning(true);
			// TODO  the managers need to change 
			// the way deals with how to new the object
			
			// TODO how about isRunning??
		
			
			
		}
		else{
			System.out.println("reading config.xml is error in MiddleWareConfig!!");
		}
	}

	private void setManagersByCurDeploymentWay(String way) {
		setDepManager(new DependencyManager());
		if (way.equals("Normal")){
			setLcManager(new NormalLoadClassManager());
			setVfsManager(new NormalVFSManager(curMiddleWareHome));
		}
		else if (way.equals("Jar")){
			setLcManager(new JarLoadClassManager());
			setVfsManager(new JarVFSManager(curMiddleWareHome));
		}
		else{
			System.out.println("Error in initConfig in setManagersByCurDeploymentWay. ");		
			setLcManager(new NormalLoadClassManager());
			setVfsManager(new NormalVFSManager(curMiddleWareHome));
		}
		
	}
	
	public ReconstructionClassLoader createClassLoaderByName(String nodeName){
		if (curClassLoader == MiddleWareConfig.RECONSTRUCTION_CLASSLOADER){
			return new ReconstructionClassLoader(curMiddleWareHome, nodeName);
		}
		else if (curClassLoader == MiddleWareConfig.EXT_RECONSTRUCTION_CLASSLOADER){
			return new ExtReconstructionClassLoader(curMiddleWareHome, nodeName);
		}
		else{
			System.out.println("Error in createClassLoaderByName");
			return new ReconstructionClassLoader(curMiddleWareHome, nodeName);
		}
	}
	
	public ReconstructionClassLoader createClassLoaderByCl(ReconstructionClassLoader cl){
		if (curClassLoader == MiddleWareConfig.RECONSTRUCTION_CLASSLOADER){
			return new ReconstructionClassLoader(cl);
		}
		else if (curClassLoader == MiddleWareConfig.EXT_RECONSTRUCTION_CLASSLOADER){
			return new ExtReconstructionClassLoader(cl);
		}
		else{
			System.out.println("Error in createClassLoaderByCl");
			return new ReconstructionClassLoader(cl);
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

	public int getCurClassLoaderWay() {
		return curClassLoader;
	}

	public void setCurClassLoaderWay(int curClassLoaderWay) {
		this.curClassLoader = curClassLoaderWay;
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

	public int getDefaultClassLoaderWay() {
		return DefaultClassLoader;
	}

	public void setDefaultClassLoaderWay(int defaultClassLoaderWay) {
		DefaultClassLoader = defaultClassLoaderWay;
	}

	public boolean getIsRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	
	private void setCurDeploymentWayByStr(String str){
		if (str.equals("Normal")){
			setCurDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
		}
		else if (str.equals("Jar")){
			setCurDeploymentWay(MiddleWareConfig.DEPLOY_WAY_JAR);
		}
		else{
			System.out.println("Error in initConfig in setCurDeploymentWayByStr. ");
			setCurDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
		}
	}
	
	private void setCurClassLoaderByStr(String str){
		if (str.equals("ReconstructionClassLoader")){
			setCurClassLoaderWay(MiddleWareConfig.RECONSTRUCTION_CLASSLOADER);
		}
		else if (str.equals("ExtReconstructionClassLoader")){
			setCurClassLoaderWay(MiddleWareConfig.EXT_RECONSTRUCTION_CLASSLOADER);
		}
		else{
			System.out.println("Error in initConfig in setCurClassLoaderByStr. ");
			setCurClassLoaderWay(MiddleWareConfig.RECONSTRUCTION_CLASSLOADER);
		}
	}
	
	private void setDefaultDeploymentWayByStr(String str){
		if (str.equals("Normal")){
			setDefaultDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
		}
		else if (str.equals("Jar")){
			setDefaultDeploymentWay(MiddleWareConfig.DEPLOY_WAY_JAR);
		}
		else{
			System.out.println("Error in initConfig in setDefaultDeploymentWayByStr. ");
			setDefaultDeploymentWay(MiddleWareConfig.DEPLOY_WAY_NORMAL);
		}
	}
	
	private void setDefaultClassLoaderByStr(String str){
		if (str.equals("ReconstructionClassLoader")){
			setDefaultClassLoaderWay(MiddleWareConfig.RECONSTRUCTION_CLASSLOADER);
		}
		else if (str.equals("ExtReconstructionClassLoader")){
			setDefaultClassLoaderWay(MiddleWareConfig.EXT_RECONSTRUCTION_CLASSLOADER);
		}
		else{
			System.out.println("Error in initConfig in setDefaultClassLoaderByStr. ");
			setDefaultClassLoaderWay(MiddleWareConfig.RECONSTRUCTION_CLASSLOADER);
		}
	}

	public DependencyManager getDepManager() {
		return depManager;
	}

	public void setDepManager(DependencyManager depManager) {
		this.depManager = depManager;
	}

	public LoadClassManager getLcManager() {
		return lcManager;
	}

	public void setLcManager(LoadClassManager lcManager) {
		this.lcManager = lcManager;
	}

	public VFSManager getVfsManager() {
		return vfsManager;
	}

	public void setVfsManager(VFSManager vfsManager) {
		this.vfsManager = vfsManager;
	}

	
}
