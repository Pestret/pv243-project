package cz.muni.fi.pv243.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
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

import cz.muni.fi.pv243.model.Product;
import cz.muni.fi.pv243.service.ProductManager;
import cz.muni.fi.pv243.service.ProductManagerImpl;
import cz.muni.fi.pv243.util.Resources;

@RunWith(Arquillian.class)
public class ProductManagerTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(Product.class, ProductManager.class, ProductManagerImpl.class, Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsWebInfResource("test-ds.xml");
	}

	@Inject
	ProductManager productManager;

	@Test
	public void createProductTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal("99.9"));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		assertNotNull(product.getId());

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		Product product2 = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		product2.setId(9l);

		try {
			productManager.create(product2);
			fail();
		} catch (EJBException e) {

		}

		try {
			productManager.create(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void createProductNameTest() {
		Product product = new Product();
		product.setName("");
		product.setPrice(new BigDecimal(99));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		
		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setName(null);

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setName("TNT 754");
		productManager.create(product);
	}

	@Test
	public void createUserPriceTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(null);
		product.setDescription("bum bum bum");
		product.setAvailable(0);

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setPrice(new BigDecimal(-11));

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setPrice(new BigDecimal("11.25654455855"));

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setPrice(new BigDecimal(3654478));

		try {
			productManager.create(product);
			fail();
		} catch (EJBException e) {

		}

		product.setPrice(new BigDecimal(11));
		productManager.create(product);
	}

	@Test
	public void updateProductTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		product.setName("TNT 54");
		productManager.update(product);

		product.setPrice(new BigDecimal(81));
		productManager.update(product);

		product.setDescription("boom boom boom");
		productManager.update(product);

		product.setAvailable(1);
		productManager.update(product);

		try {
			productManager.update(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void updateProductNullOrEmptyTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		product.setId(null);
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}

		product.setPrice(null);
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}

		product.setName(null);
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}

		product.setName("");
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void updateWrongProductTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		product.setPrice(new BigDecimal(-1));
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}
		
		product.setPrice(new BigDecimal(444444441.1));
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}
		
		product.setPrice(new BigDecimal(1.8888888888888));
		try {
			productManager.update(product);
			fail();
		} catch (EJBException e) {

		}
		
		try {
			productManager.update(new Product());
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void deleteUserTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		Long id = product.getId();
		assertNotNull(product.getId());
		productManager.delete(product);

		Product u = productManager.get(id);
		assertNull(u);

		try {
			productManager.delete(null);
			fail();
		} catch (EJBException e) {

		}

		try {
			productManager.delete(new Product());
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void getUserTest() {
		Product product = new Product();
		product.setName("TNT 454");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		Product u = productManager.get(product.getId());
		assertNotNull(u);
		assertEquals(u, product);

		u = productManager.get(9699l);
		assertNull(u);

		try {
			productManager.get(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void findByNameTest() {		
		Product product = new Product();
		product.setName("CZ4");
		product.setPrice(new BigDecimal(1));
		product.setDescription("bum bum bum");
		product.setAvailable(0);
		productManager.create(product);

		Product product2 = new Product();
		product2.setName("CZ4");
		product2.setPrice(new BigDecimal(1));
		product2.setDescription("bum bum bum");
		product2.setAvailable(0);
		productManager.create(product2);

		Product product3 = new Product();
		product3.setName("TNT 454");
		product3.setPrice(new BigDecimal(1));
		product3.setDescription("bum bum bum");
		product3.setAvailable(0);
		productManager.create(product3);

		try {
			productManager.findByName(null);
			fail();
		} catch (EJBException e) {

		}
		 
		List<Product> list = productManager.findByName("CZ4");
		assertNotNull(list);
		assertEquals(list.size(), 2);		 
	}

}
