package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.time.*;
import Entity.FuelType;
import Entity.HomeFuelOrder;
import control.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


public class OrderFuelForHomeUseController implements Initializable {
	//Controllers
	ClientController client;
	//Global Variables
	FuelType[] fueltypearr;
	@FXML
	private Button confirm_btn;
	@FXML
	private Button cancel_btn;
	@FXML
	private DatePicker date_input;
	@FXML
	private TextField qty_input;
	@FXML
	private ChoiceBox<String> fuel_type;
	@FXML 
	private TextField address_input;
	@FXML
	private CheckBox fast_suppl;
	@FXML
	private void onConfirmClick(ActionEvent event){
		try {
			String addr = address_input.getText();
			String qtyStr = qty_input.getText();
			FuelType fueltype = getFuelTypeFromString(fuel_type.getSelectionModel().getSelectedItem());
			boolean fastSupply = fast_suppl.isSelected();
			LocalDate deliveryDate = date_input.getValue();
			LocalDate orderDate = LocalDate.now();
			if(addr.length()==0 || qtyStr.length() == 0 || fueltype==null || deliveryDate==null) {
				client.displayAlert(false, "All fields must be filled!");
				return;
			}
			double qty = Double.parseDouble(qtyStr);
			double sum = calcOrderSum(fueltype, qty);
			HomeFuelOrder newOrder = new HomeFuelOrder(sum, fueltype, qty, orderDate, "Active", deliveryDate, addr, fastSupply);
			//	Update fueltype stock
			fueltype.setQuantity(fueltype.getQuantity()-qty);
			client.sendToServer(newOrder);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public OrderFuelForHomeUseController(ClientController client) {
		this.client=client;
	}
	
	public void getFuelTypesFromDB() {
		String msg = "SELECT FuelType";
		try {
			client.sendToServer(msg);
		} catch (IOException e) {
			client.displayAlert(false, "Error: Couldn't send message to server!");
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getFuelTypesFromDB();
	}
	
	
	public FuelType createFuelTypeFromList(List<Object> list) {
		FuelType newVal;
		if(list.size() > 0) {
			newVal = new FuelType(
				list.get(0).toString(),
				Double.parseDouble(list.get(1).toString()),
				Float.parseFloat(list.get(2).toString()),
				Double.parseDouble(list.get(3).toString()),
				list.get(4).toString());
			return newVal;
		}
		return null;
	}
	
	private double calcOrderSum(FuelType ft, double qty) { return ft.getPrice()*qty; }
	
	private FuelType getFuelTypeFromString(String name) {
		for(FuelType x : fueltypearr) {
			if(x.getName().equals(name))
				return x;
		}
		return null;
	}
	
	public void getObjectFromUI(Object msg) {
		//NEW METHOD !!!!!!!!!!!
		@SuppressWarnings("unchecked")
		List<List<Object>> list = (List<List<Object>>)msg;
		fueltypearr = new FuelType[list.size()];
		int i;
		for(i = 0; i<list.size(); i++) {
			fueltypearr[i] = createFuelTypeFromList(list.get(i));
			if(fueltypearr[i].getStatus().equals("ACTIVE")) {
				fuel_type.getItems().add(fueltypearr[i].getName());
			}
			/*			
			if(fueltypearr[i].getStatus().equals(FuelType.status.ACTIVE)) {
				fuel_type.getItems().add(fueltypearr[i].getName());
			}
			*/
		}
	}
}
