package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class OrderFromSupplier implements Serializable, SQLReady {
	private Integer orderID;
	private OrderStatus orderStatus;
	private FuelType fuelType;
	private Double quantity;
	public OrderFromSupplier(OrderStatus orderStatus, FuelType fuelType, Double quantity) {
		this.orderStatus = orderStatus;
		this.fuelType = fuelType;
		this.quantity = quantity;
	}
	public OrderFromSupplier(int orderID, OrderStatus orderStatus, FuelType fuelType, Double quantity) {
		this.orderID = orderID;
		this.orderStatus = orderStatus;
		this.fuelType = fuelType;
		this.quantity = quantity;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public FuelType getFuelType() {
		return fuelType;
	}
	public void setFuelType(FuelType fuelType) {
		this.fuelType = fuelType;
	}
	public Double getQuantity() {
		return quantity;
	}
	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	public Integer getorderID() {
		return orderID;
	}
	public void setorderID(Integer orderID) {
		this.orderID = orderID;
	}
	@Override
	public int createNewAddSqlStatement(Connection conn) {
		String qry;
		qry = "INSERT INTO orderfromsupplier (status, fueltype, quantity)" + " VALUES (?,?,?)";
		try {
			PreparedStatement stm = conn.prepareStatement(qry);
			stm.setString(1, OrderStatus.PREPARING.toString());
			stm.setString(2, this.fuelType.toString());
			stm.setDouble(3, this.quantity);
			stm.execute();
			qry = "SELECT orderid FROM orderfromsupplier ORDER BY orderid DESC LIMIT 1";
			stm = conn.prepareStatement(qry);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				this.orderID = rs.getInt(1);
				return this.orderID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	return -1;	
	}
}
