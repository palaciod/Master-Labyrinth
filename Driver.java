package code;

import javax.swing.SwingUtilities;

import gui.View;
/**
 * Driver class for the Master Labyrinth game.
 * It creates a new Game and View object upon invoking the main method.
 * The view is added to the game and the game is started.    
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game} game - the instance of Master Labyrinth that controls all internal game logic (turn taking, token collecting, etc)
 * {@code View} view - the instance of the GUI that the players will see on the monitor that will allow them to interact with the game and play it.
 * 
 * @author Tyler Barrett
 * @author Michael Langaman
 * @author Daniel Palacio
 * @version S.2
 * @since S.2
 *
 */
public class Driver {
	/**
	 * Takes in command line args for the players' names and then runs the game.
	 * @param {@code String}[] args - Array of Strings containing the players' names. 
	 */
	public static void main(String[] args) {
		Game game = new Game(args);
		View view = new View(game);
		game.addView(view);
		SwingUtilities.invokeLater(view);
	}
}
