package gui;

import java.time.LocalDate;

import Entity.Car;
import Entity.FuelCompany;
import Entity.FuelType;
import common.ClientIF;
import control.ClientController;
import control.FastFuelController;
import control.OrderSummeryController;
import control.OrderSummeryHomeController;
import control.RegisterNewCarController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
 * This form class handles the JavaFX start process for Order summary form
 * @author Alon Barenboim
 *
 */
public class OrderSummeryHomeForm extends Application implements ClientIF{
	private ClientController client;
	private OrderSummeryHomeController ffController;
	private double[] OrderSumArr;
	private FuelCompany fuelCompany;
	private double Liters;
	private FuelType ft;
	private AnchorPane root;
	private String addr;
	private LocalDate ddate;
	private boolean fastSupply;
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.ffController = new OrderSummeryHomeController(this.client, OrderSumArr, fuelCompany, Liters, ft, ddate, fastSupply, addr, this);
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

	public OrderSummeryHomeForm(ClientController client, double[] OrderSumArr, FuelCompany fuelCompany, double Liters, FuelType ft, LocalDate ddate, boolean fastSupply, String addr) throws Exception {
		this.client = client;
		this.OrderSumArr = OrderSumArr;
		this.fuelCompany = fuelCompany;
		this.Liters = Liters;
		this.ft = ft;
		this.addr = addr;
		this.ddate = ddate;
		this.fastSupply = fastSupply;
	}


	@Override
	public boolean sendToController(Object obj) {
		return false;
	}

}