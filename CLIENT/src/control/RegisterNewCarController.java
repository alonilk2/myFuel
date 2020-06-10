package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Entity.Car;
import Entity.FuelType;
import Entity.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class RegisterNewCarController implements Initializable {
		private ClientController client;
		FuelType[] fueltypearr;
		@FXML
		private TextField customerID;
		@FXML
		private TextField carID;
		@FXML
		private ChoiceBox<String> fuel_type;
		@FXML
		private Button addCar;
		@FXML
		private Button cancel_btn;
		@FXML
		private Button homepagebutton;
		
		

		@FXML
		private void onConfirmClick(ActionEvent event){
			try {
				String customer_ID = customerID.getText();
				String car_ID = carID.getText();
				FuelType fueltype = getFuelTypeFromString(fuel_type.getSelectionModel().getSelectedItem());



				if(customer_ID.length()==0 || car_ID.length() == 0 || fueltype==null) {
					client.displayAlert(false, "All fields must be filled!");
					return;
				}
			

				Car car = new Car(Integer.parseInt(customer_ID), Integer.parseInt(car_ID), fueltype);
				//	Update car 
				client.sendToServer(car);
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
		
		public RegisterNewCarController(ClientController client) {
			this.client=client;
		}
		
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
			}
		}
	}
