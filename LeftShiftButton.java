package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * Generates a LeftShift Button with a parent class of JButton. 
 * 
 * {@ code game} _theGame - an updated version of the model for the view.
 * @author Dan Palacio
 *
 *
 */

import code.Game;

/**
 * Contains methods and variables for creating a button in the GUI that will invoke the
 * shiftLeft() method when clicked.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _theGame - the instance of the {@code Game} currently being played
 * {@code int}: _shiftIndex - the column that the button will shift
 * @author William Stewart
 * @author Tyler Barrett
 *
 * @version S.2
 * @since S.2
 */
public class LeftShiftButton extends JButton{
	private int _shiftIndex;
	private Game _theGame;
	/**
	 * Initializes button with correctly facing arrow and adds the corresponding left
	 * shift action listener to the button.
	 * Will catch IOException and print to console using System.err.
	 */
	public LeftShiftButton(){
		super();
		BufferedImage image = null;
		try {
			URL file = getClass().getResource("/resources/arrow_lf.png");
			image = ImageIO.read(file);
		} catch (IOException ioex) {
			System.err.println("load error: " + ioex.getMessage());
		}
		ImageIcon icon = new ImageIcon(image);
		this.setIcon(icon);
		_shiftIndex = 0;
		_theGame = null;
		this.addActionListener(new LeftShiftListener());
	}

	public LeftShiftButton(int index, Game game) {
		this();
		_shiftIndex = index;
		_theGame = game;
	}
	/**
	 * Contains the method to invoke when the button is clicked that will
	 * call the correct board shifting method.
	 * This listener enables the {@code Game} instance's board to be shifted
	 * left in the index that the button visually corresponds to.
	 * @author William Stewart
	 * @author Tyler Barrett
	 *
	 */
	private class LeftShiftListener implements ActionListener {
		/**
		 * Calls upon the {@code Game} reference to shift the button's specified
		 * row index right.
		 * 
		 * @param e - unused, specified by ActionListener
		 */
		public void actionPerformed(ActionEvent e) {
			_theGame.shiftBoardRowLeft(_shiftIndex);
//			System.out.println("shiftLeft at index " + _shiftIndex);
		}	
	}
}
