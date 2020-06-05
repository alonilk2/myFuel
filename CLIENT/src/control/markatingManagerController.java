package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.SetUpdatePriceForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ocsf.server.ConnectionToClient;

public class markatingManagerController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	private static final int DEFAULT_PORT = 5555;
	
	@FXML
	private Button SetUpdetePrice;
	
	@FXML
	private void onSet(ActionEvent event) throws Exception {
		
		SetUpdatePriceForm newform = new SetUpdatePriceForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	

	@FXML
	private void onHome(ActionEvent event) throws Exception {

	}
	
	@FXML
	private void onGenerating(ActionEvent event) throws Exception {

	}
	
	
	@FXML
	private void OnStart(ActionEvent event) throws Exception {

	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void setStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	public markatingManagerController(String args, Object ClientUI, ClientController client) {
		this.client = client;	
	}

}
