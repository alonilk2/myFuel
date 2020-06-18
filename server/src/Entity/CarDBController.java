package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;

/**
 * @author team 14
 * 
 * this class handles queries to the DB, specifically for "car" table in the DB.
 *
 */
public class CarDBController {
	
	private sqlController sqlcontrol;	// an instance of sqlController in order to gain better access to the DB data.
	private EchoServer Server;			// an instance of the server through which the program runs.
	private List<Car> carList;			// a private list for saving the data received
	
	/**
	 * A constructor for the class.
	 * 
	 * @param sqlcontrol - an instance of sqlController (in order to gain better access to the DB)
	 * @param Server - an instance of the echoServer (the server through which the program runs)
	 */
	public CarDBController(sqlController sqlcontrol, EchoServer Server) {
		this.sqlcontrol = sqlcontrol;
		this.Server = Server;
		carList = new ArrayList<Car>();
	}
	
	/**
	 * A method for setting the local list to be a list of objects that contain all the data received from the DB using a query.
	 * -------- Specifically here - saves an ArrayList of car objects filled with all the cars in the DB. --------
	 * 
	 * @return true if the query and data receiving were successful, otherwise false.
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
	 * A car getter (from the private list of this class)
	 * @param ID - get a car from the list using its specific ID
	 * @return a car object with the ID that was inserted. If no such car was found, returns null.
	 */
	public Car getCarFromID(int ID) {
		for(Car c : carList) {
			if(c.getCarID() == ID)
				return c;
		}
		return null;
	}
	
	/**
	 * A getter that returns a list of all the cars that belong to a specific customer.
	 * 
	 * @param CustomerID - the ID of the customer for which we want to receive the list of cars .
	 * @return an ArrayList of Car objects with the customerID that was entered.
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
