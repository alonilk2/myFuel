package control;

import java.net.URL;
import java.util.ResourceBundle;
import common.ClientIF;
import gui.AnalyticSystemForm;
import gui.registerNewCarForm;
import gui.registerNewCustomerForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class mainMarketingRepresentativeController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	@FXML 
	private Button registerCustomer;
	@FXML 
	private Button registerCar;
	@FXML 
	private Button analyticbutton;
	@FXML 
	private Button logoutbutton;
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
	@FXML
	private void onAnalyticClick(ActionEvent event) throws Exception {
		AnalyticSystemForm newform = new AnalyticSystemForm(client);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
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
