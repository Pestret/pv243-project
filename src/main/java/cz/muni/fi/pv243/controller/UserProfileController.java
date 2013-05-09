package cz.muni.fi.pv243.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ShoppingCartManager;

@SessionScoped
@Model
public class UserProfileController implements Serializable {

	private static final long serialVersionUID = 2118963139482L;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private Identity identity;

	@Inject
	private ShoppingCartManager cartManager;

	@Inject
	private OrderItemManager orderManager;

	public BigDecimal priceOfOrder(Long id) {
		BigDecimal num = new BigDecimal(0);
		ShoppingCart cart = cartManager.get(id);
		List<OrderItem> items = cart.getItems();
		for(OrderItem item : items){
			num = num.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
		}
		return num;
	}
}
