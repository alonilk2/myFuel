package Entity;

import java.io.Serializable;
/**
 * This entity class contains all the information that Fuel Company is being represented by.
 *
 */
public class FuelCompany implements Serializable {
	private String companyName;
	private int companyID;
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public int getCompanyID() {
		return companyID;
	}
	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}
	public FuelCompany(String companyName, int companyID) {
		super();
		this.companyName = companyName;
		this.companyID = companyID;
	}
}
