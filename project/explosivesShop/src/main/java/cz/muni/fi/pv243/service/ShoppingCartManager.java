package cz.muni.fi.pv243.service;

import cz.muni.fi.pv243.model.ShoppingCart;

public interface ShoppingCartManager {
	
	void create(ShoppingCart cart);

	void update(ShoppingCart cart);
	
	void delete(ShoppingCart cart);
	
	ShoppingCart get(Long id);
	
}
