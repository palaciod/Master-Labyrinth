package gui;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import code.Game;
import code.Player;

/**
 * <h1>View</h1>
 * Contains the variables and methods for representing the game in a graphical user interface consisting of
 * a game board, controls, and an information display.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _game - the instance of the {@code Game} currently being played
 * {@code TileButton[][]}: _tileBoard - a two-dimensional array of {@code TileButton}s representing the underlying game board
 * {@code JButton[][]}: _edgeBoard - a two-dimensional array of {@code JButton}s representing the outer edge of the game board, consisting of row/column shifting buttons (and disabled buttons in between)
 * {@code JPanel}: _boardPanel - the {@code JPanel} containing the buttons making up the complete game board (ie. combination of {@code _tileBoard} and {@code _edgeBoard})
 * {@code TileButton}: _freeTile - a {@code TileButton} visually representing the game's free tile
 * {@code JFrame}: _window - the main window for the GUI
 * {@code JPanel}: _currentPlayerInfo - the {@code JPanel} containing information about the game's players
 * {@code JPanel}: _freeTilePanel - the {@code JPanel} containing the {@code _freeTile} button and its controlling elements for rotation
 * {@code JPanel}: _infoPanel - a {@code JPanel} grouping all other non-board panels to be displayed next to the game board
 * {@code JLabel}: _currentPlayer - a {@code JLabel} containing text that displays the current player number
 * {@code JLabel[]}: _tokenLists - an array of {@code JLabel}s, each containing text displaying the list of tokens the corresponding player (by index) has picked up
 * 
 * @author Daniel Palacio
 * @author Michael Langaman
 * @author Tyler Barrett
 * @author William Stewart
 */
public class View implements Runnable {

	private Game _game;
	
	private TileButton[][] _tileBoard;
	private JButton[][] _edgeBoard;
	private JPanel _boardPanel;
	private TileButton _freeTile;
	
	private JFrame _window;
	private JPanel _currentPlayerInfo;
	private JPanel _freeTilePanel;
	private JPanel _infoPanel;
	private JLabel _currentPlayer;
	private JLabel[] _tokenLists;
	
	/**
	 * Initializes the {@code View} with a reference to the {@code Game} being played and
	 * initializes most instance variables; the rest are initialized in {@code run()}.
	 * 
	 * @param game	a reference to the {@code Game} being played
	 */
	public View (Game game){
		_game = game;
		_tileBoard = new TileButton[9][9];
		_edgeBoard = new JButton[9][9];
		
		_currentPlayer = new JLabel();
		_infoPanel = new JPanel();
		_boardPanel = new JPanel();
		_freeTilePanel = new JPanel();
		_currentPlayerInfo = new JPanel();
		_tokenLists = new JLabel[_game.getPlayers()];
		_currentPlayerInfo.add(_currentPlayer);
		for (int index = 0; index < _tokenLists.length; index++) {
			_tokenLists[index] = new JLabel();
			_currentPlayerInfo.add(_tokenLists[index]);
		}
		initializeButtons();
		_freeTile = new TileButton(_game.getBoard().getFreeTile(), _game);
	}
	
	/**
	 * Fills the 9x9 {@code _tileBoard} with {@code TileButton}s that represent the corresponding {@code Tile}s
	 * on the underlying game board; the outermost rows and columns are left {@code null} by default, since those
	 * buttons are contained in {@code _edgeBoard}. Also fills the 9x9 {@code _edgeBoard} with {@code JButton}s that
	 * represent the shifting buttons with disabled buttons in between. This distinction is made because the buttons representing tiles
	 * (on the interior of the board) and shifting buttons (on the outer edge) are of different types but the {@code TileButton}s have unique extending
	 * methods that must be called, so they cannot be combined into one array of supertype {@code JButton}. The
	 * two setups are superimposed at the end of this method when each button is added to the 9x9 {@code _boardPanel}.
	 */
	private void initializeButtons() {
		for (int row = 1; row <= 7; row++) {
			for (int column = 1; column <= 7; column++) {
				_tileBoard[row][column] = new TileButton(_game.getTile(row - 1, column - 1), _game);
			}
		}
		
		for (int column = 0; column <= 8; column++) {
			if (column == 2 || column == 4 || column == 6) {
				_edgeBoard[0][column] = new DownShiftButton(column - 1, _game);
				_edgeBoard[8][column] = new UpShiftButton(column - 1, _game);
			} else {
				_edgeBoard[0][column] = new JButton();
				_edgeBoard[8][column] = new JButton();
				_edgeBoard[0][column].setEnabled(false);
				_edgeBoard[8][column].setEnabled(false);
			}
		}
		
		for (int row = 1; row <= 7; row++) {
			if (row == 2 || row == 4 || row == 6) {
				_edgeBoard[row][0] = new RightShiftButton(row - 1, _game);
				_edgeBoard[row][8] = new LeftShiftButton(row - 1, _game);
			} else {
				_edgeBoard[row][0] = new JButton();
				_edgeBoard[row][8] = new JButton();
				_edgeBoard[row][0].setEnabled(false);
				_edgeBoard[row][8].setEnabled(false);
			}
		}
		
		for (int row = 0; row <= 8; row++) {
			for (int column = 0; column <= 8; column++) {
				if (row == 0 || row == 8 || column == 0 || column == 8) {
					_boardPanel.add(_edgeBoard[row][column]);
				} else {
					_boardPanel.add(_tileBoard[row][column]);
				}
			}
		}
	}
	
	/**
	 * Iterates through the specified row of the {@code TileBoard} and updates each {@code TileButton}'s reference
	 * to the tile it represents, as well as the free tile (ie. after shifting the board).
	 * 
	 * @param index	the row to be updated
	 */
	public void updateBoardRow(int index){
		for(int i = 1; i <= 7; i++) {
			_tileBoard[index + 1][i].setTile(_game.getBoard().getTile(index, i - 1));
		}
		updateFreeTile();
	
	}
	
	/**
	 * Iterates through the specified column of the {@code TileBoard} and updates each {@code TileButton}'s reference
	 * to the tile it represents, as well as the free tile (ie. after shifting the board).
	 * 
	 * @param index	the column to be updated
	 */
	public void updateBoardColumn(int index) {
		for(int i = 1; i <=7; i++) {
			_tileBoard[i][index + 1].setTile(_game.getBoard().getTile(i - 1, index));
		}
		updateFreeTile();
	}
	
	/**
	 * Updates the {@code TileButton} at the specified position in the game board (where the indices are shifted by 1
	 * because the {@code _tileBoard} has an additional column at the top and left to account for the {@code _edgeBoard}
	 * buttons) in order to update the text representing a token.
	 * 
	 * @param row		the row of the underlying game board where the tile is located
	 * @param column	the column of the underlying game board where the tile is located
	 */
	public void updateToken(int row, int column) {
		_tileBoard[row + 1][column + 1].updateTile();
	}
	
	/**
	 * Calls {@code repaint()} on {@code JPanel}s that need to be refreshed after a change has occurred
	 * (ie. updating button icons - without these calls, the original icons remain until rollover by a mouse or some other
	 * update prompts the button to refresh).
	 */
	public void refresh() {
		_freeTilePanel.repaint();
		_boardPanel.repaint();
	}
	
	/**
	 * Updates the {@code TileButton} for the free tile with a reference to the free tile from the game board.
	 */
	public void updateFreeTile(){
		_freeTile.setTile(_game.getBoard().getFreeTile());
	}
	
	/**
	 * Updates the {@code JPanel} containing player information, including which player currently has an active turn
	 * and the tokens that each player has collected (which should be visible to everyone, as explicitly indicated in the game rules).
	 * The token lists need only be updated on a turn-by-turn basis, since no other player's list has changed except the current player.
	 */
	public void updatePlayerInfo() {
		int playerIndex = _game.getCurrentPlayer().getNumber();
		_currentPlayer.setText("Current Player: " + (playerIndex + 1) + " - " + _game.getCurrentPlayer().get_playerName());
		_tokenLists[playerIndex].setText("Player " + (playerIndex + 1) + " has tokens: " + _game.getCurrentPlayer().getTokens().toString());
		_currentPlayerInfo.revalidate();
	}

	/**
	 * Sets up and displays all of the GUI components: initializes the main window, sets the layouts of the main window
	 * and sub-panels, and adds components like control buttons to the their containing sub-panels (eg. adding rotation buttons 
	 * into the {@code _freeTilePanel}), all before setting the main window visible for gameplay.
	 */
	@Override
	public void run() {
		_window = new JFrame("Master Labyrinth");
		
		_boardPanel.setLayout(new GridLayout(9,9));
		
		_freeTilePanel.setLayout(new GridLayout(2,2));
		JLabel freeTileLabel = new JLabel("Free Tile:", SwingConstants.CENTER);
		_freeTilePanel.add(freeTileLabel);

		_freeTilePanel.add(_freeTile);
		
		RotateCounterClockWiseButton ccw = new RotateCounterClockWiseButton(_game);
		ccw.setFocusable(true);
		ccw.setText("Rotate Counter-Clockwise");
		_freeTilePanel.add(ccw);
		RotateClockWiseButton cw = new RotateClockWiseButton(_game);
		cw.setFocusable(true);
		cw.setText("Rotate Clockwise");
		_freeTilePanel.add(cw);
		
		_currentPlayerInfo.setLayout(new GridLayout(0,1));

		updatePlayerInfo();
		
		_infoPanel.setLayout(new GridLayout(0,1));
		_infoPanel.add(_freeTilePanel);
		_infoPanel.add(new CollectTokenButton(_game));
		_infoPanel.add(new EndTurnButton(_game));
		_infoPanel.add(_currentPlayerInfo);
		
		_window.add(_boardPanel);
		_window.add(_infoPanel);
		_window.setLayout(new GridLayout(1,2));
		
		_window.setFocusable(true);
		_window.setVisible(true);
		_window.pack();
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Modifies the GUI when the game has ended (ie. last token has been picked up) by hiding
	 * the main window and displaying a new JFrame containing the final scores for each player.
	 */
	public void endGame() {
		_window.setVisible(false);
		
		JFrame resultFrame = new JFrame("Master Labyrinth Results");
		resultFrame.setLayout(new GridLayout(0,1));
		
		int largestScore = 0;
		int winningPlayer = -1;
		for (Player player: _game.getPlayerList()) {
			int score = player.calculateScore();
			int playerNum = player.getNumber() + 1;
			resultFrame.add(new JLabel("Player " + playerNum + " scored: " + score));
			if (score > largestScore) {
				largestScore = score;
				winningPlayer = playerNum;
			}
		}
		resultFrame.add(new JLabel("Player " + winningPlayer + " wins!"));
		
		resultFrame.pack();
		resultFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resultFrame.setVisible(true);
	}
}
