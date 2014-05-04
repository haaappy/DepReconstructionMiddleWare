package nju.moon.lhc.reconstruction.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import org.jdesktop.application.Application;

import nju.moon.lhc.reconstruction.gui.DepRestructionMiddleWareApplication;
import nju.moon.lhc.reconstruction.manager.dependency.DependencyManager;
import nju.moon.lhc.reconstruction.manager.loadclass.AdaptLoadClassManager;
import nju.moon.lhc.reconstruction.manager.loadclass.LoadClassManager;
import nju.moon.lhc.reconstruction.manager.vfs.VFSManager;

public class MiddleWareMain {

	/**
	 * @param args
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	
	public static DepRestructionMiddleWareApplication application = null;
	
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
		if (args != null && args.length == 1){
			MiddleWareConfig.getNewInstance(args[0]);
		}
		else{
			MiddleWareConfig.getNewInstance(MiddleWareConfig.DEFAULT_HOME);
		}		
		
		// TODO different start
		if (MiddleWareConfig.getInstance().getCurClassLoaderWay() == MiddleWareConfig.ADAPT_DEP_CLASSLOADER){
			((AdaptLoadClassManager)MiddleWareConfig.getInstance().getLcManager()).initAdaptLoadClassManager();		
		}
		else{
			((DependencyManager)MiddleWareConfig.getInstance().getDepManager()).createDependencyGraph();
		}
		
		executeAllMainMethode();
	}
	
	public static void stop(){
		MiddleWareConfig.getInstance().setIsRunning(false);
	}
	
	public static void setApplication(DepRestructionMiddleWareApplication app){
		application = app;
	}
	
	
	public static void executeAllMainMethode(){
		HashMap<String, HashSet<String>> mainClassMap = MiddleWareConfig.getInstance().getVfsManager().getXMLMainClassInfoMap();
		for (String nodeName: mainClassMap.keySet()){
			for (String className: mainClassMap.get(nodeName)){
				executeMainMethod(nodeName, className);
			}
		}
	}
	
	public static void executeMainMethodByNode(String nodeName){
		HashMap<String, HashSet<String>> mainClassMap = MiddleWareConfig.getInstance().getVfsManager().getXMLMainClassInfoMap();
		for (String className: mainClassMap.get(nodeName)){
			executeMainMethod(nodeName, className);
		}
	}
	
	public static void executeMainMethod(String nodeName, String className){
		Class<?> clazz = MiddleWareConfig.getInstance().getLcManager().loadClassByDeployment(nodeName, className);
		try{
			Method method = clazz.getMethod("main", String[].class);	
			method.invoke(null, (Object)new String[]{});
		}
		catch(Exception e){
			System.out.println("Main method invoke is error!");
			e.printStackTrace();
		}
		
	}

}
