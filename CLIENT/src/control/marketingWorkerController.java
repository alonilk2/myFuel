package control;

import java.net.URL;
import java.util.ResourceBundle;

import common.ClientIF;
import gui.SetUpdatePriceForm;
import gui.marketingWorker;
import gui.saleTemplateForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class marketingWorkerController  implements Initializable {
	private ClientController client;
	private Stage mainStage;
	private static final int DEFAULT_PORT = 5555;
	
	@FXML
	private Button create;
	
	@FXML
	private void onCreate(ActionEvent event) throws Exception {
		saleTemplateForm newform = new saleTemplateForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	public void setStage(Stage mainStage) {
		this.mainStage = mainStage;
	}
	

	public marketingWorkerController(String host, ClientIF clientUI, ClientController client) {
		this.client = client;
	}


	public void getObjectFromUI(Object obj) {
		// TODO Auto-generated method stub
		
	}

}

