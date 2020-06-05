package gui;

import common.ClientIF;
import control.ClientController;
import control.RegisterNewCarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class registerNewCarForm extends Application implements ClientIF{
	private ClientController client;
	private RegisterNewCarController rncController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.rncController = new RegisterNewCarController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("registerNewCar.fxml"));
			fxmload.setController(rncController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public registerNewCarForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		rncController.getObjectFromUI(obj);
		return false;
	}

}