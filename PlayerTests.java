package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import code.Board;
import code.Player;
import code.Tile;
import code.Token;

public class PlayerTests {

	@Test public void moveTest01() {
		Player player = new Player();
		Tile currentTile = new Tile(true, true, true, false, 0, 1);
		player.setTile(currentTile);
		assertTrue(player.getTile() == currentTile && currentTile.hasPlayer(player));
		Tile destinationTile = new Tile(false, false, true, true, 0, 2);
		player.move(destinationTile);
		assertTrue(player.getTile() == destinationTile);
		assertTrue(destinationTile.hasPlayer(player));
		assertTrue(!currentTile.hasPlayer(player));
	}
	
	@Test public void moveTest02() {
		Player player = new Player("name", 1, null);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(player);
		Board board = new Board(players);
		assertTrue(board.getTile(2,2).hasPlayer(player) && player.getTile() == board.getTile(2,2));
		if (board.getTile(3,2).getNorth()) {
			assertTrue(player.move(board.getTile(3,2)));
			assertTrue(player.getTile() == board.getTile(3,2));
			assertTrue(board.getTile(2, 2).hasPlayer() == false);
			assertTrue(board.getTile(3,2).hasPlayer());
		}
		else {
			assertTrue(player.move(board.getTile(3,2)) == false);
		}
	}
	@Test
	public void playerGetInfo01(){
		Tile currentTile = new Tile (true,true,true,false,0,1);
		Player p = new Player ("Mike",1,currentTile);
		assertTrue(p.get_playerName()=="Mike");
		assertTrue(p.getTile()==currentTile);
		assertFalse(p.get_playerName()=="Dan");
		assertFalse(p.getTile()==new Tile (true,true,true,true,0,1));
		
	}
	@Test 
	public void playerSetInfo01(){
		Player p = new Player ();
		p.set_playerName("Tyler");
		Tile currentTile = new Tile (true,true,true,false,0,1);
		p.setTile(currentTile);
		assertTrue(p.get_playerName()=="Tyler");
		assertTrue(p.getTile()==currentTile);
		assertFalse(p.get_playerName()=="Dan");
		assertFalse(p.getTile()==new Tile(true,true,true,true,0,1));
	}
	
	@Test public void moveTest03() {
		Player p1 = new Player("name", 1, null);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p1);
		Board board = new Board(players);
		Tile[] row = {new Tile(false, false, true, true, 0, 0),
					  new Tile(false, false, true, true, 0, 1),
					  new Tile(false, false, true, true, 0, 2),
					  new Tile(false, false, true, true, 0, 3),
					  new Tile(false, false, true, true, 0, 4),
					  new Tile(false, false, true, true, 0, 5),
					  new Tile(false, false, true, true, 0, 6)};
		board.setRow(0, row);
		p1.setTile(row[0]);
		p1.move(row[1]);
		p1.move(row[2]);
		p1.move(row[3]);
		p1.move(row[4]);
		p1.move(row[5]);
		p1.move(row[6]);
		assertTrue(p1.getTile() == row[6]);
		assertTrue(row[6].hasPlayer(p1));
		assertTrue(row[0].hasPlayer(p1) == false);
		assertTrue(row[5].hasPlayer(p1) == false);
	}
	
	@Test public void moveTest04() {
		Player p1 = new Player("name", 1, null);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p1);
		Board board = new Board(players);
		Tile[] row0 = {new Tile(false, true, true, false, 0, 0),
					   new Tile(false, false, true, true, 0, 1),
					   new Tile(false, false, true, true, 0, 2),
					   new Tile(false, false, true, true, 0, 3),
					   new Tile(false, false, true, true, 0, 4),
					   new Tile(false, false, true, true, 0, 5),
					   new Tile(false, true, false, true, 0, 6)};
		Tile[] row1 = {new Tile(true, true, false, false, 1, 0),
					   new Tile(false, false, true, true, 1, 1),
					   new Tile(false, false, true, true, 1, 2),
					   new Tile(false, false, true, true, 1, 3),
					   new Tile(false, false, true, true, 1, 4),
					   new Tile(false, false, true, true, 1, 5),
					   new Tile(true, true, false, false, 1, 6)};
		Tile[] row2 = {new Tile(true, false, true, false, 2, 0),
					   new Tile(false, false, true, true, 2, 1),
					   new Tile(false, false, true, true, 2, 2),
					   new Tile(false, false, true, true, 2, 3),
					   new Tile(false, false, true, true, 2, 4),
					   new Tile(false, false, true, true, 2, 5),
					   new Tile(true, false, false, true, 2, 6)};
		board.setRow(0, row0);
		board.setRow(1, row1);
		board.setRow(2, row2);
		p1.setTile(row0[0]);
		
		p1.move(row0[1]);
		p1.move(row0[2]);
		p1.move(row0[3]);
		p1.move(row0[4]);
		p1.move(row0[5]);
		p1.move(row0[6]);
		
		p1.move(row1[6]);
		p1.move(row2[6]);
		
		p1.move(row2[5]);
		p1.move(row2[4]);
		p1.move(row2[3]);
		p1.move(row2[2]);
		p1.move(row2[1]);
		p1.move(row2[0]);
		
		p1.move(row1[0]);
		
		assertTrue(p1.getTile() == row1[0]);
		assertTrue(row1[0].hasPlayer(p1));
		assertTrue(row0[0].hasPlayer(p1) == false);
	}
	
	@Test public void TakeTokenTest01() {
		Tile t = new Tile();
		Token tok = new Token(5);
		t.setToken(tok);
		Player p1 = new Player("name", 1, t);
		HashSet<Token> sample = new HashSet<Token>();
		assertTrue("", !p1.takeToken());
//		assertTrue("", tok.isVisible());
		assertEquals(sample, p1.getTokens());
	}
	
	@Test public void TakenTokenTest02() {
		Tile t = new Tile();
		Token tok = new Token(1);
		t.setToken(tok);
		Player p1 = new Player("name", 1, t);
		HashSet<Token> sample = new HashSet<Token>();
		sample.add(tok);
		assertTrue("", p1.takeToken());
//		assertTrue("", tok.isVisible());
		assertEquals(sample, p1.getTokens());
	}
}