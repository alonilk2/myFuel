package control;

import java.util.ArrayList;
import java.util.List;
import Entity.Order;

public class ServerController {
	//	Global Arrays
	private List<Order> ordersList;

	public ServerController() {
		ordersList = new ArrayList<Order>();
	}
	
	public int getOrdersListSize() {
		return ordersList.size();
	}
	public boolean addNewOrder(Order order) {
		return ordersList.add(order);
	}
}
