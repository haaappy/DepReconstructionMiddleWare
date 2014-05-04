package nju.moon.lhc.reconstruction.manager.dependency;

import java.util.HashMap;

public abstract class AbstractDependencyManager {
	protected HashMap<String, DeploymentNode> nodeMap;  // store all the node

	public AbstractDependencyManager(){
		nodeMap = new HashMap<String, DeploymentNode>();
	}

	public HashMap<String, DeploymentNode> getNodeMap(){
		return nodeMap;
	}
	
}
