package control;

import java.net.URL;
import java.util.ResourceBundle;

import common.ClientIF;
import gui.SetLowFuelBounderyForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class mainStationManegerController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	@FXML 
	private Button LowFuelBound;
	@FXML
	private void onLowFuelBound(ActionEvent event) throws Exception {
	    SetLowFuelBounderyForm newform = new SetLowFuelBounderyForm(client, mainStage);
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
	public mainStationManegerController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
	}

}
