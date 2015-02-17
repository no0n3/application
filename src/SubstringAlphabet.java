import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SubstringAlphabet {

	static char[] alphabet;

	static {
		alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	}

	public static void main(String[] args) {
		String targetString = "abcdefghijklmn124345678!@#$%^&*opqrstUVwxyz!*abcdefghijklmn";

		System.out
				.println(smallestSubstringContainingTheAlphabet(targetString));
	}

	private static String smallestSubstringContainingTheAlphabet(
			final String str) {
		if (null == str) {
			return null;
		}
		if (containsInvalidASCIICharsOrWhitespaces(str)
				|| str.length() < alphabet.length) {
			return str;
		}

		HashMap<Character, Integer> hasFound = new HashMap<Character, Integer>();
		Set<Character> needToFind = new HashSet<Character>();

		for (char c : alphabet) {
			needToFind.add(c);
			hasFound.put(c, 0);
		}

		int minBeg = -1;
		int minEnd = -1;

		char[] targetStr = str.toCharArray();

		int minLen = Integer.MAX_VALUE;
		int foundC = 0;

		for (int beg = 0, end = 0; end < str.length(); end++) {
			char tce = (char) (targetStr[end] | 32);
			if (!needToFind.contains(tce)) {
				continue;
			}

			int c = g(hasFound.get(tce));
			if (0 == c) {
				foundC++;
			}
			hasFound.put(tce, c + 1);

			if (foundC == alphabet.length) {
				while (true) {
					char tcb = (char) (targetStr[beg] | 32);
					if (!needToFind.contains(tcb)) {
						beg++;
					} else if (g(hasFound.get(tcb)) > 1) {
						hasFound.put(tcb, decr(hasFound.get(tcb)));
						beg++;
					} else {
						break;
					}
				}

				int len = end - beg + 1;
				if (len < minLen) {
					minBeg = beg;
					minEnd = end;
					minLen = len;
				}
			}
		}

		if (-1 != minBeg && -1 != minEnd) {
			StringBuilder sb = new StringBuilder(str);
			sb.insert(minBeg, '[');
			sb.insert(minEnd + 2, ']');
			return sb.toString();
		} else {
			return str;
		}
	}

	private static int decr(Integer n) {
		return g(n) - 1;
	}

	private static int g(Integer n) {
		return null == n ? 0 : n;
	}

	private static boolean containsInvalidASCIICharsOrWhitespaces(String str) {
		for (char c : str.toCharArray()) {
			if (c < 33 || c > 127) {
				return true;
			}
		}
		return false;
	}

}