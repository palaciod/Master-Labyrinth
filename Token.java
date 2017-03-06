package code;
/**
 * <h1>Token</h1>
 * Contains the variables and methods for creating MasterLabyrinth token items.  A Token
 * knows if it is visible or not, what the next token is, and what number it is.  Tokens are only ever
 * created with explicit number assignments, because otherwise they would not be a MasterLabyrinth token.
 * <p>
 * Variables:
 * <p>
 * {@code static int}: _nextToken - The next available token that can be collected by a player
 * {@code int}: _number - The integer value of the token instance (1-20, 25)
 * {@code boolean}: - Whether or not the token is visible to the players on the board
 * @author Michael Langaman
 * @author Tyler Barrett
 * @author William Stewart
 * @version S.2
 * @since S.2
 */
public class Token {

	private static int _nextToken = 1;
	private int _number;
	
	/**
	 * Explicit constructor that sets the Token's value/number to the int passed to it.
	 * @param number - int representing the number/value on the face of the token
	 */
	public Token(int number) {
		_number = number;
	}
	/**
	 * Explicitly sets value for next available token
	 * @param nextToken - int representing the explicit value of the next token available to be collected
	 */
	public static void setNextToken(int nextToken) {
		_nextToken = nextToken;
	}
	/**
	 * Returns value of the next available token
	 * @return value of next available token
	 */
	public static int getNextToken() {
		return _nextToken;
	}
	/**
	 * Reset the next available token to start game conditions (ie 1).
	 */
	public static void resetNextToken() {
		_nextToken = 1;
	}
	/**
	 * Increases the next available token by 1, until twenty have been collected
	 * at which point the next available token is 25, as per the game rules.
	 */
	public static void incrementNextToken() {
		if(_nextToken == 20) {
			_nextToken = 25;
			}
		else _nextToken++;
	}
	/**
	 * Returns the value of the token instance
	 * @return - value of the token instance
	 */
	public int getNumber() {
		return _number;
	}
	/**
	 * Returns, as a String using digits 1-9, the value of the token instance.
	 * (eg. 1, 4, 7, 11, 19, 25)
	 * @return - String containing the base 10 decimal representation of the token instance's number
	 */
	public String toString() {
		return "" + _number;
	}
}
