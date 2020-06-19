package gui;

import common.ClientIF;
import control.ClientController;
import control.ReviewReportController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class loads the fxml file and connects it to the controller (for Reply/Review report form - report view process)
 * 
 * @author team 14 , 2020
 *
 */
public class ReviewReportForm extends Application implements ClientIF{
	
	private ClientController client;			   // a general controller for handling alerts, messages, etc.
	private ReviewReportController rnmmController; // the specific controller of this window
	private String SaleID;						   // The ID of the sale that the user chose using the previous window (ReplyReportSaleSelection)
	
	
	/**
	 * this method is used to set up the fxml file of the window.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.rnmmController = new ReviewReportController(this.client, SaleID);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("ReplyReportMarketingManager.fxml"));
			fxmload.setController(rnmmController);
			
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
	 * @param SaleID - the ID of the sale that the user chose using the previous window (ReplyReportSaleSelection)
	 * @throws Exception
	 */
	public ReviewReportForm(ClientController client, Stage stage, String SaleID) throws Exception {
		this.client = client;
		this.SaleID = SaleID;
	}

	/**
	 * This method sends an object to the controller of the window through the form
	 * @param obj - the object to be sent
	 */
	@Override
	public boolean sendToController(Object obj) {
		rnmmController.getMessageFromUI(obj);
		return false;
	}

}
