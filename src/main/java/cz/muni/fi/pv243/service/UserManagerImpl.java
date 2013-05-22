package cz.muni.fi.pv243.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

@Stateless
public class UserManagerImpl implements UserManager {

	@Inject
	private EntityManager em;

	@Override
	public void create(User user) {
		if (user == null || user.getIdentificator() != null) {
			throw new IllegalArgumentException("Invalid user to create operation.");
		}
		try {
			user.setPasswordHash(Encoder.encode(user.getPasswordHash(),
					user.getEmail()));
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalArgumentException("Wrong password", ex);
		}
		if(user.getRole() == null)
			user.setRole("customer");
		em.persist(user);
	}

	@Override
	public void update(User user) {
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
	}

	@Override
	public void delete(User user) {
		if (user == null || user.getIdentificator() == null) {
			throw new IllegalArgumentException("Invalid user to delete operation.");
		}
		em.remove(user);
	}

	@Override
	@Produces
	@Named("users")
	public List<User> findAll() {
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).orderBy(cb.asc(us.get("name")));
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public List<User> findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("Invalid name of user.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).where(cb.equal(us.get("name"), name));
			return em.createQuery(criteria).getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("Invalid email of user.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).where(cb.equal(us.get("email"), email));
			return em.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public User get(Long id) {
		if (id == null) {
			throw new IllegalArgumentException("Invalid id of user.");
		}
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<User> criteria = cb.createQuery(User.class);
			Root<User> us = criteria.from(User.class);
			criteria.select(us).where(cb.equal(us.get("id"), id));
			return em.createQuery(criteria).getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
}
