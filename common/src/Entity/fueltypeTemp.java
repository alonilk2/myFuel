package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class fueltypeTemp implements Serializable{


	private static final long serialVersionUID = 1L;


	private String name;

	private Float tmp_price;

	public enum status {wait, approve, rejecte};
	private String stat;
	
	public fueltypeTemp(String name, Float tmp_price, String stat) {
		this.name = name;
		this.tmp_price = tmp_price;
		this.stat = stat;
	
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getStatus() {
		return this.stat;
	}



	public Float getPrice() {
		return tmp_price;
	}

	public void setPrice(Float price) {
		this.tmp_price = price;
	}

}
