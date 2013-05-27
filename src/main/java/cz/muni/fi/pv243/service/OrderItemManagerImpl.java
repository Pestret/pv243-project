package cz.muni.fi.pv243.service;

import java.util.List;

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
			throw new IllegalArgumentException("Invalid item to create operation.");
		}
		em.persist(item);
	}

	@Override
	public void update(OrderItem item) {
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("Invalid item to update operation.");
		}
		em.merge(item);
	}

	@Override
	public void delete(OrderItem item) {
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("Invalid item to delete operation.");
		}
		OrderItem det = em.merge(item);
		em.remove(det);
	}

	@Override
	public OrderItem get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of item.");
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
	
	@Override
	public List<OrderItem> findAll() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrderItem> criteria = cb.createQuery(OrderItem.class);
			Root<OrderItem> us = criteria.from(OrderItem.class);
			criteria.select(us).orderBy(cb.asc(us.get("id")));
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			return null;
		}

	}
}
