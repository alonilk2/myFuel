package control;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.HomeFuelOrder;
import Entity.Order;
import Entity.OrderStatus;
import Entity.Request;
import gui.OrderFuelForHomeUseForm;
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
import javafx.scene.paint.Color;

public class TrackOrderController implements Initializable {
	//Controllers
	ClientController client;
	@FXML
	private TableView<HomeFuelOrder> tableview;
	@FXML
	private TableColumn<Order, Integer> OrderID;
	@FXML
	private TableColumn<HomeFuelOrder, String> scheduled;
	@FXML
	private TableColumn<HomeFuelOrder, String> address;
	@FXML
	private TableColumn<HomeFuelOrder, String> fastsupp;
	@FXML
	private TableColumn<HomeFuelOrder, String> orderstatus;
	@FXML
	private Button logoutbutton;
	@FXML 
	private Button homepagebutton;
	private ObservableList<HomeFuelOrder> olist;
	@FXML
	private void onHomePageClick(ActionEvent event) throws Exception {
		client.getMainPage().start(client.getMainStage());
		client.setClientIF(client.getMainPage());
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
		Customer customer = (Customer)client.getCurrentProfile();
		String msg = "pull homefuelorder "+customer.getCustomerID();
		Request req = new Request(msg);
		client.sendToServer(req);
	}
	
	//Handle objects sent from UI
	@SuppressWarnings("unchecked")
	public boolean getMessageFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<HomeFuelOrder>)obj);
			return true;
		}
		return false;
	}
	
	public void setTableDataFromDB(List<HomeFuelOrder> list) {
		olist = FXCollections.observableArrayList();
		for(HomeFuelOrder l : list)
			olist.add(l);
		//JavaFX
		//Injection
		OrderID.setCellValueFactory(new PropertyValueFactory<Order,Integer>("OrderID"));
	    scheduled.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("scheduled"));
	    address.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("address"));
	    fastsupp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().isFastSupply().toString()));
	    orderstatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().toString()));
	    tableview.setItems(olist);
	}

	public TrackOrderController(ClientController client) {
		this.client = client;
	}


}
