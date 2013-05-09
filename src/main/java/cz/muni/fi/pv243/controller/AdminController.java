package cz.muni.fi.pv243.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.model.User;
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ShoppingCartManager;
import cz.muni.fi.pv243.service.UserManager;

@Model
public class AdminController {
	
	@Inject
	 private FacesContext facesContext;
	
	@Inject
	private ShoppingCartManager cartManager;
	
	@Inject
	private UserManager userManager;
	
	@Inject
	private OrderItemManager orderManager;
	
	@Inject
	private ProductManager productManager;
	
	private BigDecimal totalPrice = new BigDecimal(0);
	
	public BigDecimal getTotalPrice(Long shoppCartId){
		 ShoppingCart cart = cartManager.get(shoppCartId);
		 totalPrice = new BigDecimal(0);
		 for(OrderItem item: cart.getItems()){
			 totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
		 }
		 return totalPrice;
	 }
}
