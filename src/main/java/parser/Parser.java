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
import models.measure.note.Note;
import models.measure.note.Pitch;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Technical;

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
			NodeList measureNodeList = root.getElementsByTagName("measure");

			// Iterate through the measure nodes in the measure node list
			for (int i = 0; i < measureNodeList.getLength(); i++) {
				// Parse the measure from the current measure node and add it to the list of measures
				this.measures.add((new MeasureParser(measureNodeList.item(i))).getMeasure());
			}
		}

		// Errors associated with the parser
		catch (FactoryConfigurationError e) {
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
