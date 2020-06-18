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
import gui.OrderSummeryForm;
import gui.OrderSummeryHomeForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * This controller class controls the functions required to run the Order Fuel For Home Use functionality
 * @author Alon
 *
 */
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
	@FXML
	private Text name;
	@FXML
	private void onConfirmClick(ActionEvent event){
		try {
			String addr = address_input.getText();
			String qtyStr = qty_input.getText();
			FuelType fueltype = getFuelTypeFromString("HomeFuel");
			boolean fastSupply = fast_suppl.isSelected();
			LocalDate deliveryDate = date_input.getValue();
			if(addr.length()==0 || qtyStr.length() == 0 || fueltype==null || deliveryDate==null) {
				client.displayAlert(false, "All fields must be filled!");
				return;
			}
			double qty = Double.parseDouble(qtyStr);
			if(qty <= 0)
			{
				client.displayAlert(false, "Quantity must be a positive number!");
				return;
			}
			if(fueltype.getQuantity() < qty)
			{
				client.displayAlert(false, "Sorry, we don't have enough fuel at this moment. Please comeback later.");
				return;
			}
			double[] sumArr = calcOrderSum(fueltype, qty, fastSupply);
			Customer customer = (Customer)client.getCurrentProfile();
			OrderSummeryHomeForm newform = new OrderSummeryHomeForm(client, sumArr, null, qty, fueltype, deliveryDate, fastSupply, addr);
			client.setClientIF(newform);
			newform.start(client.getMainStage());
		} 
		catch (Exception e) {
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
	/**
	 * This method asks a Fuel Types list from server
	 */
	public void getFuelTypesFromDB() {
		String msg = "pull FuelType";
		Request req = new Request(msg);
		try {
			client.sendToServer(req);
		} catch (IOException e) {
			client.displayAlert(false, "Error: Couldn't send message to server!");
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getFuelTypesFromDB();
		date_input.setDayCellFactory(picker -> new DateCell() {
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate today = LocalDate.now();

	            setDisable(empty || date.compareTo(today) < 0 );
	        }
	    });
	}
	
	/**
	 * This method gets a list of Objects and returns a FuelType instance from those objects.
	 * @param list List of Objects.
	 * @return Fueltype instance.
	 */
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
	/**
	 * This method calculates order summary according to specific arguments.
	 * @param ft The fuel type ordered.
	 * @param qty The quantity that was requested.
	 * @param fast A flag specifies if the customer want a fast shipping.
	 * @return Array of type double[] = [Order Summary, Discount(Optional)]
	 */
	private double[] calcOrderSum(FuelType ft, double qty, boolean fast) { 
		double[] arr = new double[3];
		double initial = ft.getPrice()*qty;
		double fastadd = ft.getPrice()*2/100;
		double discount = 0;
		if(fast) {
			initial += fastadd*qty;;
			arr[1] = fastadd;
		}
		if(qty >= 600 && qty <= 800) {
			discount = initial*3/100;
			initial -= discount;
		}
		else if(qty > 800) {
			discount = initial*4/100;
			initial -= discount;
		}

		arr[0] = initial;
		arr[2] = discount;
		return arr;
	}
	/**
	 * Finds a fuel type instance from array of fueltype and returns it.
	 * @param name The name of the required fuel type instance.
	 * @return FuelType instance.
	 */
	private FuelType getFuelTypeFromString(String name) {
		for(FuelType x : fueltypearr) {
			if(x.getName().equals(name))
				return x;
		}
		return null;
	}
	/**
	 * This method is used to get data from the Form java file.
	 * @param obj The object being transferred from server to client.
	 */
	public void getObjectFromUI(Object msg) {
		if(msg instanceof List) {
			@SuppressWarnings("unchecked")
			List<Object> temp = (List<Object>)msg;
			if(temp.size() == 0)
				return;
			else if(temp.get(0) instanceof List) {
				@SuppressWarnings("unchecked")
				List<List<Object>> list = (List<List<Object>>)msg;
				fueltypearr = new FuelType[list.size()];
				int i;
				for(i = 0; i<list.size(); i++) {
					fueltypearr[i] = createFuelTypeFromList(list.get(i));
				}
			}
		}
	}
}
