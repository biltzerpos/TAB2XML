//package parser;
//
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//import javax.sound.midi.InvalidMidiDataException;
//import javax.sound.midi.MidiUnavailableException;
//import javax.sound.midi.Sequence;
//import javax.sound.midi.Sequencer;
//import javax.swing.JFrame;
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.jfugue.integration.MusicXmlParser;
//import org.jfugue.midi.MidiParser;
//import org.jfugue.midi.MidiParserListener;
//import org.jfugue.pattern.Pattern;
//import org.jfugue.pattern.PatternProducer;
//import org.jfugue.player.ManagedPlayer;
//import org.jfugue.player.Player;
//import org.jfugue.realtime.RealtimeMidiParserListener;
//import org.jfugue.realtime.RealtimePlayer;
//import org.staccato.StaccatoParser;
//import org.staccato.StaccatoParserListener;
//
//import abc.midi.TunePlayer;
//import abc.notation.Tune;
//import abc.parser.TuneBook;
//import abc.ui.swing.JScoreComponent;
//import nu.xom.ParsingException;
//import nu.xom.ValidityException;
//
//
///**
// * This program demonstrates abc4j's capabilities. It loads a song from a
// * file, displays metadata, saves the song to a different file, plays the song,
// * and then displays a representation of the score.
// */
//public class ImprovedParser {
//	static String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
//			+ "<!DOCTYPE score-partwise PUBLIC \"-//Recordare//DTD MusicXML 3.1 Partwise//EN\" \"http://www.musicxml.org/dtds/partwise.dtd\">\n"
//			+ "<score-partwise version=\"3.1\">\n"
//			+ "  <identification>\n"
//			+ "    <creator type=\"composer\"></creator>\n"
//			+ "  </identification>\n"
//			+ "  <part-list>\n"
//			+ "    <score-part id=\"P1\">\n"
//			+ "      <part-name>Guitar</part-name>\n"
//			+ "    </score-part>\n"
//			+ "  </part-list>\n"
//			+ "  <part id=\"P1\">\n"
//			+ "    <measure number=\"1\">\n"
//			+ "      <attributes>\n"
//			+ "        <divisions>16</divisions>\n"
//			+ "        <key>\n"
//			+ "          <fifths>0</fifths>\n"
//			+ "        </key>\n"
//			+ "        <clef>\n"
//			+ "          <sign>TAB</sign>\n"
//			+ "          <line>5</line>\n"
//			+ "        </clef>\n"
//			+ "        <staff-details>\n"
//			+ "          <staff-lines>6</staff-lines>\n"
//			+ "          <staff-tuning line=\"1\">\n"
//			+ "            <tuning-step>E</tuning-step>\n"
//			+ "            <tuning-octave>2</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "          <staff-tuning line=\"2\">\n"
//			+ "            <tuning-step>A</tuning-step>\n"
//			+ "            <tuning-octave>2</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "          <staff-tuning line=\"3\">\n"
//			+ "            <tuning-step>D</tuning-step>\n"
//			+ "            <tuning-octave>3</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "          <staff-tuning line=\"4\">\n"
//			+ "            <tuning-step>G</tuning-step>\n"
//			+ "            <tuning-octave>3</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "          <staff-tuning line=\"5\">\n"
//			+ "            <tuning-step>B</tuning-step>\n"
//			+ "            <tuning-octave>3</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "          <staff-tuning line=\"6\">\n"
//			+ "            <tuning-step>E</tuning-step>\n"
//			+ "            <tuning-octave>4</tuning-octave>\n"
//			+ "          </staff-tuning>\n"
//			+ "        </staff-details>\n"
//			+ "      </attributes>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>2</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>6</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>B</step>\n"
//			+ "          <octave>2</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>5</string>\n"
//			+ "            <fret>2</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>4</string>\n"
//			+ "            <fret>2</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>G</step>\n"
//			+ "          <alter>1</alter>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>3</string>\n"
//			+ "            <fret>1</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>B</step>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>2</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>4</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>1</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>B</step>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>2</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>G</step>\n"
//			+ "          <alter>1</alter>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>8</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>eighth</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>3</string>\n"
//			+ "            <fret>1</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "    </measure>\n"
//			+ "    <measure number=\"2\">\n"
//			+ "      <attributes>\n"
//			+ "        <divisions>16</divisions>\n"
//			+ "        <key>\n"
//			+ "          <fifths>0</fifths>\n"
//			+ "        </key>\n"
//			+ "      </attributes>\n"
//			+ "      <note>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>4</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>1</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <chord/>\n"
//			+ "        <pitch>\n"
//			+ "          <step>B</step>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>2</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <chord/>\n"
//			+ "        <pitch>\n"
//			+ "          <step>G</step>\n"
//			+ "          <alter>1</alter>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>3</string>\n"
//			+ "            <fret>1</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <chord/>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>3</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>4</string>\n"
//			+ "            <fret>2</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <chord/>\n"
//			+ "        <pitch>\n"
//			+ "          <step>B</step>\n"
//			+ "          <octave>2</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>5</string>\n"
//			+ "            <fret>2</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "      <note>\n"
//			+ "        <chord/>\n"
//			+ "        <pitch>\n"
//			+ "          <step>E</step>\n"
//			+ "          <octave>2</octave>\n"
//			+ "        </pitch>\n"
//			+ "        <duration>64</duration>\n"
//			+ "        <voice>1</voice>\n"
//			+ "        <type>whole</type>\n"
//			+ "        <notations>\n"
//			+ "          <technical>\n"
//			+ "            <string>6</string>\n"
//			+ "            <fret>0</fret>\n"
//			+ "          </technical>\n"
//			+ "        </notations>\n"
//			+ "      </note>\n"
//			+ "    </measure>\n"
//			+ "  </part>\n"
//			+ "</score-partwise>\n"
//			+ "\n";
//	
//	public static void main(String[] args) throws IOException, ParserConfigurationException, ValidityException, ParsingException, MidiUnavailableException, InvalidMidiDataException{
//		//execute python script to convert musicXML to ABC notation	
//		try {	
//			ProcessBuilder builder = new ProcessBuilder("python", System.getProperty("user.dir") + "~/Documents/xml2abc.py");
//			Process process = builder.start();
//			//get output from python
//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//			
//			
//			String lines = null;
//			while((lines = reader.readLine()) != null) {
//				System.out.println("lines" + lines);
//			}
//			} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		//loading from the file
//		TuneBook book = new TuneBook(new File("/home/vm/Documents/guitar.abc"));
//
//
//		// show details about the tunes that are loaded
//		System.out.println("# of tunes in itsyBitsy.abc : " + book.size());
//
//		// retrieve the specific tune by reference number
//		Tune tune = book.getTune(1);
//		
//		
//		
//		
//		MusicXmlParser parser = new MusicXmlParser();		
//		MidiParserListener midilistener = new MidiParserListener();
//		parser.addParserListener(midilistener);
//		
//		parser.parse(xmlString);
//		
//		ManagedPlayer player = new ManagedPlayer();
//		
//		Sequence seq = midilistener.getSequence();
//		System.out.println(seq.toString());
//		
//		
//		player.start(seq);
//		
//	
//		
//	
//
//
//
////		 display its title
////		System.out.print("Title of #1 is " + tune.getTitles()[0]);
////		 and its key
////		System.out.println(" and is in the key of " + tune.getKey().toLitteralNotation());
//
//		// can export to a file (abc notation)
//		book.saveTo(new File("out.abc"));
//
//
//		// creates a simple midi player to play the melody
////		TunePlayer player = new TunePlayer();
////		player.start();
////		player.getInstrument();
////		player.play(tune);
//		
//		
//		
//		// creates a component that draws the melody on a musical staff 
//		JScoreComponent jscore = new JScoreComponent();
//		jscore.setJustification(true);
//		jscore.setTune(tune);
//		JFrame j = new JFrame();
//		j.add(jscore);
//		j.pack();
//		j.setVisible(true);
//		// writes the score to a JPG file
//		jscore.writeScoreTo(new File("SheetMusic.jpg"));
//		
////		Instead we use the below method to PLAY the music, since the Tune player currently does not work properly
////		MusicXmlParser xmlparser = new MusicXmlParser();
////		StaccatoParserListener stac = new StaccatoParserListener();
////		xmlparser.addParserListener(stac);
////		
////		xmlparser.parse(xmlString);
////
////		Player player = new Player();
////		Pattern pattern = stac.getPattern().setTempo(350).setInstrument("Guitar");
////		player.play(pattern);
//	}
//}