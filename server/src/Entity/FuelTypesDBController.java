package Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import control.sqlController;
/**
 * This server controller contains all the Run-Time data of the FuelType system.
 * and also contains all the methods required for FuelType's functionalities.
 */
public class FuelTypesDBController {
	private List<FuelType> fuelTypesList;
	private sqlController sqlcontrol;

	public FuelTypesDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
		fuelTypesList = new ArrayList<FuelType>();
	}
	/**
	 * This method initializes the List of Cars on server startup.
	 */
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
	/**
	 * This method receives an order quantity in order to reduce it from the station's stock.
	 * @param ft The FuelType instance to change it's stock quantity.
	 * @param qty Quantity of fuel that was purchased
	 * @return True on success
	 */
	public boolean updateFuelQuantity(FuelType ft, Double qty) {
		try {
			PreparedStatement stm = sqlcontrol.getConnection().prepareStatement("UPDATE fueltype SET quantity = ? where name = ?");
			stm.setDouble(1, ft.getQuantity()-qty);
			stm.setString(2, ft.getName());
			ft.setQuantity(ft.getQuantity() - qty);
			stm.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
	}
	/**
	 * This method returns a FuelType instance from given name.
	 * @param name The name of the requested FuelType.
	 * @return FuelType instance.
	 */
	public FuelType getFuelTypeFromString(String name) {
		for(FuelType x : fuelTypesList) {
			if(x.getName().equals(name))
				return x;
		}
		return null;
	}
	/**
	 * Returns a FuelType instance from the Server List equals to a given instance of FuelType (according to inner "equals" method)
	 * @param fuelType The instance of FuelType to look for
	 * @return Instance of FuelType.
	 */
	public FuelType findEqualFuelType(FuelType fuelType) {
		  for(FuelType f : fuelTypesList) {
			  if(f.equals(fuelType)) {
				  return f;
			  }
		  }
		  return null;
	}
	/**
	 * This method checks if a FuelType stock is lower than the minimum bound.
	 * @param f The fueltype to check it's stock
	 * @return True = lower then bound. 
	 */
	public Boolean checkFuelStock(FuelType f) {
		if(f.getQuantity() < f.getLowFuelBound())
			return true;
		else return false;
	}
}
