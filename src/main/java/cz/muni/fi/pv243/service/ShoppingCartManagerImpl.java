package cz.muni.fi.pv243.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.annotation.ejb.Clustered;

import cz.muni.fi.pv243.model.ShoppingCart;

@Stateful
@Clustered
public class ShoppingCartManagerImpl implements ShoppingCartManager {
	
	@Inject
    private EntityManager em;

	@Inject
	private Event<ShoppingCart> cartEventSrc;
	
	@Override
	public void create(ShoppingCart cart) {
		if (cart == null || cart.getId() != null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.persist(cart);
		cartEventSrc.fire(cart);
	}

	@Override
	public void update(ShoppingCart cart) {
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.merge(cart);
		cartEventSrc.fire(cart);
	}

	@Override
	public void delete(ShoppingCart cart) {
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.remove(cart);
		cartEventSrc.fire(cart);
	}

	@Override
	public ShoppingCart get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			return null;
		}
		
	}

	@Override
	public List<ShoppingCart> getFinishedOrders() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("isFinished"), true));
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			return null;
		}
		
	}

	@Override
	public List<ShoppingCart> getUnfinishedOrders() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
	        Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
	        criteria.select(us).where(cb.equal(us.get("isFinished"), false));
	        return em.createQuery(criteria).getResultList();
		}catch (Exception e){
			return null;
		}
		
	}

}
