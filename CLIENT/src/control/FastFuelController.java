package control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import Entity.Car;
import Entity.Customer;
import Entity.CustomerType;
import Entity.FuelCompany;
import Entity.FuelType;
import Entity.Order;
import Entity.PurchasePlan;
import Entity.Request;
import gui.OrderFuelForHomeUseForm;
import gui.OrderSummeryForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class FastFuelController implements Initializable {
		private ClientController client;
		@FXML
		private ChoiceBox<String> caridcombo;
		@FXML
		private ChoiceBox<String> fuelcomp;
		@FXML
		private Text carcount;
		@FXML
		private Button startfuelbutton;
		@FXML
		private Button cancelbutton;
		@FXML
		private Button homepagebutton;
		@FXML
		private TextField litersinput;
		
		private List<Car> carList;
		private List<FuelCompany> fuelCompList;
		@FXML
		private void onStartFuelClick(ActionEvent event){
			Integer Liters = Integer.parseInt(litersinput.getText());
			Car car = null;
			Order newOrder = null;
			for(Car c:carList)
				if(c.getCarID() == Integer.parseInt(caridcombo.getSelectionModel().getSelectedItem()))
					car = c;
			if(car != null) {
				if(car.getFuelType().getQuantity() < Liters)
				{
					client.displayAlert(false, "Sorry, we don't have enough fuel at this moment. Please comeback later.");
					return;
				}
				float[] orderSum = getOrderSum(Liters, car);
				FuelCompany fuelCompany = null;
				for(FuelCompany f : fuelCompList) {
					if(f.getCompanyName().equals(fuelcomp.getSelectionModel().getSelectedItem()))
						fuelCompany = f;
				}
				Customer currentCustomer = (Customer)client.getCurrentProfile();
				String compName = fuelCompany.getCompanyName();
				try {
					if(compName.equals(currentCustomer.getComp1()) || compName.equals(currentCustomer.getComp2()) || compName.equals(currentCustomer.getComp3())) {
						OrderSummeryForm newform = new OrderSummeryForm(client, orderSum, fuelCompany, Liters, car);
						client.setClientIF(newform);
						newform.start(client.getMainStage());
					}
					else {
						client.displayAlert(false, "Unfortunately, you don't have the privilege to refuel in this company. Please go to the fuel company you registered with.");
						return;
					}
				}
				catch (Exception e) {
						e.printStackTrace();
				}
			}
		}
		private float[] getOrderSum(Integer Liters, Car car) {
			float fuelPrice = car.getFuelType().getPrice();
			Customer clientProfile = (Customer) client.getCurrentProfile();
			PurchasePlan plan = clientProfile.getPurchasePlan();
			float orderSum = 0;
			float firstdisc = 0, seconddisc = 0, thirddisc = 0;
			if(plan.toString().equals("REGULAR_FUEL"))
				orderSum = fuelPrice*Liters;
			else if(plan.toString().equals("MONTHLY_SUBSCRIPTION_SINGLE")) {
				firstdisc = (float)(fuelPrice*4/100);
				orderSum = (fuelPrice-firstdisc)*Liters; // Discount per liter
			}
			else if(plan.toString().equals("MONTHLY_SUBSCRIPTION_MANY")) {
				//4% discount for every car the customer has.
				firstdisc = (float)(carList.size()*(fuelPrice*4/100));
				orderSum =(fuelPrice-firstdisc)*Liters;
				//10 percent discount on total sum
				seconddisc = orderSum*10/100;
				orderSum -= seconddisc;
			}
			else if(plan.toString().equals("FULL_MONTHLY_SUBSCRIPTION")) {
				//4% discount for every car the customer has.
				firstdisc = (float)(fuelPrice*4/100);
				orderSum = (fuelPrice-firstdisc)*Liters;
				//10 percent discount on total sum
				seconddisc = orderSum*10/100;
				orderSum -= seconddisc;
				//3 percent discount on total sum
				thirddisc = orderSum*3/100;
				orderSum -= thirddisc;
			}
			float[] f = new float[4];
			f[0] = orderSum;
			f[1] = firstdisc;
			f[2] = seconddisc;
			f[3] = thirddisc;
			return f;
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
		public void getCarsFromDB() {
			String msg = "pull car " + client.getCurrentProfile().getUserID();
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

		public FastFuelController(ClientController client) {
			this.client=client;
			carList = new ArrayList<Car>();
			fuelCompList = new ArrayList<FuelCompany>();
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			getCarsFromDB();
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
					fuelCompList = list;
					ListIterator<FuelCompany> liter = list.listIterator();
					while(liter.hasNext()) {
						String name = liter.next().getCompanyName();
						fuelcomp.getItems().add(name);
					}
				}
				else if(temp.get(0) instanceof Car) {
					List<Car> list = new ArrayList<Car>();
					for(Object o : temp)
						list.add((Car)o);
					Integer Counter = 0;
					for(Car c:list) {
						caridcombo.getItems().add(c.getCarID().toString());
						Counter++;
					}
					carcount.setText(Counter.toString());
					this.carList = list;
				}
			}
			return;
		}
}
