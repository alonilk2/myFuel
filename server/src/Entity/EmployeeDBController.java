package Entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import control.sqlController;
/**
 * This server controller contains all the Run-Time data of the Employee's system.
 * and also contains all the methods required for Employee's functionalities.
 */
public class EmployeeDBController {
	private sqlController sqlcontrol;
	private static List<Employee> employeeList;
	public EmployeeDBController(sqlController sqlcontrol) {
		this.sqlcontrol = sqlcontrol;
		employeeList = new ArrayList<Employee>();
	}
	/**
	 * This method initializes the List of Cars on server startup.
	 */
	public boolean initializeList() {
		try {
			Statement stm = sqlcontrol.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT u.username, u.password, u.firstname, u.lastname, u.email, e.employeeid, e.role, e.department "
					+ "FROM Employees e LEFT JOIN User u On u.userid = e.employeeid");
			while(rs.next()) {
				Employee e = new Employee(rs.getInt(6), Role.valueOf(rs.getString(7)), rs.getString(8),rs.getString(5), rs.getString(4), rs.getString(3), rs.getString(1), 
						rs.getString(2));
				employeeList.add(e);
			}
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method returns Employee instance by a given ID.
	 * @param ID The requested employee's ID
	 * @return Employee instance.
	 */
	public static Employee getEmployeeFromID(int ID) {
		for(Employee e : employeeList) {
			if(e.getEmployeeid() == ID)
				return e;
		}
		return null;
	}
}
