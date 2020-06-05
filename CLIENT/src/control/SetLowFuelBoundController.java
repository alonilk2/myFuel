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
import control.ClientController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
////////////////!!!!!!!!!!!!!!
public class SetLowFuelBoundController implements Initializable {
	//Controllers
	ClientController client;
	//Global Variables
	FuelType[] fueltypearr;
	
	@FXML
	private Button Set;
	@FXML
	private TextField Boundary;
	@FXML
	private ChoiceBox<String> Fuel_Type;//////////

	


	@FXML
	private void onSetBound(ActionEvent event) throws Exception {
	
		String bound = Boundary.getText();
	
		
		FuelType fueltype = getFuelTypeFromString(Fuel_Type.getSelectionModel().getSelectedItem());
		
	    if (bound.length()==0 ||Fuel_Type.getSelectionModel().getSelectedItem().length()==0 ) {
	    	System.out.println("all filde must be field");
	    //	ShowInfoAlert("all filde must be field");
	    }
        String msg = "boundery "+bound +" "+ Fuel_Type.getSelectionModel().getSelectedItem();
        try {
		client.sendToServer(msg);
	} catch (IOException e) {
		e.printStackTrace();
	}
	}

	
	


	public void getFuelTypesFromDB() {
		String msg = "SELECT FuelType";
		try {
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getFuelTypesFromDB();
	}
	
	public SetLowFuelBoundController(ClientController client) {
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
		@SuppressWarnings("unchecked")
		List<List<Object>> list = (List<List<Object>>)msg;
		fueltypearr = new FuelType[list.size()];
		int i;
		for(i = 0; i<list.size(); i++) {
			fueltypearr[i] = createFuelTypeFromList(list.get(i));
			if(fueltypearr[i].getStatus().equals("ACTIVE")) {
				Fuel_Type.getItems().add(fueltypearr[i].getName());
			}

		}
	}
}
