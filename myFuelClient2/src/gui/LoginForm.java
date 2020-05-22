package gui;
	
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


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
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("LoginFXML.fxml"));
			fxmload.setController(loginctrl);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,1280,800);
			scene.getStylesheets().add(getClass().getResource("loginCSS.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void accept() 
	{
	  try
	  {
		  BufferedReader fromConsole = 
	      new BufferedReader(new InputStreamReader(System.in));
	      String message;
	
	      while (true) 
	      {
	        message = fromConsole.readLine();
	        client.handleMessageFromClientUI(message);
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	      ("Unexpected error while reading from console!");
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
		// TODO Auto-generated method stub
		return false;
	}
}

