package cz.muni.fi.pv243.data;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
		//TODO validation everywhere
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

}
