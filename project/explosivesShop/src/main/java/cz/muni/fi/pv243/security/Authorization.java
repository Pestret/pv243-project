package cz.muni.fi.pv243.security;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

import cz.muni.fi.pv243.model.*;

public class Authorization {

	@Inject
	Logger log;

	@Secures
	@Admin
	public boolean isAdmin(Identity identity) {
		if (!identity.isLoggedIn()) {
			return false;
		}
		log.finest("Authorization: user is authorized as admin");
		return "admin".equals(((User) identity.getUser()).getRole());
	}

	@Secures
	@BasicPermission
	public boolean isCustomer(Identity identity) {
		if (!identity.isLoggedIn()) {
			return false;
		}
		log.finest("Authorization: user has default permission level");
		return "customer".equals(((User) identity.getUser()).getRole());
	}

}
