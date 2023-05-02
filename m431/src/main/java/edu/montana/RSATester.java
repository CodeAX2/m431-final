package edu.montana;

public class RSATester {

	public static void main(String[] args) {
		testGenerationTimes(10, 4096);
	}

	public static void testGenerationTimes(int numPerBitSize, int maxBitSize) {

		for (int bitSize = 3; bitSize <= maxBitSize; bitSize++) {

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
}
