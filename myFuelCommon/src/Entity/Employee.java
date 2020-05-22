package Entity;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee extends User{

	private StringProperty employeeid;
	private StringProperty role;
	private StringProperty department;

	public Employee(String employeeid, String role, String department, String email, String firstname, 
			String lastname, String username, String password) {
		super(username, password, firstname, lastname, email);
		this.department = new SimpleStringProperty(department);
		this.role = new SimpleStringProperty(role);
		this.employeeid = new SimpleStringProperty(employeeid);;
	}
	public StringProperty employeeidProperty() {
		return this.employeeid;
	}
	public StringProperty roleProperty() {
		return this.role;
	}
	public StringProperty departmentProperty() {		
		return this.department;
	}
	public String getEmployeeid() {
		return this.employeeid.get();
	}
	public String getRole() {
		return this.role.get();
	}
	public String getDepartment() {		
		return this.department.get();
	}
	public void setEmployeeID(String EmployeeID) {
		if(EmployeeID != null) {
			this.employeeid.set(EmployeeID);
		}
	}
	public void setEmployeeRole(String Role) {
		if(Role != null) {
			this.role.set(Role);
		}
	}
	public void setEmployeeDep(String Department) {
		if(Department != null) {
			this.department.set(Department);
		}
	}
}
