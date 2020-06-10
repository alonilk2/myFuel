package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class Sale implements Serializable, SQLReady {
	private int TotalProfit;
	private int SaleID;
	private String Status;
	private int ParticipantsCounter;

	public Sale(int totalProfit, int saleID, String status, int participantsCounter)
	{
		this.TotalProfit = totalProfit;
		this.SaleID = saleID;
		this.Status = status;
		this.ParticipantsCounter = participantsCounter;
	}
	
	public int getTotalProfit()
	{
		return this.TotalProfit;
	}
	
	public void setTotalProfit(int totalProfit)
	{
		this.TotalProfit = totalProfit;
	}
	
	public int getSaleID()
	{
		return this.SaleID;
	}
	
	public void setSaleID(int saleID)
	{
		this.SaleID = saleID;
	}
	
	public String getStatus()
	{
		return this.Status;
	}
	
	public void setStatus(String status)
	{
		this.Status = status;
	}

	public int getParticipantsCounter()
	{
		return this.ParticipantsCounter;
	}
	
	public void setParticipantsCounter(int participantsCounter)
	{
		this.ParticipantsCounter = participantsCounter;
	}
	
	public int createNewAddSqlStatement(Connection conn) {
		int saleID = getSaleID();
		if(saleID > -1) {
			String qry;
			qry = "SELECT saleID FROM user WHERE saleID = " + saleID;
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				ResultSet rs = stm.executeQuery(qry);
				if(rs.next()) {
					qry = "INSERT INTO SaleID (NumberOfPurchases, SaleID, Status, totalSum)" + " VALUES (?,?,?,?)";
					try {
						stm = conn.prepareStatement(qry);
						stm.setInt(1,getTotalProfit());
						stm.setInt(2, saleID);
						stm.setString(3,this.getStatus());
						stm.setInt(4,getParticipantsCounter());
						stm.execute();
						return saleID;
					} catch (SQLException e) {
						e.printStackTrace();
						return -1;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}
		}
		return -1;
	}		
}

	
