package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;
import ocsf.server.ConnectionToClient;

/**
 * This server controller contains all the Run-Time data of the Order system.
 * and also contains all the methods required for Order's functionalities.
 */
public class OrderDBController {
	private sqlController sqlcontrol;
	private EchoServer Server;
	private List<Order> ordersList;
	public OrderDBController(sqlController sqlcontrol, EchoServer Server) {
		this.sqlcontrol = sqlcontrol;
		this.Server = Server;
	    ordersList = new ArrayList<Order>();
	}
	/**
	 * This method initializes the List of Orders on server startup.
	 */
	public boolean initializeList() {
		//Initialize Orders List
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM orders");
			while(rs.next()) {
				Order o = new Order(rs.getInt(1), rs.getDouble(2), Server.getFTControl().getFuelTypeFromString(rs.getString(3)),
						rs.getDouble(4), rs.getDate(5).toLocalDate(), rs.getInt(6), Server.getFCController().getFuelCompanyFromString(rs.getString(7)), rs.getTime(8).toLocalTime());
				ordersList.add(o);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method creates adds a new order to DB.
	 * @param client Connection instance to the client.
	 * @return True on success.
	 */
	public boolean addNewOrderToDB(Order newOrder, ConnectionToClient client) {
		String qry = "INSERT INTO orders (ordersum, fueltype, quantity, orderdate, customerID, fuelcompany, orderhour)" + " VALUES (?,?,?,?,?,?,?)";
		Connection conn = sqlcontrol.getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(qry);
			stm.setDouble(1, newOrder.getOrderSum());
			stm.setString(2, newOrder.getFueltype().getName());
			stm.setDouble(3, newOrder.getQuantity());
			stm.setObject(4, newOrder.getOrderDate());
			stm.setInt(5, newOrder.getCustomerID());
			stm.setString(6, newOrder.getFuelCompany().getCompanyName());
			stm.setObject(7, newOrder.getOrderTime());
			FuelType temp = Server.getFTControl().findEqualFuelType(newOrder.getFueltype());
			Server.getFTControl().updateFuelQuantity(temp, newOrder.getQuantity());
			stm.execute();
			qry = "SELECT orderid FROM orders ORDER BY orderid DESC LIMIT 1";
			stm = conn.prepareStatement(qry);
			return true;
		} 
		catch (SQLException e) {
				e.printStackTrace();
		}
		return false;
	}
	public boolean addNewOrder(Order o) {
		if(o != null)
			return ordersList.add(o);
		return false;
	}
	public List<Order> getOrdersList() {
		return ordersList;
	}

}
