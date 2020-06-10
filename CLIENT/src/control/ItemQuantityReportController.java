package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.CustomerDuringSale;
import Entity.FuelType;
import Entity.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemQuantityReportController {
	//Controllers
		ClientController client;
		CustomerDuringSale cds;
		@FXML
		private Button ConfirmBtn;
		@FXML
		private TableView<FuelType> tableview;
		@FXML
		private TableColumn<FuelType, String> customerTotalSumOfPurchases;
		@FXML
		private TableColumn<FuelType, Double> saleID;
		@FXML 
		private Button homepagebutton;
		private ObservableList<FuelType> olist;
		@FXML
		private void onHomePageClick(ActionEvent event) throws Exception {
			client.getMainPage().start(client.getMainStage());
			client.setClientIF(client.getMainPage());
		}
		
		@FXML
		private void onConfirmClick(ActionEvent event){
			try {
				client.getMainPage().start(client.getMainStage());
			} catch (Exception e) {
				client.displayAlert(false, null);
				e.printStackTrace();
			}
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			try {
				getTableDataFromDB();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void getTableDataFromDB() throws IOException {
			Customer customer = (Customer)client.getCurrentProfile();
			String msg = "pull CustomerDuringSale "+cds.getSaleID();
			Request req = new Request(msg);
			client.sendToServer(req);
		}
		
		//Handle objects sent from UI
		@SuppressWarnings("unchecked")
		public boolean getMessageFromUI(Object obj) {
			if(obj instanceof List) {
				setTableDataFromDB((List<CustomerDuringSale>)obj);
				return true;
			}
			return false;
		}
		
		public void setTableDataFromDB(List<CustomerDuringSale> list) {
			olist = FXCollections.observableArrayList();
			for(CustomerDuringSale l : list)
				olist.add(l);
			//JavaFX
			//Injection
			customerTotalSumOfPurchases.setCellValueFactory(new PropertyValueFactory<CustomerDuringSale,Integer>("CustomerTotalSumOfPurchases"));
			saleID.setCellValueFactory(new PropertyValueFactory<CustomerDuringSale,Integer>("SaleID"));
			customerID.setCellValueFactory(new PropertyValueFactory<CustomerDuringSale,Integer>("CustomerID"));
		    tableview.setItems(olist);
		}

		public ItemQuantityReportController(ClientController client) {
			this.client = client;
		}

}
