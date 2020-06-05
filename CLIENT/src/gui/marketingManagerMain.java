package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.mainAdminController;
import control.mainCustomerController;
import control.markatingManagerController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class marketingManagerMain extends Application implements ClientIF {
	
	private markatingManagerController mmc;
	private Stage mainStage;
	private String host = "localhost";
	private User clientProfile;
	private ClientController client;
	public marketingManagerMain(ClientController client, Stage main) {
		this.client = client;
		this.mainStage = main;
		this.mmc = new markatingManagerController(host, this, client);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("marketingManagerMain.fxml"));
			fxmload.setController(mmc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show(); 
			this.mainStage = primaryStage;
			mmc.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	private Stage getMainStage() {
		return this.mainStage;
	}
	@Override
	public boolean sendToController(Object obj) {

		return false;
	}
	

}
