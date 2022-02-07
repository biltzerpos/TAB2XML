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

	private Technical getTechnicalFromTechnicalNode(Node technicalNode) {
		Element technicalElement = (Element) technicalNode;

		Technical technical = new Technical();

		technical.setString(Integer.parseInt(technicalElement.getElementsByTagName("string").item(0).getFirstChild().getNodeValue()));
		technical.setFret(Integer.parseInt(technicalElement.getElementsByTagName("fret").item(0).getFirstChild().getNodeValue()));

		return technical;
	}

	private Notations getNotationsFromNotationsNode(Node notationsNode) {
		Element notationsElement = (Element) notationsNode;

		Notations notations = new Notations();

		notations.setTechnical(getTechnicalFromTechnicalNode(notationsElement.getElementsByTagName("technical").item(0)));

		return notations;
	}

	private Pitch getPitchFromPitchNode(Node pitchNode) {
		Element pitchElement = (Element) pitchNode;

		Pitch pitch = new Pitch(
			pitchElement.getElementsByTagName("step").item(0).getFirstChild().getNodeValue(),
			Integer.parseInt(
				pitchElement.getElementsByTagName("alter").item(0) != null
				? pitchElement.getElementsByTagName("alter").item(0).getFirstChild().getNodeValue()
				: "0"
			),
			Integer.parseInt(pitchElement.getElementsByTagName("octave").item(0).getFirstChild().getNodeValue())
		);

		return pitch;
	}

	private Note getNoteFromNoteNode(Node noteNode) {
		Element noteElement = (Element) noteNode;

		Note note = new Note();

		note.setPitch(getPitchFromPitchNode(noteElement.getElementsByTagName("pitch").item(0)));
		note.setDuration(Integer.parseInt(noteElement.getElementsByTagName("duration").item(0).getFirstChild().getNodeValue()));
		note.setVoice(Integer.parseInt(noteElement.getElementsByTagName("voice").item(0).getFirstChild().getNodeValue()));
		note.setType(noteElement.getElementsByTagName("type").item(0).getFirstChild().getNodeValue());
		note.setNotations(getNotationsFromNotationsNode(noteElement.getElementsByTagName("notations").item(0)));

		return note;
	}

	private List<Note> getNotesFromMeasureNode(Node measureNode) {
		Element measureElement = (Element) measureNode;
		NodeList noteNodeList = measureElement.getElementsByTagName("note");

		List<Note> notes = new ArrayList<>();

		for (int i = 0; i < noteNodeList.getLength(); i++) {
			notes.add(getNoteFromNoteNode(noteNodeList.item(i)));
		}

		return notes;
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

			// Iterate through the measures
			for (int i = 0; i < measureNodeList.getLength(); i++) {
				// Initialize the measure
				this.measures.add(new Measure());
				this.measures.get(i).setNotesBeforeBackup(getNotesFromMeasureNode(measureNodeList.item(i)));
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
