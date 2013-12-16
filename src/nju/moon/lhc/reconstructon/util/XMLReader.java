package nju.moon.lhc.reconstructon.util;

import java.io.File;
import java.util.HashSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nju.moon.lhc.reconstructon.main.MiddleWareConfig;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class XMLReader {

	public static String readInfoByConfigXMLFile(String filePathName, String tagInfo){
		try{		
			File xmlFile = new File(filePathName);
			if (!xmlFile.exists()){
				System.out.println("ERORR! "+ filePathName + " XML file does not exisit!");
				return "";
			}
			else{			
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				return doc.getElementsByTagName(tagInfo).item(0).getTextContent();
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String[] readAllInfoByConfigXMLFile(String filePathName){	
		try{		
			File xmlFile = new File(filePathName);
			if (!xmlFile.exists()){
				System.out.println("ERORR! "+ filePathName + " XML file does not exisit!");
				return null;
			}
			else{
				String[] configResults = new String[4];
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				configResults[0] = doc.getElementsByTagName(XMLFinalField.CUR_HOME).item(0).getTextContent();
				configResults[1] = doc.getElementsByTagName(XMLFinalField.CUR_DEPLOYMENT_WAY).item(0).getTextContent();
				configResults[2] = doc.getElementsByTagName(XMLFinalField.DEFAULT_HOME).item(0).getTextContent();
				configResults[3] = doc.getElementsByTagName(XMLFinalField.DEFAULT_DEPLOYMENT_WAY).item(0).getTextContent();
				return configResults;
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static HashSet<String> readInfoByXMLFile(String filePathName, String tagInfo){
		HashSet<String> result = new HashSet<String>();
		try{		
			File xmlFile = new File(filePathName);
			if (!xmlFile.exists()){
				System.out.println("ERORR! "+ filePathName + " XML file does not exisit!");
				return result;
			}
			else{			
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				NodeList nl = doc.getElementsByTagName(tagInfo);
				for (int i=0; i<nl.getLength(); i++){
					//System.out.println(doc.getElementsByTagName("Dependency").item(i).getTextContent());
					result.add(doc.getElementsByTagName(tagInfo).item(i).getTextContent());
				}
				return result;
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
			return result;
		}
	}
	
	public static void main(String[] args){
		System.out.println(readInfoByConfigXMLFile(MiddleWareConfig.DEFAULT_HOME + "config.xml", "CurHome"));
	}
}
