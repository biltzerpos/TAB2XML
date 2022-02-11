package parser;

import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import models.measure.Measure;
import models.measure.note.Note;

public class MeasureParser {

	private Element measureElement;
	private Measure measure;

	public MeasureParser(Node measureNode) {
		this.measureElement = (Element) measureNode;
		this.measure = new Measure();
	}

	public void addAttributes() {
		NodeList attributesNodeList = this.measureElement.getElementsByTagName("attributes");

		if (attributesNodeList.getLength() > 0) {
			AttributesParser attributesParser = new AttributesParser(attributesNodeList.item(0));

			this.measure.setAttributes(attributesParser.getAttributes());
		}
	}

	public void addNotesBeforeBackup() {
		NodeList noteNodeList = this.measureElement.getElementsByTagName("note");

		if (noteNodeList.getLength() > 0) {
			NoteParser noteParser;
			List<Note> noteList = new ArrayList<>();

			for (int i = 0; i < noteNodeList.getLength(); i++) {
				noteParser = new NoteParser(noteNodeList.item(i));

				noteList.add(noteParser.getNote());
			}

			this.measure.setNotesBeforeBackup(noteList);
		}
	}

	public Measure getMeasure() {
		addNotesBeforeBackup();
		addAttributes();

		return this.measure;
	}

}
