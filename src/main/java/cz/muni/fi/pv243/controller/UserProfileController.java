package cz.muni.fi.pv243.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

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
	
	private Long cardId;

	public BigDecimal priceOfOrder(Long id) {
		BigDecimal num = new BigDecimal(0);
		ShoppingCart cart = cartManager.get(id);
		List<OrderItem> items = cart.getItems();
		for(OrderItem item : items){
			num = num.add(item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())));
		}
		return num;
	}
	 
	public void deleteOrder(Long id){
		cartManager.delete((cartManager.get(id)));
	}
	
	public List<OrderItem> getAllItems(){
		ShoppingCart cart = cartManager.get(getCardId());
		return cart.getItems();
	}
	
	public void detailRedirect(Long cartId) {
		setCardId(cartId);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("detail.jsf");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}  
}
