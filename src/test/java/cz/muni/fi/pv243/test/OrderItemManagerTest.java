package cz.muni.fi.pv243.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.List;

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
import cz.muni.fi.pv243.service.OrderItemManager;
import cz.muni.fi.pv243.service.OrderItemManagerImpl;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ProductManagerImpl;
import cz.muni.fi.pv243.util.Resources;

@RunWith(Arquillian.class)
public class OrderItemManagerTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(Product.class, ProductManager.class,
						ProductManagerImpl.class, OrderItem.class,
						OrderItemManager.class, OrderItemManagerImpl.class,
						Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	ProductManager productManager;
	@Inject
	OrderItemManager orderItemManager;

	@Test
	public void createOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal("99.9"));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(55);
		orderItemManager.create(item);

		assertNotNull(item.getId());

		try {
			orderItemManager.create(item);
			fail();
		} catch (EJBException e) {

		}

		OrderItem item2 = new OrderItem();
		item2.setProduct(product);
		item2.setQuantity(5);
		item2.setId(852l);

		try {
			orderItemManager.create(item2);
			fail();
		} catch (EJBException e) {

		}

		try {
			orderItemManager.create(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void createWrongOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal("99.9"));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(0);

		try {
			orderItemManager.create(item);
			fail();
		} catch (EJBException e) {

		}

		item.setProduct(null);
		item.setQuantity(2);
		try {
			orderItemManager.create(item);
			fail();
		} catch (EJBException e) {

		}

		item.setProduct(new Product());
		try {
			orderItemManager.create(item);
			fail();
		} catch (EJBException e) {

		}

		item.setProduct(product);
		orderItemManager.create(item);
	}

	@Test
	public void updateOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(99);
		orderItemManager.create(item);

		item.setQuantity(8);
		orderItemManager.update(item);

		product.setPrice(new BigDecimal("99"));
		productManager.update(product);
		item.setProduct(product);
		orderItemManager.update(item);

		assertSame(orderItemManager.get(item.getId()).getProduct().getPrice()
				.longValue(), item.getProduct().getPrice().longValue());

		try {
			orderItemManager.update(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void updateWrongOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(99);
		orderItemManager.create(item);

		item.setProduct(null);
		try {
			orderItemManager.update(item);
			fail();
		} catch (EJBException e) {

		}

		item.setQuantity(0);
		try {
			orderItemManager.update(item);
			fail();
		} catch (EJBException e) {

		}

		item.setId(9999l);
		try {
			orderItemManager.update(item);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void deleteOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(99);
		orderItemManager.create(item);

		Long id = item.getId();
		assertNotNull(item.getId());
		orderItemManager.delete(item);

		OrderItem u = orderItemManager.get(id);
		assertNull(u);

		try {
			orderItemManager.delete(null);
			fail();
		} catch (EJBException e) {

		}

		try {
			orderItemManager.delete(new OrderItem());
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void getOrderItemTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(99);
		orderItemManager.create(item);

		OrderItem u = orderItemManager.get(item.getId());
		assertNotNull(u);
		assertEquals(u, item);

		u = orderItemManager.get(9699l);
		assertNull(u);

		try {
			orderItemManager.get(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void findAllTest() {
		List<OrderItem> list = orderItemManager.findAll();
		assertNotNull(list);
		int i = list.size();

		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		OrderItem item = new OrderItem();
		item.setProduct(product);
		item.setQuantity(99);
		orderItemManager.create(item);

		OrderItem item2 = new OrderItem();
		item2.setProduct(product);
		item2.setQuantity(999);
		orderItemManager.create(item2);

		OrderItem item3 = new OrderItem();
		item3.setProduct(product);
		item3.setQuantity(99);
		orderItemManager.create(item3);

		list = orderItemManager.findAll();
		assertNotNull(list);
		assertTrue(list.size() == i + 3);
	}
}
