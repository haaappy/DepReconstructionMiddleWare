package nju.moon.lhc.reconstruction.manager.loadclass;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.AdaptDependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public abstract class AdaptLoadClassManager implements InterfaceLoadClassManager{

	// loading queue is to store the class file
	protected LinkedList<QueueLoadingClassInfo> loadingQueue;
	
	
	public AdaptLoadClassManager(){
		loadingQueue = new LinkedList<QueueLoadingClassInfo>();
	}
		
	
	abstract protected void prepareQueue(HashSet<File> deploymentSet);
	abstract protected String changeClassNameFormat(String className);
	abstract protected void prepareAdeploymentByFile(File f);
	
	public void addALoadingClass(QueueLoadingClassInfo info){
		loadingQueue.push(info);
	}
	
	public void addALoadingClass(DeploymentNode node, String className){
		addALoadingClass(new QueueLoadingClassInfo(node, className));
	}
	
	public QueueLoadingClassInfo popFromQueue(){
		return loadingQueue.pop();
	}
	
	public void initAdaptLoadClassManager(){
		HashSet<File> deploymentSet = MiddleWareConfig.getInstance().getVfsManager().getDeploymentSet();
		prepareQueue(deploymentSet);
		tryLoadingFromQueue();
	}
	
	public void tryLoadingFromQueue(){
		// TODO main process of loading, may have bugs	
		
		if (loadingQueue.isEmpty()){  
			return;
		}
		
		int countOfLoaded = 0;  // count the loaded class and decide whether all the class are waiting
		while(!loadingQueue.isEmpty() && countOfLoaded < loadingQueue.size()){
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
					((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).addLoadedDeploymentNode(classInfo.getDeploymentNode());
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
	
	@Override
	public void loadAllClass() {
		initAdaptLoadClassManager();	
	}

	@Override
	public Class<?> loadClassByDeployment(String deploymentName, String className) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
