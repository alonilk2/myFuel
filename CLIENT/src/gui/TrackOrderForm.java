package gui;

import common.ClientIF;
import control.ClientController;
import control.TrackOrderController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * This Form class is responsible for the JavaFX start process for Track Order
 * @author Alon Barenboim
 *
 */
public class TrackOrderForm extends Application implements ClientIF {

	//CONTROLLER
	private TrackOrderController tocontroller;
	private ClientController client;

	@Override
	public void start(Stage primaryStage) {
		try {
			tocontroller = new TrackOrderController(client);
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("TrackOrder.fxml"));
			fxmload.setController(tocontroller);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			scene.getStylesheets().add(getClass().getResource("TrackOrderCSS.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public TrackOrderForm(ClientController client, Stage stage) {
		this.client = client;

	}
	/**
	 * This method sends Objects received as a response from server to the controller.
	 */
	public boolean sendToController(Object obj) {
		return tocontroller.getMessageFromUI(obj);
	}
}
