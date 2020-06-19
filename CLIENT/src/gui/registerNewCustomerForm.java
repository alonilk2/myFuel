package gui;

import common.ClientIF;
import control.ClientController;
import control.RegisterNewCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class registerNewCustomerForm extends Application implements ClientIF{
	private ClientController client;
	private RegisterNewCustomerController rncController;
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
	
	public registerNewCustomerForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		rncController.getObjectFromUI(obj);
		return false;
	}

}