package cz.muni.fi.pv243.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJBException;
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
						UserManagerImpl.class, ProductManagerImpl.class,
						ShoppingCartManagerImpl.class,
						OrderItemManagerImpl.class, ProductManager.class,
						ShoppingCartManager.class, OrderItemManager.class,
						Resources.class, Encoder.class)
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
	ProductManager productManager;
	@Inject
	OrderItemManager orderManager;
	@Inject
	ShoppingCartManager shopManager;

	@Test
	public void test() {
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
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);

		shopManager.create(cart);

		try {
			shopManager.create(cart);
			fail();
		} catch (EJBException e) {

		}

	}

	@Test
	public void testWithNullItems() {
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nebuduTa1t@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		ShoppingCart cart2 = new ShoppingCart();
		cart2.setUser(user);
		cart2.setItems(null);
		try {
			shopManager.create(cart2);
			fail();
		} catch (EJBException e) {

		}

	}
	
	@Test
	public void testWithNull() {

		try {
			shopManager.create(null);
			fail();
		} catch (EJBException e) {

		}
		
	}
	
	@Test
	public void testUpdate() {
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nebuduTat@miraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);
		cart.setFinished(true);
		shopManager.create(cart);
		
		cart.setFinished(false);
		shopManager.update(cart);
		
		cart.setItems(new ArrayList<OrderItem>());
		shopManager.update(cart);
		
		User user2 = new User();
		user2.setName("Joseph Pepa");
		user2.setEmail("neTat@milujipraci.cz");
		user2.setAddress("doma");
		user2.setPasswordHash("totalniH4sH");
		userManager.create(user2);
		
		cart.setUser(user);
		shopManager.update(cart);
	}
	
	@Test
	public void testWrongUpdate() {
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nduTat@miraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);

		shopManager.create(cart);
		
		cart.setItems(null);
		try{
			shopManager.update(cart);
			fail();
		}catch(EJBException e){
			
		}
	}
	
	@Test
	public void testDelete() {
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nat@mici.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);

		shopManager.create(cart);
		Long id = cart.getId();
		assertNotNull(id);
		 
		shopManager.delete(cart);
		assertNull(shopManager.get(id));
		
		try {
			shopManager.delete(null);
			fail();
		} catch (EJBException e) {

		}
	}
	
	@Test
	public void testWrongDelete(){
		try {
			shopManager.delete(new ShoppingCart());
			fail();
		} catch (EJBException e) {

		}
	}
	
	@Test
	public void testGet() {
		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nat@mii.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> list = new ArrayList<OrderItem>();
		list.add(ord);
		cart.setUser(user);
		cart.setItems(list);

		shopManager.create(cart);

		ShoppingCart u = shopManager.get(cart.getId());
		assertNotNull(u);
		assertEquals(u, cart);

		u = shopManager.get(9699l);
		assertNull(u);

		try {
			shopManager.get(null);
			fail();
		} catch (EJBException e) {

		}
	}
	
	@Test
	public void testFinished() {
		List<ShoppingCart> list = shopManager.getFinishedOrders();
		assertNotNull(list);
		assertTrue(list.size() == 0);

		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nat@mi.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> lst = new ArrayList<OrderItem>();
		lst.add(ord);
		cart.setUser(user);
		cart.setItems(lst);
		cart.setFinished(true);

		shopManager.create(cart);

		list = shopManager.getFinishedOrders();
		assertNotNull(list);
		assertTrue(list.size() == 1);
	}
	
	@Test
	public void testUnfinished() {
		List<ShoppingCart> list = shopManager.getUnfinishedOrders();
		assertNotNull(list);
		int i = list.size();

		User user = new User();
		user.setName("Pepa Pepa");
		user.setEmail("nt@mii.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Product item = new Product();
		item.setAvailable(1);
		item.setDescription("bla bla");
		item.setName("Boziii");
		item.setPrice(new BigDecimal(99));
		productManager.create(item);

		OrderItem ord = new OrderItem();
		ord.setProduct(item);
		ord.setQuantity(3);

		ShoppingCart cart = new ShoppingCart();
		List<OrderItem> lst = new ArrayList<OrderItem>();
		lst.add(ord);
		cart.setUser(user);
		cart.setItems(lst);

		shopManager.create(cart);

		list = shopManager.getUnfinishedOrders();
		assertNotNull(list);
		assertTrue(list.size() == i + 1);
	}
}

