package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.HomeFuelOrder;
import Entity.Order;
import Entity.Request;
import Entity.fueltypeTemp;
import Entity.saleTemplate;
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
import javafx.scene.control.cell.PropertyValueFactory;

public class start_a_sale_Controller  implements Initializable {
	//Controllers
	ClientController client;
	
	
	@FXML
	private TableView<saleTemplate> saleTable;
	@FXML
	private TableColumn<saleTemplate, String> saleName;
	@FXML
	private TableColumn<saleTemplate, String> FuelType;
	@FXML
	private TableColumn<saleTemplate, Float> price;
	@FXML
	private TableColumn<saleTemplate, String> start;
	@FXML
	private TableColumn<saleTemplate, String> end;
	@FXML
	private TableColumn<saleTemplate, String> status;	
	
	@FXML
	private Button logoutbutton;
	@FXML 
	private Button homepagebutton;
	
	@FXML 
	private Button StartButten;
	
	
	@FXML
	private ChoiceBox<String> sale;
	@FXML
	private ChoiceBox<String> saleEnd;
	
	private ObservableList<saleTemplate> olist;
	
	@FXML
	private void onHomePageClick(ActionEvent event) throws Exception {
		client.getMainPage().start(client.getMainStage());
		client.setClientIF(client.getMainPage());
	}
	@FXML
	private void onLogOutClick(ActionEvent event) throws Exception {
		client.restartApplication(null);
	}
	
	@FXML
	private void onStart(ActionEvent event) throws Exception {
		
		String name1 = sale.getSelectionModel().getSelectedItem();
		String nameEnd = saleEnd.getSelectionModel().getSelectedItem();
		int flagEnd=0;//0 = not empty  1= empty
		int falgStart=0;
		if(nameEnd==null) {
			flagEnd=1;
		}
		if(flagEnd==0) {
			if(nameEnd.equals("     ")) {flagEnd=1;}
		}
		
		if(name1==null) {
			falgStart=1;
		}
		if(falgStart==0) {
			if(name1.equals("     ")) {falgStart=1;}
		}
		
		
		//empty fields
		if(falgStart==1 && flagEnd==1) {
			client.displayAlert(false, "All fields must be filled!\r\n" + 
					"Select a sale name to activate or stop");
		}
	
		//two fields selected
		else	if(falgStart==0 && flagEnd==0) {
			client.displayAlert(false, "Only one field can be selected!\r\n" + 
					"Clear one of the fields and resubmit.");
			}
		
		//one field
		else if(falgStart==0 && flagEnd==1) {
		
				String msg = "start_a_sale " + name1;
				Request req = new Request(msg);
				client.sendToServer(req);
				client.displayAlert(true, "Done successfully! The sale is active.");
			
		}
		
		else if(falgStart==1 && flagEnd==0) {
			
				String msg = "end_a_sale " + nameEnd;
				Request req = new Request(msg);
				client.sendToServer(req);
				client.displayAlert(true, "Done successfully! The sale is not active.");
			
		}
	
		
		
	}
	
	@FXML
	private void onCancel(ActionEvent event) throws Exception {
		client.getMainPage().start(client.getMainStage());
		client.setClientIF(client.getMainPage());
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			getTableDataFromDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getTableDataFromDB() throws IOException {
		//Customer customer = (Customer)client.getCurrentProfile();
		String msg = "pull saletemplate";
		Request req = new Request(msg);
		client.sendToServer(req);
	}
	
	//Handle objects sent from UI
	@SuppressWarnings("unchecked")
	public boolean getMessageFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<saleTemplate>)obj);
			return true;
		}
		return false;
	}
	
	public void setTableDataFromDB(List<saleTemplate> list) {
		olist = FXCollections.observableArrayList();
		List<saleTemplate> temp_list=list;
	
		for(saleTemplate l : list)
			olist.add(l);
		//JavaFX
		//Injection
		 end.setCellValueFactory(new PropertyValueFactory<saleTemplate,String>("end"));
		 FuelType.setCellValueFactory(new PropertyValueFactory<saleTemplate,String>("FuelType"));
		
		price.setCellValueFactory(new PropertyValueFactory<saleTemplate,Float>("price"));
		saleName.setCellValueFactory(new PropertyValueFactory<saleTemplate,String>("sale_name"));
	    start.setCellValueFactory(new PropertyValueFactory<saleTemplate,String>("start"));
	  //  status.setCellValueFactory(new PropertyValueFactory<saleTemplate,String>("status"));
	    status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().GetStastus().toString()));

	    
	    saleTable.setItems(olist);
	    saleEnd.getItems().add("     ");
	    sale.getItems().add("     ");
		for(saleTemplate l : temp_list) {
	         String x=l.getSale_name();
	         if(l.GetStastus().equals("on")) {
	        	 saleEnd.getItems().add(x);
	         }
             if(l.GetStastus().equals("off")) {
            	 sale.getItems().add(x); 
	         }
		}
	
		
	}

	public start_a_sale_Controller(ClientController client) {
		this.client = client;
	
	}
	public boolean getObjectFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<saleTemplate>)obj);
			return true;
		}
		return false;
		
	}


}
