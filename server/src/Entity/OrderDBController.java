package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;


public class OrderDBController {
	private sqlController sqlcontrol;
	private static List<Order> ordersList;
	public OrderDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
	    ordersList = new ArrayList<Order>();

	}
	public boolean initializeList() {
		//Initialize Orders List
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM orders");
			while(rs.next()) {
				for(int i = 1; i <= 6; i++) {
					Order o = new Order(rs.getInt(1), rs.getDouble(2), FuelTypesDBController.getFuelTypeFromString(rs.getString(3)),
							rs.getDouble(4), rs.getDate(5).toLocalDate(), rs.getInt(6));
					ordersList.add(o);
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
