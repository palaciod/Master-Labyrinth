package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import code.Game;
/**
 *Generates a RotateCounterClockWiseButton with an inheritance relationship with JButton,
 * while the JButton being the parent class. This JButton rotates the free tile that
 * has been pushed off the 7x7 board in a counter clockwise manner. 
 * It's rotated 90 degrees each time the JButton is clicked.
 * 
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code Game}:_game - An updated version of the model for the view.
 * 
 * @author Dan Palacio
 * @author Michael Langaman 
 *
 */
public class RotateCounterClockWiseButton extends JButton{
	private Game _game;
	/**
	 * Generates a JButton with the ability to rotate the free tile counter clockwise. 
	 * 
	 * @param game - The update information of the model of the view.
	 */
	public RotateCounterClockWiseButton(Game game){
		_game = game;
		this.addActionListener(new CounterClockWiseListener());
	}
	/**
	 * This action listener gives the specific instruction for the free tile to rotate
	 * counter clockwise 90 degrees.
	 * 
	 *
	 */
	private class CounterClockWiseListener implements ActionListener{
		/**
		 * Uses the method of rotateFreeTile from the game class to change the boolean values
		 * so that it appears it moved 90 degrees, while also rotating the icon on the current
		 * JButton counter clockwise.
		 */
		
		public void actionPerformed(ActionEvent e) {
			_game.rotateFreeTileCounterClockWise();
			_game.refreshView();
		}
		
	}

}
