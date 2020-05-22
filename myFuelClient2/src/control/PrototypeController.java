package control;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class PrototypeController implements Initializable {

	@FXML
	private TableView<Employee> tableview;
	@FXML
	private TableColumn<Employee, String> employeeid;
	@FXML
	private TableColumn<Employee, String> firstname;
	@FXML
	private TableColumn<Employee, String> lastname;
	@FXML
	private TableColumn<Employee, String> role;
	@FXML
	private TableColumn<Employee, String> email;
	@FXML
	private TableColumn<Employee, String> department;
	@FXML
	private Button updatebutton;
	@FXML
	private TextField employeeidtext;
	@FXML
	private TextField roletext;
	
	//Controllers
	private ClientController client;
	
	//Global object declaration
	private List<Employee> elist;
	private ObservableList<Employee> olist;
	private static final int DEFAULT_PORT = 5555;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		elist = new ArrayList<Employee>();
	    getTableDataFromDB();
	    
	    //Update button event handler
	    updatebutton.setOnAction(new EventHandler<ActionEvent>() {
	        @Override public void handle(ActionEvent e) {
	        	if(employeeid.getText().trim().length() != 0 && roletext.getText().trim().length() != 0) {
	        		updateRole(employeeidtext.getText(), roletext.getText());
	        	}
	        	else {
	        		System.out.println("All fields must be filled.");
	        	}
	        }
	    });
	}
	
	public void getTableDataFromDB() {
		String msg = "pull employees";
		client.handleMessageFromClientUI(msg);
	}
	
	//Handle objects sent from UI
	@SuppressWarnings("unchecked")
	public boolean getMessageFromUI(Object obj) {
		if(obj instanceof List) {
			setTableDataFromDB((List<List<String>>)obj);
			return true;
		}
		return false;
	}
	
	private Employee convertListOfStringsToEmployee(List<String> list) {
		if(list != null) {
			Employee tempemployee = new Employee(list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), list.get(5)
					, list.get(6), list.get(7));
			elist.add(tempemployee);
			return tempemployee;
		}
		return null;
	}
	
	public void setTableDataFromDB(List<List<String>> list) {
		olist = FXCollections.observableArrayList();
		for(List<String> l : list) {
			olist.add(convertListOfStringsToEmployee(l));
		}
		//JavaFX
		//Injection
	    employeeid.setCellValueFactory(new PropertyValueFactory<Employee,String>("employeeid"));
	    firstname.setCellValueFactory(new PropertyValueFactory<Employee,String>("firstname"));
	    lastname.setCellValueFactory(new PropertyValueFactory<Employee,String>("lastname"));
	    email.setCellValueFactory(new PropertyValueFactory<Employee,String>("email"));
	    department.setCellValueFactory(new PropertyValueFactory<Employee,String>("department"));
	    role.setCellValueFactory(new PropertyValueFactory<Employee,String>("role"));
	    tableview.setItems(olist);
	}
	private void refreshTableView() {
		if(elist.size() > 0 && olist.size() > 0) {
			FXCollections.copy(olist, elist);
			tableview.refresh();
		}
		else {
			System.out.println("No employees found ! Please check SQL connection and data.");
		}
	}
	private void updateRole(String employeeid, String role) {
		for(Employee e:elist) {
			if(e.getEmployeeid().equals(employeeid)) {
				e.setEmployeeRole(role);
			}
		}
		refreshTableView();
	    try { // FORM: update [TABLE] [FIELD] [EMPLOYEEID] [NEWVAL]
			client.sendToServer("update employees role "+employeeid+" "+role);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public PrototypeController(String args, Object ClientUI) {
	    String host = "localhost";
	    try 
	    {
	    	client = new ClientController(host, DEFAULT_PORT, ClientUI);
	    } 
	    catch(IOException exception) 
	    {
	    	System.out.println("Error: Can't setup connection!"
	                + " Terminating client.");
	    	System.exit(1);
	    }

	}


}
