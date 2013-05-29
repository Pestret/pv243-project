package cz.muni.fi.pv243.service;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.annotation.ejb.Clustered;
import org.jboss.ha.framework.interfaces.RoundRobin;

import cz.muni.fi.pv243.model.ShoppingCart;

@RolesAllowed(value={"admin","customer"})
@Stateful
@Clustered(loadBalancePolicy=RoundRobin.class)
public class ShoppingCartManagerImpl implements ShoppingCartManager {
	
	@Inject
	private Logger log;
	
	@Inject
    private EntityManager em;

	@Inject
	private Event<ShoppingCart> cartEventSrc;
	
	@Override
	public void create(ShoppingCart cart) {
		log.finest("Creating shopping cart: " + cart.toString());
		if (cart == null || cart.getId() != null) {
			throw new IllegalArgumentException("Invalid cart to create operation.");
		}
		em.persist(cart);
		cartEventSrc.fire(cart);
		log.finest("Created shopping cart: " + cart.toString());
	}

	@Override
	public void update(ShoppingCart cart) {
		log.finest("Updating shopping cart: " + cart.toString());
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("Invalid cart to update operation.");
		}
		em.merge(cart);
		cartEventSrc.fire(cart);
		log.finest("Updated cart: " + cart.toString());
	} 

	@Override
	public void delete(ShoppingCart cart) {
		log.finest("Deleting shopping cart: " + cart.toString());
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("Invalid cart to delete operation.");
		}
		em.remove(get(cart.getId()));
		cartEventSrc.fire(cart);
		log.finest("Deleted shopping cart: " + cart.toString());
	}

	@Override
	public ShoppingCart get(Long id) {
		log.finest("Getting shopping cart by id: " + id);
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of cart.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        log.finest("Returning single result");
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			log.finest("Returning null as empty result");
			return null;
		}
		
	}

	@Override
	public List<ShoppingCart> getFinishedOrders() {
		log.finest("Getting finished orders");
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("isFinished"), true));
	        log.finest("Returning list of results");
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			log.finest("Returning null as empty result");
			return null;
		}
		
	}

	@Override
	public List<ShoppingCart> getUnfinishedOrders() {
		log.finest("Getting unfinished orders");
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("isFinished"), false));
	        log.finest("Returning list of unfinished orders");
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			log.finest("Returning null as empty list");
			return null;
		}
		
	}

}
