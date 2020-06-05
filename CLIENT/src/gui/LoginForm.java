package gui;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Entity.Customer;
import Entity.Employee;
import Entity.User;
import control.ClientController;
import control.LoginController;
import common.ClientIF;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class LoginForm extends Application implements ClientIF {
	
	
	ClientController client;
	LoginController loginctrl;
	private String host = "localhost";
	@Override
	public void start(Stage primaryStage) {
		try {
			loginctrl.setClientIF(this);
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("LoginFXML.fxml"));
			fxmload.setController(loginctrl);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,384,240);
			//scene.getStylesheets().add(getClass().getResource("loginCSS.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			loginctrl.setMainStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public LoginForm() 
	{
		loginctrl = new LoginController(host, this);
	}
	
	public static void main(String[] args) {
		launch(args);
	    LoginForm chat= new LoginForm();
    }
	
	@Override
	public boolean sendToController(Object obj) {
		try {
			loginctrl.getObjectFromUI(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}

