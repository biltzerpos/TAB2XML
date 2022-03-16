package GUI;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import instruments.Guitar;
import instruments.Drumset;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Window;
import javafx.stage.Stage;
import models.ScorePartwise;
import models.measure.Measure;

public class PreviewMusic extends Application {

	private MainViewController mvc;
	@FXML
	private Pane pane;
	public Window convertWindow;
	@FXML
	private Button printButton;
	final BooleanProperty printButtonPressed = new SimpleBooleanProperty(false);
	private ScorePartwise scorePartwise;
	@FXML
	private AnchorPane anchorPane;
	@FXML
	TextField gotoMeasureField;
	private Guitar guitar;
	private Drumset drum;
	@FXML
	private Button closePreviewButton;

	public PreviewMusic() {
	}

	@FXML
	public void initialize() {
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	// method to update the preview window
	public void update() throws IOException {
		// Get the ScorePartwise object directly

		scorePartwise = mvc.converter.getScorePartwise();

		/*
		 * Get the list of measures from the ScorePartwise object.
		 *
		 * We get the list of Parts, there should be only one Part in this list, so we
		 * get the first item, which is the Part, then we get the measures from that
		 * Part.
		 */
		String instrument = getInstrument();
		if (instrument == "Guitar") {
			int spacing = 50;
			this.guitar = new Guitar(scorePartwise, pane, spacing);
			this.guitar.drawGuitar();
		} else if (instrument == "Drumset") {
			this.drum = new Drumset(scorePartwise, pane);
			this.drum.draw();
		}

	}

	// Method that handles the `print music sheet` button
	@FXML
	public <printButtonPressed> void printHandle() {

		WritableImage screenshot = anchorPane.snapshot(null, null);
		Printer printer = Printer.getDefaultPrinter();
		PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);

		double pagePrintableWidth = layout.getPrintableWidth();
		double pagePrintableHeight = layout.getPrintableHeight();

		final double scaleX = pagePrintableWidth / screenshot.getWidth();
		final double scaleY = pagePrintableHeight / screenshot.getHeight();
		final ImageView print_node = new ImageView(screenshot);
		print_node.getTransforms().add(new Scale(scaleX, scaleX));

		PrinterJob printSheet = PrinterJob.createPrinterJob();

		if (printSheet != null && printSheet.showPrintDialog(pane.getScene().getWindow())) {

			double numberOfPages = Math.ceil(scaleX / scaleY);

			Translate gridTransform = new Translate(0, 0);
			print_node.getTransforms().add(gridTransform);
			for (int i = 0; i < numberOfPages; i++) {
				gridTransform.setY(-i * (pagePrintableHeight / scaleX));
				printSheet.printPage(layout, print_node);
			}

			printSheet.endJob();

		}

	}

	// Method that handles `play note` button
	@FXML
	public void playHandle() {
		String instrument = getInstrument();
		if (instrument == "Guitar") {
			this.guitar.playGuitarNote(); // Play method in Guitar class
		} else if (instrument == "Drumset") {
			this.drum.playDrumNote(); // Play method in Drumset class
		}
	}

	// Method that handle navigating to specific measure (1- size of measure list)
	// through 'Go' button
	public void handleGotoMeasure() {
		int measureNumber = Integer.parseInt(gotoMeasureField.getText());
		String instrument = getInstrument();
		// System.out.println("instrument:" + instrument);
		int count = 1;
		boolean measureFound = false;
		if (instrument == "Guitar") {
			List<Measure> measureList = this.guitar.getMeasureList();
			for (Iterator<Measure> iterator = measureList.iterator(); iterator.hasNext();) {
				Measure measure = (Measure) iterator.next();
				if (measureNumber == count) {
					this.guitar.highlightMeasureArea(measure);
					measureFound = true;
					break;
				}
				count++;
			}
		} else if (instrument == "Drumset") {
			List<Measure> measureList = this.drum.getMeasureList();
			for (Iterator<Measure> iterator = measureList.iterator(); iterator.hasNext();) {
				Measure measure = (Measure) iterator.next();
				if (measureNumber == count) {
					this.drum.highlightMeasureArea(measure);
					measureFound = true;
					break;
				}
				count++;
			}
		}
		if (!measureFound) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Measure " + measureNumber + " could not be found.");
			alert.setHeaderText("Preview Music Sheet");
			alert.show();
		}
	}

	// Method that handles `close` button
	@FXML
	public void closePreviewHandle() {
		// mvc.editHandle();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close this Preview window?",
				ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Close preview window");
		alert.setHeaderText("You are about to close the Preview window!");
		Optional<ButtonType> o = alert.showAndWait();

		if (o.get() == ButtonType.YES) {
			mvc.convertWindow.hide();
		}

	}

	// return a string representation of the instrument
	private String getInstrument() {
		String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
		return instrument;

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

	}
}