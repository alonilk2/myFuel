package gui;

import common.ClientIF;
import control.ClientController;
import control.mainStationManegerController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainStationManager extends Application implements ClientIF {
	//
	
	private mainStationManegerController msc;
	private Stage mainStage;
	private String host = "localhost";
	private ClientController client;
	public mainStationManager(ClientController client, Stage main) {
 		this.msc = new mainStationManegerController(host, this, client);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("mainStationManager.fxml"));
			fxmload.setController(msc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
					primaryStage.setScene(scene);
					primaryStage.show();
					mainStage = primaryStage;
			    }
			});
			msc.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		/*try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("mainStationManager.fxml"));
			fxmload.setController(msc);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,1280,800);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show(); 
			this.mainStage = primaryStage;
			msc.setStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}

	private Stage getMainStage() {
		return this.mainStage;
	}
	@Override
	public boolean sendToController(Object obj) {

		return false;
	}
}
