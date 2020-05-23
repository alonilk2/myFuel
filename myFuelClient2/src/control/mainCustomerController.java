package control;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.Icon;

import gui.OrderFuelForHomeUseForm;
import gui.TrackOrderForm;
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

public class mainCustomerController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	private static final int DEFAULT_PORT = 5555;
	@FXML 
	private Button orderfuelhomebtn;
	@FXML 
	private Button trackbutton;
	@FXML
	private void onOrderFuelClick(ActionEvent event) throws Exception {
		OrderFuelForHomeUseForm newform = new OrderFuelForHomeUseForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onTrackOrderClick(ActionEvent event) throws Exception {
		TrackOrderForm newform = new TrackOrderForm(client, mainStage);
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
	public mainCustomerController(String args, Object ClientUI) {
	    String host = "localhost";
	    try 
	    {
	    	client = new ClientController(host, DEFAULT_PORT, ClientUI);
	    	
	    } 
	    catch(IOException exception) 
	    {
	    	System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	    	System.exit(1);
	    }

	}

}
