package cz.muni.fi.pv243.test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.logging.Logger;

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
	Logger log;

	@Inject
	UserManager userManager;

	@Test
	public void findByEmailTest() {
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTo6@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);
		userManager.findByEmail("nebuduTo@milujipraci.cz");
	}

	@Test
	public void createUser() {
		User user = new User();
		user.setName("Pepa Depa");
		user.setEmail("nebuduTo2@milujipraci.cz");
		user.setAddress("doma");
		user.setPasswordHash("totalniH4sH");
		userManager.create(user);

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
		} catch (EJBException e) {

		}
	}

	@Test
	public void createUserName() {
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
	public void createUserEmail() {
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
	public void wrongQueries() {
		assertNull(userManager.findByEmail("tenTuNeni!"));
	}

	@Test
	public void testUsers() {
		log.info(userManager.findAll().toString());
	}
}
