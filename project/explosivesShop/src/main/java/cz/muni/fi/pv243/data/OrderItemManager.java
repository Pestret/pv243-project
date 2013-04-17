package cz.muni.fi.pv243.data;

import cz.muni.fi.pv243.model.OrderItem;

public interface OrderItemManager {

	void create(OrderItem item);
	
	void update(OrderItem item);
	
	void delete(OrderItem item);
	
}
