package gui;

import control.ClientController;
import control.PurchaseReportController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import common.ClientIF;
import control.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class loads the fxml file and connects it to the controller (for viewing the quarterly purchase report process)
 * 
 * @author team 14 , 2020
 *
 */
public class PurchaseReportForm extends Application implements ClientIF {
	
	private ClientController client;			    // a general controller for handling alerts, messages, etc.
	private PurchaseReportController prccontroller; // the specific controller of this window

	/**
	 * this method is used to set up the fxml file of the window.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			prccontroller = new PurchaseReportController(client);
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("PurchaseReport.fxml"));
			fxmload.setController(prccontroller);
			
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			
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
	public PurchaseReportForm(ClientController client, Stage stage) {
		this.client = client;

	}
	
	/**
	 * This method sends an object to the controller of the window through the form
	 * @param obj - the object to be sent
	 */
	public boolean sendToController(Object obj) {
		return prccontroller.getMessageFromUI(obj);
	}

}
