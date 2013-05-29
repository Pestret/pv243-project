package cz.muni.fi.pv243.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.User;
import cz.muni.fi.pv243.security.Encoder;

@RolesAllowed(value={"admin","customer"})
@Stateless
public class UserManagerImpl implements UserManager {

	@Inject
	private EntityManager em;
	
	@Inject
	private Logger log;

	@Override
	public void create(User user) {
		log.finest("Creating user: " + user.toString());
		if (user == null || user.getIdentificator() != null) {
			throw new IllegalArgumentException("Invalid user to create operation.");
		}
		try {
			user.setPasswordHash(Encoder.encode(user.getPasswordHash(),
					user.getEmail()));
		} catch (NoSuchAlgorithmException ex) {
			log.warning("Exception while creating password hash.");
			throw new IllegalArgumentException("Wrong password", ex);
		}
		if(user.getRole() == null)
			user.setRole("customer");
		em.persist(user);
		log.finest("Created user: " + user.toString());
	}

	@Override
	public void update(User user) {
		log.finest("Updating user: " + user.toString());
		if (user == null || user.getIdentificator() == null) {
			throw new IllegalArgumentException("Invalid user to update operation.");
		}
		try {
			user.setPasswordHash(Encoder.encode(user.getPasswordHash(),
					user.getEmail()));
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalArgumentException("Wrong password", ex);
		}
		if(user.getRole() == null)
			user.setRole("customer");		
		em.merge(user);
		log.finest("Updated user: " + user.toString());
	}

	@Override
	public void delete(User user) {
		log.finest("Deleting user: " + user.toString());
		if (user == null || user.getIdentificator() == null) {
			throw new IllegalArgumentException("Invalid user to delete operation.");
		}
		User det = em.merge(user);
		em.remove(det);
		log.finest("Deleted user: " + user.toString());
	}

	@Override
	@Produces
	@Named("users")
	public List<User> findAll() {
		log.finest("Getting list of all users");
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).orderBy(cb.asc(us.get("name")));
			log.finest("Returning list of users.");
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			log.finest("Returning null as empty list.");
			return null;
		}

	}

	@Override
	public User findByEmail(String email) {
		log.finest("Finding user by email.");
		if (email == null) {
			throw new IllegalArgumentException("Invalid email of user.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).where(cb.equal(us.get("email"), email));
			log.finest("Returning list of user by email.");
			return em.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			log.finest("Returning null as empty list.");
			return null;
		}

	}

	@Override
	public User get(Long id) {
		log.finest("Get user by id");
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of user.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).where(cb.equal(us.get("id"), id));
			log.finest("Returning user by id");
			return em.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			log.finest("Returning null");
			return null;
		}

	}
}
