package gui;

import Entity.Car;
import Entity.FuelCompany;
import Entity.FuelType;
import common.ClientIF;
import control.ClientController;
import control.FastFuelController;
import control.OrderSummeryController;
import control.RegisterNewCarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class OrderSummeryForm extends Application implements ClientIF{
	private ClientController client;
	private OrderSummeryController ffController;
	private float[] OrderSumArr;
	private FuelCompany fuelCompany;
	private Integer Liters;
	private Car car;
	private AnchorPane root;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.ffController = new OrderSummeryController(this.client, OrderSumArr, fuelCompany, Liters, car, this);
		try {
			FXMLLoader fxmload = new FXMLLoader();
			fxmload.setLocation(getClass().getResource("OrderSummery.fxml"));
			fxmload.setController(ffController);
			root = (AnchorPane)fxmload.load();
			Scene scene = new Scene(root,772,550);
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public AnchorPane getRoot() {
		return root;
	}

	public OrderSummeryForm(ClientController client, float[] OrderSumArr, FuelCompany fuelCompany, Integer qty, Car car) throws Exception {
		this.client = client;
		this.OrderSumArr = OrderSumArr;
		this.fuelCompany = fuelCompany;
		this.Liters = qty;
		this.car = car;
	}


	@Override
	public boolean sendToController(Object obj) {
		return false;
	}

}