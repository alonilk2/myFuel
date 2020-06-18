package gui;

import common.ClientIF;
import control.ClientController;
import control.FastFuelController;
import control.RegisterNewCarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * This form class handles the JavaFX start process for Fast Fuel
 * @author Alon Barenboim
 *
 */
public class FastFuelForm extends Application implements ClientIF{
	private ClientController client;
	private FastFuelController ffController;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.ffController = new FastFuelController(this.client);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("FastFuel.fxml"));
			fxmload.setController(ffController);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public FastFuelForm(ClientController client, Stage stage) throws Exception {
		this.client = client;
	}


	@Override
	public boolean sendToController(Object obj) {
		ffController.getObjectFromUI(obj);
		return false;
	}

}