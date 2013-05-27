package cz.muni.fi.pv243.service;

import java.util.List;

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
    private EntityManager em;

	@Inject
	private Event<Product> productEventSrc;
	
	@Override
	public void create(Product product) {
		if (product == null || product.getId() != null) {
			throw new IllegalArgumentException("Invalid product to create operation.");
		}
		em.persist(product);
		productEventSrc.fire(product);
	}

	@Override
	public void update(Product product) {
		if (product == null || product.getId() == null) {
			throw new IllegalArgumentException("Invalid product to update operation.");
		}
		em.merge(product);
		productEventSrc.fire(product);
	}

	@Override
	public void delete(Product product) {
		if (product == null || product.getId() == null) {
			throw new IllegalArgumentException("Invalid product to delete operation.");
		}
		Product det = em.merge(product);
		em.remove(det);
		productEventSrc.fire(det);
	}

	@Override
	public List<Product> findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid name.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
	        Root<Product> us = criteria.from(Product.class);
	        criteria.select(us).where(cb.equal(us.get("name"), name));
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			return null;
		}
		
	}

	@Override
	public Product get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of product.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
	        Root<Product> us = criteria.from(Product.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			return null;
		}
		
	}

}
