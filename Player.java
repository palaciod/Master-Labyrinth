package code;

import java.util.HashSet;

/**
 * <h1>Player</h1>
 * Contains the variables and methods for a player to navigate a MasterLabyrinth
 * board.
 * <p>
 * <b>Note:</b>For player movement, a pawn is moved on a tile-by-tile basis.  This design choice,
 * and subsequent lack of a proper path finding method, has been made under the thought
 * that a player should not be able to pick a tile and be told if it is a valid tile to
 * land on.  Navigating the labyrinth on one's own is a core aspect of the game and is
 * thus preserved by not having that kind of functionality available to a player.
 * Movement is thereby restricted to single movements, and it is assumed that
 * the driver for the game will implement turn taking, which will allow multiple tile
 * movements.  
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Tile}:  _currentTile - Tile that the player's pawn is on.
 * {@code int}:  _playerNum - Ordinal number for the player (entered first, second, third, or fourth)
 * {@code String}:  _playerName - Contains the name of the player
 * {@code HashSet<Token>}: _tokens - Player's inventory of collected {@code Token} objects (for scoring)
 * 
 * @author Michael Langaman
 * @author William Stewart
 * @author Tyler Barrett
 * @author Daniel Palacio
 * @version S.2
 * @since S.1
 */
public class Player {
	
	private Tile _currentTile;
	private int _playerNum;
	private String _playerName;
	private HashSet<Token> _tokens;
	
	/**
	 * Sets the player "off" the board, on no Tile, with no 
	 * ordinal number, and age and name are effectively blank. 
	 */
	public Player() {
		_currentTile = null;
		_playerNum = -1;
		set_playerName("");
		_tokens = new HashSet<Token>();
		
	}
	
	/**
	 * Explicit constructor for a player's name, age, and ordinal number.
	 * @see #Player()
	 * @param name - Name of the player (for use in later stages with GUI representation)
	 * @param age - Specific age of the player (for turn taking purposes)
	 * @param number - order in which the player was entered into the game (1st, 2nd, etc.)
	 */
	public Player(String name, int number, Tile tile) {
		
		this();
		
		_playerNum = number;
		set_playerName(name);
		setTile(tile);
	}
	
	/**
	 * Attempts to collect the token on currentTile.  Token is forced visible if the tile has a token.
	 * Adds token to player inventory if it is collectable and sets the next available token.
	 * @return true if the player is able to collect the token, false if token is uncollectable
	 */
	public boolean takeToken() {
//		if (_currentTile.hasToken()) {
//			_currentTile.getToken().makeVisible();
//		}
		if (_currentTile.hasToken() && _currentTile.getToken().getNumber() == Token.getNextToken()) {
			_tokens.add(_currentTile.takeToken());
			Token.incrementNextToken();
			return true;
		} else return false;
	}
	/**
	 * Tells if the player object has at least one token.
	 * Returns true if the player has at least 1 token, false if the player has no tokens
	 * @return true if the player has at least 1 token, false if the player has no tokens
	 */
	public boolean hasToken() {
		return _tokens.size() > 0;
	}
	
	/**
	 * Assigns the {@code Tile} object that the current player is currently on to a {@code Player} instance.
	 * @param t 
	 */
	public void setTile(Tile t) {
		_currentTile = t;
		if (t != null) {
			_currentTile.addPlayer(this);
		}
	}
	
	/**
	 * Returns the {@code Tile} that the player is currently on. 
	 * @return the {@code Tile} instance the player is on.
	 */
	public Tile getTile() {
		return _currentTile;
	}
	
	/**
	 * Tries changing the current {@code Tile} the player is on to the {@code Tile} the player
	 * wants to move to.  If the path is valid, the player is moved to the destination {@code Tile},
	 * otherwise the method returns false and the player stays on the original {@code Tile}.
	 * @param destination Tile object which the player wants to move to.
	 * @param direction   Integer representation of cardinal directions 
	 * @return {@code true} if the destination {@code Tile} path is valid, false if not.
	 */
	public boolean move(Tile destination) {
//		System.out.println("Before move, player on: (" + _currentTile.getRow() + "," + _currentTile.getColumn() + ").");
		if (_currentTile != null && _currentTile.checkAdjTile(destination)) {
			_currentTile.removePlayer(this);
			destination.addPlayer(this);
			_currentTile = destination;
//			System.out.println("After move, player on: (" + _currentTile.getRow() + "," + _currentTile.getColumn() + ").");
			return true;
		} else {
//			System.out.println("Could not move to destination.");
			return false;
		}
	}
	
	/**
	 * Adds values of the tokens together using enhanced for loop.  Returns the player's score
	 * @return player's score (to be calculated once game is over).
	 */
	public int calculateScore() {
		int score = 0;
		for (Token token: _tokens) {
			score += token.getNumber();
		}
		return score;
	}
	
	/**
	 * Returns an integer value representing the ordinal position
	 * of the player, ie, first, second, third, or fourth player.
	 * @return Ordinal number of Player object
	 */
	public int getNumber() {
		return _playerNum;
	}
	
	/**
	 * Getter for _tokens instance var
	 * @return HashSet<Token> of the player's tokens.
	 */
	public HashSet<Token> getTokens() {
		return _tokens;
	}
	
	/**
	 * Returns the name of the player from the current {@code Player} instance.
	 * @return the name of the player.
	 */
	public String get_playerName() {
		return _playerName;
	}

	/**
	 * Assigns a specific name to the player.
	 * @param _playerName - the specific name of the player.
	 */
	public void set_playerName(String _playerName) {
		this._playerName = _playerName;
	}
}
