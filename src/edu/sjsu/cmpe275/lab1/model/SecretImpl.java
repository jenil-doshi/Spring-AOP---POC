package edu.sjsu.cmpe275.lab1.model;

import java.util.UUID;
import edu.sjsu.cmpe275.lab1.model.ISecretService;

/**
 * This class implements all the methods of Secret Service Interface
 * @author Jenil
 *
 */
public class SecretImpl implements ISecretService {

	/**
	 * This method stores secret and returns it.
	 */
	public UUID storeSecret(String userId, Secret secret) {
		UUID secretId = UUID.randomUUID();
		secret.setSecretId(secretId);
		return secretId;
	}

	/**
	 * This method reads the secret of User
	 */
	public Secret readSecret(String userId, UUID secretId) {
		return null;
	}

	/**
	 * THis method shares the secret of user with another user
	 */
	public void shareSecret(String userId, UUID secretId, String targetUserId) {
	}

	/**
	 * This method unshares the secret of user with another user
	 */
	public void unshareSecret(String userId, UUID secretId, String targetUserId) {	
	}
}
