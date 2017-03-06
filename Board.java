package code;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * <h1>Board</h1>
 * Contains the variables and methods necessary to create a MasterLabyrinth
 * gameboard.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Random}:		r - a Random object to be used for randomizing tile types when populating the board (can be shared by all instances)
 * {@code Tile[][]}:  	_board - a two-dimensional array of Tile objects representing the game board, with indices ordered as [row][column]
 * {@code int}:			numS - the number of tiles on this board with 2 path options in a straight-across orientation
 * {@code int}:			numL - the number of tiles on this board with 2 path options in an L-shaped orientation
 * {@code int}:			numT - the number of tiles on this board with 3 path options in a T-shaped orientation
 * {@code Tile}: 		_freeTile - a Tile object not currently in the game board framework, available to be inserted upon shifting a row/column
 * 
 * @author William Stewart
 * @author Michael Langaman
 * @author Tyler Barrett
 * @author Dan Palacio
 * @version S.2
 * @since S.1
 */
public class Board {
	
	private static Random r = new Random();

	private Tile[][] _board;
	private int numS;
	private int numL;
	private int numT;
	private Tile _freeTile;
	
	/**
	 * This public constructor initializes a Board by allocating space for
	 * the two-dimensional array of Tile objects <code> _board </code> and calling
	 * two private methods which set the board's fixed (non-random, non-shiftable) tiles
	 * and subsequently insert randomized Tile objects in the remaining spaces. A new Tile
	 * is instantiated for the board's <code> _freeTile </code>.
	 */
	public Board(ArrayList<Player> players) {
		_board = new Tile[7][7];
		numS = 0;
		numL = 0;
		numT = 0;
		
		setFixedTiles();
		populateBoard();
		placePlayers(players);
		placeTokens();
	}
	
	/**
	 * Places tiles with randomized path options at all positions on the board not
	 * already occupied by non-shiftable tiles, as initialized in the <code> setFixedTiles() </code>
	 * method.
	 */
	private void populateBoard() {
		for (int row = 0; row < 7; row++) {
			for (int column = 0; column < 7; column++) {
				do {
					if (_board[row][column] == null) {
						
						int i = r.nextInt(3);
						if(i==0){
							if(numS<13){
								numS++;
								_board[row][column] = new Tile(row, column, 'S');
							}
						}
						else if(i==1){
							if(numL<15){
								numL++;
							_board[row][column] = new Tile(row, column, 'L');
							}
						}
						else{
							if(numT <6){
								numT++;
							_board[row][column] = new Tile(row, column, 'T');
							}
						}
					}
				} while(_board[row][column]==null);
			}
		}
		if (numS != 13) {
			_freeTile = new Tile(-1, -1, 'S');
			numS++;
		}
		else if (numL != 15) {
			_freeTile = new Tile(-1, -1, 'L');
			numL++;
		}
		else {
			_freeTile = new Tile (-1, -1, 'T');
			numT++;
		}
	}
	
	/**
	 * Returns an integer value of the current Tile that has a straight path.
	 * @return an integer value of the current tile.
	 */
	public int getnumS(){
		return numS;
	}
	
	/**
	 * Returns an integer value for the current Tile that has a T shaped path.
	 * @return 
	 */
	public int getnumT(){
		return numT;
	}
	
	/**
	 * Returns an integer value of the current Tile that has a L shape path.
	 * @return
	 */
	public int getnumL(){
		return numL;
	}
	
	/**
	 * Places tiles at the 16 positions on the board which can never be shifted
	 * (ie. the intersections of all non-shiftable rows and non-shiftable columns
	 * at indices 0, 2, 4, and 6). This must be done separately because the paths 
	 * available for player movement on these tiles are non-random and depend on 
	 * the position of the static tile.
	 * <p>
	 * Positions in the two-dimensional array <code> _board </code> not initialized
	 * with a Tile object by this method should be <code> null </code> by default,
	 * to be filled in subsequently by the <code> populateBoard() </code> method.
	 */
	private void setFixedTiles() {
		_board[0][0] = new Tile(false, true, true, false, 0, 0, "/resources/Tile_L_dn_rt.png");
		_board[0][2] = new Tile(false, true, true, true, 0, 2, "/resources/Tile_T_dn.png");
		_board[0][4] = new Tile(false, true, true, true, 0, 4, "/resources/Tile_T_dn.png");
		_board[0][6] = new Tile(false, true, false, true, 0, 6, "/resources/Tile_L_dn_lf.png");
		
		_board[2][0] = new Tile(true, true, true, false, 2, 0, "/resources/Tile_T_rt.png");
		_board[2][2] = new Tile(true, true, true, false, 2, 2, "/resources/Tile_T_rt.png");
		_board[2][4] = new Tile(false, true, true, true, 2, 4, "/resources/Tile_T_dn.png");
		_board[2][6] = new Tile(true, true, false, true, 2, 6, "/resources/Tile_T_lf.png");
		
		_board[4][0] = new Tile(true, true, true, false, 4, 0, "/resources/Tile_T_rt.png");
		_board[4][2] = new Tile(true, false, true, true, 4, 2, "/resources/Tile_T_up.png");
		_board[4][4] = new Tile(true, true, false, true, 4, 4, "/resources/Tile_T_lf.png");
		_board[4][6] = new Tile(true, true, false, true, 4, 6, "/resources/Tile_T_lf.png");
		
		_board[6][0] = new Tile(true, false, true, false, 6, 0, "/resources/Tile_L_up_rt.png");
		_board[6][2] = new Tile(true, false, true, true, 6, 2,"/resources/Tile_T_up.png");
		_board[6][4] = new Tile(true, false, true, true, 6, 4, "/resources/Tile_T_up.png");
		_board[6][6] = new Tile(true, false, false, true, 6, 6, "/resources/Tile_L_up_lf.png");
	}
	/**
	 * Takes the current players and places them in the correct positions on the board
	 * @param players - The ArrayList of current players
	 */
	private void placePlayers(ArrayList<Player> players) {
		if (players != null) {
			if (players.size() >= 1) {
			players.get(0).setTile(_board[2][2]);
			}
			if (players.size() >= 2) {
				players.get(1).setTile(_board[4][4]);
			}
			if (players.size() >= 3) {
				players.get(2).setTile(_board[2][4]);
			}
			if (players.size() >= 4) {
				players.get(3).setTile(_board[4][2]);
			}
		}
	}
	
	/**
	 * Places the tokens randomly across the middle section of the board (leaves outer rim alone).
	 * The 21 tokens are added to an ArrayList, and then are shuffled using the Collections.shuffle() method.
	 * Then, they are placed in the middle 5x5 section of the board one by one until there are no tokens left to place.
	 */
	private void placeTokens() {
		
		ArrayList<Token> tokens = new ArrayList<Token>();
		for (int number = 1; number <= 20; number++) {
			tokens.add(new Token(number));
		}
		tokens.add(new Token(25));
		Collections.shuffle(tokens);
		
		int counter = 0;
		for (int row = 1; row <= 5; row++) {
			for (int column = 1; column <= 5; column++) {
				if ((row != 2 || column != 2) && (row != 4 || column != 4) && (row != 2 || column != 4) && (row != 4 || column != 2)) {
					_board[row][column].setToken(tokens.get(counter));
					counter++;
				}
			}
		}
	}
	
	/**
	 * If the specified row is shiftable, this method moves each tile in that row
	 * of the two-dimensional array <code> _board </code> to the left, knocking off the
	 * leftmost tile and inserting the board's free tile on the right.
	 * <p>
	 * The leftmost tile is remembered in a temporary variable, then references to all subsequent
	 * tiles in the same row are copied into the preceding index (and each tile's internal
	 * knowledge of its position is updated), the board's currently free tile is inserted at the
	 * rightmost index, and the previously remembered tile is assigned to be the current free tile. If
	 * the newly-assigned free tile had any players on it, those references are given to the 
	 * newly-inserted rightmost tile and cleared from the free tile.
	 * 
	 * @param	row		the index of the row to be shifted left
	 * @return	boolean	<code> true </code> if the shift was successful;
	 * 					<code> false </code> otherwise
	 */
	public boolean shiftLeft(int row) {
		if (isShiftable(row)) {
			Tile newFreeTile = _board[row][0];
			for (int index = 1; index < 7; index++) {
				_board[row][index - 1] = _board[row][index];
				_board[row][index - 1].setColumn(index - 1);
			}
			_board[row][6] = _freeTile;
			_board[row][6].setColumn(6);
			_board[row][6].setRow(row);
			
			_freeTile = newFreeTile;
			_freeTile.setRow(-1);
			_freeTile.setColumn(-1);
			
			if (_freeTile.hasPlayer()) {
				_board[row][6].setPlayerList(_freeTile.getPlayerList());
				for (Player player: _board[row][6].getPlayerList()) {
					player.setTile(_board[row][6]);
				}
				_freeTile.clear();
			}
			if (_freeTile.hasToken()) {
				_board[row][6].setToken(_freeTile.takeToken());
			}
			
			return true;
		} else return false;
	}
	
	/**
	 * If the specified row is shiftable, this method moves each tile in that row
	 * of the two-dimensional array <code> _board </code> to the right, knocking off the
	 * rightmost tile and inserting the board's free tile on the left.
	 * <p>
	 * The rightmost tile is remembered in a temporary variable, then references to all preceding
	 * tiles in the same row are copied into the subsequent index (and each tile's internal
	 * knowledge of its position is updated), the board's currently free tile is inserted at the
	 * leftmost index, and the previously remembered tile is assigned to be the current free tile. If
	 * the newly-assigned free tile had any players on it, those references are given to the 
	 * newly-inserted leftmost tile and cleared from the free tile.
	 * 
	 * @param 	row		the index of the row to be shifted right
	 * @return	boolean	<code> true </code> if the shift was successful;
	 * 					<code> false </code> otherwise
	 */
	public boolean shiftRight(int row) {
		if (isShiftable(row)) {
			Tile newFreeTile = _board[row][6];
			for (int index = 5; index >= 0; index--) {
				_board[row][index + 1] = _board[row][index];
				_board[row][index + 1].setColumn(index + 1);
			}
			_board[row][0] = _freeTile;
			_board[row][0].setColumn(0);
			_board[row][0].setRow(row);
			
			_freeTile = newFreeTile;
			_freeTile.setRow(-1);
			_freeTile.setColumn(-1);
			
			if (_freeTile.hasPlayer()) {
				_board[row][0].setPlayerList(_freeTile.getPlayerList());
				for (Player player: _board[row][0].getPlayerList()) {
					player.setTile(_board[row][0]);
				}
				_freeTile.clear();
			}
			if (_freeTile.hasToken()) {
				_board[row][0].setToken(_freeTile.takeToken());
			}
			
			return true;
		} else return false;
	}
	
	/**
	 * If the specified column is shiftable, this method moves each tile in that column
	 * of the two-dimensional array <code> _board </code> up, knocking off the
	 * topmost tile and inserting the board's free tile on the bottom.
	 * <p>
	 * The topmost tile is remembered in a temporary variable, then references to all subsequent
	 * tiles in the same column are copied into the preceding index (and each tile's internal
	 * knowledge of its position is updated), the board's currently free tile is inserted at the
	 * bottommost index, and the previously remembered tile is assigned to be the current free tile. If
	 * the newly-assigned free tile had any players on it, those references are given to the 
	 * newly-inserted bottommost tile and cleared from the free tile.
	 * 
	 * @param	column	the index of the column to be shifted up
	 * @return	boolean	<code> true </code> if the shift was successful;
	 * 					<code> false </code> otherwise
	 */
	public boolean shiftUp(int column) {
		if (isShiftable(column)) {
			Tile newFreeTile = _board[0][column];
			for (int index = 1; index < 7; index++) {
				_board[index - 1][column] = _board[index][column];
				_board[index - 1][column].setRow(index - 1);
			}
			_board[6][column] = _freeTile;
			_board[6][column].setRow(6);
			_board[6][column].setColumn(column);
			
			_freeTile = newFreeTile;
			_freeTile.setRow(-1);
			_freeTile.setColumn(-1);
			
			if (_freeTile.hasPlayer()) {
				_board[6][column].setPlayerList(_freeTile.getPlayerList());
				for (Player player: _board[6][column].getPlayerList()) {
					player.setTile(_board[6][column]);
				}
				_freeTile.clear();
			}
			if (_freeTile.hasToken()) {
				_board[6][column].setToken(_freeTile.takeToken());
			}
			
			return true;
		} else return false;
	}
	
	/**
	 * If the specified column is shiftable, this method moves each tile in that column
	 * of the two-dimensional array <code> _board </code> down, knocking off the
	 * bottommost tile and inserting the board's free tile on the top.
	 * <p>
	 * The bottommost tile is remembered in a temporary variable, then references to all preceding
	 * tiles in the same column are copied into the subsequent index (and each tile's internal
	 * knowledge of its position is updated), the board's currently free tile is inserted at the
	 * topmost index, and the previously remembered tile is assigned to be the current free tile. If
	 * the newly-assigned free tile had any players on it, those references are given to the 
	 * newly-inserted topmost tile and cleared from the free tile.
	 * 
	 * @param	column	the index of the column to be shifted down
	 * @return	boolean	<code> true </code> if the shift was successful;
	 * 					<code> false </code> otherwise
	 */
	public boolean shiftDown(int column) {
		if (isShiftable(column)) {
			Tile newFreeTile = _board[6][column];
			for (int index = 5; index >= 0; index--) {
				_board[index + 1][column] = _board[index][column];
				_board[index + 1][column].setRow(index + 1);
			}
			_board[0][column] = _freeTile;
			_board[0][column].setRow(0);
			_board[0][column].setColumn(column);
			
			_freeTile = newFreeTile;
			_freeTile.setRow(-1);
			_freeTile.setColumn(-1);
			
			if (_freeTile.hasPlayer()) {
				_board[0][column].setPlayerList(_freeTile.getPlayerList());
				for (Player player: _board[0][column].getPlayerList()) {
					player.setTile(_board[0][column]);
				}
				_freeTile.clear();
			}
			if (_freeTile.hasToken()) {
				_board[0][column].setToken(_freeTile.takeToken());
			}
			
			return true;
		} else return false;
	}
	
	/**
	 * This method indicates whether a specified index corresponds to a shiftable row or column.
	 * The only shiftable rows (beginning from index 0) are rows 1, 3, and 5; the only shiftable
	 * columns (beginning from index 0) are also columns 1, 3, and 5. Thus, only the generalized
	 * index needs to be checked for either case.
	 * 
	 * @param 	index	the index of the row/column to be checked
	 * @return			<code> true </code> if the specified row/column is shiftable;
	 * 					<code> false </code> otherwise
	 */
	public boolean isShiftable(int index) {
		return (index == 1 || index == 3 || index == 5);
	}
	
	/**
	 * Makes a copy of an array for that row that is specified by the index.
	 * @param index Tells you what row you're copying.
	 * @return A copy of the array that is distinct of the original.
	 */
	public Tile[] getRow(int index) {
		if (index >= 0 && index < 7) {
			Tile[] row = new Tile[7];
			for (int column = 0; column < 7; column++) {
				row[column] = _board[index][column];
			}
			return row;
		} else return null;
	}
	
	/**
	 * Makes a copy array for that column that is specified by the index.
	 * @param index Tells you what row you're copying.
	 * @return A copy of the array that is distinct of the orginal. 
	 */
	public Tile[] getColumn(int index) {
		if (index >= 0 && index < 7) {
			Tile[] column = new Tile[7];
			for (int row = 0; row < 7; row++) {
				column[row] = _board[row][index];
			}
			return column;
		} else return null;
	}
	
	/**
	 * Returns an object of type Tile that specifies the freeTile 
	 * that got shifted out of the board.
	 * @return the free tile.
	 */
	public Tile getFreeTile() {
		return _freeTile;
	}
	
	/**
	 * Returns tile at the specify the coordinates.
	 * @param row The index of the row
	 * @param column The index of the column.
	 * @return the tile of the specific coordinate.
	 */
	public Tile getTile(int row, int column) {
		return _board[row][column];
	}
	
	/**
	 * Takes an array that replaces the row at that index.
	 * @param index Tells you what row you're replacing.
	 * @param row An array that replaces the row at that index.
	 */
	public void setRow(int index, Tile[] row) {
		if (row.length == 7 && (index >= 0 && index < 7)) {
			_board[index] = row;
		}
	}
	
	/**
	 * Takes in an array that replaces the column at that index.
	 * @param index Tells you what column you're replacing.
	 * @param column An array that replaces the column at that index.
	 */
	public void setColumn(int index, Tile[] column) {
		if (column.length == 7 && (index >= 0 && index < 7)) {
			for (int i = 0; i < 7; i++) {
				_board[i][index] = column[i];
			}
		}
	}
}