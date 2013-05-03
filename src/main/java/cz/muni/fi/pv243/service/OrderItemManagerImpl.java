package cz.muni.fi.pv243.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.OrderItem;

@Stateless
public class OrderItemManagerImpl implements OrderItemManager {
	
	@Inject
    private EntityManager em;

	@Override
	public void create(OrderItem item) {
		if (item == null || item.getId() != null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.persist(item);
	}

	@Override
	public void update(OrderItem item) {
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.merge(item);
	}

	@Override
	public void delete(OrderItem item) {
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.remove(item);
	}

	@Override
	public OrderItem get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<OrderItem> criteria = cb.createQuery(OrderItem.class);
	        Root<OrderItem> us = criteria.from(OrderItem.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			return null;
		}
		
	}

}
