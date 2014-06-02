package nju.moon.lhc.reconstruction.manager.dependency;

import java.util.ArrayList;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;

public class DeploymentNode {

	private String nodeName;
	private ClassLoader nodeClassLoader;
	private ArrayList<DeploymentNode> fathers;
	private ArrayList<DeploymentNode> children;
	//private boolean valid;
	
	private final String DEFAULT_NODENAME = "OSGI_SYSTEM";
	
	public DeploymentNode(){
		nodeName = DEFAULT_NODENAME;
		nodeClassLoader = ClassLoader.getSystemClassLoader();
		fathers = new ArrayList<DeploymentNode>();
		children = new ArrayList<DeploymentNode>();
	}
	
	public DeploymentNode(String nodeName){
		this.nodeName = nodeName;
		nodeClassLoader = null;
		fathers = new ArrayList<DeploymentNode>();
		children = new ArrayList<DeploymentNode>();
	}
	
	public String getNodeName(){
		return nodeName;
	}
	
	public ArrayList<DeploymentNode> getFathers(){
		return fathers;
	}
	
	public ArrayList<DeploymentNode> getChildern(){
		return children;
	}
	
	public ClassLoader getClassLoader(){
		return nodeClassLoader;
	}
	
	public void setAFather(DeploymentNode father){
		if (!hasByName(father.nodeName, fathers)){
			fathers.add(father);
		}
		else{
			System.out.println("father has already in the arraylist when set a father");
		}
	}

	public void setAChild(DeploymentNode child){
		if (!hasByName(child.nodeName, children)){
			children.add(child);
		}
		else{
			System.out.println("child has already in the arraylist when set a child");
		}
	}
	
	public void setClassLoader(ClassLoader nodeClassLoader){
		this.nodeClassLoader = nodeClassLoader;
	}
	
	public void removeChildByName(String name){
		removeByName(name, children);
	}
	
	public void removeFatherByName(String name){
		removeByName(name, fathers);
	}
	
	public void removeByName(String name, ArrayList<DeploymentNode> arraylist){
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		for(DeploymentNode node: arraylist){
			if (node.nodeName.equals(name)){
				removeList.add(arraylist.indexOf(node));
			}
		}
		for(int index: removeList){
			arraylist.remove(index);
		}
	}
	
	public boolean hasByName(String name, ArrayList<DeploymentNode> arraylist){
		for(DeploymentNode node: arraylist){
			if (node.nodeName.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null){
			return false;
		}
		if (this == obj){
			return true;
		}
		if (obj instanceof DeploymentNode){
			return this.nodeName.equals(((DeploymentNode) obj).nodeName);
		}
		else{
			return false;
		}
	}
	
	@Override
	public int hashCode(){
		return nodeName.hashCode();
	}

	
}
