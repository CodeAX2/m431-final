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
	 * @param primesFilePath the path of the file containing the
	 *     primes
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

	public BigInteger breakRSA(RSAKey key) {
		BigInteger n = key.getN();

		// Check if n is larger than our max product
		BigInteger lastPrime = primes.get(primes.size() - 1);
		if (n.compareTo(lastPrime.multiply(lastPrime)) == 1) {
			// Too large, not going to find d
			return null;
		}

		boolean foundPair = false;
		BigInteger pairFirst = BigInteger.ONE, pairSecond =
												   BigInteger.ONE;

		for (int i = 0; i < primes.size() && !foundPair; i++) {
			pairFirst = primes.get(i);

			for (int j = i; j < primes.size() && !foundPair; j++) {
				pairSecond = primes.get(j);
				BigInteger product = pairFirst.multiply(pairSecond);

				if (n.compareTo(product) == -1) {
					break;
				} else if (n.compareTo(product) == 0) {
					foundPair = true;
				}
			}
		}

		// Did not find a pair, return null
		if (!foundPair)
			return null;

		// Generate a
		BigInteger a =
			pairFirst.subtract(BigInteger.ONE)
				.multiply(pairSecond.subtract(BigInteger.ONE));

		// Generate d using k
		BigInteger k = BigInteger.ONE;
		while (!k.multiply(a)
					.add(BigInteger.ONE)
					.mod(key.getE())
					.equals(BigInteger.ZERO)) {
			k = k.add(BigInteger.ONE);
		}

		// Use k to generate d
		return k.multiply(a)
			.add(BigInteger.ONE)
			.divide(key.getE())
			.mod(a);
	}
}
