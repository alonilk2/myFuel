package control;

import ocsf.client.*;
import common.*;
import gui.mainCustomer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.List;

import Entity.Customer;
import Entity.User;
import Entity.Employee;
/**
 * This controller class contains the methods that links between the client and the server
 * and also the client's unique Stage 
 * @author Alon Barenboim
 *
 */
public class ClientController extends AbstractClient
{
		//clientUI is the current Form that the client see's
	  private ClientIF clientUI; 
	  	//mainPage is the main form of the client, ex: mainCustomer for customer, etc...
	  private ClientIF mainPage;
	  //NEED TO PULL FROM SERVER AFTER LOG-IN, EMPLOYEE \ CUSTOMER \ ...
	  private User currentProfile;
	private Stage mainStage;

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

	  public ClientController(String host, int port, Object clientUI, Stage mainStage) throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    this.clientUI = (ClientIF)clientUI;
	    this.mainPage = (ClientIF)clientUI;
	    this.mainStage = mainStage;
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

		/**
		 * This method used to restart the client's application after log-out request.
		 * @param runBeforeRestart
		 * @throws IOException
		 */
		public void restartApplication(Runnable runBeforeRestart) throws IOException {
			try {
				String java = System.getProperty("java.home") + "/bin/java";
				List<String> vmArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
				StringBuffer vmArgsOneLine = new StringBuffer();
				for (String arg : vmArguments) {
					if (!arg.contains("-agentlib")) {
						vmArgsOneLine.append(arg);
						vmArgsOneLine.append(" ");
					}
				}
				final StringBuffer cmd = new StringBuffer("\"" + java + "\" " + vmArgsOneLine);
				String[] mainCommand = System.getProperty("sun.java.command").split(" ");
				if (mainCommand[0].endsWith(".jar")) {
					cmd.append("-jar " + new File(mainCommand[0]).getPath());
				} else {
					cmd.append("-cp \"" + System.getProperty("java.class.path") + "\" " + mainCommand[0]);
				}
				for (int i = 1; i < mainCommand.length; i++) {
					cmd.append(" ");
					cmd.append(mainCommand[i]);
				}
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						try {
							Runtime.getRuntime().exec(cmd.toString());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			if (runBeforeRestart!= null) {
				runBeforeRestart.run();
			}
		System.exit(0);
		} catch (Exception e) {
			throw new IOException("Error while trying to restart the application", e);
		}
	}
		/**
		 * This method handle objects received from the server.
		 * @param msg The object that was transferred.
		 */
	public void handleMessageFromServer(Object msg) 
	  {
		if(msg instanceof Boolean) {
			  displayAlert((Boolean)msg, null);
		  }
		  else if(msg instanceof List || msg instanceof double[]) {
			  clientUI.sendToController(msg);
		  }
		  else if(msg instanceof User) {
			  this.currentProfile = (User)msg;
			  clientUI.sendToController(msg);
			  
		  }
	  }
	  
	/**
	 * This method triggers an alert for the specific client.
	 * @param b True = Success alert. False = Failed alert.
	 * @param message Optional - message to be displayed inside the alert window.
	 */
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
	/**
	 * This method handles request to send a message to the server.
	 * @param message The message to the server.
	 */
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
