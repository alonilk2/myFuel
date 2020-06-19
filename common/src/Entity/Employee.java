package Entity;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
/**
 * This entity class contains all the information that Employee is being represented by.
 *
 */
public class Employee extends User implements Serializable{

	private int employeeid;
	private Role role;
	private String department;

	public Employee(int employeeid, Role role, String department, String email, String firstname, 
			String lastname, String username, String password) {
		super(firstname, lastname, email, username, password, employeeid);
		this.department = department;
		this.role = role;
		this.employeeid = employeeid;
	}
	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}
