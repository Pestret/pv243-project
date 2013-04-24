package cz.muni.fi.pv243.service;

import java.util.List;

import cz.muni.fi.pv243.model.ShoppingCart;

public interface ShoppingCartManager {
	
	void create(ShoppingCart cart);

	void update(ShoppingCart cart);
	
	void delete(ShoppingCart cart);
	
	ShoppingCart get(Long id);
	
	List<ShoppingCart> getFinishedOrders();
	
	List<ShoppingCart> getUnfinishedOrders();
}
