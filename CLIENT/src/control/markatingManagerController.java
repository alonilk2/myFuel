package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import gui.ReplyReportSaleSelectionForm;
import gui.SetUpdatePriceForm;
import gui.start_a_saleForm;
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
	private Button setUpdatePriceBtn;

	@FXML
	private Button startSaleBtn;
	@FXML
	private Button reviewReportBtn;
	@FXML
	private Button personificReportBtn;
	
	@FXML
	private void onUpdatePriceClick(ActionEvent event) throws Exception {
		
		SetUpdatePriceForm newform = new SetUpdatePriceForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	

	@FXML
	private void onHome(ActionEvent event) throws Exception {

	}
	
	@FXML
	private void onReviewReportClick(ActionEvent event) throws Exception {
		ReplyReportSaleSelectionForm newform = new ReplyReportSaleSelectionForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}

	@FXML
	private void onPersonificReportClick(ActionEvent event) throws Exception {

	}
	
	@FXML
	private void onStartSaleClick(ActionEvent event) throws Exception {
		start_a_saleForm newform = new start_a_saleForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);

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
