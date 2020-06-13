package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.CustomerDuringSale;
import Entity.Order;
import Entity.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ReviewReportController implements Initializable {
	//Controllers
	ClientController client;
	CustomerDuringSale cds;
	public int sale_ID;
	@FXML
	private Button ConfirmBtn;
	@FXML
	private TableView<CustomerDuringSale> tableview;
	@FXML
	private TableColumn<CustomerDuringSale, Integer> customerTotalSumOfPurchases;
	@FXML
	private TableColumn<CustomerDuringSale, Integer> saleID;
	@FXML
	private TableColumn<CustomerDuringSale, Integer> customerID;
	@FXML 
	private Button homepagebutton;
	private ObservableList<CustomerDuringSale> olist;
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

	public ReviewReportController(ClientController client) {
		this.client = client;
	}


	
	public void getObjectFromUI(Object msg) {
		@SuppressWarnings("unchecked")
		List<List<Object>> list = (List<List<Object>>)msg;
	}
}
