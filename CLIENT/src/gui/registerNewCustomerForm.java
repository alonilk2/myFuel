package gui;

import common.ClientIF;
import control.ClientController;
import control.RegisterNewCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * This class loads the fxml file and connects it to the controller (for register a new customer process)
 * 
 * @author team 14 , 2020
 *
 */
public class registerNewCustomerForm extends Application implements ClientIF{
	
	private ClientController client;				      // a general controller for handling alerts, messages, etc.
	private RegisterNewCustomerController rncController;  // the specific controller of this window
	
	
	/**
	 * this method is used to set up the fxml file of the window.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.rncController = new RegisterNewCustomerController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("registerNewCustomer.fxml"));
			fxmload.setController(rncController);
			
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
	public registerNewCustomerForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	/**
	 * This method sends an object to the controller of the window through the form
	 * @param obj - the object to be sent
	 */
	@Override
	public boolean sendToController(Object obj) {
		rncController.getObjectFromUI(obj);
		return false;
	}

}
