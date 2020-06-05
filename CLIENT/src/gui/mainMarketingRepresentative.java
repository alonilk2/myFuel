 package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.mainMarketingRepresentativeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainMarketingRepresentative extends Application implements ClientIF {
	//
	mainMarketingRepresentativeController mmrc;
	private Stage mainStage;
	private User clientProfile;
	private ClientController client;
	private String host = "localhost";
	public mainMarketingRepresentative(ClientController client, Stage main) {
		this.mmrc = new mainMarketingRepresentativeController(host, this, client);
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
			fxmload.setLocation(getClass().getResource("mainMarketingRepresentative.fxml"));
			fxmload.setController(mmrc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
					primaryStage.setScene(scene);
					primaryStage.show();
					mainStage = primaryStage;
					mmrc.setStage(primaryStage);
			    }
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		mmrc.getObjectFromUI(obj);
		return false;
	}
}