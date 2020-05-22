package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;

import iF.SQLReady;

public class HomeFuelOrder extends Order implements Serializable, SQLReady {

	private static final long serialVersionUID = -5842846747340584537L;
	private String status;
	private LocalDate scheduled;
	private String address;
	private Boolean fastSupply;
	public HomeFuelOrder(double orderSum, FuelType fueltype, double quantity, LocalDate orderDate,
			String status, LocalDate scheduled, String address, Boolean fastSupply) {
		super(orderSum, fueltype, quantity, orderDate);
		this.status = status;
		this.scheduled = scheduled;
		this.address = address;
		this.fastSupply = fastSupply;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
				PreparedStatement stm = conn.prepareStatement(qry, Statement.RETURN_GENERATED_KEYS);
				stm.setInt(1, genID);
				stm.setString(2, "ACTIVE");
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
