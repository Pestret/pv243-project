package cz.muni.fi.pv243.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import cz.muni.fi.pv243.model.ShoppingCart;

@Stateless
public class ShoppingCartManagerImpl implements ShoppingCartManager {
	
	@Inject
    private EntityManager em;

	@Override
	public void create(ShoppingCart cart) {
		if (cart == null || cart.getId() != null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		//TODO validation everywhere
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

}
