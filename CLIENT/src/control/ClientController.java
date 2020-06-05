package control;

import ocsf.client.*;
import common.*;
import gui.mainCustomer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

import Entity.Customer;
import Entity.User;
import Entity.Employee;

public class ClientController extends AbstractClient
{
		//clientUI is the current Form that the client see's
	  private ClientIF clientUI; 
	  	//mainPage is the main form of the client, ex: mainCustomer for customer, etc...
	  private ClientIF mainPage;
	  //NEED TO PULL FROM SERVER AFTER LOG-IN, EMPLOYEE \ CUSTOMER \ ...
	  private User currentProfile;
	public User getCurrentProfile() {
		return currentProfile;
	}
	public void setCurrentProfile(User currentProfile) {
		this.currentProfile = currentProfile;
	}
	public Stage getMainStage() {
		return mainStage;
	}
	public void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	private Stage mainStage;
  public ClientController(String host, int port, Object clientUI) throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = (ClientIF)clientUI;
    this.mainPage = (ClientIF)clientUI;
    openConnection();
  }
  public void setClientIF(ClientIF newval) {
	  this.clientUI = newval;
  }
  public ClientIF getMainPage() {
	  return mainPage;
  }

public void setMainPage(ClientIF mainPage) {
	this.mainPage = mainPage;
}
public void handleMessageFromServer(Object msg) 
  {
		if(msg instanceof Boolean) {
		  displayAlert((Boolean)msg, null);
	  }
	  else if(msg instanceof List) {
		  clientUI.sendToController(msg);
	  }
	  else if(msg instanceof User) {
		  this.currentProfile = (User)msg;
		  clientUI.sendToController(msg);
		  
	  }
  }
	  

  public void displayAlert(Boolean b, String message) {
	  Platform.runLater(new Runnable(){
		@Override
		public void run() {
			  if(b == true) {
				  Alert a = new Alert(AlertType.CONFIRMATION);
				  if(message==null)
					  a.setContentText("Action Successfully Completed");
				  else a.setContentText(message);
				  a.show();
			  } else {
				  Alert a = new Alert(AlertType.ERROR);
				  if(message==null)
					  a.setContentText("Requested Action Aborted");
				  else a.setContentText(message);
				  a.show();
			  }
		}});
  }
  public void handleMessageFromClientUI(String message)  
  {
	try
	{
		sendToServer(message);
	}
	catch(IOException e)
	{	
		displayAlert(false, "Error: Couldn't handle message from client UI");
		quit();
	}
  }
  
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
