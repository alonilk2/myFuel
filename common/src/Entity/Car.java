package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class Car implements Serializable, SQLReady{
	private int CustomerID;
	private int CarID;
	private String FuelType;

	public Car(int customerID, int carID, String fuelType) {
		this.CustomerID = customerID;
		this.CarID = carID;
		this.FuelType = fuelType;

	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	
	public int getCarID() {
		return CarID;
	}
	public void setCarID(int carID) {
		CarID = carID;
	}
	
	public String getFuelType() {
		return FuelType;
	}
	public void setFuelType(String fuelType) {
		FuelType = fuelType;
	}
	
	

	public int createNewAddSqlStatement(Connection conn) {
		int carID = getCarID();
		if(carID > -1) {
			String qry;
			qry = "SELECT CustomerID FROM customer WHERE CustomerID = " + this.getCustomerID();
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				ResultSet rs = stm.executeQuery(qry);
				if(rs.next()) {
					qry = "INSERT INTO Car (CustomerID, CarID, FuelType)" + " VALUES (?,?,?)";
					try {
						stm = conn.prepareStatement(qry);
						stm.setInt(1, this.getCustomerID());
						stm.setInt(2, carID);
						stm.setString(3, this.getFuelType());
						stm.execute();
						return carID;
					} catch (SQLException e) {
						e.printStackTrace();
						return -1;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}	
		}
		return -1;
	}
	
}
