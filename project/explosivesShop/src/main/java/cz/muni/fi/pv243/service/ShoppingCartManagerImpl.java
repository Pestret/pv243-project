package cz.muni.fi.pv243.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.ShoppingCart;

@Stateful
public class ShoppingCartManagerImpl implements ShoppingCartManager {
	
	@Inject
    private EntityManager em;

	@Override
	public void create(ShoppingCart cart) {
		if (cart == null || cart.getId() != null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.persist(cart);
	}

	@Override
	public void update(ShoppingCart cart) {
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.merge(cart);
	}

	@Override
	public void delete(ShoppingCart cart) {
		if (cart == null || cart.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.remove(cart);
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
