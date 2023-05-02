package edu.montana;

import java.math.BigInteger;
import java.util.Random;

/**
 * Represents a generator for RSA-based public and private keys
 */
public class RSAGenerator {

	private int bitLength;

	/**
	 * Creates a new key generator that supports messages up to the
	 * specified bit length
	 * @param bitLength the maximum bit length of messages to support
	 */
	public RSAGenerator(int bitLength) {
		this.bitLength = bitLength;
	}

	/**
	 * Generates a new public and private key using RSA
	 * @return the generated key
	 */
	public RSAKey nextKey() {

		Random r = new Random();

		// Generate 2 random primes
		BigInteger p = BigInteger.probablePrime(bitLength, r);
		BigInteger q = BigInteger.probablePrime(bitLength, r);

		// Ensure p != q
		while (p.equals(q)) {
			q = BigInteger.probablePrime(bitLength, r);
		}

		// Generate n
		BigInteger n = p.multiply(q);

		// Generate a
		BigInteger a = p.subtract(BigInteger.ONE)
						   .multiply(q.subtract(BigInteger.ONE));

		// Generate e by checking 2,3,4,... until we get gcd(e,a) = 1
		BigInteger e = BigInteger.TWO;
		while (e.compareTo(a) == -1) {
			if (e.gcd(a).equals(BigInteger.ONE))
				break;
			e = e.add(BigInteger.ONE);
		}

		// Generate d by solving d = (ka+1)/e mod a for some k

		// Increment k until we find a ka+1 divisible by e
		BigInteger k = BigInteger.ONE;
		while (!k.multiply(a)
					.add(BigInteger.ONE)
					.mod(e)
					.equals(BigInteger.ZERO)) {
			k = k.add(BigInteger.ONE);
		}

		// Use k to generate d
		BigInteger d =
			k.multiply(a).add(BigInteger.ONE).divide(e).mod(a);

		// Create the key
		return new RSAKey(n, e, d);
	}
}
