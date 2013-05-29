package cz.muni.fi.pv243.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.Product;

@Stateless
public class ProductManagerImpl implements ProductManager {
	
	@Inject
	private Logger log;
	
	@Inject
    private EntityManager em;

	@Inject
	private Event<Product> productEventSrc;
	
	@Override
	public void create(Product product) {
		log.finest("Creating product: " + product.toString());
		if (product == null || product.getId() != null) {
			throw new IllegalArgumentException("Invalid product to create operation.");
		}
		em.persist(product);
		productEventSrc.fire(product);
		log.finest("Created product: " + product.toString());
	}

	@Override
	public void update(Product product) {
		log.finest("Updating product: " + product.toString());
		if (product == null || product.getId() == null) {
			throw new IllegalArgumentException("Invalid product to update operation.");
		}
		em.merge(product);
		productEventSrc.fire(product);
		log.finest("Updated product: " + product.toString());
	}

	@Override
	public void delete(Product product) {
		log.finest("Deleting product: " + product.toString());
		if (product == null || product.getId() == null) {
			throw new IllegalArgumentException("Invalid product to delete operation.");
		}
		Product det = em.merge(product);
		em.remove(det);
		productEventSrc.fire(det);
		log.finest("Deleted product: " + product.toString());
	}

	@Override
	public List<Product> findByName(String name) {
		log.finest("Getting all products");
		if (name == null) {
			throw new IllegalArgumentException("Invalid name.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
	        Root<Product> us = criteria.from(Product.class);
	        criteria.select(us).where(cb.equal(us.get("name"), name));
	        log.finest("Returning list of all products");
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			log.info("Returning null as empty list");
			return null;
		}
		
	}

	@Override
	public Product get(Long id) {
		log.finest("Get product by id: " + id);
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of product.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
	        Root<Product> us = criteria.from(Product.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        log.finest("Returning product");
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			log.finest("Retuning null as no result");
			return null;
		}
		
	}

}
