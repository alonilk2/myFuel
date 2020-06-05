package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.mainAdminController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainAdmin extends Application implements ClientIF {
	//
	mainAdminController mac;
	private Stage mainStage;
	private String host = "localhost";
	private User clientProfile;
	private ClientController client;
	public mainAdmin(ClientController client, Stage main) {
		this.mac = new mainAdminController(host, this, client);
		this.client = client;
		this.mainStage = main;
	}
	public Stage getMainStage() {
		return mainStage;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("mainAdmin.fxml"));
			fxmload.setController(mac);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
				primaryStage.setScene(scene);
				primaryStage.show();
			    }
			});
			this.mainStage = primaryStage;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		mac.getObjectFromUI(obj);
		return false;
	
	}
}
