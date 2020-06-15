package Entity;

import java.io.Serializable;

public class CustomerDuringSale implements Serializable {
	private double TotalSumOfPurchases;
	private String SaleID;
	private int CustomerID;

	public CustomerDuringSale(double totalSumOfPurchases, String saleID, int customerID)
	{
		this.TotalSumOfPurchases = totalSumOfPurchases;
		this.SaleID = saleID;
		this.CustomerID = customerID;
	}
	public double addToTotalSum(double d) {
		return this.TotalSumOfPurchases += d;
	}
	public double getTotalSumOfPurchases() {
		return TotalSumOfPurchases;
	}

	public void setTotalSumOfPurchases(double totalSumOfPurchases) {
		TotalSumOfPurchases = totalSumOfPurchases;
	}

	public String getSaleID() {
		return SaleID;
	}

	public void setSaleID(String saleID) {
		SaleID = saleID;
	}

	public int getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	
}