package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.OrderFromSupplier;
import Entity.OrderStatus;
import Entity.Request;
import common.ClientIF;
import gui.ApproveOrderSupplierForm;
import gui.IncomeReportForm;
import gui.ItemQuantityReportForm;
import gui.PurchaseReportForm;
import gui.SetLowFuelBounderyForm;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.LabelBuilder;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class mainStationManegerController implements Initializable {
	private ClientController client;
	private Stage mainStage;
	@FXML 
	private Button setLowFuelBoundary;
	@FXML 
	private Button confirmNewOrders;
	@FXML 
	private Button incomeReport;
	@FXML 
	private Button quantityReport;
	@FXML 
	private Button purchaseReport;
	private Integer counter = 0;
	@FXML
	private void onLowFuelBound(ActionEvent event) throws Exception {
	    SetLowFuelBounderyForm newform = new SetLowFuelBounderyForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onQuantityReport(ActionEvent event) throws Exception {
		ItemQuantityReportForm newform = new ItemQuantityReportForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	@FXML
	private void onIncomeReport(ActionEvent event) throws Exception {
		IncomeReportForm newform = new IncomeReportForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	
	@FXML
	private void onConfirmNewOrders(ActionEvent event) throws Exception {
		ApproveOrderSupplierForm newform = new ApproveOrderSupplierForm(client, mainStage);
		client.setClientIF(newform);
		newform.start(mainStage);
	}
	@FXML
	private void onQuarterlyReport(ActionEvent event) throws Exception {

	}
	@FXML
	private void onHomePageClick(ActionEvent event) throws Exception {
		client.getMainPage().start(client.getMainStage());
		client.setClientIF(client.getMainPage());
	}
	@FXML
	private void onLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
	}
	
	@FXML
	private void onPurchaseReport(ActionEvent event) throws Exception {
		  PurchaseReportForm newform = new PurchaseReportForm(client, mainStage);
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
	public mainStationManegerController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
		getDataFromDB();
	}
	public void getDataFromDB() {
		counter = 0;
		String msg = "pull orderfromsupplier";
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setTableDataFromDB(List<OrderFromSupplier> list) {
		counter = 0;
		for(OrderFromSupplier l : list) {
			if(l.getOrderStatus().equals(OrderStatus.PREPARING)) {
				counter++;
			}
		}			
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
				confirmNewOrders.setContentDisplay(ContentDisplay.CENTER);
		        Label label1 = new Label("Confirm New Orders (");
		        label1.setStyle("-fx-text-fill: black;");

		        Label label2 = new Label(counter.toString());
		        label2.setStyle("-fx-text-fill: red;");

		        Label label3 = new Label(")");
		        label3.setStyle("-fx-text-fill: black;");

		        Region region1 = new Region();
		        HBox.setHgrow(region1, Priority.ALWAYS);

		        Region region2 = new Region();
		        HBox.setHgrow(region2, Priority.ALWAYS);

		        HBox coloredTextBox = new HBox(label1, region1, label2, region2, label3);
		        confirmNewOrders.setGraphic(coloredTextBox);
		    }
		});
	}
	public void getObjectFromUI(Object msg) {
		if(msg instanceof List)
			setTableDataFromDB((List<OrderFromSupplier>)msg);
		return;
	}
}