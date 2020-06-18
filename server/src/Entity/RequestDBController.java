package Entity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
					return;
				}
				break; 
			}
			case "pull": { handlePullRequestFromClient(Req, client); break; }
			case "boundery": { handleBoundryRequest(Req, client); break; }
			case "Price": { handlePriceRequest(Req, client); break; }
			case "approve": { handleApprovePriceRequest(Req, client); break; }
			case "rejecte": { handleRejectPriceRequest(Req, client); break; }
			case "update": { handleUpdateRequest(Req, client); break; }
			case "analytic": { handleAnalyticSystemRequest(Req, client); break; }
			case "saleTemaplte":{handelSaleTemplate(Req,client); break;}
			case "start_a_sale":{handelStartASale(Req,client); break;}
			case "end_a_sale":{handelEndASale(Req,client); break;}
		}
	}
	private void  handelEndASale (Request Req,ConnectionToClient client) {
		PreparedStatement stm;
		String sale_name = Req.getRequestComponent(1);
		
		String qry;
		
		qry = "UPDATE saletemplate SET status=? WHERE sale_name = ? ";
		try {
             stm = conn.prepareStatement(qry);
            stm.setString(1,"off");
			stm.setString(2,sale_name);
			stm.execute();
		}
		 catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	private void  handelStartASale (Request Req,ConnectionToClient client) {
		PreparedStatement stm;
		String sale_name = Req.getRequestComponent(1);
		
		String qry;
		
		qry = "UPDATE saletemplate SET status=? WHERE sale_name = ? ";
		try {
             stm = conn.prepareStatement(qry);
            stm.setString(1,"on");
			stm.setString(2,sale_name);
			stm.execute();
		}
		 catch (SQLException e) {
				e.printStackTrace();
			}
		
	}
	
	private void handelSaleTemplate(Request Req,ConnectionToClient client) {
		PreparedStatement stm;
		
		
		String price = Req.getRequestComponent(1);
		String saleName = Req.getRequestComponent(2);
		String Fuel=Req.getRequestComponent(3);
		String start=Req.getRequestComponent(4);
		String end=Req.getRequestComponent(5);
		//(fuelType, price, start, end, status, sale name)
		String qry;
		qry = "INSERT INTO saletemplate (fuelType, price, start, end, status, sale_name) VALUES (?,?,?,?,?,?)";
		try {
			stm = conn.prepareStatement(qry);
			
			stm.setString(1,Fuel);
			stm.setFloat(2,Float.parseFloat(price));
			stm.setString(3,start);
			stm.setString(4,end);
			stm.setString(5,"off");
			stm.setString(6,saleName);
			
			stm.execute();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private void handleAnalyticSystemRequest(Request Req, ConnectionToClient client) {
		switch(Req.getRequestComponent(1)) {
			case "recalculate" : {
				Server.getASControl().RecalculateRankings(client);
				break;
			}
			case "pull" : {
				if(Req.getRequestComponent(2) == null)
					Server.getASControl().getCustomersRankList(client);
				else 
					Server.getASControl().getCustomersByFuelType(client, Req.getRequestComponent(2));
				break;
			}
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
			String username = Req.getRequestComponent(1);
			String password = Req.getRequestComponent(2);
			if(username == null || password == null)
				return false;
			stm.setString(1, username);
			stm.setString(2, password);
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
					+ " c.creditcard,c.fuelcompanyapproach, c.fuelcomp1, c.fuelcomp2, c.fuelcomp3"
					+ " FROM user u LEFT JOIN customer c On u.userid = c.customerid WHERE c.customerid = ?";
			stm = conn.prepareStatement(qrytempl);
			stm.setString(1, tempID);
			rs = stm.executeQuery();
			if(rs.next()) {
				Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getInt(6), CustomerType.valueOf(rs.getString(7)),rs.getString(9), PurchasePlan.valueOf(rs.getString(8)),
						FuelCompanyApproach.valueOf(rs.getString(10)), rs.getString(11), rs.getString(12), rs.getString(13));
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
				
			// Pull customerID and the amount of orders the customer has made as a List from orders table. 
			// Results will be received in descending order.   
			case "order": {
				try {
					List<OrdersPerCustomer> list = new ArrayList<OrdersPerCustomer>();
					
					PreparedStatement stm = conn.prepareStatement("SELECT customerID, count(customerID) as custcount FROM myfuel.orders GROUP BY customerID ORDER BY custcount DESC;");
					ResultSet rs = stm.executeQuery();
					
					while(rs.next()) {	// loop on results from DB
						OrdersPerCustomer e = new OrdersPerCustomer(rs.getInt(1),rs.getInt(2)); // create an object to keep the data stored in it. 
						list.add(e); // add it to the list that will later be returned
					}
					client.sendToClient(list);
				} 
				catch (IOException | SQLException e) {
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
				
			// Pull the quantity of each fuel type from the FuelType table. the results will be returned in an array of doubles.
			case "purchasereport": {
				List<Order> l = Server.getOrderControl().getOrdersList();
				double[] count = new double[4]; // the array will be automatically initialized with zeros.
				for(Order o : l) {
					if(o.getFueltype().getName().equals("95"))
						count[0] += o.getQuantity();
					else if(o.getFueltype().getName().equals("BikeFuel"))
						count[1] += o.getQuantity();
					else if(o.getFueltype().getName().equals("Diesel"))
						count[2] += o.getQuantity();
					else if(o.getFueltype().getName().equals("HomeFuel"))
						count[3] += o.getQuantity();
				}
				try {
					client.sendToClient(count);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			
			// Pull all the cars that exist in the DB. An arrayList of Car objects will be returned. 
			case "car": {
				List<Car> list = Server.getCarControl().getCarListByCustomer(Integer.parseInt(Req.getRequestComponent(2)));
				try {
					client.sendToClient(list);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
				
			// Pull all the customers which ever participated in a sale that exist in the DB. An arrayList of CustomerDuringSale objects will be returned.
			// Automatically removes duplicates.	
			case "CustomerDuringSale": {
				try {
					List<CustomerDuringSale> list = new ArrayList<CustomerDuringSale>();
					// the "Get data" section
					Statement stm = this.conn.createStatement();
					ResultSet rs = stm.executeQuery("SELECT * FROM customerduringsale");
					while(rs.next()) {
						list.add(new CustomerDuringSale(rs.getDouble(1), rs.getString(2), rs.getInt(3)));
					}
					
					// the "Remove duplicates" section 
					int i, n;
					for(i = 0; i < list.size(); i++) {
						for(n=i+1; n<list.size(); n++) {
							if(list.get(n).getCustomerID() == list.get(i).getCustomerID()) {
								list.get(i).addToTotalSum(list.get(n).getTotalSumOfPurchases());
								list.remove(n);
							}
						}
					}
					// the return:
					client.sendToClient(list);
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
				break;
			}
				
				
			case "newcustomerformdata": {
				try {
					List<FuelCompany> templ = Server.getFCController().getFclist();
					if(templ != null && templ.size() > 0)
						client.sendToClient(templ);
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		
	}
}
