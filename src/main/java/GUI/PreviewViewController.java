package GUI;

import com.itextpdf.kernel.pdf.PdfDocument;
import custom_exceptions.TXMLException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import player.MXLPlayer;
import visualizer.Visualizer;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PreviewViewController extends Application {
	@FXML ImageView pdfViewer;
	@FXML TextField playMeasureField;

	private MainViewController mvc;
	private PdfDocument pdf;
	private int pageNumber = 0;
	private Visualizer visualizer;
	private MXLPlayer player;
	public void setMainViewController(MainViewController mvcInput) {
		mvc = mvcInput;
	}
	public void update() throws TXMLException, FileNotFoundException {
		this.player = new MXLPlayer(mvc.converter.getScore());
		this.visualizer = new Visualizer(mvc.converter.getScore());
		pdf = visualizer.draw();
	}
	private void initPdf(PdfDocument pdf){
		this.pdf = pdf;
	}
	@FXML
	private void gotoMeasureHandler(){

	}
	@FXML
	private void exportPDFHandler(){

	}
	@FXML
	private void LastPageHandler(){

	}
	@FXML
	private void NextPageHandler(){

	}
	@FXML
	private void goToPageHandler(){

	}
	@FXML
	private void playHandler(){
		int measureNumber = Integer.parseInt(playMeasureField.getText() );
		player.play(-1,measureNumber,-1);
	}
	private void goToPage(int page) throws IOException {
		//find a way to render given page into image
	}
	@Override
	public void start(Stage primaryStage) throws Exception {}
}

