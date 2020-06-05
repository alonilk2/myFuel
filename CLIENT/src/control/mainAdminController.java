package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Employee;
import Entity.FuelType;
import Entity.OrderFromSupplier;
import Entity.OrderStatus;
import Entity.fuelStatus;
import Entity.fueltypeTemp;
import common.ClientIF;
import gui.SetUpdatePriceForm;
import gui.mainAdmin;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class mainAdminController implements Initializable {
	private ClientController client;
	private static final int DEFAULT_PORT = 5555;
	
	//Global Variables
	fueltypeTemp[] fueltypearr;
	private Stage mainStage;
	@FXML
	private TableView<fueltypeTemp> priceTable;
	@FXML
	private TableColumn<fueltypeTemp, String> Fueltype;
	@FXML
	private TableColumn<fueltypeTemp, String> tmp_price;
	@FXML
	private ChoiceBox<String> fuel;
	@FXML
	private ChoiceBox<String> statuschoicebox;
	@FXML 
	private Button SetStatuseButten;
	
	private ObservableList<fueltypeTemp> olist;
	private List<fueltypeTemp> elist;
	@FXML
	private void onSet(ActionEvent event) throws Exception {
		
		String Fuel=fuel.getValue();
		String Status=statuschoicebox.getValue();
		Float price=(float) 0;
		
		if(Fuel==null || Status==null) {
			client.displayAlert(false, "All fields must be filled!");
		}
		
		if(Status.equals("approve"))
		{
			int i=0;
			for(i=0;i<fueltypearr.length;i++) {
				
				if(fueltypearr[i]!=null) {
	         if(fueltypearr[i].getName().equals(Fuel))
	        	 price=fueltypearr[i].getPrice();	 
		}
			}
		//	priceTable.refresh();	
		//	refreshTableView();
	        String msg = "approve "+Fuel+" "+price.toString();
	        try {
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		if(Status.equals("rejecte"))
		{//  refreshTableView();
	        String msg = "rejecte "+Fuel;
	        try {
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		//priceTable.refresh();
		/*try {
			client.getMainPage().start(client.getMainStage());
		} catch (Exception e) {
			client.displayAlert(true, null);
			e.printStackTrace();
		}*/
	}
	
	
	private void refreshTableView() {
		if(elist.size() > 0 && olist.size() > 0) {
			FXCollections.copy(olist, elist);
			priceTable.refresh();
		}
		else {
			System.out.println("No employees found ! Please check SQL connection and data.");
		}
	}

	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		getTableDataFromDB();
	}
	
	public void getTableDataFromDB() {
		String msg = "pull fueltypetemp";
		try {
			client.sendToServer(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setTableDataFromDB(List<fueltypeTemp> list) {
		statuschoicebox.getItems().add("approve");
		statuschoicebox.getItems().add("rejecte");
		List<fueltypeTemp> temp_list=list;
		
		
		olist = FXCollections.observableArrayList();
		for(fueltypeTemp l : list)
			olist.add(l);
		//JavaFX

		Fueltype.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName().toString()));
		tmp_price.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice().toString()));
		priceTable.setItems(olist);
		
	
	    int i=0;
		for(fueltypeTemp l : temp_list) {
	         String x=l.getStatus();
	         
			if(x.equals("wait")) {
				fuel.getItems().add(l.getName());
			}
			
			
		}
		for(fueltypeTemp ll : temp_list) {
			fueltypearr[i]=new fueltypeTemp(ll.getName(),ll.getPrice(),ll.getStatus());
			i++;
		}
	}

	public mainAdminController(String args, ClientIF ClientUI, ClientController client) {
		this.client = client;
	    fueltypearr = new fueltypeTemp[10];
	}

	
	public fueltypeTemp createFuelTypeFromList(List<Object> list) {
		fueltypeTemp newVal;
		if(list.size() > 0) {
			newVal = new fueltypeTemp(
				list.get(0).toString(),
				Float.parseFloat(list.get(1).toString()),
				list.get(2).toString());
			return newVal;
		}
		return null;
	}

	public boolean getObjectFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<fueltypeTemp>)obj);
			return true;
		}
		//////////	
	return false;
		
	}
	
}


