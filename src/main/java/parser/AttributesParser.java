package parser;

import java.util.List;
import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import models.measure.attributes.Attributes;
import models.measure.attributes.Key;
import models.measure.attributes.Clef;
import models.measure.attributes.StaffDetails;
import models.measure.attributes.StaffTuning;

public class AttributesParser {

	private Element attributesElement;
	private Attributes attributes;

	public AttributesParser(Node attributesNode) {
		this.attributesElement = (Element) attributesNode;
		this.attributes = new Attributes();
	}

	private void addDivisions() {
		NodeList divisionsNodeList = attributesElement.getElementsByTagName("divisions");

		if (divisionsNodeList.getLength() > 0) {
			this.attributes.setDivisions(Integer.parseInt(divisionsNodeList.item(0).getFirstChild().getNodeValue()));
		}
	}

	private void addKey() {
		NodeList keyNodeList = attributesElement.getElementsByTagName("key");

		if (keyNodeList.getLength() > 0) {
			NodeList fifthsNodeList = ((Element) keyNodeList.item(0)).getElementsByTagName("fifths");

			int fifths = Integer.parseInt(fifthsNodeList.item(0).getFirstChild().getNodeValue());

			this.attributes.setKey(new Key(fifths));
		}
	}

	private void addClef() {
		NodeList clefNodeList = attributesElement.getElementsByTagName("clef");

		if (clefNodeList.getLength() > 0) {
			String sign;
			int line;

			NodeList signNodeList = ((Element) clefNodeList.item(0)).getElementsByTagName("sign");

			sign = signNodeList.item(0).getFirstChild().getNodeValue();

			NodeList lineNodeList = ((Element) clefNodeList.item(0)).getElementsByTagName("line");

			line = Integer.parseInt(lineNodeList.item(0).getFirstChild().getNodeValue());

			this.attributes.setClef(new Clef(sign, line));
		}
	}

	private int getStaffLinesFromStaffDetailsElement(Element staffDetailsElement) {
		NodeList staffLinesNodeList = staffDetailsElement.getElementsByTagName("staff-lines");

		int staffLines = Integer.parseInt(staffLinesNodeList.item(0).getFirstChild().getNodeValue());

		return staffLines;
	}

	private List<StaffTuning> getStaffTuningListFromStaffTuningNodeList(NodeList staffTuningNodeList) {
		List<StaffTuning> staffTuningList = new ArrayList<>();

		Element staffTuningElement;
		int line;
		String tuningStep;
		int tuningOctave;

		for (int i = 0; i < staffTuningNodeList.getLength(); i++) {
			staffTuningElement = (Element) staffTuningNodeList.item(i);

			line = Integer.parseInt(staffTuningElement.getAttribute("line"));

			tuningStep = staffTuningElement.getElementsByTagName("tuning-step").item(0).getFirstChild().getNodeValue();

			tuningOctave = Integer.parseInt(
				staffTuningElement.getElementsByTagName("tuning-octave").item(0).getFirstChild().getNodeValue()
			);

			staffTuningList.add(new StaffTuning(line, tuningStep, tuningOctave));
		}

		return staffTuningList;
	}

	private void addStaffDetails() {
		NodeList staffDetailsNodeList = attributesElement.getElementsByTagName("staff-details");

		if (staffDetailsNodeList.getLength() > 0) {
			Element staffDetailsElement = (Element) staffDetailsNodeList.item(0);
	
			int staffLines = getStaffLinesFromStaffDetailsElement(staffDetailsElement);
	
			NodeList staffTuningNodeList = staffDetailsElement.getElementsByTagName("staff-tuning");
	
			List<StaffTuning> staffTuningList = getStaffTuningListFromStaffTuningNodeList(staffTuningNodeList);
	
			this.attributes.setStaffDetails(new StaffDetails(staffLines, staffTuningList));
		}
	}

	public Attributes getAttributes() {
		addDivisions();
		addKey();
		addClef();
		addStaffDetails();

		return this.attributes;
	}

}
