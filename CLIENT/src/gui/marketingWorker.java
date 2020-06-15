 package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.marketingWorkerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class marketingWorker extends Application implements ClientIF {
	
	marketingWorkerController mw;
	private Stage mainStage;
	private User clientProfile;
	private ClientController client;
	private String host = "localhost";
	
	
	public marketingWorker(ClientController client, Stage main) {
		this.mw = new marketingWorkerController(host, this, client);
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
			fxmload.setLocation(getClass().getResource("marketingWorker.fxml"));
			fxmload.setController(mw);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,654,490);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
					primaryStage.setScene(scene);
					primaryStage.show();
					mainStage = primaryStage;
					mw.setStage(primaryStage);
			    }
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		mw.getObjectFromUI(obj);
		return false;
	}
}