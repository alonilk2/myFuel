package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.CustomerDuringSale;
import Entity.Order;
import Entity.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class ReviewReportController implements Initializable {
	//Controllers
	ClientController client;
	private String SaleID;



	@FXML
	private Button ConfirmBtn;
	@FXML
	private TableView<CustomerDuringSale> tableview;
	@FXML
	private TableColumn<CustomerDuringSale, String> totalSumPerCustomerCol;
	@FXML
	private TableColumn<CustomerDuringSale, String> customerIDCol;
	@FXML
	private Label purchsum;
	@FXML
	private Label custcount;
	//@FXML 
	//private Button homepagebutton;
	private ObservableList<CustomerDuringSale> olist;
	@FXML
	private void onHomePageClick(ActionEvent event) throws Exception {
	}
	
	@FXML
	private void onConfirmClick(ActionEvent event){
		/*
		try {
			client.getMainPage().start(client.getMainStage());
		} catch (Exception e) {
			client.displayAlert(false, null);
			e.printStackTrace();
		}
		*/
	}
	public String getSale_ID() {
		return SaleID;
	}
	public void setTableDataFromDB(List<CustomerDuringSale> list) {
		Double count = 0.0;
		olist = FXCollections.observableArrayList();
		for(CustomerDuringSale l : list) {
			if(l.getSaleID().equals(SaleID)) {
				olist.add(l);
				count += l.getTotalSumOfPurchases();
			}
		}
		customerIDCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getCustomerID())));
		totalSumPerCustomerCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getTotalSumOfPurchases())));
	    tableview.setItems(olist);
	    custcount.setText(String.valueOf(olist.size()));
	    custcount.autosize();
	    purchsum.setText(count.toString());
	    purchsum.autosize();

	    return;
	}
	@FXML
	private void onLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
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
		String msg = "pull CustomerDuringSale";
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
		else if(obj instanceof String) {
			this.SaleID = String.valueOf(obj);
			return true;
		}
		return false;
	}
	

	public ReviewReportController(ClientController client, String SaleID) {
		this.client = client;
		this.SaleID = SaleID;
	}


}