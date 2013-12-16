package nju.moon.lhc.reconstructon.main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

import nju.moon.lhc.reconstruction.DependencyManager;
import nju.moon.lhc.reconstruction.LoadClassManager;
import nju.moon.lhc.reconstruction.VFSManager;

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
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
		// TODO Auto-generated method stub
		VFSManager.getInstance();
		DependencyManager.getInstance().createDependencyGraph();
		//LoadClassManager.getInstance().loadAllClass();
		//executeMainMethod("A-JAR", "NodeAMain.class");
		executeAllMainMethode();
	}
	
	
	public static void executeAllMainMethode(){
		HashMap<String, HashSet<String>> mainClassMap = VFSManager.getInstance().getXMLMainClassInfoMap();
		for (String nodeName: mainClassMap.keySet()){
			for (String className: mainClassMap.get(nodeName)){
				executeMainMethod(nodeName, className);
			}
		}
	}
	
	public static void executeMainMethodByNode(String nodeName){
		HashMap<String, HashSet<String>> mainClassMap = VFSManager.getInstance().getXMLMainClassInfoMap();
		for (String className: mainClassMap.get(nodeName)){
			executeMainMethod(nodeName, className);
		}
	}
	
	public static void executeMainMethod(String nodeName, String className){
		Class<?> clazz = LoadClassManager.getInstance().loadClassByDeployment(nodeName, className);
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
