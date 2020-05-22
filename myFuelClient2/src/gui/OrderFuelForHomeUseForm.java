package gui;

import common.ClientIF;
import control.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrderFuelForHomeUseForm extends Application implements ClientIF{
	private ClientController client;
	private OrderFuelForHomeUseController ofhController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.ofhController = new OrderFuelForHomeUseController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("orderFuelForHomeUse.fxml"));
			fxmload.setController(ofhController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("prototype.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public OrderFuelForHomeUseForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		ofhController.getObjectFromUI(obj);
		return false;
	}

}
