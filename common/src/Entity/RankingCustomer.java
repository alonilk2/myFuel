package Entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

public class RankingCustomer implements Serializable {
	private Integer CustomerID;
	private List<FuelType> fuelTypes;
	private List<LocalTime> fuelHours;
	private CustomerType customerType;
	private static final Double TotalRankFuelWeight = 4.0; //Percentage from total
	private static final Double TotalRankHoursWeight = 4.0; //Percentage from total
	private static final Double TotalCustomerTypeWeight = 2.0; //Percentage from total
	private static final Double Fuel95Weight = 8.0;
	private static final Double FuelDieselWeight = 10.0;
	private static final Double FuelBikeFuelWeight = 5.0;
	private static final Double PrivateTypeWeight = 7.0;
	private static final Double BusinessTypeWeight = 9.0;
	// Weight for each hour in the day, [00:00, 01:00, ... -> , 23:00]
	private static final Double[] HourWeight = new Double[] {2.0, 3.0, 2.0, 1.0, 2.0, 5.0, 8.0, 9.0, 8.0, 7.0, 5.0, 4.0, 4.0, 5.0, 6.0, 6.0, 7.0, 8.0, 8.0, 8.0, 5.0, 3.0, 4.0, 5.0};
	//Flag Array
	private boolean[] HourWeightIsAdded = new boolean[24];
	private Double TotalRank = 0.0;
	public RankingCustomer(Integer customerID, CustomerType customerType, List<FuelType> fuelTypes, List<LocalTime> fuelHours) {
		super();
		CustomerID = customerID;
		this.fuelTypes = fuelTypes;
		this.fuelHours = fuelHours;
		this.customerType = customerType;
	}
	public RankingCustomer(Integer customerID, Double Rank ,CustomerType customerType, List<FuelType> fuelTypes, List<LocalTime> fuelHours) {
		CustomerID = customerID;
		this.TotalRank = Rank;
		this.customerType = customerType;
		this.fuelHours =fuelHours;
		this.fuelTypes = fuelTypes;
	}
	public Integer getCustomerID() {
		return CustomerID;
	}
	public Double getTotalRank() {
		return TotalRank;
	}
	public void setCustomerID(Integer customerID) {
		CustomerID = customerID;
	}
	public List<FuelType> getFuelTypes() {
		return fuelTypes;
	}
	public void setFuelTypes(List<FuelType> fuelTypes) {
		this.fuelTypes = fuelTypes;
	}
	public List<LocalTime> getFuelHours() {
		return fuelHours;
	}
	public void setFuelHours(List<LocalTime> fuelHours) {
		this.fuelHours = fuelHours;
	}
	public CustomerType getCustomerType() {
		return customerType;
	}
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}
	public Double calculateRank() {
		// Calculate FuelType Part of Rank
		Double tempRank = 0.0;
		for(FuelType f : fuelTypes) {
			switch(f.getName()) {
				case "95":{
					if(tempRank < Fuel95Weight)
						tempRank = Fuel95Weight;
					break;
				}
				case "BikeFuel":{
					if(tempRank < FuelBikeFuelWeight)
						tempRank = FuelBikeFuelWeight;
					break;
				}
				case "Diesel":{
					if(tempRank < FuelDieselWeight)
						tempRank = FuelDieselWeight;
					break;
				}
			}		
		}
		TotalRank += tempRank*TotalRankFuelWeight/10;
		
		// Calculate Hour Part of Rank
		tempRank = 0.0;
		for(LocalTime l : fuelHours) {
			int hour =  l.getHour();
			if(!HourWeightIsAdded[hour]) {
				tempRank += HourWeight[hour];
				HourWeightIsAdded[hour] = true;
			}
		}
		if(tempRank > 100) tempRank = 100.0;
		tempRank /= 10;
		tempRank *= TotalRankHoursWeight/10; 
		TotalRank += tempRank;
		
		// Calculate CustomerType Part of Rank
		tempRank = 0.0;
		if(CustomerType.privatecustomer.equals(this.customerType))
			tempRank+=PrivateTypeWeight;
		else if(CustomerType.businesscustomer.equals(this.customerType))
			tempRank+=BusinessTypeWeight;
		
		TotalRank += tempRank*TotalCustomerTypeWeight/10;
		return TotalRank;
	}
}
