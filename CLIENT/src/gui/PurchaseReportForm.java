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

public class PurchaseReportForm extends Application implements ClientIF {

	//CONTROLLER
	private PurchaseReportController prccontroller;
	private ClientController client;

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

	public PurchaseReportForm(ClientController client, Stage stage) {
		this.client = client;

	}
	
	public boolean sendToController(Object obj) {
		return prccontroller.getMessageFromUI(obj);
	}

}
