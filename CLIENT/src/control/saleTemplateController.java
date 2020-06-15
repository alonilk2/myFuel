package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.FuelType;
import Entity.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class saleTemplateController implements Initializable {
	
    //Controllers
	ClientController client;
	//Global Variables
	FuelType[] fueltypearr;
	
	@FXML
	private TextField saleprice;
	
	@FXML
	private Button set;
	
	@FXML
	private ChoiceBox<String> fueltype;
	
	
	@FXML
	private ChoiceBox<String> start_time;
	@FXML
	private ChoiceBox<String> end_time;
	
	@FXML
	private TextField name;
	
	
	public saleTemplateController(ClientController client) {
		this.client=client;
	}
	@FXML
	private void onSet(ActionEvent event) throws Exception {
	 ////////////have to change
	     String Price = saleprice.getText();
	     String saleName=name.getText();
	     String Fuel=fueltype.getValue();
	     String start=start_time.getValue();
	     String end=end_time.getValue();
	     
	     String startT[] = start.split(":");
	     int startTime = Integer.parseInt(startT[0]);
	     String endT[] = end.split(":");
	     int endTime = Integer.parseInt(endT[0]);
	     
	     if(endTime<=startTime) {
	    	 client.displayAlert(false, "The end time you have chosen does not match the start time,\r\n" + 
	    	 		"You must choose a later hour than the start time!");
	     }
	     
		
		
	    if (Price.length()==0 ||saleName.length()==0||Fuel.length()==0||start.length()==0||end.length()==0 ) {
	       client.displayAlert(false, "all filde must be field");
	    	//System.out.println("all filde must be field");
	    //	ShowInfoAlert("all filde must be field");
	    }
       
	    String msg = "saleTemaplte "+Price+" "+saleName+" "+Fuel+" "+start+" "+end;
	    Request qry= new Request(msg);
	    
	    try {
		client.sendToServer(qry);
	} catch (IOException e) {
		e.printStackTrace();
	}
		 client.displayAlert(true, "Sale template successfully created!");
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
	 start_time.getItems().add("00:00");
	 start_time.getItems().add("01:00");
	 start_time.getItems().add("02:00");
	 start_time.getItems().add("03:00");
	 start_time.getItems().add("04:00");
	 start_time.getItems().add("05:00");
	 start_time.getItems().add("06:00");
	 start_time.getItems().add("07:00");
	 start_time.getItems().add("08:00");
	 start_time.getItems().add("09:00");
	 start_time.getItems().add("10:00");
	 start_time.getItems().add("11:00");
	 start_time.getItems().add("12:00");
	 start_time.getItems().add("13:00");
	 start_time.getItems().add("14:00");
	 start_time.getItems().add("15:00");
	 start_time.getItems().add("16:00");
	 start_time.getItems().add("17:00");
	 start_time.getItems().add("18:00");
	 start_time.getItems().add("19:00");
	 start_time.getItems().add("20:00");
	 start_time.getItems().add("21:00");
	 start_time.getItems().add("22:00");
	 start_time.getItems().add("23:00");
	 
	 
	 end_time.getItems().add("00:00");
	 end_time.getItems().add("01:00");
	 end_time.getItems().add("02:00");
	 end_time.getItems().add("03:00");
	 end_time.getItems().add("04:00");
	 end_time.getItems().add("05:00");
	 end_time.getItems().add("06:00");
	 end_time.getItems().add("07:00");
	 end_time.getItems().add("08:00");
	 end_time.getItems().add("09:00");
	 end_time.getItems().add("10:00");
	 end_time.getItems().add("11:00");
	 end_time.getItems().add("12:00");
	 end_time.getItems().add("13:00");
	 end_time.getItems().add("14:00");
	 end_time.getItems().add("15:00");
	 end_time.getItems().add("16:00");
	 end_time.getItems().add("17:00");
	 end_time.getItems().add("18:00");
	 end_time.getItems().add("19:00");
	 end_time.getItems().add("20:00");
	 end_time.getItems().add("21:00");
	 end_time.getItems().add("22:00");
	 end_time.getItems().add("23:00");
	 
	 
	 
	@SuppressWarnings("unchecked")
	List<List<Object>> list = (List<List<Object>>)msg;
	fueltypearr = new FuelType[list.size()];
	int i;
	for(i = 0; i<list.size(); i++) {
		fueltypearr[i] = createFuelTypeFromList(list.get(i));
		if(fueltypearr[i].getStatus().equals("ACTIVE")) {
			fueltype.getItems().add(fueltypearr[i].getName());
		}
	
	}
}



}
