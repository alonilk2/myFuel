package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;

public class FuelTypeTempDBController {
	private List<fueltypeTemp> fuelTypesList = new ArrayList<fueltypeTemp>();
	private sqlController sqlcontrol;

	public FuelTypeTempDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
	}
	
	public void initializeList() {
		Statement stm;
		try {
			stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM fueltypetemp");
			while(rs.next()) {
				for(int i = 1; i <= 5; i++) {
					fueltypeTemp f = new fueltypeTemp(rs.getString(1),rs.getDouble(2),rs.getFloat(3),rs.getDouble(4),rs.getString(5));
					fuelTypesList.add(f);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
