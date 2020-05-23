package control;

import ocsf.client.*;
import common.*;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

import Entity.Customer;

public class ClientController extends AbstractClient
{
	  private ClientIF clientUI; 
	  private ClientIF mainPage;
	  //NEED TO PULL FROM SERVER AFTER LOG-IN IF IS A CUSTOMER
	  private Customer customer;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
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
    sendToServer("login alonilk2 1234");
  }
  public void setClientIF(ClientIF newval) {
	  this.clientUI = newval;
  }
  public ClientIF getMainPage() {
	  return mainPage;
  }

public void handleMessageFromServer(Object msg) 
  {
	  if(msg instanceof Boolean) {
		  displayAlert((Boolean)msg, null);
	  }
	  else if(msg instanceof List) {
		  clientUI.sendToController(msg);
	  }
	  else if(msg instanceof Customer) {
		  this.customer = (Customer)msg;
		  
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
