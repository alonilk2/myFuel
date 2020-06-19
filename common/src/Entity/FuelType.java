package Entity;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 * This entity class contains all the information that FuelType is being represented by.
 *
 */
public class FuelType implements Serializable{

	private static final long serialVersionUID = 1L;
	// Defines the maximum fuel capacity the tanks can contain.
	private final int maxCapacity = 10000;

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
	public FuelType(String name, double quantity) {
		this.name = name;
		this.quantity = quantity;
		//status =
	}
	@Override
	public String toString() {
		return name;
	}
	/**
	 * This method receives an order quantity in order to reduce it from the station's stock.
	 * @param conn Connection to SQL Server	
	 * @param qty Quantity of fuel that was purchased
	 * @return True on success
	 */
	public boolean updateFuelQuantity(Connection conn, double qty) {
		try {
			PreparedStatement stm = conn.prepareStatement("UPDATE fueltype SET quantity = ? where name = ?");
			stm.setDouble(1, this.getQuantity()-qty);
			stm.setString(2, this.getName());
			this.setQuantity(this.getQuantity() - qty);
			stm.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
	}
	return false;
	}
	public int getMaxcapacity() {
		return this.maxCapacity;
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

	public boolean setQuantity(Double quantity) {
		if(quantity < 0) return false;
		this.quantity = quantity;
		return true;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FuelType other = (FuelType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
