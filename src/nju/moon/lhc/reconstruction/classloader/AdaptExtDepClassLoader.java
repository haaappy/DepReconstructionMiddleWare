package nju.moon.lhc.reconstruction.classloader;

import java.util.ArrayList;
import java.util.HashMap;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;
import nju.moon.lhc.reconstruction.manager.dependency.AdaptDependencyManager;
import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class AdaptExtDepClassLoader extends AdaptDepClassLoader {

	protected boolean valid;
	
	public AdaptExtDepClassLoader(String rootDir, String classLoaderName) {
		super(rootDir, classLoaderName);
		valid = true;
	}

	public AdaptExtDepClassLoader() {
		super();
		valid = true;
	}
	
	public boolean getValid(){
		return valid;
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}

	// TODO  need to test ********  with bugs
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException{
		Class<?> c = findLoadedClass(name);
		if (c == null){
			try{
				if (name.startsWith("java.")){
					c = ClassLoader.getSystemClassLoader().loadClass(name);
					if (c != null){
						return c;
					}
				}
				else{
					// load by itself
					c = findClass(name);
					if (c == null){
						for (ClassLoader depClassLoader: depClassLoaders){
							c = ((AdaptDepClassLoader) depClassLoader).loadClassByRepository(name);
							if (c != null){
								return c;
							}		
						}
						
						HashMap<String, DeploymentNode> map = ((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).getLoadedNodeResporitory();
						//ArrayList<ClassLoader> repositoryList = ((AdaptDependencyManager)MiddleWareConfig.getInstance().getDepManager()).getAllRepositoryClassLoaders();
						for (DeploymentNode node: map.values()){
							ClassLoader repClassLoader = node.getClassLoader();
							if (repClassLoader instanceof AdaptExtDepClassLoader){
								if (((AdaptExtDepClassLoader) repClassLoader).getValid() == false){
									node.setClassLoader(MiddleWareConfig.getInstance().createClassLoaderByName(node.getNodeName()));
									repClassLoader = node.getClassLoader();
								}
							}
							c = ((AdaptDepClassLoader) repClassLoader).loadClassByRepository(name);
							if (c != null){
								this.depClassLoaders.add(repClassLoader);
								((AdaptDepClassLoader) repClassLoader).depInverseClassLoaders.add(this);
								return c;
							}		
						}
						c = this.getParent().loadClass(name);   // no class loader can load. load class with AppClassLoader				
					}					
				}			
			}
			catch (ClassNotFoundException e){
				c = ClassLoader.getSystemClassLoader().loadClass(name);   //no classloader can load. load class with AppClassLoader
			}
		}
		return c;
	}

}
