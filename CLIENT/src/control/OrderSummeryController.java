package control;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
import gui.OrderSummeryForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class OrderSummeryController implements Initializable {
		@FXML
		private	Text FUELTYPE;
		@FXML
		private Text BASECOST;
		@FXML
		private Text FIRSTDISC;
		@FXML
		private Text FIRSTDISCTEXT;
		@FXML
		private Text SECDISC;
		@FXML
		private Text SECDISCTEXT;
		@FXML
		private Text THIRDDISC;
		@FXML
		private Text THIRDDISCTEXT;
		@FXML
		private Text AMOUNT;
		@FXML
		private Text SUM;
		@FXML
		private Button CONFIRMBUTTON;
		@FXML
		private Button CANCELBUTTON;
		@FXML
		private Button homepagebutton;
		@FXML
		private Button logoutbutton;
		private ClientController client;
		private float[] OrderSumArr;
		private FuelCompany fuelCompany;
		private Integer Liters;
		private Car car;
		private OrderSummeryForm form;
		@FXML
		private void onConfirmClick(ActionEvent event){
			Order newOrder = new Order(OrderSumArr[0], car.getFuelType(), Liters, LocalDate.now(), car.getCustomerID(), fuelCompany, LocalTime.now());
			car.getFuelType().setQuantity(car.getFuelType().getQuantity()-Liters);
			try {
				client.sendToServer(newOrder);
				client.displayAlert(true, "Your car has been refueled! Drive safe.");
				client.getMainPage().start(client.getMainStage());
				client.setClientIF(client.getMainPage());
			} catch (Exception e) {
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

		public OrderSummeryController(ClientController client, float[] OrderSumArr, FuelCompany fuelCompany, Integer Liters, Car car, OrderSummeryForm form) {
			this.client = client;
			this.OrderSumArr = OrderSumArr;
			this.fuelCompany = fuelCompany;
			this.Liters = Liters;
			this.car = car;
			this.form = form;
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			FUELTYPE.setText(this.car.getFuelType().getName());
			BASECOST.setText(String.valueOf(this.car.getFuelType().getPrice()));
			if(OrderSumArr[1] == 0.0) {
				FIRSTDISC.setVisible(false);
				FIRSTDISCTEXT.setVisible(false);
			}
			if(OrderSumArr[2] == 0.0) {
				SECDISC.setVisible(false);
				SECDISCTEXT.setVisible(false);
			}
			if(OrderSumArr[3] == 0.0) {
				THIRDDISC.setVisible(false);
				THIRDDISCTEXT.setVisible(false);
			}
			FIRSTDISC.setText(String.valueOf(OrderSumArr[1])+"¤/Ltr");
			SECDISC.setText(String.valueOf(OrderSumArr[2]));
			THIRDDISC.setText(String.valueOf(OrderSumArr[3]));
			AMOUNT.setText(String.valueOf(Liters));
			SUM.setText(String.valueOf(OrderSumArr[0]));
		}

}
