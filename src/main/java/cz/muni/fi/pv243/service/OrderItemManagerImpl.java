package cz.muni.fi.pv243.service;

import java.util.List;
import java.util.logging.Logger;

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
	private Logger log;
	
	@Inject
    private EntityManager em;

	@Override
	public void create(OrderItem item) {
		log.finest("Creating OrderItem: " + item.toString());
		if (item == null || item.getId() != null) {
			throw new IllegalArgumentException("Invalid item to create operation.");
		}
		em.persist(item);
		log.finest("Created order item: " + item.toString());
	}

	@Override
	public void update(OrderItem item) {
		log.finest("Updating item: " + item.toString());
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("Invalid item to update operation.");
		}
		em.merge(item);
		log.finest("Updated item: " + item.toString());
	}

	@Override
	public void delete(OrderItem item) {
		log.finest("Deleting item: " + item.toString());
		if (item == null || item.getId() == null) {
			throw new IllegalArgumentException("Invalid item to delete operation.");
		}
		OrderItem det = em.merge(item);
		em.remove(det);
		log.finest("Deleted item: " + item.toString());
	}

	@Override
	public OrderItem get(Long id) {
		log.finest("Get item by id: " + id);
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of item.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
	        CriteriaQuery<OrderItem> criteria = cb.createQuery(OrderItem.class);
	        Root<OrderItem> us = criteria.from(OrderItem.class);
	        criteria.select(us).where(cb.equal(us.get("id"), id));
	        log.finest("Returning single result");
	        return em.createQuery(criteria).getSingleResult();
		}catch (Exception e){
			log.finest("Returning null as no result");
			return null;
		}
		
	}
	
	@Override
	public List<OrderItem> findAll() {
		log.finest("Calling find all for OrderItem");
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<OrderItem> criteria = cb.createQuery(OrderItem.class);
			Root<OrderItem> us = criteria.from(OrderItem.class);
			criteria.select(us).orderBy(cb.asc(us.get("id")));
			log.finest("Returning list of all items.");
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			log.finest("Returning null as empty result");
			return null;
		}

	}
}
