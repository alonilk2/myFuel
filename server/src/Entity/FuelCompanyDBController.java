package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;
/**
 * This server controller contains all the Run-Time data of the FuelCompany's system.
 * and also contains all the methods required for FuelCompany's functionalities.
 */
public class FuelCompanyDBController {
	private List<FuelCompany> fclist = new ArrayList<FuelCompany>();
	private sqlController sqlcontrol;
	public List<FuelCompany> getFclist() {
		return fclist;
	}
	public void setFclist(List<FuelCompany> fclist) {
		this.fclist = fclist;
	}
	public sqlController getSqlcontrol() {
		return sqlcontrol;
	}
	public void setSqlcontrol(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
	}
	public FuelCompanyDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
	}
	/**
	 * This method initializes the List of Cars on server startup.
	 */
	public void initializeList() {
		Statement stm;
		try {
			stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM fuelcompany");
			while(rs.next()) {
				FuelCompany temp = new FuelCompany(rs.getString(2), rs.getInt(1));
				fclist.add(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method returns FuelCompany instance by a given name.
	 * @param name The requested FuelCompany name
	 * @return FuelCompany instance.
	 */
	public FuelCompany getFuelCompanyFromString(String name) {
		for(FuelCompany f : fclist) {
			if(f.getCompanyName().equals(name))
				return f;
		}
		return null;
	}
}
