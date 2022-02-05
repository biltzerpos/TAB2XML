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

import java.util.List;
import models.measure.Measure;

public class Parser {

	private List<Measure> measures;

	public Parser() {
		this.measures = new ArrayList<Measure>();
	}

	public void parse(String xmlString) {
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

			// Iterate through the measures
			for (int i = 0; i < measures.getLength(); i++) {
				// Initialize the measure
				Measure currentMeasure = new Measure();
				this.measures.add(currentMeasure);

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
	}

	public List<Measure> getMeasures() {
		return this.measures;
	}

}
