package Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import control.sqlController;

public class OrderFromSupplierDBController {
	private List<OrderFromSupplier> orderFromSupplierList;
	private sqlController sqlcontrol;
	private EchoServer Server;
	public OrderFromSupplierDBController(sqlController sqlcontrol, EchoServer Server) {
		this.sqlcontrol = sqlcontrol;
		this.Server = Server;
		orderFromSupplierList = new ArrayList<OrderFromSupplier>();
	}

	public boolean initializeList() {
		//Initialize Orders List
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM orderfromsupplier");
			while(rs.next()) {
				OrderFromSupplier o = new OrderFromSupplier(rs.getInt(1), OrderStatus.valueOf(rs.getString(2)),
						EmployeeDBController.getEmployeeFromID(rs.getInt(3)),
						Server.getFTControl().getFuelTypeFromString(rs.getString(4)), rs.getDouble(5));
				orderFromSupplierList.add(o);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public ListIterator<OrderFromSupplier> getListIterator() {
		return orderFromSupplierList.listIterator();
	}
	public int createNewOrderFromFuelSupplier(FuelType fuelType) {
		OrderFromSupplier newOrder = new OrderFromSupplier(OrderStatus.PREPARING, fuelType,
			fuelType.getMaxcapacity() - fuelType.getQuantity());
		orderFromSupplierList.add(newOrder);
		String qry = "INSERT INTO orderfromsupplier (status, fueltype, quantity)" + " VALUES (?,?,?)";
		try {
			PreparedStatement stm = sqlcontrol.getConnection().prepareStatement(qry);
			stm.setString(1, OrderStatus.PREPARING.toString());
			stm.setString(2, newOrder.getFuelType().toString());
			stm.setDouble(3, newOrder.getQuantity());
			stm.execute();
			qry = "SELECT orderid FROM orderfromsupplier ORDER BY orderid DESC LIMIT 1";
			stm = sqlcontrol.getConnection().prepareStatement(qry);
			ResultSet rs = stm.executeQuery();
			if(rs.next()) {
				int orderID = rs.getInt(1);
				return orderID;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return newOrder.createNewAddSqlStatement(sqlcontrol.getConnection());
	}
}
