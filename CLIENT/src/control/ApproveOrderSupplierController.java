package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Entity.OrderFromSupplier;
import Entity.OrderStatus;
import Entity.Request;
import common.ClientIF;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * This class handles all the logic behind Approve Order From Supplier form.
 * @author Alon Barenboim
 *
 */
public class ApproveOrderSupplierController implements Initializable {
	private ClientController client;
	@FXML
	private TableView<OrderFromSupplier> ordertable;
	@FXML
	private TableColumn<OrderFromSupplier, Integer> OrderID;
	@FXML
	private TableColumn<OrderFromSupplier, String> Status;
	@FXML
	private TableColumn<OrderFromSupplier, String> FuelType;
	@FXML
	private TableColumn<OrderFromSupplier, Double> Quantity;
	@FXML
	private TextField orderidinput;
	@FXML
	private ChoiceBox<String> orderidcombo;
	@FXML 
	private Button updatebutton;
	@FXML 
	private Button logoutbutton;
	private ObservableList<OrderFromSupplier> olist;
	@FXML
	private void onUpdateClick(ActionEvent event) throws Exception {
		String orderID = orderidcombo.getSelectionModel().getSelectedItem();
		if(orderID != null) {
			String msg = "update orderstatus " + orderID + " SHIPPING";
			Request req = new Request(msg);
			client.sendToServer(req);
			client.displayAlert(true, "Fuel order status has been updated successfully!");
			orderidcombo.getItems().clear();
			getTableDataFromDB();
		}
		else client.displayAlert(true, "You need to choose an order from the list.");
	}
	
	@FXML
	private void OnHomePageClick(ActionEvent event) throws Exception {
		client.getMainPage().start(client.getMainStage());
		client.setClientIF(client.getMainPage());
	}
	
	@FXML
	private void OnLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTableDataFromDB();
	}
	/**
	 * This method is used to get relevant data for table initialization.
	 * 
	 */
	public void getTableDataFromDB() {
		String msg = "pull orderfromsupplier";
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is used to initialize table-view data.
	 * @param list The list of OrderFromSupplier to be recieved from the server.
	 */
	public void setTableDataFromDB(List<OrderFromSupplier> list) {

		ordertable.getItems().clear();
		ordertable.setItems(FXCollections.observableArrayList(list));
		olist = FXCollections.observableArrayList();
		for(OrderFromSupplier l : list) {
			if(l.getOrderStatus().equals(OrderStatus.PREPARING)) {
				olist.add(l);
				orderidcombo.getItems().add(l.getorderID().toString());
			}
		}
		//JavaFX
		//Injection
		OrderID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getorderID()).asObject());
		Status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderStatus().toString()));
		//FuelType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFuelType().toString()));
		Quantity.setCellValueFactory(new PropertyValueFactory<OrderFromSupplier,Double>("Quantity"));
		ordertable.setItems(olist);
		ordertable.refresh();
	}

	public ApproveOrderSupplierController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;

	}
	/**
	 * This method is used to get data from the Form java file.
	 * @param obj The object being transferred from server to client.
	 */
	public boolean getObjectFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<OrderFromSupplier>)obj);
			return true;
		}
		return false;
		
	}

}
