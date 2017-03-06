package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import code.Game;
import code.Tile;

/**
 * <h1>TileButton</h1> 
 * Contains the variables and methods for representing a tile on
 * the game board as a button in the GUI, including knowledge of the current game instance, 
 * the underlying tile this button currently represents, and an icon representing the paths, players,
 * and tokens on the tile. This class extends the functionality of a JButton to fit the additional
 * functionality of a button on the game board, including updating the button's reference to its
 * tile and encapsulating the necessary visual updates on the JButton (setting icon and text) into
 * a single method. This class also contains the definition for a button's event handler, which allows
 * players to move between tiles via clicking, as an inner class.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _game - the instance of the {@code Game} currently being played
 * {@code Tile}: _tile - the tile this button represents
 * {@code TileIcon}: _icon - the icon which draws the underlying tile's paths, players, and tokens on this button
 * 
 * @author Tyler Barrett
 * @author William Stewart
 */
public class TileButton extends JButton {

	private Game _game;
	private Tile _tile;
	private TileIcon _icon;
	
	/**
	 * Initializes this TileButton with a reference to the underlying tile it represents as well as the overarching game object.
	 * The button is set up with its action listener (defined as an inner class), a text color of white, and centered text position
	 * (so the text is placed over the icon), and its icon and text are updated via {@code updateTile()}.
	 * 
	 * @param tile	the underlying Tile object this button initially represents
	 * @param game	the instance of the current game
	 */
	public TileButton(Tile tile, Game game) {
		_tile = tile;
		_game = game;
		
		this.addActionListener(new PushHandler());
		this.setForeground(Color.WHITE);
		this.setHorizontalTextPosition(JButton.CENTER);
		this.setVerticalTextPosition(JButton.CENTER);
		updateTile();
	}
	
	/**
	 * Sets this button's reference to the underlying tile it represents via a supplied argument,
	 * then updates the button's icon and text via {@code updateTile()};
	 * 
	 * @param tile	the underlying Tile object this button will now represent
	 */
	public void setTile(Tile tile) {
		_tile = tile;
		updateTile();
	}
	
	/**
	 * Sets this button's icon to be the icon supplied by the underlying Tile object and 
	 * updates the tile's text to represent its token (ie. after a change to the tile or 
	 * board necessitates a visual update).
	 */
	public void updateTile() {
		_icon = _tile.getIcon();
		super.setIcon(_icon);
		if (_tile.getToken() != null) {
			this.setText(_tile.getToken().toString());
		} else {
			this.setText("");
		}
	}
	
	/**
	 * <h1>PushHandler</h1> 
	 * Defines the behavior of the containing TileButton when clicked by implementing the
	 * {@code ActionListener} interface. This handler enables the {@code _game}'s current
	 * player to move to this button's tile on click if the move is valid.
	 * 
	 * @author Tyler Barrett
	 * @author William Stewart
	 */
	private class PushHandler implements ActionListener {
		
		/**
		 * Calls upon the containing class's {@code _game} reference to try moving its current
		 * player to the tile represented by this button when clicked. Also refreshes
		 * the {@code _game}'s {@code View} object to repaint the GUI since the player's
		 * icon will leave its old button after valid moves.
		 * 
		 * @param arg0	unused here, but specified by {@code ActionListener}
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			_game.move(_tile);
			_game.refreshView();
		}
	}
}
