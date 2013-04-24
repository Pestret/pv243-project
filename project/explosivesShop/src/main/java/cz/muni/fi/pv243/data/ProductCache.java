package cz.muni.fi.pv243.data;

import java.util.logging.Logger;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.Product;
@RequestScoped
public class ProductCache {
	
	@Inject
	EntityManager em;
	
	@Inject
	private Logger log;
	
	private List<Product> allProducts;
	
	@Produces
	@Named("products")
	public List<Product> getAllProducts() {
		return allProducts;
	}
	
	@PostConstruct
	public void loadAllProducts(){
        try {
        	CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
            Root<Product> us = criteria.from(Product.class);
            criteria.select(us).orderBy(cb.asc(us.get("name")));
            allProducts = em.createQuery(criteria).getResultList();
        } catch (Exception e) {
        	//TODO log
        	allProducts = new ArrayList<Product>();
        }
	}
	
	public void onProductListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final Product product) {
			loadAllProducts();
}
}
