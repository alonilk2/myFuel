 package gui;

import common.ClientIF;
import control.mainCustomerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainCustomer extends Application implements ClientIF {
	//
	mainCustomerController mcc;
	private Stage mainStage;
	private String host = "localhost";
	public mainCustomer() {
		this.mcc = new mainCustomerController(host, this);
	}
	public static void main(String[] args) {
		mainCustomer mc = new mainCustomer();
		launch(args);
	}
	public Stage getMainStage() {
		return mainStage;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("mainCustomer.fxml"));
			fxmload.setController(mcc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			this.mainStage = primaryStage;
			mcc.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		return false;
	}
}
