package code;

import java.util.HashSet;
import java.util.Random;

import gui.TileIcon;

/**
 * <h1>Tile</h1> Contains the variables and methods for representing a Tile on
 * the game board, including knowledge of its position, its path options, and
 * any players that may be on it, as well as functionality to generate specific
 * tile types (with T-shaped, L-shaped, or straight paths) and check whether
 * adjacent tiles have matching paths.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code boolean}: _north - whether or not this tile has a path up
 * {@code boolean}: _south - whether or not this tile has a path down
 * {@code boolean}: _west - whether or not this tile has a path left
 * {@code boolean}: _east - whether or not this tile has a path right
 * {@code int}: _column - the tile's current position horizontally (ie. its column) 
 * {@code int}: _row - the tile's current position vertically (ie. its row) 
 * {@code HashSet<Player>}: _playerList - a HashSet (to avoid duplicates) of all players currently on this tile 
 * {@code int}: _pathNumber - the number of available path options on this tile 
 * {@code Random}: rand - a Random object for use in generating random path options (can be shared by all instances)
 * {@code static final int}: NORTH / WEST / SOUTH / EAST - So we can use cardinal names instead of confusing ints for directions
 * {@code static final int}: CW / CCW - So we can use abbreviations for clockwise and counter clockwise for rotation.
 * 
 * @author William Stewart
 * @author Daniel Palacio
 * @author Tyler Barrett
 * @author Michael Langaman
 * @version S.2
 * @since S.1
 */

public class Tile {

	public static final int NORTH = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;

	public static final int CW = 90;
	public static final int CCW = -90;

	private boolean _north;
	private boolean _south;
	private boolean _west;
	private boolean _east;
	private int _pathNumber;

	private int _column;
	private int _row;

	private HashSet<Player> _playerList;
	private Token _token;

	private static Random rand = new Random();

	private String _pathSource;
	private TileIcon _icon;

	/**
	 * Generates a new default Tile with no available path options and a
	 * position not within the game board's range - primarily to be used by
	 * other constructors to initialize default values (like the
	 * <code> _playerList </code>) before overriding them with more specific
	 * information.
	 */
	public Tile() {
		_north = false;
		_south = false;
		_east = false;
		_west = false;
		_column = -1;
		_row = -1;
		_playerList = new HashSet<Player>();
		_pathNumber = 0;
		_token = null;
		_pathSource = null;
	}

	/**
	 * Generates a new Tile with the specified knowledge of its position and a
	 * specified path orientation.
	 * 
	 * @param posX
	 *            the horizontal position (ie. column) of the Tile
	 * @param posY
	 *            the vertical position (ie. row) of the Tile
	 * @param tileShape
	 *            specifies the orientation of paths on the Tile; 'S' for
	 *            straight across, 'T' for T-shaped, and 'L' for L-shaped
	 */
	public Tile(int row, int column, char tileShape) {
		this();

		_row = row;
		_column = column;

		if (tileShape == 'S') {
			randStraightPath();
		}
		if (tileShape == 'L') {
			randLShapePath();
		}
		if (tileShape == 'T') {
			randTShapePath();
		}

		_icon = new TileIcon(this);
	}

	public Tile(boolean north, boolean south, boolean east, boolean west, int row, int column) {
		this();

		_north = north;
		_south = south;
		_east = east;
		_west = west;

		_row = row;
		_column = column;

		if (north)
			_pathNumber++;
		if (south)
			_pathNumber++;
		if (east)
			_pathNumber++;
		if (west)
			_pathNumber++;

		_icon = new TileIcon(this);
	}

	/**
	 * Generates a new Tile with the specified path options and knowledge of its
	 * position.
	 * 
	 * @param north
	 *            whether this tile has a path up
	 * @param south
	 *            whether this tile has a path down
	 * @param east
	 *            whether this tile has a path right
	 * @param west
	 *            whether this tile has a path left
	 * @param positionX
	 *            the horizontal position (ie. column) of the Tile
	 * @param positionY
	 *            the vertical position (ie. row) of the Tile
	 */
	public Tile(boolean north, boolean south, boolean east, boolean west, int row, int column, String icon) {
		this();

		_north = north;
		_south = south;
		_east = east;
		_west = west;

		_row = row;
		_column = column;

		if (north)
			_pathNumber++;
		if (south)
			_pathNumber++;
		if (east)
			_pathNumber++;
		if (west)
			_pathNumber++;

		_pathSource = icon;
		_icon = new TileIcon(this);
	}

	/**
	 * setter to set the Tile's token
	 * @param token - token number to be set on the tile
	 */
	public void setToken(Token token) {
		_token = token;
	}

	/**
	 * Returns the token that the tile has
	 * @return the token that the tile has
	 */
	public Token getToken() {
		return _token;
	}

	/**
	 * Returns true if the tile has a token on it, false if no token
	 * @return true if the tile has a token on it, false if no token
	 */
	public boolean hasToken() {
		return _token != null;
	}

	/**
	 * Removes token from the tile, returns the token that the tile used to have
	 * @return Token that was on the tile
	 */
	public Token takeToken() {
		Token temp = _token;
		_token = null;

		return temp;
	}

	/**
	 * Returns whether or not this tile's <code> _playerList </code> contains a
	 * Player.
	 * 
	 * @return boolean <code> true </code> if the <code> _playerList </code> has
	 *         at least one element; <code> false </code> otherwise
	 */
	public boolean hasPlayer() {
		return _playerList.size() > 0;
	}

	/**
	 * Attempts to add the specified player to this tile's
	 * <code> _playerList </code>.
	 * 
	 * @param p
	 *            the Player to be added
	 * @return boolean <code> true </code> if the HashSet accepted the new
	 *         Player; <code> false </code> otherwise
	 */
	public boolean addPlayer(Player p) {
		if (_playerList.add(p)) {
			if (_icon != null) {
				_icon.update();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Attempts to remove the specified player from this tile's
	 * <code> _playerList </code>.
	 * 
	 * @param p
	 *            the Player to be removed
	 * @return boolean <code> true </code> if the HashSet removed the new
	 *         Player; <code> false </code> otherwise
	 */
	public boolean removePlayer(Player p) {
		if (_playerList.remove(p)) {
			if (_icon != null) {
				_icon.update();
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Clears this tile's <code> _playerList </code>.
	 * 
	 * @return boolean <code> true </code> since this should always be
	 *         successful
	 */
	public boolean clear() {
		_playerList.clear();
		_icon.update();
		return true;
	}

	/**
	 * Returns a reference to this tile's <code> _playerList </code>
	 * 
	 * @return HashSet<Player> this tile's <code> _playerList </code>
	 */
	public HashSet<Player> getPlayerList() {
		HashSet<Player> copy = new HashSet<Player>();
		for (Player player: _playerList) {
			copy.add(player);
		}
		return copy;
	}

	/**
	 * Sets this tile's <code> _playerList </code> to the specified HashSet
	 * <Player>.
	 * 
	 * @param p
	 *            the HashSet<Player> to replace the current list
	 */
	public void setPlayerList(HashSet<Player> p) {
		_playerList = p;
		_icon.update();
	}

	/**
	 * Checks whether or not the specified Player is on this tile.
	 * 
	 * @param p
	 *            the Player to be checked for
	 * @return boolean <code> true </code> if the specified Player is found;
	 *         <code> false </code> otherwise
	 */
	public boolean hasPlayer(Player p) {
		return _playerList.contains(p);
	}

	/**
	 * Sets this tile's x-coordinate.
	 * 
	 * @param x
	 *            the coordinate value to be set
	 */
	public void setColumn(int x) {
		_column = x;
	}

	/**
	 * Sets this tile's y-coordinate.
	 * 
	 * @param y
	 *            the coordinate value to be set
	 */
	public void setRow(int y) {
		_row = y;
	}

	/**
	 * Returns this tile's x-coordinate.
	 * 
	 * @return int the tile's x-coordinate
	 */
	public int getColumn() {
		return _column;
	}

	/**
	 * Returns this tile's y-coordinate.
	 * 
	 * @return int the tile's y-coordinate
	 */
	public int getRow() {
		return _row;
	}

	/**
	 * Sets this tile's upward path option.
	 * 
	 * @param n
	 *            whether the upward path should be available
	 */
	public void setNorth(boolean n) {
		_north = n;
	}

	/**
	 * Sets this tile's downward path option.
	 * 
	 * @param s
	 *            whether the downward path should be available
	 */
	public void setSouth(boolean s) {
		_south = s;
	}

	/**
	 * Sets this tile's rightward path option.
	 * 
	 * @param e
	 *            whether the rightward path should be available
	 */
	public void setEast(boolean e) {
		_east = e;
	}

	/**
	 * Sets this tile's leftward path option.
	 * 
	 * @param w
	 *            whether the leftward path should be available
	 */
	public void setWest(boolean w) {
		_west = w;
	}

	/**
	 * Returns the state of this tile's upward path option.
	 * 
	 * @return boolean whether the upward path is available
	 */
	public boolean getNorth() {
		return _north;
	}

	/**
	 * Returns the state of this tile's downward path option.
	 * 
	 * @return boolean whether the downward path is available
	 */
	public boolean getSouth() {
		return _south;
	}

	/**
	 * Returns the state of this tile's rightward path option.
	 * 
	 * @return boolean whether the rightward path is available
	 */
	public boolean getEast() {
		return _east;
	}

	/**
	 * Returns the state of this tile's leftward path option.
	 * 
	 * @return boolean whether the leftward path is available
	 */
	public boolean getWest() {
		return _west;
	}

	/**
	 * Rotates this tile's path options clockwise.
	 */
	public void rotateClockwise() {
		boolean temp1 = _north;
		boolean temp2 = _east;
		boolean temp3 = _south;
		boolean temp4 = _west;
		_north = temp4;
		_east = temp1;
		_south = temp2;
		_west = temp3;
		// rotateIcon(0);
	}

	/**
	 * Rotates this tile's path options counterclockwise.
	 */
	public void rotateCounterClockwise() {
		boolean temp1 = _north;
		boolean temp2 = _east;
		boolean temp3 = _south;
		boolean temp4 = _west;
		_north = temp2;
		_east = temp3;
		_south = temp4;
		_west = temp1;
		// rotateIcon(1);
	}

	/**
	 * Checks if the specified tile and this tile are adjacent in a particular
	 * direction and both tiles' path options match for motion in the specified
	 * direction (from this tile to the specified tile).
	 * 
	 * @param tile
	 *            the Tile to be checked against this tile
	 * @param direction
	 *            specifies the direction of the destination tile relative to
	 *            this tile: 0 specifies North, 1 specifies West, 2 specifies
	 *            South, 3 specifies East
	 * @return boolean <code> true </code> if this tile and the specified tile
	 *         are adjacent and have matching paths in the specified direction;
	 *         <code> false </code> otherwise
	 */
	public boolean checkAdjTile(Tile tile) {
		if ((this.sameColumn(tile)) && (this.getRow() - tile.getRow() == 1)) {
			return (tile.getSouth() && this.getNorth());
		} else if ((this.sameRow(tile)) && (this.getColumn() - tile.getColumn() == 1)) {
			return (tile.getEast() && this.getWest());
		} else if (this.sameColumn(tile) && (this.getRow() - tile.getRow() == -1)) {
			return (tile.getNorth() && this.getSouth());
		} else if (this.sameRow(tile) && (this.getColumn() - tile.getColumn() == -1)) {
			return (tile.getWest() && this.getEast());
		} else
			return false;
	}

	/**
	 * A private helper method for <code> checkAdjTile() </code> to check
	 * whether or not this tile and the specified tile are adjacent
	 * horizontally.
	 * 
	 * @param tile
	 *            the tile to be checked against this tile
	 * @return boolean <code> true </code> if the tiles are adjacent
	 *         horizontally; <code> false </code> otherwise
	 */
	private boolean adjacentInColumn(Tile tile) {
		return Math.abs(this.getColumn() - tile.getColumn()) == 1;
	}

	/**
	 * A private helper method for <code> checkAdjTile() </code> to check
	 * whether or not this tile and the specified tile are adjacent vertically.
	 * 
	 * @param tile
	 *            the tile to be checked against this tile
	 * @return boolean <code> true </code> if the tiles are adjacent vertically;
	 *         <code> false </code> otherwise
	 */
	private boolean adjacentInRow(Tile tile) {
		return Math.abs(this.getRow() - tile.getRow()) == 1;
	}

	/**
	 * A private helper method for <code> checkAdjTile() </code> to check
	 * whether or not this tile and the specified tile lie in the same column.
	 * 
	 * @param tile
	 *            the tile to be checked against this tile
	 * @return boolean <code> true </code> if the tiles are in the same column;
	 *         <code> false </code> otherwise
	 */
	private boolean sameColumn(Tile tile) {
		return this.getColumn() == tile.getColumn();
	}

	/**
	 * A private helper method for <code> checkAdjTile() </code> to check
	 * whether or not this tile and the specified tile lie in the same row.
	 * 
	 * @param tile
	 *            the tile to be checked against this tile
	 * @return boolean <code> true </code> if the tiles are in the same row;
	 *         <code> false </code> otherwise
	 */
	private boolean sameRow(Tile tile) {
		return this.getRow() == tile.getRow();
	}

	/**
	 * Returns the number of available path options on this tile.
	 * 
	 * @return int the number of path options
	 */
	public int getPathNumber() {
		return _pathNumber;
	}
	/**
	 * Returns the path for the Tile's texture
	 * @return the path for the Tile's texture
	 */
	public String getIconString() {
		return _pathSource;
	}
	/**
	 * Sets the path for the desired icon texture
	 * @param s - path for the texture
	 */
	public void setIconString(String s) {
		_pathSource = s;
	}

	/**
	 * A private helper method for the Tile constructor (which takes in a
	 * specified <code> tileShape </code> parameter). This presumes the desired
	 * tile should have 3 available paths in a T formation and randomizes this
	 * tile's path options subject to that constraint by considering all
	 * possibilities.
	 */
	private void randTShapePath() {
		int randInt = rand.nextInt(4) + 1;
		switch (randInt) {
		case 1:
			_north = false;
			_south = true;
			_east = true;
			_west = true;
			_pathSource = "/resources/Tile_T_dn.png";
			break;
		case 2:
			_north = true;
			_south = false;
			_east = true;
			_west = true;
			_pathSource = "/resources/Tile_T_up.png";
			break;
		case 3:
			_north = true;
			_south = true;
			_east = false;
			_west = true;
			_pathSource = "/resources/Tile_T_lf.png";
			break;
		case 4:
			_north = true;
			_south = true;
			_east = true;
			_west = false;
			_pathSource = "/resources/Tile_T_rt.png";
			break;
		default:
			break;
		}
		_pathNumber = 3;
	}

	/**
	 * A private helper method for the Tile constructor (which takes in a
	 * specified <code> tileShape </code> parameter). This presumes the desired
	 * tile should have 2 available paths in an L formation and randomizes this
	 * tile's path options subject to that constraint by considering all
	 * possibilities.
	 */
	private void randLShapePath() {
		int randInt = rand.nextInt(4) + 1;
		switch (randInt) {
		case 1:
			_north = true;
			_south = false;
			_east = true;
			_west = false;
			_pathSource = "/resources/Tile_L_up_rt.png";
			break;
		case 2:
			_north = true;
			_south = false;
			_east = false;
			_west = true;
			_pathSource = "/resources/Tile_L_up_lf.png";
			break;
		case 3:
			_north = false;
			_south = true;
			_east = true;
			_west = false;
			_pathSource = "/resources/Tile_L_dn_rt.png";
			break;
		case 4:
			_north = false;
			_south = true;
			_east = false;
			_west = true;
			_pathSource = "/resources/Tile_L_dn_lf.png";
			break;
		}
		_pathNumber = 2;
	}

	/**
	 * A private helper method for the Tile constructor (which takes in a
	 * specified <code> tileShape </code> parameter). This presumes the desired
	 * tile should have 2 available paths in a straight-across formation and
	 * randomizes this tile's path options subject to that constraint by
	 * considering all possibilities.
	 */
	private void randStraightPath() {
		int randInt = rand.nextInt(2) + 1;
		switch (randInt) {
		case 1:
			_north = true;
			_south = true;
			_east = false;
			_west = false;
			_pathSource = "/resources/Tile_S_up.png";
			break;
		case 2:
			_north = false;
			_south = false;
			_east = true;
			_west = true;
			_pathSource = "/resources/Tile_S_rt.png";
			break;
		}
		_pathNumber = 2;
	}

	/**
	 * A private helper method used to rotate the Tile's
	 * <code> _pathSource </code>.
	 * 
	 * @return integer 0-9 according to the tile's <code> _pathSource </code>.
	 */
	private int numIcon() {
		if (_pathSource.equals("/resources/Tile_L_dn_lf.png")) {
			return 0;
		}
		if (_pathSource.equals("/resources/Tile_L_dn_rt.png")) {
			return 1;
		}
		if (_pathSource.equals("/resources/Tile_L_up_lf.png")) {
			return 2;
		}
		if (_pathSource.equals("/resources/Tile_L_up_rt.png")) {
			return 3;
		}
		if (_pathSource.equals("/resources/Tile_S_rt.png")) {
			return 4;
		}
		if (_pathSource.equals("/resources/Tile_S_up.png")) {
			return 5;
		}
		if (_pathSource.equals("/resources/Tile_T_dn.png")) {
			return 6;
		}
		if (_pathSource.equals("/resources/Tile_T_lf.png")) {
			return 7;
		}
		if (_pathSource.equals("/resources/Tile_T_rt.png")) {
			return 8;
		}
		if (_pathSource.equals("/resources/Tile_T_up.png")) {
			return 9;
		}
		return -1;
	}

	/**
	 * Checks the tile's <code> _pathSource </code> using the
	 * <code> numIcon </code> method and sets it's <code> _pathSource </code>
	 * according to the direction passed in the parameter.
	 * 
	 * @param direction
	 *            integer used to specify which direction the icon is going to
	 *            be rotated. <code> 90 </code> if the direction is clockwise.
	 *            <code> -90 </code> if the direction is counter clockwise.
	 */
	public void rotateIcon(int direction) {

		switch (numIcon()) {
		case 0:
			if (direction == CW) {
				_pathSource = "/resources/Tile_L_up_lf.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_L_dn_rt.png";
			}
			break;
		case 1:
			if (direction == CW) {
				_pathSource = "/resources/Tile_L_dn_lf.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_L_up_rt.png";
			}
			break;
		case 2:
			if (direction == CW) {
				_pathSource = "/resources/Tile_L_up_rt.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_L_dn_lf.png";
			}
			break;
		case 3:
			if (direction == CW) {
				_pathSource = "/resources/Tile_L_dn_rt.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_L_up_lf.png";
			}
			break;
		case 4:
			_pathSource = "/resources/Tile_S_up.png";
			break;
		case 5:
			_pathSource = "/resources/Tile_S_rt.png";
			break;
		case 6:
			if (direction == CW) {
				_pathSource = "/resources/Tile_T_lf.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_T_rt.png";
			}
			break;
		case 7:
			if (direction == CW) {
				_pathSource = "/resources/Tile_T_up.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_T_dn.png";
			}
			break;
		case 8:
			if (direction == CW) {
				_pathSource = "/resources/Tile_T_dn.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_T_up.png";
			}
			break;
		case 9:
			if (direction == CW) {
				_pathSource = "/resources/Tile_T_rt.png";
			} else if (direction == CCW) {
				_pathSource = "/resources/Tile_T_lf.png";
			}
			break;
		}
		_icon.updatePath();
	}
	/**
	 * Returns the icon associated with the tile
	 * @return TileIcon that the Tile is showing
	 */
	public TileIcon getIcon() {
		return _icon;
	}
}
