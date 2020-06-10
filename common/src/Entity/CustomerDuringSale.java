package Entity;

public class CustomerDuringSale {
	private int TotalSumOfPurchases;
	private int SaleID;
	private int CustomerID;

	public CustomerDuringSale(int totalSumOfPurchases, int saleID, int customerID)
	{
		this.TotalSumOfPurchases = totalSumOfPurchases;
		this.SaleID = saleID;
		this.CustomerID = customerID;
	}
	
	public int getTotalSumOfPurchases()
	{
		return this.TotalSumOfPurchases;
	}
	
	public void setTotalSumOfPurchases(int totalSumOfPurchases)
	{
		this.TotalSumOfPurchases = totalSumOfPurchases;
	}
	
	public int getSaleID()
	{
		return this.SaleID;
	}
	
	public void setSaleID(int saleID)
	{
		this.SaleID = saleID;
	}
	
	public int getCustomerID()
	{
		return this.CustomerID;
	}
	
	public void setCustomerID(int customerID)
	{
		this.CustomerID = customerID;
	}
	
	
}
