package control;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Entity.Customer;
import Entity.CustomerDuringSale;
import Entity.FuelType;
import Entity.Request;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ItemQuantityReportController implements Initializable {
	//Controllers
		ClientController client;
		FuelType ft;
		@FXML
		private Button ConfirmBtn;
		@FXML
		private TableView<FuelType> tableview;
		@FXML
		private TableColumn<FuelType, String> fuelType;
		@FXML
		private TableColumn<FuelType, String> quantity;
		@FXML 
		private Button homepagebutton;
		private ObservableList<FuelType> olist;
		private FuelType[] fueltypearr;
		@FXML
		private void onHomePageClick(ActionEvent event) throws Exception {
			client.getMainPage().start(client.getMainStage());
			client.setClientIF(client.getMainPage());
		}
		
		@FXML
		private void onConfirmClick(ActionEvent event){
			try {
				client.getMainPage().start(client.getMainStage());
			} catch (Exception e) {
				client.displayAlert(false, null);
				e.printStackTrace();
			}
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
			String msg = "pull FuelType";
			Request req = new Request(msg);
			client.sendToServer(req);
		}
		
		//Handle objects sent from UI
		@SuppressWarnings("unchecked")
		public boolean getMessageFromUI(Object obj) {

			if(obj instanceof List) {
				setTableDataFromDB(obj);
				return true;
			}
			return false;
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
		public void setTableDataFromDB(Object list) {
			if(list instanceof List) {
				List<Object> temp = (List<Object>)list;
				if(temp.get(0) instanceof List) {
					@SuppressWarnings("unchecked")
					List<List<Object>> templist = (List<List<Object>>)list;
					fueltypearr = new FuelType[temp.size()];
					int i;
					for(i = 0; i<temp.size(); i++)
						fueltypearr[i] = createFuelTypeFromList(templist.get(i));
				}
			}
			olist = FXCollections.observableArrayList();
			for(FuelType l : fueltypearr)
				olist.add(l);
			//JavaFX
			//Injection
			fuelType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
			quantity.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuantity().toString()));
		    tableview.setItems(olist);
		}

		public ItemQuantityReportController(ClientController client) {
			this.client = client;
		}

}
