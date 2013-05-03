package cz.muni.fi.pv243.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.ejb.EJBException;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.muni.fi.pv243.model.OrderItem;
import cz.muni.fi.pv243.model.Product;
import cz.muni.fi.pv243.model.ShoppingCart;
import cz.muni.fi.pv243.model.User;
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.OrderItemManagerImpl;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ProductManagerImpl;
import cz.muni.fi.pv243.service.ShoppingCartManager;
import cz.muni.fi.pv243.service.ShoppingCartManagerImpl;
import cz.muni.fi.pv243.service.UserManager;
import cz.muni.fi.pv243.service.UserManagerImpl;
import cz.muni.fi.pv243.util.Resources;

@RunWith(Arquillian.class)
public class ShoppingCartManagerTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(User.class, ShoppingCart.class, OrderItem.class,
						Product.class, UserManager.class,
						UserManagerImpl.class,ProductManagerImpl.class, 
						ShoppingCartManagerImpl.class, OrderItemManagerImpl.class, ProductManager.class, 
						ShoppingCartManager.class, OrderItemManager.class, Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	Logger log;

	@Inject
	UserManager userManager;
	@Inject
	ProductManager product;
	@Inject
	OrderItemManager order;
	@Inject
	ShoppingCartManager shop;
	
	@Test
	public void Test() {
//		User user = new User();
//		user.setName("Pepa z depa");
//		user.setEmail("nebuduToDelat@milujipraci.cz");
//		user.setAddress("doma");
//		user.setPasswordHash("totalniH4sH");
//		userManager.create(user);
//		userManager.findByEmail("nebuduToDelat@milujipraci.cz");
		
		Product item = new Product();
		item.setAvailable(100);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		
		product.create(item);
		
		OrderItem ord = new OrderItem();
//		item.setId(0l);
		ord.setProduct(item);
		ord.setQuantity(3);
		
		order.create(ord);
		
		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setItems(list);
		
		shop.create(cart);
	
		List<ShoppingCart> fin = shop.getFinishedOrders();
		System.out.println(fin.size());
		List<ShoppingCart> unfin = shop.getUnfinishedOrders();
		System.out.println(unfin.size()+"");
	}
}
