package gui;

import java.awt.Component;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import code.Player;
import code.Tile;

/**
 * <h1>TileIcon</h1> 
 * Represents the graphical appearance of a {@code TileButton} on the game board in the GUI by
 * consolidating all separate {@code ImageIcon}s for the path display and player icons on that tile.
 * Implements the {@code Icon} interface so that this class may serve as an icon for a button, 
 * defining the specified methods so that all individual path and player icon objects are painted
 * over each other in layers and displayed simultaneously on a button. This is achieved by setting
 * each player icon to a transparent image by default and selectively "turning on" relevant layers as
 * players enter or leave the tile.
 * <p>
 * <b>Variables:</b>
 * <p>
 * {@code ImageIcon}: player1 - the {@code ImageIcon} for Player 1's blue circle; this is only loaded once and selectively activated by each {@code TileIcon} instance as needed, so it is {@code static} and {@code final}
 * {@code ImageIcon}: player2 - the {@code ImageIcon} for Player 2's red circle; this is only loaded once and selectively activated by each {@code TileIcon} instance as needed, so it is {@code static} and {@code final}
 * {@code ImageIcon}: player3 - the {@code ImageIcon} for Player 3's yellow circle; this is only loaded once and selectively activated by each {@code TileIcon} instance as needed, so it is {@code static} and {@code final}
 * {@code ImageIcon}: player4 - the {@code ImageIcon} for Player 4's green circle; this is only loaded once and selectively activated by each {@code TileIcon} instance as needed, so it is {@code static} and {@code final}
 * {@code Tile}: _tile - the tile this icon represents
 * {@code ImageIcon}: _paths - the {@code ImageIcon} layer for the underlying tile's path layout
 * {@code ImageIcon}: _player1 - the {@code ImageIcon} layer for Player 1's current status on the tile (will be set to {@code player1} if Player 1 is on this tile; {@code transparent} otherwise)
 * {@code ImageIcon}: _player2 - the {@code ImageIcon} layer for Player 2's current status on the tile (will be set to {@code player2} if Player 2 is on this tile; {@code transparent} otherwise)
 * {@code ImageIcon}: _player3 - the {@code ImageIcon} layer for Player 3's current status on the tile (will be set to {@code player3} if Player 3 is on this tile; {@code transparent} otherwise)
 * {@code ImageIcon}: _player4 - the {@code ImageIcon} layer for Player 4's current status on the tile (will be set to {@code player4} if Player 4 is on this tile; {@code transparent} otherwise)
 * 
 * @author Tyler Barrett
 * @author Michael Langaman
 */
public class TileIcon implements Icon {

	private static final ImageIcon player1 = makeIcon("/resources/ml_p1.png");
	private static final ImageIcon player2 = makeIcon("/resources/ml_p2.png");
	private static final ImageIcon player3 = makeIcon("/resources/ml_p3.png");
	private static final ImageIcon player4 = makeIcon("/resources/ml_p4.png");
	private static final ImageIcon transparent = makeIcon("/resources/transparent.png");

	private final Tile _tile;

	private ImageIcon _paths;
	private ImageIcon _player1;
	private ImageIcon _player2;
	private ImageIcon _player3;
	private ImageIcon _player4;

	/**
	 * Initializes this {@code TileIcon} by setting the defaults for each {@code ImageIcon} layer
	 * representing the players on the tile, remembering a reference to the tile this icon represents,
	 * setting the {@code ImageIcon} layer for the tile's path layout, and updating the player layers
	 * to reflect the tile's actual state.
	 * 
	 * @param tile	the {@code Tile} this icon represents
	 */
	public TileIcon(Tile tile) {
		setDefaults();
		_tile = tile;
		updatePath();
		update();
	}

	/**
	 * Sets each {@code ImageIcon} layer (representing each player's presence on the tile) to transparent
	 * by default; each will be selectively activated by the {@code update()} method as players arrive/depart.
	 */
	private void setDefaults() {
		_player1 = transparent;
		_player2 = transparent;
		_player3 = transparent;
		_player4 = transparent;
	}

	/**
	 * Updates this icon to reflect the players currently on the tile.
	 * Resets each {@code ImageIcon} layer representing the tile's players to their defaults, then iterates through
	 * the set of players currently on the tile and "activates" the relevant {@code ImageIcon} layers by changing
	 * their references from the default {@code transparent} to the appropriate {@code ImageIcon} for that player.
	 */
	public void update() {
		setDefaults();
		for (Player player : _tile.getPlayerList()) {
			if (player != null) {
				switch (player.getNumber()) {
				case 0:
					_player1 = player1;
					break;
				case 1:
					_player2 = player2;
					break;
				case 2:
					_player3 = player3;
					break;
				case 3:
					_player4 = player4;
					break;
				}
			}
		}
	}

	/**
	 * Updates the {@code ImageIcon} representing the path on the tile this icon represents.
	 */
	public void updatePath() {
		_paths = makeIcon(_tile.getIconString());
	}

	/**
	 * Creates a new {@code ImageIcon} from the specified file path, scaled to 128x128 pixels.
	 * 
	 * @param path	the path to the image being used
	 * @return		the {@code ImageIcon} created from the path if creation was successful, or the {@code static final ImageIcon} transparency by default
	 */
	private static ImageIcon makeIcon(String path) {
		if (path != null) {
			ImageIcon icon = transparent;
			try {
				icon = new ImageIcon(ImageIO.read(TileIcon.class.getResource(path)).getScaledInstance(128, 128,
						java.awt.Image.SCALE_SMOOTH));
			} catch (Exception e) {
				System.err.println("Could not load image at" + path + ".");
			}
			return icon;
		} else {
			return transparent;
		}
	}

	/**
	 * Returns this icon's height, based on the paths icon.
	 * 
	 * @return the path icon's height
	 */
	@Override
	public int getIconHeight() {
		return _paths.getIconHeight();
	}

	/**
	 * Returns this icon's width, based on the paths icon.
	 * 
	 * @return the path icon's width
	 */
	@Override
	public int getIconWidth() {
		return _paths.getIconWidth();
	}

	/**
	 * The method specified by the {@code Icon} interface, which in this case
	 * calls the same method on each layer of {@code ImageIcon}s contained in this
	 * {@code TileIcon}, resulting in a superimposed image.
	 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		_paths.paintIcon(c, g, x, y);
		_player1.paintIcon(c, g, x, y);
		_player2.paintIcon(c, g, x, y);
		_player3.paintIcon(c, g, x, y);
		_player4.paintIcon(c, g, x, y);
	}
}
