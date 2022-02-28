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
	private Guitar g;
	private Drumset d;
	@FXML private Button closePreviewButton;
	

	public PreviewMusic() {
	}

	@FXML
	public void initialize() {
	}

	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}

	//method to update the music sheet
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
		String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
		if (instrument == "Guitar") {
			g = new Guitar(scorePartwise, pane);
			g.drawGuitar();
		} else if (instrument == "Drumset") {
			d = new Drumset(scorePartwise, pane);
			d.draw();
		}

	}

	@FXML
	public <printButtonPressed> void printHandle() {
		printButton.setOnAction(aEvent -> {
			printButtonPressed.set(true);
		});

		WritableImage screenshot = pane.snapshot(null, null);
		Printer printer = Printer.getDefaultPrinter();
		PageLayout layout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT,
				Printer.MarginType.HARDWARE_MINIMUM);

		final double scaleX = layout.getPrintableWidth() / screenshot.getWidth();
		final double scaleY = layout.getPrintableHeight() / screenshot.getHeight();
		final double scale = Math.min(scaleX, scaleY);
		final ImageView print_node = new ImageView(screenshot);
		print_node.getTransforms().add(new Scale(scale, scale));

		PrinterJob printSheet = PrinterJob.createPrinterJob();

		if (printSheet != null && printSheet.showPrintDialog(pane.getScene().getWindow())) {
			boolean printed = printSheet.printPage(print_node);
			if (printed) {
				printSheet.endJob();
			}
		}
	}

	@FXML
	public void playHandle() {
		//ScorePartwise scorePartwise = mvc.converter.getScorePartwise();

		String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
		if (instrument == "Guitar") {
			//Guitar g = new Guitar(scorePartwise, pane);
			g.playGuitarNote();
		}
		else if(instrument == "Drumset") {
			//Drumset d = new Drumset(scorePartwise, pane);
			d.playDrumNote();
		}
	}
	// private Window openNewWindow(Parent root, String windowName) {
	// Stage stage = new Stage();
	// stage.setTitle(windowName);
	// stage.initModality(Modality.APPLICATION_MODAL);
	// stage.initOwner(MainApp.STAGE);
	// stage.setResizable(false);
	// Scene scene = new Scene(root);
	// stage.setScene(scene);
	// stage.show();
	// return scene.getWindow();
	// }

	public void handleGotoMeasure() {
		//System.out.println("Go Button is Clicked");
		int measureNumber = Integer.parseInt(gotoMeasureField.getText());
		// Get the ScorePartwise object directly
		ScorePartwise scorePartwise = mvc.converter.getScorePartwise();
		String instrument = scorePartwise.getPartList().getScoreParts().get(0).getPartName();
		
		//System.out.println("instrument:" + instrument);
		int count = 1;
		if (instrument == "Guitar") {
			List<Measure> measureList = g.getMeasureList();
			for (Iterator iterator = measureList.iterator(); iterator.hasNext();) {
				Measure measure = (Measure) iterator.next();
				if(measureNumber == count) {
					g.highlightMeasureArea(measure);
					break;
				}
				count++;
			}
		} else if (instrument == "Drumset") {
			List<Measure> measureList = d.getMeasureList();
			for (Iterator iterator = measureList.iterator(); iterator.hasNext();) {
				Measure measure = (Measure) iterator.next();
				if(measureNumber == count) {
					d.highlightMeasureArea(measure);
					break;
				}
				count++;
			}
		}
	}
	
	@FXML
	public void closePreviewHandle() {
		//mvc.editHandle(); 
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, 
				"Are you sure you want to close this Preview window?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle("Close preview window");
		alert.setHeaderText("You are about to close the Preview window!");
		Optional<ButtonType> o = alert.showAndWait();
		
		if(o.get() == ButtonType.YES) {
			mvc.convertWindow.hide();
		}
		
		
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
