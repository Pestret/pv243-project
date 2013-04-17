package cz.muni.fi.pv243.data;

import java.util.List;

import cz.muni.fi.pv243.model.Product;

public interface ProductManager {
	
	void create(Product product);
	
	void update(Product product);
	
	void delete(Product product);
	
	List<Product> findByName(String name);
	
}
