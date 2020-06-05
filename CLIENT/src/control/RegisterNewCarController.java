package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import Entity.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class RegisterNewCarController implements Initializable {
		private ClientController client;
		@FXML
		private TextField customerID;
		@FXML
		private TextField carID;
		@FXML
		private TextField fuelType;
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
				String fuel_type = fuelType.getText();


				if(customer_ID.length()==0 || car_ID.length() == 0 || fuel_type.length() == 0) {
					client.displayAlert(false, "All fields must be filled!");
					return;
				}
			

				Car car = new Car(Integer.parseInt(customer_ID), Integer.parseInt(car_ID), fuel_type);
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
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
		}
		
		public void getObjectFromUI(Object msg) {
			//NEW METHOD !!!!!!!!!!!
			@SuppressWarnings("unchecked")
			List<List<Object>> list = (List<List<Object>>)msg;

				}
		
}
