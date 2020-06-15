package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class saleTemplate implements Serializable, SQLReady{
	private String FuelType;
	private Float price;
	private String start;
	private String End;
	//public enum status {On, Off};
	private String sale_name;
	private String status;
	
	public saleTemplate(String FuelType,Float price,String start,String End,String status,String name) {
		this.FuelType=FuelType;
		this.price=price;
		this.start=start;
		this.End=End;
		this.status=status;
		this.sale_name=name;
	}
	public String getFuelType() {
		return FuelType;
	}

	public void setFuelType(String fuelType) {
		FuelType = fuelType;
	}

	public Float getPrice() {
		return price;
	}
	
	public String GetStastus() {
		return status;
	}

	public void setstatus(String status) {
		this.status = status;
	}
	public void setPrice(Float price) {
		this.price = price;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return End;
	}

	public void setEnd(String end) {
		End = end;
	}

	public String getSale_name() {
		return sale_name;
	}

	public void setSale_name(String sale_name) {
		this.sale_name = sale_name;
	}
	

	
	
	@Override
	public int createNewAddSqlStatement(Connection conn) {
		//////////////////////////////////////////////////////////////
		
			String qry = null;
	
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				ResultSet rs = stm.executeQuery(qry);
				if(rs.next()) {
					qry = "INSERT INTO saletemplate (fuelType, price, start, end, status, sale name)" + " VALUES (?,?,?,?,?)";
					try {
						stm = conn.prepareStatement(qry);
						stm.setString(1, this.getFuelType());
						stm.setFloat(2, this.getPrice());
						stm.setString(3, this.getStart());
						stm.setString(4, this.getEnd());
						stm.setString(5, this.GetStastus());
						stm.setString(6, this.getSale_name());
						stm.execute();
						return 1;
					} catch (SQLException e) {
						e.printStackTrace();
						return -1;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return -1;
			}	
		
		return -1;
	}

}
