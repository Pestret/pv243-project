package cz.muni.fi.pv243.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;
import org.jboss.seam.security.util.Base64;

public class Encoder {
	protected static Logger log = Logger.getAnonymousLogger();

	public static String encode(String password, String salt)
			throws NoSuchAlgorithmException {
		log.finest("Calling encode method");
		MessageDigest digest;
		digest = MessageDigest.getInstance("SHA-256");
		digest.reset();
		digest.update(salt.getBytes());
		log.finest("Returning encoded result");
		return Base64.encodeBytes(digest.digest(password.getBytes()));
	}
}
