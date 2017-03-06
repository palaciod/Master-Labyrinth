package gui;

import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import code.Game;
/**
 * <h1>DownShiftButton</h1> 
 * Contains the variables and methods for the downward movement of a tile on
 * the game board as a button in the GUI, including knowledge of the current game instance.
 *  This class extends the functionality of a JButton to fit the additional
 * functionality of a button on the game board. This class also contains the definition for a button's event handler, which allows
 * players to move downward via clicking, as an inner class.
 * 
 * {@code Game}: _theGame - the instance of the {@code Game} currently being played
 *{@code int}:_shiftIndex - the instance of shifted index in the column for the updated game instance.
 * @author Dan Palacio
 * @author Will Stewart 
 * @author Tyler Barrett
 * @author Michael Langaman
 * 
 *
 */
public class DownShiftButton extends JButton{
	private int _shiftIndex;
	private Game _theGame;
	/**
	 * Initializes this DownShiftButton with a reference to the underlying tile it represents as well as the over arching game object.
	 * The button is set up with its action listener (defined as an inner class).
	 * 
	 */
	
	
	public DownShiftButton(){
		super();
		BufferedImage image = null;
		try {
			URL file = getClass().getResource("/resources/arrow_dn.png");
			image = ImageIO.read(file);
		} catch (IOException ioex) {
			System.err.println("load error: " + ioex.getMessage());
		}
		ImageIcon icon = new ImageIcon(image);
		this.setIcon(icon);
		_shiftIndex = 0;
		_theGame = null;
		this.addActionListener(new DownShiftListener());
	}
	/**
	 * Initializes this UpShiftButton with a reference to the underlying tile it represents as well as the over arching game object.
	 * The button is set up with its action listener (defined as an inner class).
	 * @param index - An instance of the shifted index of the column after moving down.
	 * @param game - the instance of the {@code Game} currently being played
	 */
	public DownShiftButton(int index, Game game) {
		this();
		_shiftIndex = index;
		_theGame = game;
	}
	/**
	 * <h1>PushHandler</h1> 
	 * Defines the behavior of the containing DownShiftButton when clicked by implementing the
	 * {@code ActionListener} interface. This handler enables the {@code _game}'s current
	 * player to move to this button's tile on click if the downward movement is valid.
	 * 
	 * @author Tyler Barrett
	 * @author William Stewart
	 * @author Daniel Palacio
	 * @author Michael Langaman
	 */
	private class DownShiftListener implements ActionListener {
		
		/**
		 * Calls upon the containing class's {@code _thegame} reference to try moving its current
		 * player downward. 
		 * 
		 * @param e - 	unused here, but specified by {@code ActionListener}
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		//	_theGame.getBoard().shiftDown(_shiftIndex);
			_theGame.shiftBoardColumnDown(_shiftIndex);
			System.out.println("shiftDown at index " + _shiftIndex);
		}	
	}

}
