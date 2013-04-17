package cz.muni.fi.pv243.data;

import java.util.List;

import cz.muni.fi.pv243.model.User;

public interface UserManager {
	
	void create(User user);
	
	void update(User user);
	
	void delete(User user);
	
	List<User> findAll();
	
	List<User> findByName(String name);
	
	User findByEmail(String email);	

}
