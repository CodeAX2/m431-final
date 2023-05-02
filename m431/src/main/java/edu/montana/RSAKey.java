package edu.montana;

import java.math.BigInteger;

/**
 * Represents an RSA-based public and private key that can be used to
 * encrypt and decrypt messages
 */
public class RSAKey {

	private BigInteger keyN, keyE, keyD;

	/**
	 * Creates a new RSA public and private key
	 * @param keyN the N component of the public key
	 * @param keyE the E component of the public key
	 * @param keyD the D component of the private key
	 */
	public RSAKey(BigInteger keyN, BigInteger keyE, BigInteger keyD) {
		this.keyN = keyN;
		this.keyE = keyE;
		this.keyD = keyD;
	}

	/**
	 * Encrypts a gives message using RSA encryption
	 * @param message the message to encrypt
	 * @return the encrypted message
	 */
	public BigInteger encrypt(BigInteger message) {
		return message.modPow(keyE, keyN);
	}

	/**
	 * Encrypts a gives message using RSA encryption
	 * @param message the message to encrypt
	 * @return the encrypted message
	 */
	public BigInteger encrypt(int message) {
		return encrypt(BigInteger.valueOf(message));
	}

	/**
	 * Decrypts a given message previously encrypted using the same key
	 * @param message the message to decrypt
	 * @return the decrypted (original) message
	 */
	public BigInteger decrypt(BigInteger message) {
		return message.modPow(keyD, keyN);
	}

	/**
	 * Decrypts a given message previously encrypted using the same key
	 * @param message the message to decrypt
	 * @return the decrypted (original) message
	 */
	public BigInteger decrypt(int message) {
		return decrypt(BigInteger.valueOf(message));
	}

	/**
	 * Obtain the N component of the public key
	 * @return the N component
	 */
	public BigInteger getN() {
		return keyN;
	}

	/**
	 * Obtain the E component of the public key
	 * @return the E component
	 */
	public BigInteger getE() {
		return keyE;
	}
}
