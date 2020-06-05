package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import Entity.User;
import common.ClientIF;
import gui.registerNewCarForm;
import gui.registerNewCustomerForm;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainMarketingRepresentativeController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	private static final int DEFAULT_PORT = 5555;
	@FXML 
	private Button registerCustomer;
	@FXML 
	private Button registerCar;
	@FXML
	private void onRegCustomerClick(ActionEvent event) throws Exception {
		registerNewCustomerForm newform = new registerNewCustomerForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onRegCarClick(ActionEvent event) throws Exception {
		registerNewCarForm newform = new registerNewCarForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void setStage(Stage mainStage) {
		this.mainStage = mainStage;
    	client.setMainStage(mainStage);
	}
	public mainMarketingRepresentativeController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
	}
	public void getObjectFromUI(Object msg) {

	}

}
