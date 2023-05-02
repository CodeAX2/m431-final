package edu.montana;

import java.math.BigInteger;
import java.util.Scanner;

public final class App {
	public static void main(String[] args) {

		// Create a generator for 256-bit messages
		RSAGenerator generator = new RSAGenerator(256);
		// Generate the key
		RSAKey key = generator.nextKey();
		System.out.println(
			"Public Key: (N=" + key.getN() + ", E=" + key.getE() + ")"
		);

		// Create the breaker
		RSABreaker breaker = new RSABreaker("primes.txt");
		breaker.loadPrimes();
		// Attempt to break manually
		System.out.println("Breaking RSA");
		BigInteger privD = breaker.breakRSA(key);
		if (privD != null) {
			System.out.println("Broke RSA! D=" + privD);
		} else {
			System.out.println("Could not break RSA!");
		}

		// Ask the user for a number to encrypt
		Scanner inputScanner = new Scanner(System.in);
		System.out.print("Enter a message to encrypt: ");
		String messageStr = inputScanner.nextLine();
		inputScanner.close();

		// Encrypt the message
		BigInteger message = new BigInteger(messageStr.getBytes());
		BigInteger encrypted = key.encrypt(message);
		System.out.println("Encrypted (decimal): " + encrypted);

		// Decrypt the message
		BigInteger decrypted = key.decrypt(encrypted);
		String decryptedStr = new String(decrypted.toByteArray());
		System.out.println("Decrypted: " + decryptedStr);

		// Break the encrypted message
		if (privD != null) {
			RSAKey customKey =
				new RSAKey(key.getN(), key.getE(), privD);
			BigInteger broken = customKey.decrypt(encrypted);
			String brokenStr = new String(broken.toByteArray());
			System.out.println("Broken: " + brokenStr);
		}
	}
}