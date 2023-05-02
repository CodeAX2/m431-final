package edu.montana;

public class RSATester {

	public static void main(String[] args) {
		testGenerationTimes();
	}

	public static void testGenerationTimes() {

		for (int bitSize = 3; bitSize <= 4096;
			 bitSize += Math.ceil(bitSize / 10f)) {

			RSAGenerator gen = new RSAGenerator(bitSize);

			long totalElapsed = 0;
			for (int i = 0; i < 5; i++) {
				long start = System.currentTimeMillis();
				gen.nextKey();
				long end = System.currentTimeMillis();
				totalElapsed += end - start;
			}

			System.out.println(bitSize + ", " + totalElapsed / 5);
		}
	}
}
