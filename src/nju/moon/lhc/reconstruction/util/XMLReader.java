package nju.moon.lhc.reconstruction.util;

import java.io.File;
import java.io.InputStream;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nju.moon.lhc.reconstruction.main.MiddleWareConfig;

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
				String[] configResults = new String[8];
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(xmlFile);
				configResults[0] = doc.getElementsByTagName(XMLFinalField.CUR_HOME).item(0).getTextContent();
				configResults[1] = doc.getElementsByTagName(XMLFinalField.CUR_DEPLOYMENT_WAY).item(0).getTextContent();
				configResults[2] = doc.getElementsByTagName(XMLFinalField.CUR_CLASSLOADER).item(0).getTextContent();
				configResults[3] = doc.getElementsByTagName(XMLFinalField.CUR_POLLINGTIME).item(0).getTextContent();
				configResults[4] = doc.getElementsByTagName(XMLFinalField.DEFAULT_HOME).item(0).getTextContent();
				configResults[5] = doc.getElementsByTagName(XMLFinalField.DEFAULT_DEPLOYMENT_WAY).item(0).getTextContent();
				configResults[6] = doc.getElementsByTagName(XMLFinalField.DEFAULT_CLASSLOADER).item(0).getTextContent();
				configResults[7] = doc.getElementsByTagName(XMLFinalField.DEFAULT_POLLINGTIME).item(0).getTextContent();
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
	
	// It is used for Jar File configure xml
	public static HashSet<String> readInfoByXMLFileFromJar(String jarPath, String xmlName, String tagInfo){
		HashSet<String> result = new HashSet<String>();
		try{		
			ZipFile zf = new ZipFile(jarPath);
			ZipEntry ze = zf.getEntry(xmlName);
			InputStream ins = zf.getInputStream(ze);	
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(ins);
			NodeList nl = doc.getElementsByTagName(tagInfo);
			for (int i=0; i<nl.getLength(); i++){
				//System.out.println(doc.getElementsByTagName("Dependency").item(i).getTextContent());
				result.add(doc.getElementsByTagName(tagInfo).item(i).getTextContent());
			}
			ins.close();
			return result;
		}
		catch(Exception e){
			e.printStackTrace();
			return result;
		}
		
	}
	
	public static void main(String[] args){
		System.out.println(readInfoByXMLFileFromJar(MiddleWareConfig.DEFAULT_HOME + "A-JAR.jar", "A-JAR.xml", "Dependency"));
	}
}
