package control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Entity.HomeFuelOrder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrackOrderController implements Initializable {
	//Controllers
	ClientController client;
	@FXML
	private TableView<HomeFuelOrder> tableview;
	@FXML
	private TableColumn<HomeFuelOrder, String> order_id;
	@FXML
	private TableColumn<HomeFuelOrder, String> scheduled;
	@FXML
	private TableColumn<HomeFuelOrder, String> address;
	@FXML
	private TableColumn<HomeFuelOrder, Boolean> fastsupp;
	@FXML
	private TableColumn<HomeFuelOrder, String> orderstatus;

	private ObservableList<HomeFuelOrder> olist;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTableDataFromDB();
	}
	
	public void getTableDataFromDB() {
		String msg = "pull homefuelorder "+client.getCustomer().getCustomerID();
		client.handleMessageFromClientUI(msg);
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
		order_id.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("order_id"));
	    scheduled.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("scheduled"));
	    address.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("address"));
	    fastsupp.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,Boolean>("fastsupp"));
	    orderstatus.setCellValueFactory(new PropertyValueFactory<HomeFuelOrder,String>("orderstatus"));
	    tableview.setItems(olist);
	}

	public TrackOrderController(ClientController client) {
		this.client = client;
	}


}
