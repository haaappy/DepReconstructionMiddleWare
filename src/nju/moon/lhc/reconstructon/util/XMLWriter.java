package nju.moon.lhc.reconstructon.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import nju.moon.lhc.reconstructon.main.MiddleWareConfig;

import org.w3c.dom.Document;

public class XMLWriter {
	
	public static boolean doc2XmlFile(Document document, String filename) {
        boolean flag = true;
        try {
        	TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
           
            // transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filename));
            transformer.transform(source, result);
        } catch (Exception ex) {
            flag = false;
            ex.printStackTrace();
        }
        return flag;
    }

	
	public static Document loadXMLFile(String filePathname) {
		Document document = null;
	    try {
	    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder builder = factory.newDocumentBuilder();
	    	document = builder.parse(new File(filePathname));
	    	document.normalize();
	    } catch (Exception ex) {
	    	ex.printStackTrace();
	    }
	    return document;
	}
	
	public static void xmlUpdateTagValue(Document doc, String tagInfo, String newValue) {
		try{	
			doc.getElementsByTagName(tagInfo).item(0).setTextContent(newValue);
		}
		catch(Exception e){
			e.printStackTrace();
		}     
    }
	
	public static void xmlUpdateCurValue(String filePathName, String curHomeValue, String curWayValue){
		if (filePathName.endsWith("config.xml")){
			Document doc = loadXMLFile(filePathName);
			xmlUpdateTagValue(doc, XMLFinalField.CUR_HOME, curHomeValue);
			xmlUpdateTagValue(doc, XMLFinalField.CUR_DEPLOYMENT_WAY, curWayValue);
			doc2XmlFile(doc, filePathName);
		}
		else{
			System.out.println("filePathName = " + filePathName + ". It is not config.xml in xmlUpdateCurValue" );
		}		
	}
	
	public static void xmlUpdateDefaultValue(String filePathName, String defaultHomeValue, String defaultWayValue){
		if (filePathName.endsWith("config.xml")){
			Document doc = loadXMLFile(filePathName);
			xmlUpdateTagValue(doc, XMLFinalField.DEFAULT_HOME, defaultHomeValue);
			xmlUpdateTagValue(doc, XMLFinalField.DEFAULT_DEPLOYMENT_WAY, defaultWayValue);
			doc2XmlFile(doc, filePathName);
		}
		else{
			System.out.println("filePathName = " + filePathName + ". It is not config.xml in xmlUpdateDefaultValue" );
		}		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		xmlUpdateCurValue(MiddleWareConfig.DEFAULT_HOME + "config.xml", "1212", "sdffas");
	}

}
