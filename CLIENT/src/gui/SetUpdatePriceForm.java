package gui;

import common.ClientIF;
import control.ClientController;
import control.SetUpdatePriceController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SetUpdatePriceForm extends Application implements ClientIF{

	private ClientController client;
	private SetUpdatePriceController setUpPriceController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.setUpPriceController = new SetUpdatePriceController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("set_Update_Price.fxml"));
			fxmload.setController(setUpPriceController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,700,500);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public SetUpdatePriceForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}
	


	@Override
	public boolean sendToController(Object obj) {
		setUpPriceController.getObjectFromUI(obj);
		return false;
	}
}
