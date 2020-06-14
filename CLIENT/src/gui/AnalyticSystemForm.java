package gui;

import common.ClientIF;
import control.AnalyticSystemController;
import control.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AnalyticSystemForm extends Application implements ClientIF{
	private ClientController client;
	private AnalyticSystemController ffController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.ffController = new AnalyticSystemController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("AnalyticSystem.fxml"));
			fxmload.setController(ffController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public AnalyticSystemForm(ClientController client) {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		ffController.getMessageFromUI(obj);
		return false;
	}

}