package cz.muni.fi.pv243.data;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.ShoppingCart;

@RequestScoped
public class UserShoppingCartCache {
	@Inject
	EntityManager em;
	
	@Inject
	private Logger log;
	
	private List<ShoppingCart> unfinishedOrders;
	private List<ShoppingCart> finishedOrders;
	
	public List<ShoppingCart> getUnfinishedOrders () {
		return unfinishedOrders;
	}
	
	public List<ShoppingCart> getFinishedOrders () {
		return finishedOrders;
	}
	
	public void onShoppingCartChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final ShoppingCart shoppingCart) {
		loadAllOrders();
	}
	
	@PostConstruct
	public void loadAllOrders () {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
            Root<ShoppingCart> us = criteria.from(ShoppingCart.class);
            criteria.select(us).orderBy(cb.asc(us.get("id")));
            List<ShoppingCart> allOrders = em.createQuery(criteria).getResultList();
            finishedOrders = new ArrayList<ShoppingCart>();
            unfinishedOrders = new ArrayList<ShoppingCart>();
            for (ShoppingCart order : allOrders) {
            	if (order.isFinished()) {
            		finishedOrders.add(order);
            	} else {
            		unfinishedOrders.add(order);
            	}
            }
		}catch (Exception e) {
			finishedOrders = new ArrayList<ShoppingCart>();
			unfinishedOrders = new ArrayList<ShoppingCart>();
		}
	}
}
