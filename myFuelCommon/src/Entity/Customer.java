package Entity;

import java.io.Serializable;

public class Customer extends User implements Serializable{
	private int CustomerID;
	private CustomerType customerType;
	private PurchasePlan purchasePlan;
	private String CreditCard;
	public Customer(String username, String password, String firstname, String lastname, String email, int customerID,
			CustomerType customerType, PurchasePlan purchasePlan, String creditCard) {
		super(username, password, firstname, lastname, email);
		CustomerID = customerID;
		this.customerType = customerType;
		this.purchasePlan = purchasePlan;
		CreditCard = creditCard;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public PurchasePlan getPurchasePlan() {
		return purchasePlan;
	}
	public void setPurchasePlan(PurchasePlan purchasePlan) {
		this.purchasePlan = purchasePlan;
	}
	public String getCreditCard() {
		return CreditCard;
	}
	public void setCreditCard(String creditCard) {
		CreditCard = creditCard;
	}

}
