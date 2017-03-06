package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

import code.Player;
import code.Tile;

/**
 * @author Michael Langaman
 * @author William Stewart
 * @author Tyler Barrett
 * @author Daniel Palacio
 */

public class TileTests {

	@Test public void playerListMethodTest() {
		Tile test = new Tile();
		Player expected = new Player("Dan", 3, null);
		test.addPlayer(expected);
		test.removePlayer(expected);
		assertTrue("", !test.hasPlayer());
	}
	
	@Test public void addPlayerTest() {
		Tile test = new Tile();
		assertTrue(test.hasPlayer() == false);
		Player p = new Player("Player", 1, null);
		test.addPlayer(p);
		assertTrue(test.hasPlayer());
	}
	
	@Test public void getPlayerListTest() {
		Tile test = new Tile();
		Player p1 = new Player("p1", 1, null);
		Player p2 = new Player("p2", 2, null);
		test.addPlayer(p1);
		test.addPlayer(p2);
		HashSet<Player> expected = new HashSet<Player>();
		expected.add(p1);
		expected.add(p2);
		assertEquals(test.getPlayerList(), expected);
	}
	
	@Test public void setPlayerListTest() {
		Tile test = new Tile();
		Player p1 = new Player("p1", 1, null);
		Player p2 = new Player("p2", 2, null);
		HashSet<Player> actual = new HashSet<Player>();
		actual.add(p1);
		actual.add(p2);
		test.addPlayer(p1);
		test.addPlayer(p2);
		assertEquals(actual, test.getPlayerList());
	}
	
	@Test public void checkForPlayerTest() {
		Tile test = new Tile();
		Player p1 = new Player("p1", 1, null);
		test.addPlayer(p1);
		assertTrue(test.hasPlayer(p1));
	}
	
	@Test public void getXTest() {
		Tile test = new Tile(0,5,'T');
		assertTrue(5 == test.getColumn());
	}
	
	@Test public void getYTest() {
		Tile test = new Tile(3,4,'S');
		assertTrue(3 == test.getRow());
	}
	
	@Test public void setXTest() {
		Tile test = new Tile(4,3,'L');
		assertTrue(3 == test.getColumn());
		test.setColumn(5);
		assertTrue(5 == test.getColumn());
	}
	
	@Test public void setYTest() {
		Tile test = new Tile(4,3,'L');
		assertTrue(4 == test.getRow());
		test.setRow(5);
		assertTrue(5 == test.getRow());
	}

	@Test public void positionTest() {
		Tile test = new Tile(0, 0, 'S');
		int expectedX = 0;
		int expectedY = 0;
		int actualX = test.getColumn();
		int actualY = test.getRow();
		assertTrue("", actualX == expectedX && actualY == expectedY);
	}
	
	private boolean pathCode(Tile test) {
		boolean north = test.getNorth();
		boolean south = test.getSouth();
		boolean west = test.getWest();
		boolean east = test.getEast();
		return (north && south) && (west == false && east == false) || (north == false && south == false) && (west && east);
	}
	
	@Test public void sPath() {
		Tile test = new Tile(0, 0, 'S');
		assertTrue("", pathCode(test));
	}
	
	@Test public void lPath() {
		Tile test = new Tile(0, 0, 'L');
		assertTrue("", test.getPathNumber() == 2 && !pathCode(test));
	}
	
	@Test public void tPath() {
		Tile test = new Tile(0, 0, 'T');
		assertTrue("", test.getPathNumber() == 3);
	}
	
	@Test public void getPathNumberTest01() {
		Tile test = new Tile(true, true, false, false, 0, 0);
		assertTrue("", test.getPathNumber() == 2);
	}
	
	@Test public void getPathNumberTest01_f() {
		Tile test = new Tile(true, true, true, false, 0, 0);
		assertTrue("", test.getPathNumber() != 2);
	}
	
	@Test public void getPathNumberTest02() {
		Tile test = new Tile(true, true, true, false, 0, 0);
		assertTrue("", test.getPathNumber() == 3);
	}
	
	@Test public void setPositionTest() {
		Tile test = new Tile();
		int expectedX = 0;
		test.setColumn(0);
		int actualX = test.getColumn();
		int expectedY = 5;
		test.setRow(5);
		int actualY = test.getRow();
		assertTrue("", actualX == expectedX && actualY == expectedY);
	}

	@Test public void northTest() {
		Tile test = new Tile(false, false, false, false, 1, 1);
		boolean expected = true;
		test.setNorth(true);
		boolean actual = test.getNorth();
		assertTrue("", actual == expected);
	}

	@Test public void southTest() {
		Tile test = new Tile(false, true, false, false, 1, 1);
		boolean expected = false;
		test.setSouth(false);
		boolean actual = test.getSouth();
		assertTrue("", actual == expected);
	}

	@Test public void eastTest() {
		Tile test = new Tile(false, false, true, false, 1, 1);
		boolean expected = false;
		test.setEast(false);
		boolean actual = test.getEast();
		assertTrue("", actual == expected);
	}

	@Test public void westTest() {
		Tile test = new Tile(false, false, false, true, 1, 1);
		boolean expected = false;
		test.setWest(false);
		boolean actual = test.getWest();
		assertTrue("", actual == expected);
	}

	
	private boolean shiftCommonTestCode(Tile actual, Tile expected) {
		boolean north = actual.getNorth() == expected.getNorth();
		boolean south = actual.getSouth() == expected.getSouth();
		boolean west = actual.getWest() == expected.getWest();
		boolean east = actual.getEast() == expected.getEast();
		return north && south && west && east;
	}

	@Test public void shiftClockWiseTest() {
		Tile expected = new Tile(false, false, true, true, 0, 0);
		expected.rotateClockwise();
		Tile actual = new Tile(true, true, false, false, 0 ,0);
		assertTrue("", shiftCommonTestCode(actual, expected));
	}

	@Test public void shiftCounterClockWiseTest() {
		Tile expected = new Tile(true, true, false, false, 0, 0);
		expected.rotateCounterClockwise();
		Tile actual = new Tile(false, false, true, true, 0, 0);
		assertTrue("", shiftCommonTestCode(actual, expected));
	}

	private void checkAdjTileTestCode(Tile one, Tile two, boolean expected) {
		boolean actual = one.checkAdjTile(two);
		assertTrue("", actual == expected);
	}

	@Test public void checkAdjTileTestNorth() {
		Tile one = new Tile(true, true, true, false, 1,0);
		Tile two = new Tile(false, true, false, true, 0,0);
		boolean expected = true;
		checkAdjTileTestCode(one, two, expected);
	}
	
	@Test public void checkAdjTileTestSouth() {
		Tile one = new Tile(true, true, false, false, 1,0);
		Tile two = new Tile(true, true, false, false, 2,0);
		boolean expected = true;
		checkAdjTileTestCode(one, two, expected);
	}

	@Test public void checkAdjTileTestEast() {
		Tile one = new Tile(false, true, true, true, 0,1);
		Tile two = new Tile(false, false, true, true, 0,2);
		boolean expected = true;
		checkAdjTileTestCode(one, two, expected);
	}

	@Test public void checkAdjTileTestWest() {
		Tile one = new Tile(true, false, true, false, 0, 0);
		Tile two = new Tile(false, false, true, true, 0, 1);
		boolean expected = true;
		checkAdjTileTestCode(one, two, expected);
	}
	
	@Test public void invalidCheckAdjTileTestNorth() {
		Tile one = new Tile(true, true, true, false, 1,0);
		Tile two = new Tile(false, true, false, true, 2, 3);
		boolean expected = false;
		checkAdjTileTestCode(one, two, expected);
	}

	@Test public void invalidCheckAdjTileTestSouth() {
		Tile one = new Tile(true, true, false, false, 0, 1);
		Tile two = new Tile(true, true, false, false, 1, 0);
		boolean expected = false;
		checkAdjTileTestCode(one, two, expected);
	}
	
	@Test public void invalidCheckAdjTileTestEast() {
		Tile one = new Tile(false, true, true, true, 1, 0);
		Tile two = new Tile(false, false, true, true, 4, 0);
		boolean expected = false;
		checkAdjTileTestCode(one, two, expected);
	}
	
	@Test public void invalidCheckAdjTileTestWest() {
		Tile one = new Tile(true, false, true, false, 0, 0);
		Tile two = new Tile(false, false, true, true, 0, 0);
		boolean expected = false;
		checkAdjTileTestCode(one, two, expected);
	}
}
