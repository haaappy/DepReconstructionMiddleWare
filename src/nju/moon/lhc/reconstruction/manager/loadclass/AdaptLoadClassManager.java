package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public abstract class AdaptLoadClassManager implements InterfaceLoadClassManager{

	// loading queue is to store the class file
	protected LinkedList<QueueLoadingClassInfo> loadingQueue;
	// count the loaded class and decide whether all the class are waiting
	protected int countOfLoaded;
	
	public AdaptLoadClassManager(){
		loadingQueue = new LinkedList<QueueLoadingClassInfo>();
		countOfLoaded = 0;
	}
		
	
	abstract protected void prepareQueue(HashSet<File> deploymentSet);
	abstract protected String changeClassNameFormat(String className);
	
	public void addALoadingClass(QueueLoadingClassInfo info){
		loadingQueue.push(info);
	}
	
	public void addALoadingClass(DeploymentNode node, String className){
		addALoadingClass(new QueueLoadingClassInfo(node, className));
	}
	
	public QueueLoadingClassInfo popFromQueue(){
		return loadingQueue.pop();
	}
	
	public void initAdaptLoadClassManager(HashSet<File> deploymentSet){
		prepareQueue(deploymentSet);
		tryLoadingFromQueue();
	}
	
	public void tryLoadingFromQueue(){
		// TODO main process of loading, may have bugs	
		
		while(!loadingQueue.isEmpty() && countOfLoaded == loadingQueue.size()){
			QueueLoadingClassInfo classInfo = popFromQueue();
			
			ClassLoader cl = classInfo.getDeploymentNode().getClassLoader();
			try {
				Class<?> c = cl.loadClass(classInfo.getClassName());
				if (c == null){
					addALoadingClass(classInfo);
					countOfLoaded++;
				}
				else{
					// successful loading TODO with framework
					//MiddleWareConfig.getInstance().getDepManager().addLoadedDeploymentNode(classInfo.getDeploymentNode());
					countOfLoaded = 0;
				}
			} catch (ClassNotFoundException e) {
				// not load time, put into queue and wait next time
				addALoadingClass(classInfo);
				countOfLoaded++;
			}
			
		}
		
		if (loadingQueue.isEmpty()){
			System.out.println("LoadingQueue is Empty! Classes are all loaded!");
		}
		else{
			System.out.println("LoadingQueue is not Empty! There is dependency with each other!");
		}
		
	}
	
}
