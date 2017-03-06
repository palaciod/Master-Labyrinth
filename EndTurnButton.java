package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import code.Game;
/**
 * Contains variables and methods for creating a button that will end a player's Master Labyrinth turn when pressed.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _game - the instance of the {@code Game} currently being played
 * @author Michael Langaman
 * @author Tyler Barrett
 * @author Daniel Palacio
 *
 */
public class EndTurnButton extends JButton {

	private Game _game;
	/**
	 * Initializes the button's {@code Game} reference by the {@code Game} parameter passed into it,
	 * sets the text on the button to indicate to the players what the button will do if clicked (end their turn), and
	 * adds the correct listener to the button instance to end a player's turn.
	 * @param game - instance of the {@code Game} currently being played
	 */
	public EndTurnButton(Game game) {
		_game = game;
		super.setText("End Turn");
		super.addActionListener(new EndTurnHandler());
	}
	/**
	 * Calls upon the containing class's {@code _game} reference to try end the player's turn when this button is clicked. 
	 * Listener enables the {@code Game} instance's nextTurn() method to be invoked by mouse click.
	 * 
	 * @author Michael Langaman
	 * @author Daniel Palacio
	 * @author Tyler Barrett
	 *
	 */
	private class EndTurnHandler implements ActionListener {
		/**
		 * Calls upon the {@code Game} reference to go to the next turn in the player order.  It will only
		 * advance to another player's turn as long as the board has been shifted at the very minimum.
		 * 
		 * @param arg0 - unused, specified by ActionListener
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			_game.nextTurn();
		}
	}
}
