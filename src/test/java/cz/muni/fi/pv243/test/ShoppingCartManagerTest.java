package cz.muni.fi.pv243.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.security.util.Base64;
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
import cz.muni.fi.pv243.security.Encoder;
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
						ShoppingCartManager.class, OrderItemManager.class, Resources.class, Encoder.class)
						.addPackage(org.picketlink.idm.api.User.class.getPackage())
				.addPackage(Base64.class.getPackage())
				.addPackage("cz.muni.fi.pv243.model.validation")
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml");
	}

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
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nebuduTat@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);
		
		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		
		product.create(item);
		
		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);
		
		order.create(ord);
		
		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);
		
		shop.create(cart);
	}
}
