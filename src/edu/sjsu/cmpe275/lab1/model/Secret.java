package edu.sjsu.cmpe275.lab1.model;

import java.util.UUID;

/**
 * This is a secret class which stores the secret of a user.
 * @author Jenil
 *
 */
public class Secret {

	private UUID secretId = UUID.randomUUID();
	
	public Secret() {
	}

	public UUID getSecretId() {
		return secretId;
	}

	public void setSecretId(UUID secretId) {
		this.secretId = secretId;
	}
	
}
