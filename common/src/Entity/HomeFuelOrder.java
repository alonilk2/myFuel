package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;
import java.util.List;

import iF.SQLReady;

public class HomeFuelOrder extends Order implements Serializable, SQLReady {

	private static final long serialVersionUID = -5842846747340584537L;
	private OrderStatus status;
	private LocalDate scheduled;
	private String address;
	private Boolean fastSupply;
	public HomeFuelOrder(double orderSum, FuelType fueltype, double quantity, LocalDate orderDate,
			OrderStatus status, LocalDate scheduled, String address, Boolean fastSupply, int customerid) {
		super(orderSum, fueltype, quantity, orderDate, customerid);
		this.status = status;
		this.scheduled = scheduled;
		this.address = address;
		this.fastSupply = fastSupply;
	}
	public HomeFuelOrder(int orderid, double orderSum, FuelType fueltype, double quantity, LocalDate orderDate,
			OrderStatus status, LocalDate scheduled, String address, Boolean fastSupply, int customerid) {
		super(orderid, orderSum, fueltype, quantity, orderDate, customerid);
		this.status = status;
		this.scheduled = scheduled;
		this.address = address;
		this.fastSupply = fastSupply;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public LocalDate getScheduled() {
		return scheduled;
	}
	public void setScheduled(LocalDate scheduled) {
		this.scheduled = scheduled;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Boolean isFastSupply() {
		return fastSupply;
	}
	public void setFastSupply(Boolean fastSupply) {
		this.fastSupply = fastSupply;
	}

	public int createNewAddSqlStatement(Connection conn) {
		int genID = super.createNewAddSqlStatement(conn);
		if(genID > -1) {
			String qry;
			qry = "INSERT INTO homefuelorder (orderid, status, scheduled, address, fastsupply)" + " VALUES (?,?,?,?,?)";
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				stm.setInt(1, genID);
				stm.setString(2, OrderStatus.PREPARING.toString());
				stm.setObject(3, this.getScheduled());
				stm.setString(4, this.getAddress());
				stm.setBoolean(5, this.isFastSupply());
				stm.execute();
				return genID;
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}
}
