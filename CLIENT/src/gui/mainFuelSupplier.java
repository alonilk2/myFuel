 package gui;

import common.ClientIF;
import control.ClientController;
import control.mainCustomerController;
import control.mainFuelSupplierController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainFuelSupplier extends Application implements ClientIF {
	//
	mainFuelSupplierController mfsc;
	private Stage mainStage;
	private ClientController client;
	private String host = "localhost";
	public mainFuelSupplier(ClientController client, Stage main) {
		this.mfsc = new mainFuelSupplierController(host, this, client);
		this.client = client;
	}
	public Stage getMainStage() {
		return mainStage;
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("mainFuelSupplier.fxml"));
			fxmload.setController(mfsc);
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean sendToController(Object obj) {
		mfsc.getObjectFromUI(obj);
		return false;
	}
}