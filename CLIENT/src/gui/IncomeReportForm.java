package gui;

import control.ClientController;
import control.IncomeReportController;
import control.TrackOrderController;
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

public class IncomeReportForm extends Application implements ClientIF {

	//CONTROLLER
	private IncomeReportController ircontroller;
	private ClientController client;

	@Override
	public void start(Stage primaryStage) {
		try {
			ircontroller = new IncomeReportController(client);
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("IncomeReport.fxml"));
			fxmload.setController(ircontroller);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public IncomeReportForm(ClientController client, Stage stage) {
		this.client = client;

	}
	
	public boolean sendToController(Object obj) {
		return ircontroller.getMessageFromUI(obj);
	}

}
