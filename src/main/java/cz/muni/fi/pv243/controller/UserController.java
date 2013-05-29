/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.pv243.controller;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.PasswordCredential;

import cz.muni.fi.pv243.model.User;
import cz.muni.fi.pv243.security.Authorization;
import cz.muni.fi.pv243.security.Encoder;
import cz.muni.fi.pv243.service.UserManager;

@Model
public class UserController extends BaseAuthenticator {

	@Inject
	private Logger log;
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private UserManager userManager;

	@Inject
	private Credentials credentials;

	@Inject
	private Identity identity;

	private User newUser;

	@Produces
	@Named
	public User getNewUser() {
		return newUser;
	}

	public boolean isAdmin(Identity identity) {
		return Authorization.isAdmin(identity);
	}

	public boolean isCustomer(Identity identity) {
		return Authorization.isCustomer(identity);
	}

	@PostConstruct
	public void initNewUser() {
		newUser = new User();
	}

	public void register() throws Exception {
		log.finest("Registering");
		try {
			userManager.create(newUser);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Registered!", "Registration successful");
			facesContext.addMessage("registerForm:registerMessages", m);
			initNewUser();
			log.info("Registration success");
		} catch (Exception e) {
			log.info("Registration failed");
			facesContext.addMessage("registerForm:registerMessages",
					new FacesMessage("Registration failed."));
		}
	}

	public void auth() {
		log.finest("Calling auth method");
		identity.login();
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			if (!request
					.getHeader("Referer")
					.substring(
							request.getHeader("Referer").lastIndexOf('/') + 1)
					.equals("login.jsf")) {
				log.fine("Auth method invoked from previously unaccessible page.");
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(
								request.getHeader("Referer").substring(
										request.getHeader("Referer")
												.lastIndexOf('/') + 1));
			} else {
				if (!isAdmin(identity)) {
					log.fine("Auth method ended as user");
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("user_profile.jsf");
				} else {
					log.fine("Auth method ended as admin");
					FacesContext.getCurrentInstance().getExternalContext()
							.redirect("admin_page.jsf");
				}
			}

		} catch (Exception e) {
			log.warning("Auth method failure: " + e.toString());
		}
	}

	@Override
	public void authenticate() {
		log.finest("Calling authenticate method for identity");
		User userFromDb = userManager.findByEmail(credentials.getUsername());
		if (userFromDb == null) {
			log.info("Null user tried to log in");
			setStatus(AuthenticationStatus.FAILURE);
			facesContext.addMessage("loginForm:email", new FacesMessage(
					"Non existing user"));
		} else {
			try {
				if (userFromDb.getPasswordHash().equals(
						Encoder.encode(((PasswordCredential) credentials
								.getCredential()).getValue(), credentials
								.getUsername()))) {
					// login success
					log.info("Login success");
					facesContext.addMessage("loginForm:passwordHash",
							new FacesMessage("Good password"));
					setStatus(AuthenticationStatus.SUCCESS);
					setUser(userFromDb);

				} else {
					// login failed
					log.info("Login failed");
					facesContext.addMessage("loginForm:passwordHash",
							new FacesMessage("Wrong password"));
					setStatus(AuthenticationStatus.FAILURE);
				}
			} catch (NoSuchAlgorithmException e) {
				log.warning("Exception raised while logging in: " + e.toString());
				e.printStackTrace();
			}
		}
	}

}
