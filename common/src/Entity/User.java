package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import iF.SQLReady;

public class User implements Serializable{

	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	private int UserID;
	

	
	
	public User(String firstname, String lastname, String email, String username, String password,int userid) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.UserID = userid;
	}
	
	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userid) {
		UserID = userid;
	}

	public int createNewAddSqlStatementUser(Connection conn) {
		int userid = getUserID();
		if(userid > -1 && !(this instanceof Employee)) {
			String qry;
			qry = "SELECT userid FROM user WHERE userid != " + userid;
			try {
				PreparedStatement stm = conn.prepareStatement(qry);
				ResultSet rs = stm.executeQuery(qry);
				if(rs.next()) {
					qry = "INSERT INTO User (firstname, lastname, email, username, password, userid)" + " VALUES (?,?,?,?,?,?)";
					try {
						stm = conn.prepareStatement(qry);
						stm.setString(1, this.getFirstname());
						stm.setString(2, this.getLastname());
						stm.setString(3, this.getEmail());
						stm.setString(4, this.getUsername());
						stm.setString(5, this.getPassword());
						stm.setInt(6, userid);
						stm.execute();
						return userid;
					}catch (SQLException e) {
						//e.printStackTrace();
						return -1;
					}
				}
			} catch (SQLException e) {
				//e.printStackTrace();
				return -1;
				}
			}
		return -1;
		}
	}
	


