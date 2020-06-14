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
import Entity.HomeFuelOrder;
import Entity.Order;
import Entity.OrderStatus;
import Entity.PurchasePlan;
import Entity.Request;
import gui.OrderSummeryForm;
import gui.OrderSummeryHomeForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class OrderSummeryHomeController implements Initializable {
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
		private double[] OrderSumArr;
		private FuelCompany fuelCompany;
		private double Liters;
		private FuelType ft;
		private OrderSummeryHomeForm form;
		private LocalDate ddate;
		private boolean fastSupply;
		private String addr;
		@FXML
		private void onConfirmClick(ActionEvent event){
			Customer customer = (Customer)client.getCurrentProfile();
			HomeFuelOrder newOrder = new HomeFuelOrder(OrderSumArr[0], ft, Liters, LocalDate.now(), OrderStatus.PREPARING, ddate, 
					addr, fastSupply, customer.getCustomerID(), fuelCompany, LocalTime.now());
			ft.setQuantity(ft.getQuantity()-Liters);
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
		private void onLogOutClick(ActionEvent event) throws Exception {
			client.restartApplication(null);
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
		public OrderSummeryHomeController(ClientController client, double[] OrderSumArr, FuelCompany fuelCompany, double Liters, FuelType ft, LocalDate ddate, boolean fastSupply, String addr, OrderSummeryHomeForm form) {
			this.client = client;
			this.OrderSumArr = OrderSumArr;
			this.fuelCompany = fuelCompany;
			this.Liters = Liters;
			this.ft = ft;
			this.ddate = ddate;
			this.fastSupply = fastSupply;
			this.addr = addr;
			this.form = form;
		}
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			FUELTYPE.setText(ft.getName());
			BASECOST.setText(String.valueOf(ft.getPrice()));
			FIRSTDISCTEXT.setText("Fast Supply Addition:");
			SECDISCTEXT.setText("Discount:");
			if(OrderSumArr[1] == 0.0) {
				FIRSTDISC.setVisible(false);
				FIRSTDISCTEXT.setVisible(false);
			}
			if(OrderSumArr[2] == 0.0) {
				SECDISC.setVisible(false);
				SECDISCTEXT.setVisible(false);
			}
			FIRSTDISC.setText(String.valueOf(Math.floor(OrderSumArr[1]*100)/100)+"¤/Ltr");
			SECDISC.setText(String.valueOf(Math.floor(OrderSumArr[2]*100)/100));
			THIRDDISC.setVisible(false);
			THIRDDISCTEXT.setVisible(false);
			AMOUNT.setText(String.valueOf(Liters));
			SUM.setText(String.valueOf(Math.floor(OrderSumArr[0]*100)/100));
		}

}
