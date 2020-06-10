package control;



import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.Employee;
import Entity.FuelType;
import Entity.Request;
import Entity.Role;
import Entity.User;
import javafx.application.Application;
import common.ClientIF;
import gui.LoginForm;
import gui.OrderFuelForHomeUseForm;
import gui.mainAdmin;
import gui.mainCustomer;
import gui.mainFuelSupplier;
import gui.mainMarketingRepresentative;
import gui.mainStationManager;
import gui.marketingManagerMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	
	private ClientController client;
	private Stage mainStage;
	final public static int DEFAULT_PORT = 5555;
	@FXML
	private TextField userNameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button LgnButton;
	
	private ClientIF lgnForm;

	public LoginController(String args, Object logForm) {
		
	    String host = "";
	    int port = 0;  //The port number
	
	    try
	    {
	    	host = args;
	    }
	    catch(ArrayIndexOutOfBoundsException e)
	    {
	    	host = "localhost";
	    }
	    try 
	    {
	    	client= new ClientController(host, DEFAULT_PORT, logForm);
	    } 
	    catch(IOException exception) 
	    {
	    	System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	    	System.exit(1);
	    }

	}
	
	
	@FXML
	private void LoginButton (ActionEvent event) throws Exception {
		System.out.println("Username: "+userNameField.getText() + " Password: " + passwordField.getText());
		String userName, password;
		userName = userNameField.getText();
		password = passwordField.getText();
		String qry = "login "+ userName + " " + password;
		Request loginRequest = new Request(qry);
		client.sendToServer(loginRequest);
	}

	private void successLogin(User msg) throws Exception {
		if (msg instanceof Employee) {
			if(((Employee) msg).getRole().equals(Role.FUEL_SUPPLIER)) {
				mainFuelSupplier newform = new mainFuelSupplier(client, mainStage);
				client.setClientIF(newform);
				client.setMainPage(newform);
				client.setCurrentProfile((Employee)msg);
				newform.start(mainStage);
			}
			else if(((Employee) msg).getRole().equals(Role.MARKETING_REPRESENTATIVE)) {
				mainMarketingRepresentative newform = new mainMarketingRepresentative(client, mainStage);
				client.setClientIF(newform);
				client.setMainPage(newform);
				client.setCurrentProfile((Employee)msg);
				newform.start(mainStage); 
			}
			else if(((Employee) msg).getRole().equals(Role.ADMINISTRATOR)) {
				mainAdmin newform = new mainAdmin(client, mainStage);
				client.setClientIF(newform);
				client.setMainPage(newform);
				client.setCurrentProfile((Employee)msg);
				newform.start(mainStage); 
			}
			else if(((Employee) msg).getRole().equals(Role.MARKETING_MANAGER)) {
				marketingManagerMain newform = new marketingManagerMain(client, mainStage);
				client.setClientIF(newform);
				client.setMainPage(newform);
				client.setCurrentProfile((Employee)msg);
				newform.start(mainStage); 
			}
			else if(((Employee) msg).getRole().equals(Role.STATION_MANAGER)) {
				mainStationManager newform = new mainStationManager(client, mainStage);
				client.setClientIF(newform);
				client.setMainPage(newform);
				client.setCurrentProfile((Employee)msg);
				newform.start(mainStage); 
			}
		}
		else if (msg instanceof Customer) {
			mainCustomer newform = new mainCustomer(client, mainStage);
			client.setClientIF(newform);
			client.setMainPage(newform);
			client.setCurrentProfile((Customer)msg);
			newform.start(mainStage);
		}
		

		/**
		 * 
		 */
	}
	public void getObjectFromUI(Object msg) throws Exception {
		if(msg instanceof User)
			successLogin((User)msg);
		else if(msg instanceof Boolean) {
			client.displayAlert(false, "Username'\'Password is incorrect!");
		}
	}
	public void setClientIF(ClientIF lgnScreen)
	{
		this.lgnForm = lgnScreen;
	}
	
	
	public Stage getMainStage() {
		return mainStage;
	}


	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    LgnButton.setDefaultButton(true);
	}
}
	
