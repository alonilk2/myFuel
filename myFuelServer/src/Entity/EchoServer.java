// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package Entity;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.ServerController;
import control.UserController;
import control.sqlController;
import iF.SQLReady;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ocsf.server.*;



public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  final public static int DEFAULT_PORT = 5555;
  private List<List<Object>> list;
  
  //Global Lists ****************************************************
  private List<Order> ordersList;
  private List<HomeFuelOrder> HomeFuelOrdersList;
  private List<FuelType> fuelTypesList;
  
  //Controllers *****************************************************
  
  private UserController usercontrol;
  private sqlController sqlcontrol;
  private ServerController serverControl;
  
  //Constructors ****************************************************
  
  public EchoServer(int port) 
  {
    super(port);
    usercontrol = new UserController();
  }

  public void handleMessageFromClient(Object msg, ConnectionToClient client)
  {
	  System.out.println("Message received: " + msg + " from " + client);
	  try {
		handleObjectsFromClient(msg, client);
	} catch (IOException e) {
		e.printStackTrace();
	}
	  queryParser(msg.toString(), client);
  }
  private void handleObjectsFromClient(Object msg, ConnectionToClient client) throws IOException {
	  if(msg != null) {
		  if(msg instanceof SQLReady) {
			  //New HomeFuelOrder instance creation
			  if(msg instanceof HomeFuelOrder) {
				  HomeFuelOrder newOrder = (HomeFuelOrder)msg;
				  int genID = newOrder.createNewAddSqlStatement(sqlcontrol.getConnection());
				  if(genID == -1) client.sendToClient(false);
				  else {
					  newOrder.setOrderID(genID);
					  ordersList.add(newOrder);				
					  HomeFuelOrdersList.add(newOrder);
					  client.sendToClient(true);
				  }
			  }
		  }
	  }
  }
  /*
  //Handles ONLY Insert new rows
  //Returns last added row's auto-generated ID
  //Returns -1 on failure
  private int executeSQLInsert(PreparedStatement stm) {
	  try {
		if(stm.execute()) {
			ResultSet rs = stm.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
		}
	} catch (SQLException e) {
		e.printStackTrace();
		return -1;
	}
	return -1;
  }
  */
  	//SELECT FuelType
	public void queryParser(String input, ConnectionToClient client)
	{
		String firstWord[] = input.split(" ", 2);
		String qrytempl;
		ResultSet rs;
		PreparedStatement stm;
		List<String> retlist = new ArrayList<String>();
		Connection conn = sqlcontrol.getConnection();
		if(conn == null)
			return;
		switch(firstWord[0]) {
			// handles login request from server
			case "login":
			{
				try {
					qrytempl = "SELECT * FROM user WHERE username = ? AND password = ?";
					stm = conn.prepareStatement(qrytempl);
					String[] param = firstWord[1].split(" ", 2);
					stm.setString(1, param[0]);
					stm.setString(2, param[1]);
					rs = stm.executeQuery();
					if(rs.next()) 
						for(int i=1; i<=6; i++)
							retlist.add(rs.getString(i));
					else return;
					qrytempl = "SELECT u.username, u.password, u.firstname, u.lastname, u.email, c.customerid, c.customertype, c.purchaseplan,"
							+ " c.creditcard FROM user u LEFT JOIN customer c On u.userid = c.customerid WHERE c.customerid = ?";
					stm = conn.prepareStatement(qrytempl);
					stm.setString(1, rs.getString(6));
					rs = stm.executeQuery();
					if(rs.next()) {
						Customer c = new Customer(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), 
								rs.getString(5), rs.getInt(6), CustomerType.valueOf(rs.getString(7)), PurchasePlan.valueOf(rs.getString(8)), rs.getString(9));
						client.sendToClient(c);
					}
					usercontrol.createNewUserInstance(retlist);
				}
				catch (SQLException | IOException e) {
					e.printStackTrace();
				}
				break;
			}
			//pull is for getting info without any constraints
			case "pull":
			{
				firstWord = firstWord[1].split(" ", 2);
				//
				//		Pull HomeFuelOrder List for Customer
				//
				if(firstWord[0].equals("homefuelorder")) { // => firstWord[1] = customerID
					List<HomeFuelOrder> customerlist = new ArrayList<HomeFuelOrder>();
					for(HomeFuelOrder h : HomeFuelOrdersList) {
						if(h.getCustomerID() == Integer.parseInt(firstWord[1])) {
							customerlist.add(h);
						}
					}
					try {
						client.sendToClient(customerlist);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					list = new ArrayList<List<Object>>();
					qrytempl = "SELECT * FROM employees e LEFT JOIN user u ON u.email = e.email";
					stm = conn.prepareStatement(qrytempl);
					rs = stm.executeQuery();
					while(rs.next()) {
						List<Object> templist = new ArrayList<Object>();
						for(int x=1; x<=9; x++) 
							templist.add(rs.getString(x));
						list.add(templist);
					}
					if(list.size() == 0)
						return;
					try {
						client.sendToClient(list);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
			case "SELECT":
			{
				if(firstWord[1].equals("FuelType")){
					try {
						list = new ArrayList<List<Object>>();
						stm = conn.prepareStatement("SELECT * FROM FuelType");
						rs = stm.executeQuery();
						while(rs.next()) {
							List<Object> templist = new ArrayList<Object>();
							for(int x=1; x<=5; x++) 
								templist.add(rs.getString(x));
							list.add(templist);
						}
						try {
							client.sendToClient(list);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			case "update":
			{
				String[] param = firstWord[1].split(" ", 2);
				String table = param[0];
				param = param[1].split(" ", 2);
				String field = param[0];
				param = param[1].split(" ", 2);
				String oldval = param[0];
				String newval = param[1];
				qrytempl = "UPDATE Employees SET Role=? WHERE employeeid=?";
				try {
					stm = conn.prepareStatement(qrytempl);
					stm.setString(1, newval);
					stm.setString(2, oldval);
					stm.execute();

				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			}
		}
		return;
	}
  protected void serverStarted()
  {
    System.out.println("Server listening for connections on port " + getPort());
  }
  
	private FuelType getFuelTypeFromString(String name) {
		for(FuelType x : fuelTypesList) {
			if(x.getName().equals(name))
				return x;
		}
		return null;
	}
  protected void serverStopped()
  {
    System.out.println("Server has stopped listening for connections.");
  }
  private void init() {
    //
    //			Initialize Server Lists & Variables
    //						On start up
    //
    ordersList = new ArrayList<Order>();
    fuelTypesList = new ArrayList<FuelType>();
    HomeFuelOrdersList = new ArrayList<HomeFuelOrder>();
    Connection conn = sqlcontrol.getConnection();
    Statement stm;
    ResultSet rs;
	try {
		//Initialize FuelTypes List
		stm = conn.createStatement();
		rs = stm.executeQuery("SELECT * FROM Fueltype");
		while(rs.next()) {
			for(int i = 1; i <= 5; i++) {
				FuelType f = new FuelType(rs.getString(1),rs.getDouble(2),rs.getFloat(3),rs.getDouble(4),rs.getString(5));
				fuelTypesList.add(f);
			}
		}
		//Initialize Orders List
		rs = stm.executeQuery("SELECT * FROM orders");
		while(rs.next()) {
			for(int i = 1; i <= 6; i++) {
				Order o = new Order(rs.getInt(1), rs.getDouble(2), getFuelTypeFromString(rs.getString(3)),
						rs.getDouble(4), rs.getDate(5).toLocalDate(), rs.getInt(6));
				ordersList.add(o);
			}
		}
		//Initialize HomeFuelOrders List
		rs = stm.executeQuery("SELECT h.orderid, h.status, h.scheduled, h.address, h.fastsupply, o.ordersum, o.fueltype, o.quantity, o.orderdate, o.customerid FROM homefuelorder h INNER JOIN orders o ON h.orderid = o.orderid");
		while(rs.next()) {
			for(int i = 1; i <= 5; i++) {
				HomeFuelOrder o = new HomeFuelOrder(rs.getInt(1), rs.getDouble(6), getFuelTypeFromString(rs.getString(7)),
						rs.getDouble(8), rs.getDate(9).toLocalDate(),OrderStatus.valueOf(rs.getString(2)),
						rs.getDate(3).toLocalDate(), rs.getString(4), rs.getBoolean(5), rs.getInt(10));
				HomeFuelOrdersList.add(o);
			}
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
  }
  public static void main(String[] args) 
  {
    int port = 0;
    try
    {
      port = Integer.parseInt(args[0]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT;
    }
    EchoServer sv = new EchoServer(port);
    try 
    {
      sv.listen();
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
    sv.sqlcontrol = new sqlController();
    sv.init();
  }
}
//End of EchoServer class
