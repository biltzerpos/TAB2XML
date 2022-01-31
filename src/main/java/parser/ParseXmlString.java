package parser;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;

import GUI.MainViewController;

import org.w3c.dom.*;
import java.io.*;

public class ParseXmlString {

	public ParseXmlString() {

	}

	public String parse(MainViewController mvc) {
		String xmlString = mvc.converter.getMusicXML();
		String parsedXML = "";

		try {
			//making the DOC
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			//used as an input for dBuilder.parse() instead of inputting a file
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));

			//input is
			Document doc = dBuilder.parse(is);

			//better Visual
			doc.getDocumentElement().normalize();

//			----------------------------------------------------------------------------------------------------------------------------


//			NodeList nodeList1 = doc.getElementsByTagName("score-partwise");
//
//			ParseXmlString parser = new ParseXmlString();
//
//			for(int i = 0; i < nodeList1.getLength(); i++) {
//				parser.printChild(nodeList1.item(i));
//			}

//			----------------------------------------------------------------------------------------------------------------------------
//			THIS IS THE WAY I FOUND WORKS BEST


			//get all nodes in doc
			NodeList nodesList1 = doc.getElementsByTagName("*");

			//iterate and print nodes
			for (int i = 0; i < nodesList1.getLength(); i++) {
				Node node = nodesList1.item(i);

				
				//want to put this into an array instead
				Element element = (Element) node;
				parsedXML += element.getNodeName() + "\n";
				
				//want to put this into an array instead
				String name = element.getAttribute("name");
				parsedXML += name + "\n";
				
			}

//			----------------------------------------------------------------------------------------------------------------------------

//			//get root element and print
//			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

//			//create a list of the nodes with same tag name
//			NodeList nodeList1 = doc.getE

//			System.out.println("----------------------------");

//			for(int i = 0; i < nodeList1.getLength(); i++) {
//				NodeList childNodesList1 = nodeList1.item(i).getChildNodes();
//
//
//			}


//--------------------------------------------------------------------------------------------------------------------------------------



//			for (int i = 0; i < nodeList1.getLength(); i++) {
//				Node node1 = nodeList1.item(i);
//				System.out.println("Current Element :" + node1.getNodeName());
//
//				if (node1.getNodeType() == Node.ELEMENT_NODE) {
//					Element eElement = (Element) node1;
//					System.out.println("Student roll no : "
//							+ eElement.getAttribute("rollno"));
//					System.out.println("First Name : "
//							+ eElement
//							.getElementsByTagName("firstname")
//							.item(0)
//							.getTextContent());
//					System.out.println("Last Name : "
//							+ eElement
//							.getElementsByTagName("lastname")
//							.item(0)
//							.getTextContent());
//					System.out.println("Nick Name : "
//							+ eElement
//							.getElementsByTagName("nickname")
//							.item(0)
//							.getTextContent());
//					System.out.println("Marks : "
//							+ eElement
//							.getElementsByTagName("marks")
//							.item(0)
//							.getTextContent());
//				}
//			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return parsedXML;

//		--------------------------------------------------------------------------------------------------------------------------------


//		try {
//			DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			InputSource is = new InputSource();
//			is.setCharacterStream(new StringReader(xmlString));
//
//			Document doc = db.parse(is);
//			NodeList nodes = doc.getElementsByTagName("part");
//
//			for (int i = 0; i < nodes.getLength(); i++) {
//				Element element = (Element) nodes.item(i);
//
//				NodeList name = element.getElementsByTagName("name");
//				Element line = (Element) name.item(0);
//				System.out.println("Name: " + getCharacterDataFromElement(line));
//
//				NodeList title = element.getElementsByTagName("title");
//				line = (Element) title.item(0);
//				System.out.println("Title: " + getCharacterDataFromElement(line));
//			}
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}


		/*
    output :
        Name: John
        Title: Manager
        Name: Sara
        Title: Clerk
		 */

	}

//	public void printChild(Node node) {
//	    NodeList childNodes = node.getChildNodes();
//	    System.out.println("Node: " + node.getNodeType() + ", " + node.getLocalName());
//
//	    for(int i = 0; i < childNodes.getLength(); i++) {
//	        Node childNode = childNodes.item(i);
//
//	        if(childNode.hasAttributes()) {
//	            System.out.println("Attributes: " + childNode.getAttributes()); //just an example...
//	            //Here you can iterate over each attributes to do something
//	        }
//
//	        if(childNode.hasChildNodes()) {
//	            System.out.println(""); //just an empty string
//	            printChild(childNode);
//	        }
//	    }
//	}

//	public static String getCharacterDataFromElement(Element e) {
//		Node child = e.getFirstChild();
//		if (child instanceof CharacterData) {
//			CharacterData cd = (CharacterData) child;
//			return cd.getData();
//		}
//		return "?";
//	}
}
