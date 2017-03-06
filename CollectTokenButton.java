package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import code.Game;
/**
 * Contains variables and methods for a creating a GUI button that will allow a 
 * Master Labyrinth player to try to collect a Token.  
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _game - the instance of the {@code Game} currently being played
 * @author Tyler Barrett
 * @author Daniel Palacio
 * @author Michael Langaman
 *
 */
public class CollectTokenButton extends JButton {
	private Game _game;
	/**
	 * Initializes the button's {@code Game} reference by the {@code Game} parameter passed into it,
	 * sets the text on the button to indicate to the players what the button will do if clicked (attempt to collect a token),
	 * and adds the correct listener to the button instance (to attempt to collect a token).
	 * @param game - instance of the {@code Game} currently being played
	 */
	public CollectTokenButton(Game game) {
		_game = game;
		this.setText("Collect Token");
		this.addActionListener(new CollectTokenListener());
	}
	/**
	 * Contains the method to invoke when the button is clicked that will
	 * call the method to collect {@code Token}s.
	 * This listener enables the player to attempt to collect the Token on the tile that
	 * he or she is located on.
	 * @author William Stewart
	 * @author Tyler Barrett
	 *
	 */
	private class CollectTokenListener implements ActionListener {
		/**
		 * Calls upon the {@code Game} reference to invoke the collectToken() method
		 * which will check what player is clicking it, what tile the player is on, 
		 * and if the token is collectable (if there is a token on the tile to begin with)
		 * it will be added to the player's token inventory.
		 * 
		 * @param e - unused, specified by ActionListener
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			_game.collectToken();
		} 
		
	}
}
