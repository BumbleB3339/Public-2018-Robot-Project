package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generates a SHA-256 hash of a given object.
 * 
 * @author Rody
 *
 * @param <T>
 */
public class BumbleHash<T> {

	private T object;

	public BumbleHash(T object) {
		this.object = object;
	}

	public String getHash() {
		String hash = "";
		try {
			byte[] encodedHash = MessageDigest.getInstance("SHA-256").digest(object.toString().getBytes());
			hash = bytesToHex(encodedHash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash;
	}

	private String bytesToHex(byte[] hash) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		return hexString.toString();
	}

}
