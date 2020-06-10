package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import iF.SQLReady;

public class Customer extends User implements Serializable, SQLReady {
	private int CustomerID;
	private CustomerType customerType;
	private PurchasePlan purchasePlan;
	private String CreditCard;
	private String PhoneNumber;
	private String CompanyID;
	private FuelCompanyApproach app;
	private String comp1;
	private String comp2;
	private String comp3;
	public Customer(String username, String password, String firstname, String lastname, String email, int customerID,
			CustomerType customerType, String creditCard, PurchasePlan purchasePlan, FuelCompanyApproach app,
			String comp1, String comp2 ,String comp3) {
		super(username, password, firstname, lastname, email, customerID);
		this.CustomerID = customerID;
		this.customerType = customerType;
		this.purchasePlan = PurchasePlan.REGULAR_FUEL;
		this.CreditCard = creditCard;
		this.app = app;
		this.comp1 = comp1;
		this.comp2 = comp2;
		this.comp3 = comp3;
	}

	public FuelCompanyApproach getApp() {
		return app;
	}

	public void setApp(FuelCompanyApproach app) {
		this.app = app;
	}

	public String getComp1() {
		return comp1;
	}

	public void setComp1(String comp1) {
		this.comp1 = comp1;
	}

	public String getComp2() {
		return comp2;
	}

	public void setComp2(String comp2) {
		this.comp2 = comp2;
	}

	public String getComp3() {
		return comp3;
	}

	public void setComp3(String comp3) {
		this.comp3 = comp3;
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

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public String getCompanyID() {
		return CompanyID;
	}

	public void setCompanyID(String companyID) {
		CompanyID = companyID;
	}

	public int createNewAddSqlStatement(Connection conn) {
		int custID = getCustomerID();
		if (custID > -1) {
			String qry;

			qry = "SELECT EmployeeID FROM employees WHERE EmployeeID = " + custID;
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				ResultSet rs = stm.executeQuery(qry);
				if (!rs.next()) {
					qry = "SELECT userid FROM user WHERE userid != " + custID;

					try {
						stm = conn.prepareStatement(qry);
						rs = stm.executeQuery(qry);
						if (rs.next()) {
							// String qry;
							qry = "INSERT INTO Customer (CustomerID, CustomerType, PurchasePlan, CreditCard, PhoneNumber, CompanyID,"
									+ " FuelCompanyApproach, FuelComp1, FuelComp2, FuelComp3)"
									+ " VALUES (?,?,?,?,?,?,?,?,?,?)";
							try {
								stm = conn.prepareStatement(qry);
								stm.setInt(1, custID);
								stm.setString(2, getCustomerType().name());
								stm.setString(3, getPurchasePlan().name());
								stm.setString(4, this.getCreditCard());
								stm.setString(5, this.getPhoneNumber());
								stm.setString(6, this.getCompanyID());
								stm.setString(7, this.getApp().toString());
								stm.setString(8, this.getComp1());
								stm.setString(9, this.getComp2());
								stm.setString(10, this.getComp3());
								stm.execute();
								return custID;
							} catch (SQLException e) {
								//e.printStackTrace();
								return -1;
							}
						}
					} catch (SQLException e) {
						//e.printStackTrace();
						return -1;
					}
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;

			}
			return -1;
		}
		return -1;
	}
}
