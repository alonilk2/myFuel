package control;



import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

import gui.LoginForm;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	ClientController client;
	final public static int DEFAULT_PORT = 5555;
	@FXML
	private TextField userNameField;
	@FXML
	private TextField passwordField;
	@FXML
	private Button loginBtn;

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
	private void checkIfUserExist() {
		System.out.println(userNameField.getText() + "ps: " + passwordField.getText());
		String userName, password;
		userName = userNameField.getText();
		password = passwordField.getText();
		String qry = "login "+ userName + " " + password;
		client.handleMessageFromClientUI(qry);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
	
