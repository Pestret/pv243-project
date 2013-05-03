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
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.jboss.seam.security.Identity;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.model.User;

@RequestScoped
public class UserShoppingCartCache {
	@Inject
	EntityManager em;
	
	@Inject
	private Identity identity;
	
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
		finishedOrders = new ArrayList<ShoppingCart>();
        unfinishedOrders = new ArrayList<ShoppingCart>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ShoppingCart> criteria = cb.createQuery(ShoppingCart.class);
        Root<ShoppingCart> carts = criteria.from(ShoppingCart.class);
        User user = (cz.muni.fi.pv243.model.User) identity.getUser();
    	criteria.select(carts).orderBy(cb.asc(carts.get("id")));
    	Expression<User> userExpression = carts.get("user");
    	List<ShoppingCart> allOrders = em.createQuery(
    	criteria.where(cb.equal(userExpression, user)))
    	.getResultList();
    	for (ShoppingCart cart : allOrders) {
    		if (cart.isFinished()) {
    		finishedOrders.add(cart);
    		} else {
    		unfinishedOrders.add(cart);
    		}
    		}
	}
}