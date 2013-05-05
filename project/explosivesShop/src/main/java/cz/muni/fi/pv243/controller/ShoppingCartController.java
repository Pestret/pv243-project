package cz.muni.fi.pv243.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.service.ShoppingCartManager;

@Model
public class ShoppingCartController {

	 @Inject
	 private FacesContext facesContext;

	 @Inject
	 private Identity identity;
	 private ShoppingCart cart;
	 
	 @Inject
	 private ShoppingCartManager cartManager;
	 
	 public ShoppingCartController(){
	 }
	 
	 public void addToCart(Long productId, int quantity){
		 
	 }
	 
	 public void deleteProductInCart(Long productId){
		 
	 }
	 
	 public List<OrderItem> getAll(){
		 return cart.getItems();
	 }
	 
	 public BigDecimal getTotalPrice(){
		 return new BigDecimal(0);
	 }
	 
	 public BigDecimal getItemPrice(Long productId){
		 return new BigDecimal(0);
	 }
}
