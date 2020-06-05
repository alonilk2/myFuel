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
	private int OrderID;
	private double orderSum;
	private FuelType fueltype;
	private double quantity;
	private LocalDate orderDate;
	private int CustomerID;
	public Order(double orderSum, FuelType fueltype, double quantity, LocalDate orderDate, int CustomerID) {
		this.orderSum = orderSum;
		this.fueltype = fueltype;
		this.quantity = quantity;
		this.orderDate = orderDate;
		this.CustomerID = CustomerID;
	}
	public Order(int OrderID, double orderSum, FuelType fueltype, double quantity, LocalDate orderDate, int CustomerID) {
		this.OrderID = OrderID;
		this.orderSum = orderSum;
		this.fueltype = fueltype;
		this.quantity = quantity;
		this.orderDate = orderDate;
		this.CustomerID = CustomerID;
	}
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public int getOrderID() {
		return OrderID;
	}
	public void setOrderID(int orderID) {
		this.OrderID = orderID;
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
		qry = "INSERT INTO orders (ordersum, fueltype, quantity, orderdate, customerID)" + " VALUES (?,?,?,?,?)";
		try {
			PreparedStatement stm = conn.prepareStatement(qry);
			stm.setDouble(1, this.getOrderSum());
			stm.setString(2, this.getFueltype().getName());
			stm.setDouble(3, this.getQuantity());
			stm.setObject(4, this.getOrderDate());
			stm.setInt(5, this.CustomerID);
			this.fueltype.updateFuelQuantity(conn, this.quantity);
			try {
				stm.execute();
				qry = "SELECT orderid FROM orders ORDER BY orderid DESC LIMIT 1";
				stm = conn.prepareStatement(qry);
				//stm.setDouble(1, this.getOrderSum());
				//stm.setDouble(2, this.getQuantity());
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
