package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import control.ServerController;
import control.UserController;
import control.sqlController;
import ocsf.server.ConnectionToClient;

public class HomeFuelOrderDBController {


	private sqlController sqlcontrol;
	private OrderDBController OrderController;
	private List<HomeFuelOrder> HomeFuelOrdersList;
	public HomeFuelOrderDBController(sqlController sqlcontrol, OrderDBController OrderController) {
		this.sqlcontrol = sqlcontrol;
		this.OrderController = OrderController;
		HomeFuelOrdersList = new ArrayList<HomeFuelOrder>();
	}
	
	public ListIterator<HomeFuelOrder> getListIterator() {
		return HomeFuelOrdersList.listIterator();
	}
	
	public boolean addNewOrderToDB(HomeFuelOrder newOrder, ConnectionToClient client) {
		String qry = "INSERT INTO orders (ordersum, fueltype, quantity, orderdate, customerID)" + " VALUES (?,?,?,?,?)";
		Connection conn = sqlcontrol.getConnection();
		int generatedID = 0;
		try {
			PreparedStatement stm = conn.prepareStatement(qry);
			stm.setDouble(1, newOrder.getOrderSum());
			stm.setString(2, newOrder.getFueltype().getName());
			stm.setDouble(3, newOrder.getQuantity());
			stm.setObject(4, newOrder.getOrderDate());
			stm.setInt(5, newOrder.getCustomerID());
			newOrder.getFueltype().updateFuelQuantity(conn, newOrder.getQuantity());
			stm.execute();
			qry = "SELECT orderid FROM orders ORDER BY orderid DESC LIMIT 1";
			stm = conn.prepareStatement(qry);
			//stm.setDouble(1, this.getOrderSum());
			//stm.setDouble(2, this.getQuantity());
			ResultSet rs = stm.executeQuery();
			if(rs.next())
				generatedID = rs.getInt(1);
			if(generatedID > -1) {
				qry = "INSERT INTO homefuelorder (orderid, status, scheduled, address, fastsupply)" + " VALUES (?,?,?,?,?)";
				stm = conn.prepareStatement(qry);
				stm.setInt(1, generatedID);
				stm.setString(2, OrderStatus.PREPARING.toString());
				stm.setObject(3, newOrder.getScheduled());
				stm.setString(4, newOrder.getAddress());
				stm.setBoolean(5, newOrder.isFastSupply());
				stm.execute();
				return true;
			} 
		}catch (SQLException e) {
				e.printStackTrace();
		}
		return false;

	}
	public boolean initializeList() {
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT h.orderid, h.status, h.scheduled, h.address, h.fastsupply,"
					+ " o.ordersum, o.fueltype, o.quantity, o.orderdate, o.customerid FROM homefuelorder h"
					+ " INNER JOIN orders o ON h.orderid = o.orderid");
			while(rs.next()) {
				HomeFuelOrder o = new HomeFuelOrder(rs.getInt(1), rs.getDouble(6), FuelTypesDBController.getFuelTypeFromString(rs.getString(7)),
					rs.getDouble(8), rs.getDate(9).toLocalDate(),OrderStatus.valueOf(rs.getString(2)),
					rs.getDate(3).toLocalDate(), rs.getString(4), rs.getBoolean(5), rs.getInt(10));
				HomeFuelOrdersList.add(o);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}