package edu.montana;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a class that can attempt to break an RSA key using a
 * list of primes
 */
public class RSABreaker {

	private ArrayList<BigInteger> primes;
	private String primesFilePath;

	/**
	 * Creates a new RSA breaker that uses primes loaded into a file
	 * @param primesFilePath the path of the file containing the primes
	 */
	public RSABreaker(String primesFilePath) {
		this.primesFilePath = primesFilePath;
	}

	/**
	 * Loads all the primes from the file into memory
	 * @return true if the file was loaded successfully
	 */
	public boolean loadPrimes() {
		primes = new ArrayList<>();
		File file = new File(primesFilePath);
		primes = new ArrayList<>();

		Scanner fs;
		try {

			fs = new Scanner(file);
			while (fs.hasNextBigInteger()) {
				primes.add(fs.nextBigInteger());
			}

			fs.close();
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}
}
