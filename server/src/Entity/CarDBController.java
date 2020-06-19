package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;
/**
 * This server controller contains all the Run-Time data of the Cars system.
 * and also contains all the methods required for Car functionalities.
 */
public class CarDBController {
	private sqlController sqlcontrol;
	private EchoServer Server;
	private List<Car> carList;
	public CarDBController(sqlController sqlcontrol, EchoServer Server) {
		this.sqlcontrol = sqlcontrol;
		this.Server = Server;
		carList = new ArrayList<Car>();
	}
	/**
	 * This method initializes the List of Cars on server startup.
	 */
	public boolean initializeList() {
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT * FROM Car");
			while(rs.next()) {
				Car c = new Car(rs.getInt(1), rs.getInt(2), Server.getFTControl().getFuelTypeFromString(rs.getString(3)));
				carList.add(c);
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method returns a Car instance from given car's ID.
	 * @param ID Car's ID.
	 */
	public Car getCarFromID(int ID) {
		for(Car c : carList) {
			if(c.getCarID() == ID)
				return c;
		}
		return null;
	}
	/**
	 * Returns a list of all the car's the Customer own's.
	 * @param CustomerID The customer's ID.
	 * @return List of car's being owned by the Customer.
	 */
	public List<Car> getCarListByCustomer(int CustomerID){
		List<Car> tempList = new ArrayList<Car>();
		for(Car c : carList) {
			if(c.getCustomerID() == CustomerID)
				tempList.add(c);
		}
		return tempList;	
	}
}
