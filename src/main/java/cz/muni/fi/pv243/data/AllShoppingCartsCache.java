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
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.service.ShoppingCartManager;

@RequestScoped
public class AllShoppingCartsCache {

	@Inject
	ShoppingCartManager shoppingCartManager;
	
	@Inject
	private Logger log;
	
	private List<ShoppingCart> unfinishedOrders;
	private List<ShoppingCart> finishedOrders;
	
	@Produces
	@Named(value = "unfinishedCarts")
	public List<ShoppingCart> getUnfinishedOrders () {
		return unfinishedOrders;
	}
	
	@Produces
	@Named(value = "finishedCarts")
	public List<ShoppingCart> getFinishedOrders () {
		return finishedOrders;
	}
	
	@Produces
	@Named(value = "allCarts")
	public List<ShoppingCart> getAllOrders () {
		List <ShoppingCart> result = new ArrayList<ShoppingCart>();
		result.addAll(getFinishedOrders());
		result.addAll(getUnfinishedOrders());
		return result;
	}
	
	public void onShoppingCartChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final ShoppingCart shoppingCart) {
		loadAllOrders();
	}

	@PostConstruct
	public void loadAllOrders () {
		finishedOrders = shoppingCartManager.getFinishedOrders();
		unfinishedOrders = shoppingCartManager.getUnfinishedOrders();
	}
}
