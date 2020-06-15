package gui;

import Entity.User;
import common.ClientIF;
import control.ClientController;
import control.start_a_sale_Controller;
import control.SetUpdatePriceController;
import control.mainAdminController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class start_a_saleForm extends Application implements ClientIF {
	//
	private Stage mainStage;
	private ClientController client;
	private start_a_sale_Controller startControl;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.startControl = new start_a_sale_Controller(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("start_a_sale.fxml"));
			fxmload.setController(startControl);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public start_a_saleForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}
	


	@Override
	public boolean sendToController(Object obj) {
		startControl.getObjectFromUI(obj);
		return false;
	}
}
