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
	public void requestParser(Request Req, ConnectionToClient client) throws IOException {
		switch(Req.getRequestComponent(0)) {
			case "login": { 
				if(!handleLoginRequestFromClient(Req, client)) {
					client.sendToClient(false); //Report the client about login failure
				}
				break; 
			}
			case "pull": { handlePullRequestFromClient(Req, client); break; }
			case "boundery": { handleBoundryRequest(Req, client); break; }
			case "Price": { handlePriceRequest(Req, client); break; }
			case "approve": { handleApprovePriceRequest(Req, client); break; }
			case "rejecte": { handleRejectPriceRequest(Req, client); break; }
			case "update": { handleUpdateRequest(Req, client); break; }
		}
	}
	private void handleUpdateRequest(Request Req, ConnectionToClient client) {
		switch(Req.getRequestComponent(1)) {
			case "orderstatus" : {
				ListIterator<OrderFromSupplier> l = Server.getOFSControl().getListIterator();
				while(l.hasNext()) {
					OrderFromSupplier tempOrder = l.next();
					if(tempOrder.getorderID() == Integer.parseInt(Req.getRequestComponent(2))) {
						tempOrder.setOrderStatus(OrderStatus.valueOf(Req.getRequestComponent(3)));
						String qry;
						PreparedStatement stm;
						qry = "UPDATE orderfromsupplier SET status=? WHERE orderid = ? ";
						try {
							stm = conn.prepareStatement(qry);
							stm.setString(1, tempOrder.getOrderStatus().toString());
							stm.setInt(2, tempOrder.getorderID());
							stm.execute();
						}
						 catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			}
		}
	}
	private void handleBoundryRequest(Request Req, ConnectionToClient client) {
		String qry;
		PreparedStatement stm;
		System.out.println("arrive: "+Req.getRequestComponent(0));
		String bound = Req.getRequestComponent(1);;
		String type = Req.getRequestComponent(2);;
		qry = "UPDATE fueltype SET lowfuelbound=? WHERE name=? ";
		try {
			stm = conn.prepareStatement(qry);
			stm.setString(1, bound);
			stm.setString(2,type);
			stm.execute();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void handlePriceRequest(Request Req, ConnectionToClient client) {
		PreparedStatement stm;
		String bound = Req.getRequestComponent(1);
		String type = Req.getRequestComponent(2);
		String qry = "INSERT INTO fueltypetemp VALUES (?,?,?) ";
		try {
			stm = conn.prepareStatement(qry);
			stm.setString(2, bound);
			stm.setString(1,type);
			stm.setString(3, "wait");
			stm.execute();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void handleApprovePriceRequest(Request Req, ConnectionToClient client) {
		PreparedStatement stm;
		String name = Req.getRequestComponent(1);
		String newPrice = Req.getRequestComponent(2);
		String qry = "UPDATE fueltype SET price=? WHERE name=? ";
		try {
			stm = conn.prepareStatement(qry);
			stm.setString(2, name);
			stm.setString(1,newPrice);
			stm.execute();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		qry = "DELETE FROM fueltypetemp WHERE name=? ";
		try {
			stm = conn.prepareStatement(qry);
			stm.setString(1, name);
			stm.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void handleRejectPriceRequest(Request Req, ConnectionToClient client) {
		PreparedStatement stm;
		String name = Req.getRequestComponent(1);
		String qry = "DELETE FROM fueltypetemp WHERE name=? ";
		try {
			stm = conn.prepareStatement(qry);
			stm.setString(1, name);
			stm.execute();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean handleLoginRequestFromClient(Request Req, ConnectionToClient client) {
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
			else return false;
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
				return true;
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
			return true;
		}
		catch (SQLException | IOException e) {
			e.printStackTrace();
			return false;
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
				break;
			}
	
			//		Pull Orders From Supplier List for Fuel Supplier
			case "orderfromsupplier": {
				List<OrderFromSupplier> orderslist = new ArrayList<OrderFromSupplier>();
				ListIterator<OrderFromSupplier> liter = Server.getOFSControl().getListIterator();
				while(liter.hasNext()) 
					orderslist.add(liter.next());
				try {
					client.sendToClient(orderslist);
				} catch (IOException e) {
						e.printStackTrace();
				}
				break;
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
				} 
				catch (IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case "car": {
				List<Car> list = Server.getCarControl().getCarListByCustomer(Integer.parseInt(Req.getRequestComponent(2)));
				try {
					client.sendToClient(list);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
			/*case "fueltypetemp": { 
				List<fueltypeTemp> pList = new ArrayList<fueltypeTemp>();
				for(fueltypeTemp h : tempFuelList) {
					if(h.getStatus().equals("wait")) { 
						pList.add(h);
					}
				}
				try {
					client.sendToClient(pList);
				} catch (IOException e) {
						e.printStackTrace();
				}
				break;
			}*/
				
				
			case "CustomerDuringSale": {
				try {
					List<List<Object>> list = new ArrayList<List<Object>>();
					PreparedStatement stm = conn.prepareStatement("SELECT cds.*, count(cds.CustomerID) as AmountOfParticipants , sum(cds.CustomerTotalSumOfPurchases) as TotalProfitFromSale FROM CustomerDuringSale cds");
					ResultSet rs = stm.executeQuery();
					while(rs.next()) {
						List<Object> templist = new ArrayList<Object>();
						for(int x=1; x<=5; x++) 
							templist.add(rs.getString(x));
						list.add(templist);
					}
					/*
					stm = conn.prepareStatement("SELECT count(CustomerID) as AmountOfParticipants , sum(CustomerTotalSumOfPurchases) as TotalProfitFromSale FROM CustomerDuringSale");
					rs = stm.executeQuery();
					while(rs.next()) {
						List<Object> templist = new ArrayList<Object>();
						for(int x=1; x<=2; x++) 
							templist.add(rs.getString(x));
						list.add(templist);
					}*/
						client.sendToClient(list);
					} catch (IOException | SQLException e) {
						e.printStackTrace();
					}
				}
			

		}
	}
}
