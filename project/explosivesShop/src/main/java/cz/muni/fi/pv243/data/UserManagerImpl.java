package cz.muni.fi.pv243.data;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import cz.muni.fi.pv243.model.Member;
import cz.muni.fi.pv243.model.User;

@ApplicationScoped
public class UserManagerImpl implements UserManager {

	@Inject
    private EntityManager em;
	
	@Override
	public void create(User user) {
		if (user.getId() != null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		//TODO validation everywhere
		em.persist(user);
	}

	@Override
	public void update(User user) {
		if (user.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.merge(user);
	}

	@Override
	public void delete(User user) {
		if (user.getId() == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		em.remove(user);
	}

	@Override
	public List<User> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> us = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(us).orderBy(cb.asc(us.get("name")));
        return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<User> findByName(String name) {
		if (name == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> us = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
        criteria.select(us).where(cb.equal(us.get("name"), name));
        return em.createQuery(criteria).getResultList();
	}

	@Override
	public User findByEmail(String email) {
		if (email == null) {
			throw new IllegalArgumentException("je to zosrate");
		}
		CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> criteria = cb.createQuery(User.class);
        Root<User> us = criteria.from(User.class);
        // Swap criteria statements if you would like to try out type-safe criteria queries, a new
        // feature in JPA 2.0
        // criteria.select(member).where(cb.equal(member.get(Member_.name), email));
        criteria.select(us).where(cb.equal(us.get("email"), email));
        return em.createQuery(criteria).getSingleResult();
	}

}
