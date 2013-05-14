package cz.muni.fi.pv243.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
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

@SessionScoped
@Model
public class AdminController implements Serializable{

	private static final long serialVersionUID = 1464525L;

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
	
	private Long cId;
	
	private BigDecimal totalPrice = new BigDecimal(0);
	
	public BigDecimal getTotalPrice(Long shoppCartId){
		 ShoppingCart cart = cartManager.get(shoppCartId);
		 totalPrice = new BigDecimal(0);
		 for(OrderItem item: cart.getItems()){
			 totalPrice = totalPrice.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
		 }
		 return totalPrice;
	 }
	
	public void deleteOrder(Long id){
		cartManager.delete((cartManager.get(id)));
	}
	
	public List<OrderItem> getAllItems(){
		if(getCId() == null){
			return new ArrayList<OrderItem>();
		}
		ShoppingCart cart = cartManager.get(getCId());
		return cart.getItems();
	}
	
	public void detailRedirect(Long id) {
		setCId(id);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("detail_admin.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 
	public void confirmOrder(Long id){
		ShoppingCart cart = cartManager.get(id);
		cart.setFinished(true);
		cartManager.update(cart);
	}

	public Long getCId() {
		return cId;
	}

	public void setCId(Long id) {
		this.cId = id;
	}  
	
	public List<User> getAllUsers(){
		return userManager.findAll();
	}
	
	public void deleteUser(Long id){
		userManager.delete(userManager.get(id));
	}
}
