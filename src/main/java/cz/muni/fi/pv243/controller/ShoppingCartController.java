package cz.muni.fi.pv243.controller;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.model.Product;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ShoppingCartManager;

@Model
public class ShoppingCartController {

	 @Inject
	 private FacesContext facesContext;

	 @Inject
	 private Identity identity;
	 
	 @Inject
	 private ShoppingCart cart;
	 
	 @Inject
	 private ShoppingCartManager cartManager;
	 
	 @Inject 
	 private ProductManager productManager;
	 
	 @Inject
	 private OrderItemManager orderManager;
	 
	 private int quantity = 1;
	 
	 public int getQuantity() {
		return quantity;
	}
	 
	 public void setQuantity(int quantity){
		 this.quantity = quantity;
	 }

	public void addToCart (Long productId, Long quantity) {
		 if(quantity == null){
			 throw new IllegalArgumentException("Long number quantity was null.");
		 }
		 addToCart(productId, quantity.intValue());
	 }
	 
	 public void addToCart(Long productId, int quantity){
		 Product product = productManager.get(productId);
		 List<OrderItem> items = cart.getItems();
		 for (OrderItem element : items) {
			 if(element.getProduct().equals(product)){
				 element.setQuantity(element.getQuantity()+quantity);
				 return;
			 }
		 }
		 OrderItem order = new OrderItem();
		 order.setProduct(product);
		 order.setQuantity(quantity);
		 items.add(order);
	 }
	 
	 public void deleteOrderItemInCart(Product product){
		 List<OrderItem> items = cart.getItems();
		 for(OrderItem forItem : items){
			 if(forItem.getProduct().equals(product)){
				 items.remove(forItem);
			 }
		 }
	 }
	 
	 public List<OrderItem> getAll(){
		 return cart.getItems();
	 }
	 
	 public void clearCart(){
		 cart.setItems(new ArrayList<OrderItem>());
	 }

}
