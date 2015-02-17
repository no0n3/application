import java.util.LinkedList;
import java.util.List;

public class PrimesInInterval {

	public static void main(String[] args) {
		List<Integer> primes = primesInAnInterval(10, 30);
		System.out.println(primes);
	}

	private static List<Integer> primesInAnInterval(int from, int to) {
		if (from < 0 || to < 0) {
			throw new IllegalArgumentException("Invalid input");
		}
		if (from >= to) {
			throw new IllegalArgumentException("Invalid input");
		}
		List<Integer> primes = new LinkedList<Integer>();
		while (from <= to) {
			if (isPrime(from)) {
				primes.add(from);
			}
			from++;
		}
		return primes;
	}

	private static boolean isPrime(int n) {
		for (int i = 2; i < Math.sqrt(n); i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}
