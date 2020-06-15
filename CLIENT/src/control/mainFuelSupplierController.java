package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.gluonhq.charm.glisten.control.Icon;

import Entity.Customer;
import Entity.Employee;
import Entity.FuelType;
import Entity.HomeFuelOrder;
import Entity.Order;
import Entity.OrderFromSupplier;
import Entity.OrderStatus;
import Entity.Request;
import Entity.User;
import common.ClientIF;
import gui.LoginForm;
import gui.OrderFuelForHomeUseForm;
import gui.TrackOrderForm;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class mainFuelSupplierController implements Initializable {
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
	private ChoiceBox<String> statuschoicebox;
	@FXML 
	private Button updatebutton;
	@FXML 
	private Button logoutbutton;
	private ObservableList<OrderFromSupplier> olist;
	@FXML
	private void onUpdateClick(ActionEvent event) throws Exception {
		String orderID = statuschoicebox.getSelectionModel().getSelectedItem();
		String msg = "update orderstatus " + orderID + " DELIVERED";
		Request req = new Request(msg);
		client.sendToServer(req);
		client.displayAlert(true, "Fuel order status has been updated successfully!");
		statuschoicebox.getItems().clear();
		getTableDataFromDB();
	}
	@FXML
	private void OnLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTableDataFromDB();
	}
	
	public void getTableDataFromDB() {
		String msg = "pull orderfromsupplier";
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTableDataFromDB(List<OrderFromSupplier> list) {

		ordertable.getItems().clear();
		ordertable.setItems(FXCollections.observableArrayList(list));

		olist = FXCollections.observableArrayList();
		for(OrderFromSupplier l : list) {
			if(l.getOrderStatus().equals(OrderStatus.SHIPPING)) {
				olist.add(l);
				statuschoicebox.getItems().add(l.getorderID().toString());
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
	public mainFuelSupplierController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
	}

	public boolean getObjectFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<OrderFromSupplier>)obj);
			return true;
		}
		return false;
		
	}

}
