package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import iF.SQLReady;

public class Order implements Serializable, SQLReady{
	private static final long serialVersionUID = 1L;
	private int orderID;
	private double orderSum;
	private FuelType fueltype;
	private double quantity;
	private LocalDate orderDate;
	public Order(double orderSum, FuelType fueltype, double quantity, LocalDate orderDate) {
		this.orderSum = orderSum;
		this.fueltype = fueltype;
		this.quantity = quantity;
		this.orderDate = orderDate;
	}
	public Order(int orderID, double orderSum, FuelType fueltype, double quantity, LocalDate orderDate) {
		this.orderID = orderID;
		this.orderSum = orderSum;
		this.fueltype = fueltype;
		this.quantity = quantity;
		this.orderDate = orderDate;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public double getOrderSum() {
		return orderSum;
	}
	public void setOrderSum(double orderSum) {
		this.orderSum = orderSum;
	}
	public FuelType getFueltype() {
		return fueltype;
	}
	public void setFueltype(FuelType fueltype) {
		this.fueltype = fueltype;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	@Override
	public int createNewAddSqlStatement(Connection conn) {
		String qry;
		qry = "INSERT INTO orders (ordersum, fueltype, quantity, orderdate)" + " VALUES (?,?,?,?)";
		try {
			PreparedStatement stm = conn.prepareStatement(qry);
			stm.setDouble(1, this.getOrderSum());
			stm.setString(2, this.getFueltype().getName());
			stm.setDouble(3, this.getQuantity());
			stm.setObject(4, this.getOrderDate());
			this.fueltype.updateFuelQuantity(conn, this.quantity);
			try {
				stm.execute();
				qry = "SELECT orderid FROM orders WHERE ordersum = ? and quantity = ?";
				stm = conn.prepareStatement(qry);
				stm.setDouble(1, this.getOrderSum());
				stm.setDouble(2, this.getQuantity());
				ResultSet rs = stm.executeQuery();
				if(rs.next())
					return rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return -1;
	}
	
}
