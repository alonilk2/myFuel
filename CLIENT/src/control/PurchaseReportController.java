package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.CustomerDuringSale;
import Entity.FuelType;
import Entity.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PurchaseReportController implements Initializable {
	//Controllers
		ClientController client;
		FuelType ft;
		@FXML
		private Button ConfirmBtn;
		@FXML
		private TableView<FuelType> tableview;
		@FXML
		private TableColumn<FuelType, String> fuelType;
		@FXML
		private TableColumn<FuelType, String> purchase;
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
			//Customer customer = (Customer)client.getCurrentProfile();
			String msg = "pull purchasereport";
			Request req = new Request(msg);
			client.sendToServer(req);
		}
		
		//Handle objects sent from UI
		@SuppressWarnings("unchecked")
		public boolean getMessageFromUI(Object obj) {
			if(obj instanceof double[]) {
				setTableDataFromDB((double[])obj);
				return true;
			}
			return false;
		}
		
		public void setTableDataFromDB(double[] arr) {
			FuelType[] arrF = new FuelType[4];
			arrF[0] = new FuelType("95", arr[0]); 
			arrF[1] = new FuelType("BikeFuel", arr[1]);
			arrF[2] = new FuelType("Diesel", arr[2]);
			arrF[3] = new FuelType("HomeFuel", arr[3]);
			olist = FXCollections.observableArrayList();
			for(FuelType l : arrF)
				olist.add(l);
			//JavaFX
			//Injection
			fuelType.setCellValueFactory(new PropertyValueFactory<FuelType,String>("Name"));
			purchase.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString()));
		    tableview.setItems(olist);
		}

		public PurchaseReportController(ClientController client) {
			this.client = client;
		}

}
