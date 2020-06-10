package control;

import java.io.IOException;
import java.util.List;
import Entity.Customer;
import Entity.CustomerType;
import Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class RegisterNewCustomerController {
	
		private ClientController client;

		@FXML
		private TextField firstName;
		@FXML
		private TextField lastName;
		@FXML
		private TextField email;
		@FXML
		private TextField IDNum;
		@FXML
		private TextField phoneNumber;
		@FXML
		private CheckBox businessCustomer;
		@FXML
		private TextField companyID;
		@FXML
		private TextField username;
		@FXML
		private TextField password;
		@FXML
		private TextField creditCard;
		@FXML
		private Button addCustomer;
		@FXML
		private Button cancel_btn;
		@FXML
		private Button homepagebutton;
		
		@FXML
		private void onConfirmClick(ActionEvent event){
			try {
				String first_name = firstName.getText();
				String last_name = lastName.getText();
				String eMail = email.getText();
				String IDnum = IDNum.getText();
				String phone_number = phoneNumber.getText();
				Boolean business_customer = businessCustomer.isSelected();
				String company_ID = companyID.getText();
				String user_name = username.getText();
				String pass_word = password.getText();
				String credit_card = creditCard.getText();

				if(first_name.length()==0 || last_name.length() == 0 || eMail.length() == 0 || IDnum.length() == 0 || phone_number.length() == 0 || user_name.length() == 0 || pass_word.length() == 0) {
					client.displayAlert(false, "All fields must be filled!");
					return;
				}
				
				//Customer customer = (Customer)client.getCurrentProfile();
				CustomerType customerType;
				if(business_customer)
					customerType=CustomerType.businesscustomer;
				else customerType=CustomerType.privatecustomer;
				User user = new User(first_name,last_name,eMail,user_name,pass_word,Integer.parseInt(IDnum));
				Customer customer = new Customer(first_name,last_name,eMail,user_name,pass_word,Integer.parseInt(IDnum),customerType,credit_card);
				
				//	Update new customer 
				customer.setPhoneNumber(phone_number);
				customer.setCompanyID(company_ID);
								
				client.sendToServer(user);
				client.sendToServer(customer);
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
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
		
		public RegisterNewCustomerController(ClientController client) {
			this.client=client;
		}
		
		public void getObjectFromUI(Object msg) {
			@SuppressWarnings("unchecked")
			List<List<Object>> list = (List<List<Object>>)msg;
		}
		
}
