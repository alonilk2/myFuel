package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.HomeFuelOrder;
import Entity.OrderFromSupplier;
import Entity.OrderStatus;
import Entity.Request;
import Entity.User;
import common.ClientIF;
import gui.FastFuelForm;
import gui.OrderFuelForHomeUseForm;
import gui.TrackOrderForm;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
/**
 * This controller class controls the logic behind mainCustomer instance.
 * @author Alon Barenboim
 *
 */
public class mainCustomerController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	@FXML 
	private Button orderfuelhomebtn;
	@FXML 
	private Button trackbutton;
	@FXML 
	private Button fastfuelbutton;
	@FXML
	private Button logoutbutton;
	private Integer counter=0;
	@FXML
	private void onOrderFuelClick(ActionEvent event) throws Exception {
		OrderFuelForHomeUseForm newform = new OrderFuelForHomeUseForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
	}
	@FXML
	private void onFastFuelClick(ActionEvent event) throws Exception {
		FastFuelForm newform = new FastFuelForm(client, mainStage);
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
	public mainCustomerController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
		getDataFromDB();
	}
	public void setTableDataFromDB(List<HomeFuelOrder> list) {
		counter = list.size();	
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	trackbutton.setContentDisplay(ContentDisplay.CENTER);
		        Label label1 = new Label("Track Orders (");
		        label1.setStyle("-fx-text-fill: white; -fx-font: 12px Arial Black; -fx-font-weight: bold;");

		        Label label2 = new Label(counter.toString());
		        label2.setStyle("-fx-text-fill: red; -fx-font: 12px Arial Black;-fx-font-weight: bold;");

		        Label label3 = new Label(")");
		        label3.setStyle("-fx-text-fill: white; -fx-font: 12px Arial Black;-fx-font-weight: bold;");

		        Region region1 = new Region();
		        HBox.setHgrow(region1, Priority.ALWAYS);

		        Region region2 = new Region();
		        HBox.setHgrow(region2, Priority.ALWAYS);

		        HBox coloredTextBox = new HBox(label1, region1, label2, region2, label3);
		        trackbutton.setGraphic(coloredTextBox);
		    }
		});
	}
	public void getDataFromDB() {
		Customer customer = (Customer)client.getCurrentProfile();
		String msg = "pull homefuelorder "+customer.getCustomerID();
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getObjectFromUI(Object msg) {
		if(msg instanceof List)
			setTableDataFromDB((List<HomeFuelOrder>)msg);
		return;
	}
}
