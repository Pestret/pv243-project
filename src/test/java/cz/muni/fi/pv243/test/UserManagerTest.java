package cz.muni.fi.pv243.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import cz.muni.fi.pv243.service.UserManager;
import cz.muni.fi.pv243.service.UserManagerImpl;
import cz.muni.fi.pv243.util.Resources;

@RunWith(Arquillian.class)
public class UserManagerTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(User.class, ShoppingCart.class, OrderItem.class,
						Product.class, UserManager.class,
						UserManagerImpl.class, Resources.class, Encoder.class)
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

	@Test
	public void createUserTest() {
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTo2@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		assertNotNull(user.getIdentificator());

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		User user2 = new User();
		user2.setName("Pepa Depa");
		user2.setEmail("nebuduTo2@milujipraci.cz");
		user2.setAddress("doma");
		user2.setPasswordHash("totalniH4sH");
		user2.setId(9l);

		try {
			userManager.create(user2);
			fail();
		} catch (EJBException e) {

		}

		try {
			userManager.create(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void createUserNameTest() {
		User user = new User();
		user.setName("");
		user.setEmail("nebuduTo3@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName("jmeno");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName(null);

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName("Pavel Burkes");
		userManager.create(user);
	}

	@Test
	public void createUserEmailTest() {
		User user = new User();
		user.setName("Pavel Burke");
		user.setEmail("");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("email");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail(null);

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("e@e");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("e.e");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("borec@zlesa.cm");
		userManager.create(user);
	}

	@Test
	public void createUserAddressTest() {
		User user = new User();
		user.setName("Pavel Burkee");
		user.setEmail("dovodo@ess.cz");
		user.setAddress("");
		user.setPasswordHash("totalniH4sH");

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setAddress(null);

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setAddress("vedle tebe");
		userManager.create(user);
	}

	@Test
	public void createUserPassTest() {
		User user = new User();
		user.setName("Pajo Boorkee");
		user.setEmail("dovo@ess.cz");
		user.setAddress("Strasse 8");
		user.setPasswordHash(null);

		try {
			userManager.create(user);
			fail();
		} catch (EJBException e) {

		}

		user.setPasswordHash("kruty");
		userManager.create(user);
	}

	@Test
	public void updateUserTest() {
		User user = new User();
		user.setName("Pepa Repa");
		user.setEmail("ac2@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		user.setAddress("home");
		userManager.update(user);

		user.setEmail("necaka@e.com");
		userManager.update(user);

		user.setName("Josef Kopecky");
		userManager.update(user);

		user.setPasswordHash("unhashedPass");
		userManager.update(user);

		try {
			userManager.update(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void updateUserNullOrEmptyTest() {
		User user = new User();
		user.setName("Pepa Repa");
		user.setEmail("ac23@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		user.setId(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setCart(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setRole(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setAddress(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setPasswordHash(null);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setAddress("");
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("");
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName("");
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void updateWrongUserTest() {
		User user = new User();
		user.setName("Pepa Repa");
		user.setEmail("acee2@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		User user2 = new User();
		user2.setName("Pepa Repa");
		user2.setEmail("aaac2@milujipraci.cz");
		user2.setAddress("dsssoma");
		user2.setPasswordHash("tsssotalniH4sH");
		userManager.create(user2);

		user.setId(user2.getIdentificator());
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setId(99999l);
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setEmail("email");
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}

		user.setName("jmeno");
		try {
			userManager.update(user);
			fail();
		} catch (EJBException e) {

		}
		
		try {
			userManager.update(new User());
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void deleteUserTest() {
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTeeo6@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		Long id = user.getIdentificator();
		assertNotNull(user.getIdentificator());
		userManager.delete(user);

		User u = userManager.get(id);
		assertNull(u);

		try {
			userManager.delete(null);
			fail();
		} catch (EJBException e) {

		}

		try {
			userManager.delete(new User());
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void getUserTest() {
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("Teeo6@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		User u = userManager.get(user.getIdentificator());
		assertNotNull(u);
		assertEquals(u, user);

		u = userManager.get(9699l);
		assertNull(u);

		try {
			userManager.get(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void findByEmailTest() {
		User u = userManager.findByEmail("nebuduTro6@mjip.cz");
		assertNull(u);
		
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTro6@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		User user2 = new User();
		user2.setName("Pepa Depa");
		user2.setEmail("nebuduTro6@mjipraci.cz");
		user2.setAddress("doma");
		user2.setPasswordHash("totalniH4sH");
		userManager.create(user2);

		User user3 = new User();
		user3.setName("Pepa Zruska");
		user3.setEmail("n@m.cz");
		user3.setAddress("doma");
		user3.setPasswordHash("totalniH4sH");
		userManager.create(user3);

		u = userManager.findByEmail("n@m.cz");
		assertNotNull(u);
		assertNotNull(u.getIdentificator());
		assertSame(u.getIdentificator(), user3.getIdentificator());

		u = userManager.findByEmail("nTr6@mj.cz");
		assertNull(u);

		try {
			userManager.findByEmail(null);
			fail();
		} catch (EJBException e) {

		}
	}

	@Test
	public void findAllTest() {
		List <User> list = userManager.findAll();
		assertNotNull(list);
		int i = list.size();
		
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTro6@misipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

		User user2 = new User();
		user2.setName("Pepa Depa");
		user2.setEmail("nebuduTro6@mjiprsaci.cz");
		user2.setAddress("doma");
		user2.setPasswordHash("totalniH4sH");
		userManager.create(user2);

		User user3 = new User();
		user3.setName("Pepa Zruska");
		user3.setEmail("nebo6@mjip.cz");
		user3.setAddress("doma");
		user3.setPasswordHash("totalniH4sH");
		userManager.create(user3);

		list = userManager.findAll();
		assertNotNull(list);
		assertTrue(list.size() == i+3);

	}
}
