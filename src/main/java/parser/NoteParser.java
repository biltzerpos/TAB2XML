package parser;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import models.measure.note.Note;
import models.measure.note.Pitch;
import models.measure.note.notations.Notations;
import models.measure.note.notations.technical.Technical;

public class NoteParser {

	private Element noteElement;
	private Note note;

	public NoteParser(Node noteNode) {
		this.noteElement = (Element) noteNode;
		this.note = new Note();
	}

	private void addPitch() {
		NodeList pitchNodeList = noteElement.getElementsByTagName("pitch");

		if (pitchNodeList.getLength() > 0) {
			Element pitchElement = (Element) pitchNodeList.item(0);

			String step = pitchElement.getElementsByTagName("step").item(0).getFirstChild().getNodeValue();

			Integer alter = Integer.parseInt(
				pitchElement.getElementsByTagName("alter").item(0) != null
				? pitchElement.getElementsByTagName("alter").item(0).getFirstChild().getNodeValue()
				: "0"
			);

			int octave = Integer.parseInt(pitchElement.getElementsByTagName("octave").item(0).getFirstChild().getNodeValue());

			this.note.setPitch(new Pitch(step, alter, octave));
		}
	}

	private void addDuration() {
		NodeList durationNodeList = noteElement.getElementsByTagName("duration");

		if (durationNodeList.getLength() > 0) {
			Element durationElement = (Element) durationNodeList.item(0);

			Integer duration = Integer.parseInt(durationElement.getFirstChild().getNodeValue());

			this.note.setDuration(duration);
		}
	}

	private void addVoice() {
		NodeList voiceNodeList = noteElement.getElementsByTagName("voice");

		if (voiceNodeList.getLength() > 0) {
			Element voiceElement = (Element) voiceNodeList.item(0);

			Integer voice = Integer.parseInt(voiceElement.getFirstChild().getNodeValue());

			this.note.setVoice(voice);
		}
	}

	private void addType() {
		NodeList typeNodeList = noteElement.getElementsByTagName("type");

		if (typeNodeList.getLength() > 0) {
			Element typeElement = (Element) typeNodeList.item(0);

			String type = typeElement.getFirstChild().getNodeValue();

			this.note.setType(type);
		}
	}

	private Technical getTechnicalFromNotationsElement(Element notationsElement) {
		NodeList technicalNodeList = notationsElement.getElementsByTagName("technical");

		Element technicalElement = (Element) technicalNodeList.item(0);

		Technical technical = new Technical();

		technical.setString(Integer.parseInt(technicalElement.getElementsByTagName("string").item(0).getFirstChild().getNodeValue()));
		technical.setFret(Integer.parseInt(technicalElement.getElementsByTagName("fret").item(0).getFirstChild().getNodeValue()));

		return technical;
	}

	private void addNotations() {
		NodeList notationsNodeList = noteElement.getElementsByTagName("notations");

		if (notationsNodeList.getLength() > 0) {
			Element notationsElement = (Element) notationsNodeList.item(0);

			Technical technical = getTechnicalFromNotationsElement(notationsElement);

			Notations notations = new Notations();

			notations.setTechnical(technical);

			this.note.setNotations(notations);
		}
	}

	public Note getNote() {
		addPitch();
		addDuration();
		addVoice();
		addType();
		addNotations();

		return this.note;
	}

}
