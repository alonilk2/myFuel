package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FuelType implements Serializable{

	private static final long serialVersionUID = 1L;
	private String name;
	private double quantity;
	private float price;
	private double lowFuelBound;
	public enum status {TEMP, ACTIVE, NOTACTIVE};
	private String stat;
	
	public FuelType(String name, double quantity, float price, double lowFuelBound, String stat) {
		this.name = name;
		this.quantity = quantity;
		this.price = price;
		this.lowFuelBound = lowFuelBound;
		this.stat = stat;
		//status =
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getQuantity() {
		return quantity;
	}
	public String getStatus() {
		return this.stat;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Double getLowFuelBound() {
		return lowFuelBound;
	}

	public void setLowFuelBound(Double lowFuelBound) {
		this.lowFuelBound = lowFuelBound;
	}
	
	//	Updates fuel quantity using the equation: [OldQuantity]+[qty]
	//	Returns the new quantity
	public Double updateFuelQuantity(Connection conn, Double qty) {
		try {
			PreparedStatement stm = conn.prepareStatement("UPDATE fueltype SET quantity = ? where name = ?");
			stm.setDouble(1, this.quantity+qty);
			this.quantity = this.quantity+qty;
			return this.quantity;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return 0.0;
	}
	
}
