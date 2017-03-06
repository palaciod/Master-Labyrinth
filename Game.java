package code;

import java.util.ArrayList;

import gui.View;

/**
 * <h1>Game</h1> The underlying model that the graphical user interface
 * displays.
 * 
 * <b>Variables:</b>
 * <p>
 * {@code Board}: _board - Board that is initialized when game starts.
 * {@code View}: _view - reference to the graphical user interface.
 * {@code ArrayList<Player>}: _players - A list of the players that are in the
 * game. {@code boolean}: _boardShifted - whether or not the current player has
 * shifted the board. {@code boolean}: _currentPlayerHasMoved - whether or not
 * the current player has moved during their turn. {@code int}: _turnNumber -
 * indicates which player's turn it currently is. {@code Tile} _startingTile -
 * the tile the current player is on prior to their movement to a new tile.
 * 
 * @author Michael Langaman
 * @author Tyler Barret
 * @author Will Stewart
 * @author Dan Palacio
 * @version S.2
 * @since S.2
 */

public class Game {

	private Board _board;
	private View _view;
	private ArrayList<Player> _players;

	private Player _currentPlayer;
	private boolean _boardShifted;
	private boolean _currentPlayerHasMoved;
	private int _turnNumber;

	private int _lastShiftIndex;
	private int _lastShiftDirection;
	private Tile _startingTile;

	public Game(String[] args) {
		_players = new ArrayList<Player>();
		for (int index = 0; index < args.length; index++) {
			_players.add(new Player(args[index], index, null));
		}

		_board = new Board(_players);

		_turnNumber = 0;
		_lastShiftIndex = -1;
		_lastShiftDirection = -1;
		_currentPlayer = _players.get(0);
		setFlags();

		_view = null;
	}

	/**
	 * Sets <code> _startingTile </code> to the current player's tile and resets
	 * the boolean values <code> _boardShifted </code> and
	 * <code> _currentPlayerHasMoved </code back to false
	 */
	private void setFlags() {
		_startingTile = _currentPlayer.getTile();
		_boardShifted = false;
		_currentPlayerHasMoved = false;
	}

	/**
	 * Ends the current player's turn if <code> _boardShifted </code> and
	 * <code> _currentPlayerHasMoved </code> is true. After the player ends
	 * their turn, the <code> setFlags </code> method is called and resets these
	 * values.
	 */
	public void nextTurn() {
		if (Token.getNextToken() > 25) {
			endGame();
		}

		if (_boardShifted == false) {
			System.out.println("You must shift the board before ending your turn.");
		} else if (_currentPlayerHasMoved && _currentPlayer.getTile() == _startingTile) {
			System.out.println("You cannot end on the same tile you started from.");
		} else {
			_turnNumber++;
			_currentPlayer = _players.get(_turnNumber % _players.size());
			_view.updatePlayerInfo();
			setFlags();
		}
	}

	/**
	 * Ends the game after all 25 tokens are collected.
	 */
	private void endGame() {
		_view.endGame();
	}

	/**
	 * Sets <code> _view </code> to the specified parameter.
	 * 
	 * @param v
	 *            - reference to the View
	 */
	public void addView(View v) {
		_view = v;
	}

	/**
	 * Returns a tile specified by the parameter.
	 * 
	 * @param row
	 *            - the row the tile is located on the board.
	 * @param column
	 *            - the column the tile is located on the board.
	 * @return Tile specified by the specific row and column on the board.
	 */
	public Tile getTile(int row, int column) {
		return _board.getTile(row, column);
	}

	/**
	 * Returns the game's board
	 * 
	 * @return Board the game's board
	 */
	public Board getBoard() {
		return _board;
	}

	/**
	 * Returns the game's reference to the graphical user interface.
	 * 
	 * @return View the game's reference to the graphical user interface.
	 */
	public View getView() {
		return _view;
	}

	/**
	 * Calls the <code> refresh </code> method in the View class which repaints
	 * the board.
	 */
	public void refreshView() {
		_view.refresh();
	}

	/**
	 * Moves the current player to specified destination tile and sets
	 * <code> _currentPlayerHasMoved </code> to true.
	 * 
	 * @param destination
	 *            refers to the tile the player wants to move to.
	 */
	public void move(Tile destination) {
		if (_boardShifted) {
			_currentPlayer.move(destination);
			_currentPlayerHasMoved = true;
		} 
	}

	/**
	 * Shifts the board upwards at the specified index.
	 * 
	 * @param index
	 *            the specific column that is going to be shifted.
	 */
	public void shiftBoardColumnUp(int index) {
		if (_boardShifted == false && (_lastShiftIndex != index || _lastShiftDirection != Tile.SOUTH)) {
			_board.shiftUp(index);
			_view.updateBoardColumn(index);
			_lastShiftIndex = index;
			_lastShiftDirection = Tile.NORTH;
			_boardShifted = true;
		} 
	}

	/**
	 * Shifts the board downwards at the specified index and updates the
	 * graphical user interface.
	 * 
	 * @param index
	 *            the specific column that is going to be shifted.
	 */
	public void shiftBoardColumnDown(int index) {
		if (_boardShifted == false && (_lastShiftIndex != index || _lastShiftDirection != Tile.NORTH)) {
			_board.shiftDown(index);
			_view.updateBoardColumn(index);
			_lastShiftIndex = index;
			_lastShiftDirection = Tile.SOUTH;
			_boardShifted = true;
		} 
	}

	/**
	 * Shifts the board to the right at the specified index and updates the
	 * graphical user interface.
	 * 
	 * @param index
	 *            the specific row that is going to be shifted
	 */
	public void shiftBoardRowRight(int index) {
		if (_boardShifted == false && (_lastShiftIndex != index || _lastShiftDirection != Tile.WEST)) {
			_board.shiftRight(index);
			_view.updateBoardRow(index);
			_lastShiftIndex = index;
			_lastShiftDirection = Tile.EAST;
			_boardShifted = true;
		}
	}

	/**
	 * Shifts the board to the left at the specified index and updates the
	 * graphical user interface.
	 * 
	 * @param index
	 *            the specific row that is going to be shifted
	 */
	public void shiftBoardRowLeft(int index) {
		if (_boardShifted == false && (_lastShiftIndex != index || _lastShiftDirection != Tile.EAST)) {
			_board.shiftLeft(index);
			_view.updateBoardRow(index);
			_lastShiftIndex = index;
			_lastShiftDirection = Tile.WEST;
			_boardShifted = true;
		}
	}

	/**
	 * Rotates the free tile in the clockwise direction and updates it's icon on
	 * the graphical user interface.
	 */
	public void rotateFreeTileClockWise() {
		_board.getFreeTile().rotateIcon(Tile.CW);
		_board.getFreeTile().rotateClockwise();
		_view.updateFreeTile();
	}

	/**
	 * Rotates the free tile in the counter clockwise direction and updates it's
	 * icon on the graphical user interface.
	 */
	public void rotateFreeTileCounterClockWise() {
		_board.getFreeTile().rotateIcon(Tile.CCW);
		_board.getFreeTile().rotateCounterClockwise();
		_view.updateFreeTile();
	}

	/**
	 * Current player collects the token and updates the graphical user
	 * interface.
	 */
	public void collectToken() {
		if (_currentPlayerHasMoved && _currentPlayer.takeToken()) {
			_view.updatePlayerInfo();
			_view.updateToken(_currentPlayer.getTile().getRow(), _currentPlayer.getTile().getColumn());
			nextTurn();
		}
	}
	
	/**
	 * Returns the current player.
	 * 
	 * @return the current player
	 */
	public Player getCurrentPlayer() {
		return _currentPlayer;
	}
	
	/**
	 * Returns the amount of players in the game.
	 * 
	 * @return an integer value that indicates size of the <code> _players </code> 
	 */
	public int getPlayers() {
		return _players.size();
	}
	
	/**
	 * Returns the list of players in the game
	 * 
	 * @return <code> _players </code>
	 */
	public ArrayList<Player> getPlayerList() {
		return _players;
	}
	
	/**
	 * Returns whether or not the current player has moved.
	 * 
	 * @return true if the move method has been called, false otherwise.
	 */
	public boolean currentPlayerHasMoved() {
		return _currentPlayerHasMoved;
	}
	
	/**
	 * Sets the value of <code> _currentPlayerHasMoved </code> to true. Used for testing purposes. 
	 */
	public void setCurrentPlayerMovedValue() {
		_currentPlayerHasMoved = true;
	}
	
	/**
	 * Returns whether or not the board has been shifted.
	 * 
	 * @return true if any of the shift board methods have been called, false otherwise.
	 */
	public boolean boardHasShifted() {
		return _boardShifted;
	}
	
	/**
	 * Sets <code> _boardShifted </code> to true for testing purposes.
	 */
	public void setBoardShiftValue() {
		_boardShifted = true;
	}
	
	/**
	 * Sets the value of <code> _lastShiftIndex </code> to the specified parameter. Used for testing purposes.
	 * 
	 * @param index indicates which index was last shifted
	 */
	public void setLastShiftIndex(int index) {
		_lastShiftIndex = index;
	}
	
	/**
	 * Sets the value of <code> _lastShiftDirection </code> to the specified parameter. Used for testing purposes.
	 * 
	 * @param direction indicates in which direction the board was last shifted
	 */
	public void setLastShiftDirection(int direction) {
		_lastShiftDirection = direction;
	}
	
}


