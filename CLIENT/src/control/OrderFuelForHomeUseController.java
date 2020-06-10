package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;
import java.time.*;

import Entity.Car;
import Entity.Customer;
import Entity.FuelCompany;
import Entity.FuelType;
import Entity.HomeFuelOrder;
import Entity.Order;
import Entity.OrderStatus;
import Entity.Request;
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
	private ChoiceBox<String> fuelcomp;
	@FXML 
	private TextField address_input;
	@FXML
	private CheckBox fast_suppl;
	@FXML
	private Button logoutbutton;
	private List<FuelCompany> fclist = new ArrayList<FuelCompany>();
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
			Customer customer = (Customer)client.getCurrentProfile();
			FuelCompany fuelCompany = null;
			for(FuelCompany f : fclist) {
				if(f.getCompanyName().equals(fuelcomp.getSelectionModel().getSelectedItem()))
					fuelCompany = f;
			}
			String compName = fuelCompany.getCompanyName();
			HomeFuelOrder newOrder = null;
			if(compName.equals(customer.getComp1()) || compName.equals(customer.getComp2()) || compName.equals(customer.getComp3())) {
				newOrder = new HomeFuelOrder(sum, fueltype, qty, orderDate, OrderStatus.PREPARING, deliveryDate, 
						addr, fastSupply, customer.getCustomerID(), fuelCompany);
				fueltype.setQuantity(fueltype.getQuantity()-qty);
			}
			else client.displayAlert(false, "Unfortunately, you don't have the privilege to refuel in this company. Please go to the fuel company you registered with.");
			client.sendToServer(newOrder);
			client.displayAlert(true, "Your order has been placed successfully.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FXML
	private void onCancelClick(ActionEvent event){
		try {
			client.getMainPage().start(client.getMainStage());
		} catch (Exception e) {
			client.displayAlert(false, null);
			e.printStackTrace();
		}
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
	public OrderFuelForHomeUseController(ClientController client) {
		this.client=client;
	}
	
	public void getFuelTypesFromDB() {
		String msg = "pull FuelType";
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
			msg = "pull newcustomerformdata";
			req.parseStringIntoComponents(msg);
			client.sendToServer(req);
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
		if(msg instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> temp = (List<Object>)msg;
			if(temp.size() == 0)
				return;
			if(temp.get(0) instanceof FuelCompany) {
				List<FuelCompany> list = new ArrayList<FuelCompany>();
				for(Object o : temp)
					list.add((FuelCompany)o);
				fclist = list;
				ListIterator<FuelCompany> liter = list.listIterator();
				while(liter.hasNext()) {
					String name = liter.next().getCompanyName();
					fuelcomp.getItems().add(name);
				}
			}
			else if(temp.get(0) instanceof List) {
				@SuppressWarnings("unchecked")
				List<List<Object>> list = (List<List<Object>>)msg;
				fueltypearr = new FuelType[list.size()];
				int i;
				for(i = 0; i<list.size(); i++) {
					fueltypearr[i] = createFuelTypeFromList(list.get(i));
					if(fueltypearr[i].getStatus().equals("ACTIVE")) {
						fuel_type.getItems().add(fueltypearr[i].getName());
					}
				}
			}
		}
	}
}
