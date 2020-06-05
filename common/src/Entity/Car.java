package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class Car implements Serializable, SQLReady{
	private Integer CustomerID;
	private Integer CarID;
	private FuelType FuelType;

	public Car(Integer customerID, Integer carID, FuelType fuelType) {
		this.CustomerID = customerID;
		this.CarID = carID;
		this.FuelType = fuelType;

	}
	public Integer getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(Integer customerID) {
		CustomerID = customerID;
	}
	
	public Integer getCarID() {
		return CarID;
	}
	public void setCarID(Integer carID) {
		CarID = carID;
	}
	
	public FuelType getFuelType() {
		return FuelType;
	}
	public void setFuelType(FuelType fuelType) {
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
						stm.setString(3, this.getFuelType().getName());
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
