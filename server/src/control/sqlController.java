package control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Entity.EchoServer;
import Entity.User;

public class sqlController {
	private Connection conn;
	private boolean isConnected;
	public sqlController() {
		try 
		{
	        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	        System.out.println("Driver definition succeed");
	    } 
	    catch (Exception ex) 
	    {
	    	 System.out.println("Driver definition failed");
	    }
	    try 
	    {
	    	Scanner scn = new Scanner(System.in);
	    	//System.out.println("Please enter your DB Password:");
	    	//password = scn.next();
	        conn = DriverManager.getConnection("jdbc:mysql://localhost/myfuel?serverTimezone=Asia/Jerusalem","root","abc12345");
	        System.out.println("SQL connection succeed");
	        isConnected = true;
	 	} 
	    catch (SQLException ex) 
	 	{
	        System.out.println("SQLException: " + ex.getMessage());
	        System.out.println("SQLState: " + ex.getSQLState());
	        System.out.println("VendorError: " + ex.getErrorCode());
	    }
	}
	
	public Connection getConnection() {
		if (isConnected)
			return this.conn;
		else return null;
	}
	public Boolean isConnected() {
		return this.isConnected;
	}
	

	
}
