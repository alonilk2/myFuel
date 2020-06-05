package Entity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import control.sqlController;
import ocsf.server.ConnectionToClient;

public class RequestDBController {
	private sqlController sqlcontrol;
	private EchoServer Server;
	private Connection conn;
	
	public RequestDBController(sqlController sqlcontrol, EchoServer server) {
		this.sqlcontrol = sqlcontrol;
		this.conn = sqlcontrol.getConnection();
		this.Server = server;
	}
	
	public void requestParser(Request Req, ConnectionToClient client) {
		switch(Req.getRequestComponent(0)) {
			case "login": { handleLoginRequestFromClient(Req, client); break; }
			case "pull": { handlePullRequestFromClient(Req, client); break; }
		}
	}
	
	private void handleLoginRequestFromClient(Request Req, ConnectionToClient client) {
		PreparedStatement stm;
		ResultSet rs;
		List<String> retList = new ArrayList<String>();
		try {
			String qrytempl = "SELECT * FROM user WHERE username = ? AND password = ?";
			stm = conn.prepareStatement(qrytempl);
			stm.setString(1, Req.getRequestComponent(1));
			stm.setString(2, Req.getRequestComponent(2));
			rs = stm.executeQuery();
			if(rs.next()) 
				for(int i=1; i<=6; i++)
					retList.add(rs.getString(i));
			else return;
			Server.getUsercontrol().createNewUserInstance(retList);
			String tempID = rs.getString(6);
			//
			//			Check if the user is a Customer
			//			returns new Customer instance if so
			//
			qrytempl = "SELECT u.username, u.password, u.firstname, u.lastname, u.email, c.customerid, c.customertype, c.purchaseplan,"
					+ " c.creditcard FROM user u LEFT JOIN customer c On u.userid = c.customerid WHERE c.customerid = ?";
			stm = conn.prepareStatement(qrytempl);
			stm.setString(1, tempID);
			rs = stm.executeQuery();
			if(rs.next()) {
				Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), CustomerType.valueOf(rs.getString(7)), rs.getString(8));
				client.sendToClient(c);
				return;
			}
			//
			//			Check if the user is an Employee
			//			returns new Employee instance if so
			//
			qrytempl = "SELECT u.username, u.password, u.firstname, u.lastname, u.email, e.employeeid, e.role, e.department "
					+ "FROM user u LEFT JOIN employees e On u.userid = e.employeeid WHERE e.employeeid = ?";
			stm = conn.prepareStatement(qrytempl);
			stm.setString(1, tempID);
			rs = stm.executeQuery();
			Server.getUsercontrol().createNewUserInstance(retList);
			if(rs.next()) {
				Employee e = new Employee(rs.getInt(6), Role.valueOf(rs.getString(7)), rs.getString(8),rs.getString(5), rs.getString(4), rs.getString(3), rs.getString(1), 
						rs.getString(2));
				client.sendToClient(e);
			}
			return;
		}
		catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void handlePullRequestFromClient(Request Req, ConnectionToClient client) {
		switch(Req.getRequestComponent(1)) {
		case "homefuelorder": {
			List<HomeFuelOrder> customerList = new ArrayList<HomeFuelOrder>();
			ListIterator<HomeFuelOrder> liter = Server.getHFOControl().getListIterator();
			while(liter.hasNext()) {
				HomeFuelOrder tempOrder = liter.next();
				if(tempOrder.getCustomerID() == Integer.parseInt(Req.getRequestComponent(2))) {
					customerList.add(tempOrder);
				}
			}
			try {
				client.sendToClient(customerList);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		//		Pull Orders From Supplier List for Fuel Supplier
		case "orderfromsupplier": {
			List<OrderFromSupplier> orderslist = new ArrayList<OrderFromSupplier>();
			ListIterator<OrderFromSupplier> liter = Server.getOFSControl().getListIterator();
			while(liter.hasNext()) {
				OrderFromSupplier tempOrder = liter.next();
				if(tempOrder.getFuelSupplier().getEmployeeid() == Integer.parseInt(Req.getRequestComponent(2))){
					orderslist.add(tempOrder);
				}
			}
			try {
				client.sendToClient(orderslist);
			} catch (IOException e) {
					e.printStackTrace();
			}
			return;
		}
		
		case "FuelType": {
			try {
				List<List<Object>> list = new ArrayList<List<Object>>();
				PreparedStatement stm = conn.prepareStatement("SELECT * FROM FuelType");
				ResultSet rs = stm.executeQuery();
				while(rs.next()) {
					List<Object> templist = new ArrayList<Object>();
					for(int x=1; x<=5; x++) 
						templist.add(rs.getString(x));
					list.add(templist);
				}
					client.sendToClient(list);
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
