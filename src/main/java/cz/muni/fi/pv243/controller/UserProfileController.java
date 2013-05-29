package cz.muni.fi.pv243.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.jboss.seam.security.Identity;

import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.service.OrderItemManager;
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
	
	@Inject
	private Logger log;
	
	private Long cardId;

	public BigDecimal priceOfOrder(Long id) {
		log.finest("Counting priceOfOrder");
		BigDecimal num = new BigDecimal(0);
		ShoppingCart cart = cartManager.get(id);
		List<OrderItem> items = cart.getItems();
		for(OrderItem item : items){
			num = num.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
		}
		log.finest("Counted priceOfOrder");
		return num;
	}
	 
	public void deleteOrder(Long id){
		cartManager.delete((cartManager.get(id)));
	}
	
	public List<OrderItem> getAllItems(){
		if(getCardId() == null){
			return new ArrayList<OrderItem>();
		}
		ShoppingCart cart = cartManager.get(getCardId());
		return cart.getItems();
	}
	
	public void detailRedirect(Long cartId) {
		setCardId(cartId);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("detail.jsf");
		} catch (IOException e) {
			log.warning("IO Exception: " + e.toString());
		}
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}  
}
