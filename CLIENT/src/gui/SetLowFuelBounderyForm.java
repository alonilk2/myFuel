package gui;

import common.ClientIF;
import control.ClientController;
import control.SetLowFuelBoundController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/////did it
public class SetLowFuelBounderyForm extends Application implements ClientIF{
	private ClientController client;
	private SetLowFuelBoundController setLowController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setLowController = new SetLowFuelBoundController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("SetLowFuelBound.fxml"));
			fxmload.setController(setLowController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public SetLowFuelBounderyForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}
	


	@Override
	public boolean sendToController(Object obj) {
		setLowController.getObjectFromUI(obj);
		return false;
	}

	
}
