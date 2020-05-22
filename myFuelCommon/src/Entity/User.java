package Entity;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User{

	protected StringProperty username;
	protected StringProperty password;
	protected StringProperty firstname;
	protected StringProperty lastname;
	protected StringProperty email;
	
	public User(String username, String password, String firstname, String lastname, String email) {
		this.username = new SimpleStringProperty(username);
		this.firstname = new SimpleStringProperty(firstname);
		this.lastname = new SimpleStringProperty(lastname);
		this.password = new SimpleStringProperty(password);
		this.email = new SimpleStringProperty(email);
	}
	
	public String toString() {
		return "Username: "+this.username+" has logged in.";
	}
	
	public StringProperty usernameProperty() {
		return this.username;
	}
	public StringProperty passwordProperty() {
		return this.password;
	}
	public StringProperty firstnameProperty() {
		return this.firstname;
	}
	public StringProperty lastnameProperty() {
		return this.lastname;
	}
	public StringProperty emailProperty() {
		return this.email;
	}
	public void setUserFirstname(String newval) {
		if(newval != null) {
			this.firstname.set(newval);
		}
	}
	public void setUserLastname(String newval) {
		if(newval != null) {
			this.lastname.set(newval);
		}
	}
	public void setUserUsername(String newval) {
		if(newval != null) {
			this.username.set(newval);
		}
	}
	public void setUserPassword(String newval) {
		if(newval != null) {
			this.password.set(newval);
		}
	}
	public void setUserEmail(String newval) {
		if(newval != null) {
			this.email.set(newval);
		}
	}

}
