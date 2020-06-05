package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import control.sqlController;

public class FuelTypesDBController {
	private static List<FuelType> fuelTypesList;
	private sqlController sqlcontrol;

	public FuelTypesDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
		fuelTypesList = new ArrayList<FuelType>();
	}
	public void initializeList() {
		Statement stm;
		try {
			stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM Fueltype");
			while(rs.next()) {
				for(int i = 1; i <= 5; i++) {
					FuelType f = new FuelType(rs.getString(1),rs.getDouble(2),rs.getFloat(3),rs.getDouble(4),rs.getString(5));
					fuelTypesList.add(f);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public boolean updateFuelQuantity(FuelType ft, Double qty) {
		try {
			PreparedStatement stm = sqlcontrol.getConnection().prepareStatement("UPDATE fueltype SET quantity = ? where name = ?");
			stm.setDouble(1, ft.getQuantity()+qty);
			stm.setString(2, ft.getName());
			ft.setQuantity(ft.getQuantity() + qty);
			stm.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
	}
	public static FuelType getFuelTypeFromString(String name) {
		for(FuelType x : fuelTypesList) {
			if(x.getName().equals(name))
				return x;
		}
		return null;
	}
	public static FuelType findEqualFuelType(FuelType fuelType) {
		  for(FuelType f : fuelTypesList) {
			  if(f.equals(fuelType)) {
				  return f;
			  }
		  }
		  return null;
	}
	public Boolean checkFuelStock(FuelType f) {
		if(f.getQuantity() < f.getLowFuelBound())
			return true;
		else return false;
	}
}
