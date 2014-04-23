package nju.moon.lhc.reconstruction.manager.loadclass;

import java.util.HashSet;

public interface InterfaceLoadClassManager {

	abstract public void loadAllClass();
	abstract public Class<?> loadClassByDeployment(String deploymentName, String className);	
	abstract public void loadClassByDeploymentNameSet(HashSet<String> nameSet);

}
