package cz.muni.fi.pv243.service;

import java.util.List;

import cz.muni.fi.pv243.model.OrderItem;

public interface OrderItemManager {

	void create(OrderItem item);
	
	void update(OrderItem item);
	
	void delete(OrderItem item);
	
	OrderItem get(Long id);
	
	List<OrderItem> findAll();
}
