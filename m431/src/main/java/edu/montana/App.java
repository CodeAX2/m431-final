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

		//RSABreaker breaker = new RSABreaker("primes.txt");
		//breaker.loadPrimes();

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
	}
}