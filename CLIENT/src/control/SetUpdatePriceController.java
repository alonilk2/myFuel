package control;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Entity.FuelType;
import Entity.HomeFuelOrder;
import Entity.Request;
import control.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SetUpdatePriceController implements Initializable {
	
	    //Controllers
		ClientController client;
		//Global Variables
		FuelType[] fueltypearr;
		
		@FXML
		private TextField price;
		
		@FXML
		private ChoiceBox<String> fuel_type;
		
		
		
		
		@FXML
		private void onSetPrice(ActionEvent event) throws Exception {
		
		String Price = price.getText();
		
			
			FuelType fueltype = getFuelTypeFromString(fuel_type.getSelectionModel().getSelectedItem());
			
		    if (Price.length()==0 ||fuel_type.getSelectionModel().getSelectedItem().length()==0 ) {
		    	
		    	client.displayAlert(false, "All fields must be filled");
		
		    }
	        String msg = "Price "+Price +" "+ fuel_type.getSelectionModel().getSelectedItem();
	    	Request qry= new Request(msg);
	        try {
			client.sendToServer(qry);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    	client.displayAlert(true, "Fuel price change succeeded!");
		}
		
	
	public void getFuelTypesFromDB() {
		String msg = "pull FuelType";
		Request qry= new Request(msg);
		try {
			client.sendToServer(qry);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getFuelTypesFromDB();
	}
	
	public SetUpdatePriceController(ClientController client) {
		this.client=client;
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
		/*
		@SuppressWarnings("unchecked")
		List<List<Object>> list = (List<List<Object>>)msg;
		fueltypearr = new FuelType[list.size()];
		int i;
		for(i = 0; i<list.size(); i++) {
			fueltypearr[i] = createFuelTypeFromList(list.get(i));
			if(fueltypearr[i].getStatus().equals("ACTIVE")) {
				fuel_type.getItems().add(fueltypearr[i].getName());
			}
		
		}*/
		
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
