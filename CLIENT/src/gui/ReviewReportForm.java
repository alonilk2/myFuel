package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Entity.Customer;
import Entity.Employee;
import Entity.User;
import control.ClientController;
import control.LoginController;
import control.ReviewReportController;
import common.ClientIF;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class ReviewReportForm extends Application implements ClientIF {
	ClientController client;
	LoginController rvwctrl;
	private String host = "localhost";
	@Override
	public void start(Stage primaryStage) {
		try {
			rvwctrl.setClientIF(this);
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("ReplyReportMarketingManager.fxml"));
			fxmload.setController(rvwctrl);
			AnchorPane root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			//scene.getStylesheets().add(getClass().getResource("loginCSS.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			rvwctrl.setMainStage(primaryStage);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public ReviewReportForm() 
	{
		this.client = client;
	}
	
	public static void main(String[] args) {
		launch(args);
	    ReviewReportForm chat= new ReviewReportForm();
    }
	
	@Override
	public boolean sendToController(Object obj) {
		try {
			rvwctrl.getObjectFromUI(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}



