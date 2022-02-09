package parser;

import javax.xml.parsers.*;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import GUI.MainViewController;
import models.ScorePartwise;

import org.jfugue.integration.MusicXmlParser;
import org.jfugue.player.Player;
import org.staccato.StaccatoParserListener;
import org.w3c.dom.*;
import java.io.*;


//ScorePartwise value is the object containing the data for the music
public class ParseXmlString {
	private String xmlString = "";
	private String xmlG = "";
	private String xmlD = "";
	private String parsedString;
	private ScorePartwise value;

	public ParseXmlString() {

	}

//GUITAR
//------
//
//	E  |-----------0-----|-0---------------|   (THINNEST STRING)
//	B  |---------0---0---|-0---------------|
//	G  |-------1-------1-|-1---------------|
//	D  |-----2-----------|-2---------------|
//	A  |---2-------------|-2---------------|
//	E  |-0---------------|-0---------------|   (THICKEST STRING)
//
//	-THE NUMBER INDICATES THE FRET (WHERE ON THE GUITAR)
//	-USING THE POSITION OF THE NUMBER ON THE SHEET
//	 	ALONG WITH THE NUMBER ITSELF (THE FRET)
//		WILL LET YOU KNOW WHERE ON THE GUITAR
//
//  -ONLY WAY TO KNOW ON WHICH LINE TO PLACE (OUT OF THE 6 LINES), IS THROUGH THE DATA
//  -ALIGN FINGER WITH CORRECT STRING
//  -THEN ALIGN CORRECT FRET 

	public String getXmlString() {
		return this.xmlString;
	}

	public void setXmlString(String xmlString) {
		this.xmlString = xmlString;
	}

	public String getXmlG() {
		return this.xmlG;
	}

	public void setXmlG(String xmlG) {
		this.xmlG = xmlG;
	}

	public String getXmlD() {
		return this.xmlD;
	}

	public void setXmlD(String xmlD) {
		this.xmlD = xmlD;
	}

	public String getParsedString() {
		return this.parsedString;
	}

	public void setParsedString(String parsedString) {
		this.parsedString = parsedString;
	}

	public ScorePartwise getValue() {
		return this.value;
	}

	public void setValue(ScorePartwise value) {
		this.value = value;
	}

	public String parse(MainViewController mvc) {
		this.xmlString = mvc.converter.getMusicXML();
		this.parsedString = "";

		try {
			//making the DOC
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			//used as an input for dBuilder.parse() instead of inputting a file
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(this.xmlString));

			//input is
			Document doc = dBuilder.parse(is);

			//better Visual
			doc.getDocumentElement().normalize();

			//get all nodes in doc
			NodeList nodesList1 = doc.getElementsByTagName("*");

			//iterate and print nodes
			for (int i = 0; i < nodesList1.getLength(); i++) {
				Node node = nodesList1.item(i);


				//want to put this into an array instead
				Element element = (Element) node;
				this.parsedString += element.getNodeName() + "\n";

				//want to put this into an array instead
				String name = element.getAttribute("name");
				this.parsedString += name + "\n";
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		ObjectMapper objMapper = new XmlMapper();
		 
		if(this.xmlString.contains("Guitar")) {
			this.xmlG = this.xmlString; 
			//(Guitar) replace xml Node name with Java Object already created
			//originally <noteBefore>, prof changed to <note>, here change to <noteAfter>
			this.xmlG = this.xmlG.replace("note", "noteAfter");
			try {
				this.value = objMapper.readValue(this.xmlG, ScorePartwise.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		else if(this.xmlString.contains("Drumset")) {
			this.xmlD = this.xmlString;
			//(Drum) replace xml Node name with Java Object already created 
			//originally <noteBefore> , prof changed to <note>, here change to <
			//           <notehead>
			this.xmlD = this.xmlD.replace("note", "noteBefore");
			this.xmlD = this.xmlD.replace("noteBeforehead", "notehead");
			try {
				this.value = objMapper.readValue(this.xmlD, ScorePartwise.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}	

//		xmlD = xmlD.replaceAll("\n", "\\R[ \\t]*<midiinstruments>[\\s\\S]*</midiinstruments>[ \\t]*\\R");
//	    xmlD = xmlD.replaceAll("\n", "\\R[ \\t]*<timeModification>\\s*<actual-notes>\\d+</actual-notes>\\s*<normal-notes>\\d+</normal-notes>\\s*</timeModification>[ \\t]*\\R");
	        		 
		//Options for 
//		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	 	objMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		return this.parsedString;
	}
	
	public void playMusic() {
		try {
			MusicXmlParser parser = new MusicXmlParser();
			StaccatoParserListener listener = new StaccatoParserListener();
			parser.addParserListener(listener);

			parser.parse(xmlString);;

			System.out.println("after parser");
			Player player = new Player();
			org.jfugue.pattern.Pattern musicXmlPattern = listener.getPattern().setTempo(400).setInstrument("Guitar");
			
			System.out.println("starting to play music");
			player.play(musicXmlPattern);
		}catch (Exception e) {
			// TODO: handle exception
		}	
	}

}
