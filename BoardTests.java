package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import code.Board;
import code.Player;
import code.Tile;

/**
 * @author William Stewart
 * @author Tyler Barrett
 * @author Dan Palacio
 * @author Michael Langaman
 */
public class BoardTests {

	private void testShift(int index, boolean expected) {
		
		Board board = new Board(null);
		boolean result = board.isShiftable(index);
		assertEquals(result, expected);
	}
	
	@Test public void testValidShift() {
		
		testShift(3, true);
	}
	
	@Test public void testInvalidShift() {
		
		testShift(4, false);
	}
	
	@Test
	public void testLeftShift01(){
		Board b = new Board(null);
		boolean actual = true;
		boolean expected = b.shiftLeft(3);
		assertEquals(actual,expected);
	}
	
	@Test
	public void testLeftShift02(){
		Board b = new Board(null);
		boolean expected = b.shiftLeft(0);
		boolean actual = false;
		assertEquals(actual,expected);
	}
	
	@Test
	public void testRightShift01(){
		Board b = new Board(null);
		boolean actual = true;
		boolean expected = b.shiftRight(5);
		assertEquals(actual,expected);
	}
	
	@Test
	public void testRightShift02(){
		Board b = new Board(null);
		boolean expected = b.shiftLeft(6);
		boolean actual = false;
		assertEquals(actual,expected);
	}
	
	@Test
	public void testUpShift01(){
		Board b = new Board(null);
		boolean actual = true;
		boolean expected = b.shiftUp(1);
		assertTrue("..",actual==expected);
	}
	
	@Test
	public void testUpShift02(){
		Board b = new Board(null);
		boolean expected = b.shiftUp(2);
		boolean actual = false;
		assertEquals(actual,expected);
	}
	
	@Test
	public void testDownShift01(){
		Board b = new Board(null);
		boolean actual = true;
		boolean expected = b.shiftDown(5);
		assertTrue("..",actual==expected);
	}
	
	@Test
	public void testDownShift02(){
		Board b = new Board(null);
		boolean expected = b.shiftDown(4);
		boolean actual = false;
		assertEquals(actual,expected);
		
	}
	
	@Test public void creationTest() {
		Board board = new Board(null);
		boolean expected = false;
		boolean result = testForNullTiles(board);
		assertEquals(expected,result);
	}

	private boolean testForNullTiles(Board board) {
		for (int row = 0; row < 7; row++) {
			for (int column = 0; column < 7; column++) {
				if (board.getTile(row, column) == null) return true;
			}
		}
		return false;
	}

	@Test public void shiftLeftTest() {
		Board board = new Board(null);
		Tile[] originalRow = board.getRow(1);
		Tile freeTile = board.getFreeTile();
		board.shiftLeft(1);
		Tile[] shiftedRow = board.getRow(1);
		assertTrue(shiftedRow[0] == originalRow[1]);
		assertTrue(shiftedRow[1] == originalRow[2]);
		assertTrue(shiftedRow[2] == originalRow[3]);
		assertTrue(shiftedRow[3] == originalRow[4]);
		assertTrue(shiftedRow[4] == originalRow[5]);
		assertTrue(shiftedRow[5] == originalRow[6]);
		assertTrue(shiftedRow[6] == freeTile);
		assertTrue(board.getFreeTile() == originalRow[0]);
	}
	
	@Test 
	public void shiftRight03(){
		Board b = new Board(null);
		Tile freetile = b.getFreeTile();
		Tile[] original = b.getRow(1);
		b.shiftRight(1);
		Tile[] shifted = b.getRow(1);
		
		assertTrue(shifted[6] == original[5]);
		assertTrue(shifted[5] == original[4]);
		assertTrue(shifted[4] == original[3]);
		assertTrue(shifted[3] == original[2]);
		assertTrue(shifted[2] == original[1]);
		assertTrue(shifted[1] == original[0]);
		assertTrue(shifted[0] == freetile);
		assertTrue(b.getFreeTile() == original[6]);
	}
	
	@Test public void shiftUpTest() {
		Board board = new Board(null);
		Tile[] originalColumn = board.getColumn(1);
		Tile freeTile = board.getFreeTile();
		board.shiftUp(1);
		Tile[] shiftedColumn = board.getColumn(1);
		assertTrue(shiftedColumn[0] == originalColumn[1]);
		assertTrue(shiftedColumn[1] == originalColumn[2]);
		assertTrue(shiftedColumn[2] == originalColumn[3]);
		assertTrue(shiftedColumn[3] == originalColumn[4]);
		assertTrue(shiftedColumn[4] == originalColumn[5]);
		assertTrue(shiftedColumn[5] == originalColumn[6]);
		assertTrue(shiftedColumn[6] == freeTile);
		assertTrue(board.getFreeTile() == originalColumn[0]);
	}
	
	@Test 
	public void shiftDownTest01(){
		Board b = new Board(null);
		Tile[] original = b.getColumn(1);
		Tile freeTile = b.getFreeTile();
		b.shiftDown(1);
		Tile [] shifted = b.getColumn(1);
		assertTrue (shifted[6]==original[5]);
		assertTrue (shifted[5]==original[4]);
		assertTrue (shifted[4]==original[3]);
		assertTrue (shifted[3]==original[2]);
		assertTrue (shifted[2]==original[1]);
		assertTrue (shifted[1]==original[0]);
		assertTrue (shifted[0]== freeTile);
		assertTrue(b.getFreeTile()==original[6]);
	}

	@Test public void getRowTest() {
		Board board = new Board(null);
		Tile[] row = board.getRow(0);
		for (int index = 0; index < 7; index++) {
			assertTrue(row[index] == board.getTile(0, index));
		}
	}
	
	@Test public void boardPopulationTest(){
		Board b = new Board(null);
		assertTrue(b.getnumL()==15);
		assertTrue(b.getnumS()==13);
		assertTrue(b.getnumT()==6);
	}
	


	@Test public void boardTilePosTest(){
		Board b = new Board(null);
		int x = b.getTile(3, 4).getColumn();
		int y = b.getTile(3, 4).getRow();
		assertTrue("",x==4);
		assertTrue("",y==3);
	}
	
	@Test public void shiftPlayerTest() {
		Board board = new Board(null);
		Player p1 = new Player("name", 0, board.getTile(3, 2));
		board.shiftLeft(3);
		board.shiftLeft(3);
		board.shiftLeft(3);
		assertTrue(board.getTile(3, 0).hasPlayer() == false);
		assertTrue(board.getTile(3, 6).hasPlayer());
		assertTrue(board.getTile(3, 6).hasPlayer(p1));
	}
	
	@Test public void placePlayersTest() {
		ArrayList<Player> players = new ArrayList<Player>();
		Player p1 = new Player("p1", 0, null);
		players.add(p1);
		Player p2 = new Player("p2", 1, null);
		players.add(p2);
		Player p3 = new Player("p3", 2, null);
		players.add(p3);
		Player p4 = new Player("p4", 3, null);
		players.add(p4);
		Board board = new Board(players);
		assertTrue(board.getTile(2, 2).hasPlayer(p1));
		assertTrue(board.getTile(4, 4).hasPlayer(p2));
		assertTrue(board.getTile(2, 4).hasPlayer(p3));
		assertTrue(board.getTile(4, 2).hasPlayer(p4));
	}
	
	@Test public void placeTokensTest() {
		Board board = new Board(null);
		ArrayList<Integer> tokenNumbers = new ArrayList<Integer>();
		for (int row = 0; row < 7; row++) {
			for (int column = 0; column < 7; column++) {
				if (row == 0 || row == 6 || column == 0 || column == 6 || ((row == 2 || row == 4) && (column == 2 || column == 4))) {
					assertTrue(board.getTile(row, column).getToken() == null);
				} else {
					assertTrue(board.getTile(row, column).hasToken());
					if (board.getTile(row, column).hasToken()) {
						int tokenNum = board.getTile(row, column).getToken().getNumber();
						assertTrue(tokenNumbers.contains(tokenNum) == false);
						tokenNumbers.add(tokenNum);
					}
				}
			}
		}
	}
}