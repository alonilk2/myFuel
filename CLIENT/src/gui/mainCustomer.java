 package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.mainCustomerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainCustomer extends Application implements ClientIF {
	//
	mainCustomerController mcc;
	private Stage mainStage;
	private User clientProfile;
	private ClientController client;
	private String host = "localhost";
	public mainCustomer(ClientController client, Stage main) {
		this.mcc = new mainCustomerController(host, this, client);
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
			fxmload.setLocation(getClass().getResource("mainCustomer.fxml"));
			fxmload.setController(mcc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
					primaryStage.setScene(scene);
					primaryStage.show();
					mainStage = primaryStage;
					mcc.setStage(primaryStage);
			    }
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		mcc.getObjectFromUI(obj);
		return false;
	}
}
