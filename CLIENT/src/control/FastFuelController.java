package control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Car;
import Entity.Order;
import Entity.Request;
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
		@FXML
		private void onStartFuelClick(ActionEvent event){
			Integer Liters = Integer.parseInt(litersinput.getText());
			Car car = null;
			Order newOrder = null;
			for(Car c:carList)
				if(c.getCarID() == Integer.parseInt(caridcombo.getSelectionModel().getSelectedItem()))
					car = c;
			if(car != null) {
				float orderSum = car.getFuelType().getPrice()*Liters;
				newOrder = new Order(orderSum, car.getFuelType(), Liters, LocalDate.now(), car.getCustomerID());
			}
			try {
				client.sendToServer(newOrder);
			} catch (IOException e) {
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
		
		public void getCarsFromDB() {
			String msg = "pull car " + client.getCurrentProfile().getUserid();
			Request req = new Request(msg);
			try {
				client.sendToServer(req);
			} catch (IOException e) {
				client.displayAlert(false, "Error: Couldn't send message to server!");
				e.printStackTrace();
			}
		}
		
		
		
		
		
		public FastFuelController(ClientController client) {
			this.client=client;
			carList = new ArrayList<Car>();
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			getCarsFromDB();
		}
		
		public void getObjectFromUI(Object msg) {
			@SuppressWarnings("unchecked")
			List<Car> list = (List<Car>)msg;
			Integer Counter = 0;
			for(Car c:list) {
				caridcombo.getItems().add(c.getCarID().toString());
				Counter++;
			}
			carcount.setText(Counter.toString());
			this.carList = list;
		}
}
