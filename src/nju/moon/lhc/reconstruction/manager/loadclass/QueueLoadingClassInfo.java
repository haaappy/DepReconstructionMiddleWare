package nju.moon.lhc.reconstruction.manager.loadclass;

import nju.moon.lhc.reconstruction.manager.dependency.DeploymentNode;

public class QueueLoadingClassInfo {

	private DeploymentNode deploymentNode;
	private String className;
	
	public QueueLoadingClassInfo(){
		deploymentNode = new DeploymentNode();
		className = "";
	}
	
	public QueueLoadingClassInfo(DeploymentNode deploymentNode, String className){
		this.deploymentNode = deploymentNode;
		this.className = className;
	}
	
	public String getClassName(){
		return className;
	}
	
	public DeploymentNode getDeploymentNode(){
		return deploymentNode;
	}
}
