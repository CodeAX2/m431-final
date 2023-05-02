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
		System.out.print("Enter a number to encrypt: ");
		BigInteger message = inputScanner.nextBigInteger();
		inputScanner.close();

		// Encrypt the number
		BigInteger encrypted = key.encrypt(message);
		System.out.println("Encrypted: " + encrypted);

		// Decrypt the number
		BigInteger decrypted = key.decrypt(encrypted);
		System.out.println("Decrypted: " + decrypted);

		// Break the encrypted number
		if (privD != null) {
			RSAKey customKey =
				new RSAKey(key.getN(), key.getE(), privD);
			BigInteger broken = customKey.decrypt(encrypted);
			System.out.println("Broken: " + broken);
		}
	}
}