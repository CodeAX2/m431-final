package edu.montana;

import java.math.BigInteger;

/**
 * Represents a basic profiler to test generation and break times for
 * RSA keys of various bit sizes
 */
public class RSATester {

	public static void main(String[] args) {
		testGenerationTimes(10, 4096, 12);
		testBreakTimes(10, 32);
	}

	/**
	 * Test the generation time for RSA keys
	 * @param numPerBitSize the number of keys to generate for each
	 *     bit size
	 * @param maxBitSize the (rough) maximum bit size to generate keys
	 *     for. Keys will not be generated larget than maxBitSize *
	 * (divStepSize + 1) / divStepSize
	 * @param divStepSize the amount to divide the current bit size
	 *     when incrementing to the new bit size
	 */
	public static void testGenerationTimes(
		int numPerBitSize, int maxBitSize, int divStepSize
	) {

		for (int bitSize = 4;
			 bitSize < maxBitSize * (divStepSize + 1) / divStepSize;
			 bitSize += Math.ceil(bitSize / (float)divStepSize)) {

			RSAGenerator gen = new RSAGenerator(bitSize);

			long totalElapsed = 0;
			for (int i = 0; i < numPerBitSize; i++) {
				long start = System.currentTimeMillis();
				gen.nextKey();
				long end = System.currentTimeMillis();
				totalElapsed += end - start;
			}

			System.out.println(
				bitSize + ", " + totalElapsed / numPerBitSize
			);
		}
	}

	/**
	 * Test the break time for RSA keys
	 * @param numPerBitSize the number of keys to generate/break for
	 *     each bit size
	 * @param maxBitSize the maximum bit size to generate keys up to
	 */
	public static void testBreakTimes(
		int numPerBitSize, int maxBitSize
	) {
		RSABreaker breaker = new RSABreaker("primes.txt");
		breaker.loadPrimes();

		for (int bitSize = 3; bitSize <= maxBitSize; bitSize++) {
			RSAGenerator gen = new RSAGenerator(bitSize);
			long totalElapsed = 0;
			int successful = 0;
			for (int i = 0; i < numPerBitSize; i++) {
				RSAKey key = gen.nextKey();
				long start = System.currentTimeMillis();
				BigInteger dPriv = breaker.breakRSA(key);
				long end = System.currentTimeMillis();
				if (dPriv != null) {
					totalElapsed += end - start;
					successful++;
				}
			}
			if (successful != 0) {
				System.out.println(
					bitSize + ", " + totalElapsed / successful
				);
			} else {
				System.out.println(bitSize + ", FAILED");
			}
		}
	}
}
