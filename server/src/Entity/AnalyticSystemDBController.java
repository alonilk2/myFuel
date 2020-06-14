package Entity;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import control.sqlController;
import ocsf.server.ConnectionToClient;

public class AnalyticSystemDBController {
	private sqlController sqlcontrol;
	private EchoServer Server;
	private List<Order> ordersList;
	private List<RankingCustomer> rankingCustomers;
	public AnalyticSystemDBController(sqlController sqlcontrol,EchoServer Server) {
		this.sqlcontrol = sqlcontrol;
		this.Server = Server;
		rankingCustomers = new ArrayList<RankingCustomer>();
	}
	public void RecalculateRankings(ConnectionToClient client) {
		ordersList = Server.getOrderControl().getOrdersList();
		rankingCustomers = new ArrayList<RankingCustomer>();
		PreparedStatement stm;
		ResultSet rs;
		try {
			stm = sqlcontrol.getConnection().prepareStatement("SELECT customerid, customertype FROM Customer");
			rs = stm.executeQuery();
			while(rs.next()) {
				Integer customerID = rs.getInt(1);
				CustomerType customerType = CustomerType.valueOf(rs.getString(2));
				List<FuelType> CustfuelTypes = new ArrayList<FuelType>();
				List<LocalTime> fuelHours = new ArrayList<LocalTime>();
				for(Order o:ordersList) {
					if(o.getCustomerID() == customerID) {
						CustfuelTypes.add(o.getFueltype());
						fuelHours.add(o.getOrderTime());
					}
				}
				RankingCustomer r = new RankingCustomer(customerID, customerType, CustfuelTypes, fuelHours);
				r.calculateRank();
				rankingCustomers.add(r);
			}
			uploadRankingsToDB();
			client.sendToClient(rankingCustomers);
		} catch (SQLException | IOException e) {e.printStackTrace();}
	}
	private void uploadRankingsToDB() throws SQLException {
		PreparedStatement stm;
		stm = sqlcontrol.getConnection().prepareStatement("DELETE FROM analyticsystem");
		stm.execute();
		for(RankingCustomer r : rankingCustomers) {
			stm = sqlcontrol.getConnection().prepareStatement("INSERT INTO analyticsystem VALUES(?,?,?)");
			stm.setInt(1,r.getCustomerID());
			stm.setDouble(2, r.getTotalRank());
			stm.setString(3, r.getCustomerType().toString());
			stm.execute();
		}
	}
	public boolean initializeList() {
		try {
			ordersList = Server.getOrderControl().getOrdersList();
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM analyticsystem");
			while(rs.next()) {
				List<FuelType> list = new ArrayList<FuelType>();
				List<LocalTime> list2 = new ArrayList<LocalTime>();
				for(Order o: ordersList) {
					if(o.getCustomerID() == rs.getInt(1)) {
						if(!list.contains(o.getFueltype()))
							list.add(o.getFueltype());
						if(!list2.contains(o.getOrderTime()))
							list2.add(o.getOrderTime());
					}
				}
				RankingCustomer r = new RankingCustomer(rs.getInt(1), rs.getDouble(2), CustomerType.valueOf(rs.getString(3)), list, list2);
				rankingCustomers.add(r);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public void getCustomersRankList(ConnectionToClient client) {
		try {
			client.sendToClient(this.rankingCustomers);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getCustomersByFuelType(ConnectionToClient client, String FuelType) {
		try {
			ordersList = Server.getOrderControl().getOrdersList();
			List<String> idList = new ArrayList<String>();
			for(Order o: ordersList) {
				if(o.getFueltype().getName().equals(FuelType)) {
					idList.add(String.valueOf(o.getCustomerID()));
				}
			}
			client.sendToClient(idList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
