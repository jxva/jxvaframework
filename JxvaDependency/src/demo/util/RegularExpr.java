package demo.util;

/**
 * This is a simplified regular character mattching class. Supports *?^[]-
 * pattern characters.
 * 
 * @author <a href="mailto:rana_b@yahoo.com">Rana Bhattacharyya</a>
 */
public class RegularExpr {

	private char[]	mcPattern;

	/**
	 * Constructor.
	 * 
	 * @param pattern
	 *          regular expression
	 */
	public RegularExpr(String pattern) {
		mcPattern = pattern.toCharArray();
	}

	/**
	 * Compare string with a regular expression.
	 */
	public boolean isMatch(String name) {

		// common pattern - *
		if ((mcPattern.length == 1) && (mcPattern[0] == '*')) {
			return true;
		}

		return isMatch(name.toCharArray(), 0, 0);
	}

	/**
	 * Is a match?
	 */
	private boolean isMatch(char[] strName, int strIndex, int patternIndex) {

		while (true) {

			// no more pattern characters
			// if no more strName characters - return true
			if (patternIndex >= mcPattern.length) {
				return strIndex == strName.length;
			}

			char pc = mcPattern[patternIndex++];
			switch (pc) {

			// Match a single character in the range
			// If no more strName character - return false
			case '[':

				// no more string character - returns false
				// example : pattern = ab[^c] and string = ab
				if (strIndex >= strName.length) {
					return false;
				}

				char fc = strName[strIndex++];
				char lastc = 0;
				boolean bMatch = false;
				boolean bNegete = false;
				boolean bFirst = true;

				while (true) {

					// single character match
					// no more pattern character - error condition.
					if (patternIndex >= mcPattern.length) {
						return false;
					}
					pc = mcPattern[patternIndex++];

					// end character - break out the loop
					// if end bracket is the first character - always a match.
					// example pattern - [], [^]
					if (pc == ']') {
						if (bFirst) {
							bMatch = true;
						}
						break;
					}

					// if already matched - just read the rest till we get ']'.
					if (bMatch) {
						continue;
					}

					// if the first character is the negete
					// character - inverse the matching condition
					if ((pc == '^') && bFirst) {
						bNegete = true;
						continue;
					}
					bFirst = false;

					// '-' range check
					if (pc == '-') {

						// pattern string is [a- error condition.
						if (patternIndex >= mcPattern.length) {
							return false;
						}

						// read the high range character and compare.
						pc = mcPattern[patternIndex++];
						bMatch = (fc >= lastc) && (fc <= pc);
						lastc = pc;
					}

					// Single character match check. It might also be the
					// low range character.
					else {
						lastc = pc;
						bMatch = (pc == fc);
					}
				}

				// no match - return false.
				if (bNegete) {
					if (bMatch) {
						return false;
					}
				}
				else {
					if (!bMatch) {
						return false;
					}
				}
				break;

			// * - skip zero or more characters
			// No more patern character - return true
			// Increment strIndex till the rest of the pattern matches.
			case '*':

				// no more string character remaining - returns true
				if (patternIndex >= mcPattern.length) {
					return true;
				}

				// compare rest of the string
				do {
					if (isMatch(strName, strIndex++, patternIndex)) {
						return true;
					}
				}
				while (strIndex < strName.length);

				// Example pattern is (a*b) and the string is (adfdc).
				return false;

				// ? - skip one character - increment strIndex.
				// If no more strName character - return false.
			case '?':

				// already at the end - no more character - returns false
				if (strIndex >= strName.length) {
					return false;
				}
				strIndex++;
				break;

			// match character.
			default:

				// already at the end - no match
				if (strIndex >= strName.length) {
					return false;
				}

				// the characters are not equal - no match
				if (strName[strIndex++] != pc) {
					return false;
				}
				break;
			}
		}
	}

}