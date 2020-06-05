package Entity;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee extends User implements Serializable{

	private int employeeid;
	private Role role;
	private String department;

	public Employee(int employeeid, Role role, String department, String email, String firstname, 
			String lastname, String username, String password) {
		super(username, password, firstname, lastname, email, employeeid);
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
