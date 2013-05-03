package cz.muni.fi.pv243.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.model.Product;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.ProductManager;
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
	 
	 @Inject 
	 private ProductManager productManager;
	 
	 @Inject
	 private OrderItemManager orderManager;
	 
	 @PostConstruct
	 public void initShoppingCart(){
		 List<OrderItem> list = new ArrayList<OrderItem>();
		 cart.setItems(list);
		 //cart.setUser(identity.getUser());
		 cartManager.create(cart);
	 }
	 
	 public void addToCart(Long productId, int quantity){
		 Product product = productManager.get(productId);
		 OrderItem order = new OrderItem();
		 order.setProduct(product);
		 order.setQuantity(quantity);
		 orderManager.create(order);
		 
		 
		 
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
	 
	 public void clearCart(){
		 
	 }
	 
	 private void addOrderItem(OrderItem item){
		 List<OrderItem> items = cart.getItems();
		 if(items.contains(item)){
			for(int i=0;i<items.size();i++){
				OrderItem inCart = items.get(i);
				if(inCart.equals(item)){
					inCart.setQuantity((inCart.getQuantity()+item.getQuantity()));
				}
			}
		 } else {
			 items.add(item);
		 }
	 }
}
