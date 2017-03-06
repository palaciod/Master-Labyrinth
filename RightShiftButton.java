package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

import code.Game;


/**
 * Contains variables and methods for creating a button in the GUI that will invoke the
 * shiftRight() method when clicked.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}: _theGame - the instance of the {@code Game} currently being played
 * {@code int}: _shiftIndex - the row that the button will shift
 * @author William Stewart
 * @author Tyler Barrett
 *
 * @version S.2
 * @since S.2
 */
public class RightShiftButton extends JButton{
	private int _shiftIndex;
	private Game _theGame;
	
	/**
	 * Initializes button with correctly facing arrow and adds the action listener
	 * for the shift right button.
	 * Will catch IOExceptions and print to console using System.err.
	 */
	public RightShiftButton(){
		super();
		BufferedImage image = null;
		try {
			URL file = getClass().getResource("/resources/arrow_rt.png");
			image = ImageIO.read(file);
		} catch (IOException ioex) {
			System.err.println("load error: " + ioex.getMessage());
		}
		ImageIcon icon = new ImageIcon(image);
		this.setIcon(icon);
		_shiftIndex = 0;
		_theGame = null;
		this.addActionListener(new RightShiftListener());
	}

	public RightShiftButton(int index, Game game) {
		this();
		_shiftIndex = index;
		_theGame = game;
	}
	/**
	 * Contains the method to invoke when the button is clicked that will
	 * call the correct board shifting method.
	 * This listener enables the {@code Game} instance's board to be shifted
	 * right in the index that the button visually corresponds to.
	 * @author William Stewart
	 * @author Tyler Barrett
	 *
	 */
	private class RightShiftListener implements ActionListener {
		/**
		 * Calls upon the {@code Game} reference to shift the button's specified
		 * row index right.
		 * 
		 * @param e - unused, specified by ActionListener
		 */
		public void actionPerformed(ActionEvent e) {
		//	_theGame.getBoard().shiftRight(_shiftIndex);
			_theGame.shiftBoardRowRight(_shiftIndex);
			System.out.println("shiftRight at index " + _shiftIndex);
		}	
	}

}
