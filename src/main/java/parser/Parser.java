package parser;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.StringReader;
import java.io.IOException;

public class Parser {

	public Parser() {}

	public ArrayList<String> parse(String xmlString) {
		ArrayList<String> notes = null;
		try {
			// Getting the parser ready
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputSource is = new InputSource(new StringReader(xmlString));

			// Parse and get the root element
			Document dom = builder.parse(is);
			Element root = dom.getDocumentElement();

			// Get the measures from the document
			NodeList measures = root.getElementsByTagName("measure");

			// This will store the output
			notes = new ArrayList<String>();

			// Iterate through the measures
			for (int i = 0; i < measures.getLength(); i++) {

				// Get the first element of the measure
				Node currentElement = measures.item(i).getFirstChild();

				// --- Iterate through the elements of the measure ---
				// We iterate goes in order of the document (top to bottom),
				// so if the current element is null, then we reached the bottom of the document.
				while (currentElement != null) {

					// We only care about elements that are notes
					if (currentElement.getNodeName() == "note") {

						// --- Find the pitch of the note ---
						// Each note element has a pitch element.
						// We iterate through the note element to find this pitch element.
						Node pitch = currentElement.getFirstChild();
						while (pitch.getNodeName() != "pitch") {
							pitch = pitch.getNextSibling();
						}

						// --- Find the step of the pitch ---
						// Each pitch element has a step element.
						// We iterate through the pitch element to find this step element.
						Node step = pitch.getFirstChild();
						while (step.getNodeName() != "step") {
							step = step.getNextSibling();
						}

						notes.add(step.getFirstChild().getNodeValue());

					}

					// Get the next element in the measure
					currentElement = currentElement.getNextSibling();
				}
			}
		// Errors associated with the parser
		} catch (FactoryConfigurationError e) {
			System.out.println(e);
		} catch (ParserConfigurationException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch (SAXException e) {
			System.out.println(e);
		}

		return notes;
	}

}
