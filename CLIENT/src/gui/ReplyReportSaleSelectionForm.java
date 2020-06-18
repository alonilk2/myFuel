package gui;

import common.ClientIF;
import control.ClientController;
import control.ReplyReportSaleSelectionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class loads the fxml file and connects it to the controller (for Reply/Review report form - saleID selection process)
 * 
 * @author team 14 , 2020
 *
 */
public class ReplyReportSaleSelectionForm extends Application implements ClientIF{
	
	private ClientController client;				           // a general controller for handling alerts, messages, etc.
	private ReplyReportSaleSelectionController rrssController; // the specific controller of this window
	private Stage mainStage;								   // the stage upon which the form will be presented
	

	/**
	 * this method is used to set up the fxml file of the window.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.mainStage = primaryStage;
		this.rrssController = new ReplyReportSaleSelectionController(this.client, mainStage);
		
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("ReplyReportSaleSelection.fxml"));
			fxmload.setController(rrssController);
			
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * A constructor for the form.
	 * 
	 * @param client - an instance of a ClientController
	 * @param stage - the current stage on which we are presenting windows.
	 * @throws Exception
	 */
	public ReplyReportSaleSelectionForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}

	/**
	 * This method sends an object to the controller of the window through the form
	 * @param obj - the object to be sent
	 */
	@Override
	public boolean sendToController(Object obj) {
		rrssController.getObjectFromUI(obj);
		return false;
	}

}
