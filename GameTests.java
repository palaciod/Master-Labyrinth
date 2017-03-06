package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import code.Game;
import code.Tile;
import gui.View;

public class GameTests {

	private Game _game;
	private View _v;
	
	
	private void createGame() {
		String[] playerList = new String[4];
		playerList[0] = "p1";
		playerList[1] = "p2";
		playerList[2] = "p3";
		playerList[3] = "p4";
		_game = new Game(playerList);
		_v = new View(_game);
		_game.addView(_v);
	}
	
	private void incrementTurn() {
		_game.setBoardShiftValue();
		_game.nextTurn();
		
	}
	
	@Test public void getPlayersTest() {
		createGame();
		assertTrue("", _game.getPlayers() > 0);
	}
	
	@Test public void nextTurnTest() {
		createGame();
		assertTrue(_game.boardHasShifted() == false);
		assertTrue(_game.currentPlayerHasMoved() == false);
		assertTrue(_game.getCurrentPlayer().getNumber() == 0);
		incrementTurn();
		assertTrue(_game.getCurrentPlayer().getNumber() == 1);
		incrementTurn();
		assertTrue(_game.getCurrentPlayer().getNumber() == 2);
		incrementTurn();
		assertTrue(_game.getCurrentPlayer().getNumber() == 3);
		incrementTurn(); 
		assertTrue(_game.getCurrentPlayer().getNumber() == 0);
	}
	
	@Test public void getViewTest() {
		createGame();
		assertEquals(_game.getView(), _v);
	}
	
	@Test public void moveTest() {
		createGame();
		_game.setBoardShiftValue();
		if(_game.getBoard().getTile(3, 2).getNorth()) {
			_game.move(_game.getBoard().getTile(3, 2));
			assertTrue(_game.currentPlayerHasMoved());
		} else {
			assertTrue(_game.getCurrentPlayer().move(_game.getBoard().getTile(3, 2)) == false);
		}
	}
	
	@Test public void invalidMove() {
		createGame();
		if(_game.getBoard().getTile(3, 2).getNorth()) {
			_game.move(_game.getBoard().getTile(3, 2));
			assertTrue(_game.currentPlayerHasMoved() == false);
		}
	}
	
	@Test public void columnShiftTest01() {
		createGame();
		_game.shiftBoardColumnUp(1);
		assertTrue(_game.boardHasShifted());
	}
	
	@Test public void columnShiftTest02() {
		createGame();
		_game.shiftBoardColumnDown(5);
		assertTrue(_game.boardHasShifted());
	}
	
	@Test public void rowShiftTest01() {
		createGame();
		_game.shiftBoardRowLeft(3);
		assertTrue(_game.boardHasShifted());
	}
	
	@Test public void rowShiftTest02() {
		createGame();
		_game.shiftBoardRowRight(1);
		assertTrue(_game.boardHasShifted());
	}
	
	@Test public void invalidShift01() {
		createGame();
		_game.setLastShiftDirection(Tile.NORTH);
		_game.setLastShiftIndex(3);
		_game.shiftBoardColumnDown(3);
		assertTrue(_game.boardHasShifted() == false);
	}
	
	@Test public void invalidShift02() {
		createGame();
		_game.setLastShiftDirection(Tile.SOUTH);
		_game.setLastShiftIndex(1);
		_game.shiftBoardColumnUp(1);
		assertTrue(_game.boardHasShifted() == false);
	}
	
	@Test public void invalidShift03() {
		createGame();
		_game.setLastShiftDirection(Tile.EAST);
		_game.setLastShiftIndex(5);
		_game.shiftBoardRowLeft(5);
		assertTrue(_game.boardHasShifted() == false);
	}
	
	@Test public void invalidShift04() {
		createGame();
		_game.setLastShiftDirection(Tile.WEST);
		_game.setLastShiftIndex(1);
		_game.shiftBoardRowRight(1);
		assertTrue(_game.boardHasShifted() == false);
	}
	
	@Test public void calcScoreTest() {
		createGame();
		for(int row = 1; row < 7; row++) {
			for(int column = 1; column < 7; column++) {
				Tile tile = _game.getBoard().getTile(row, column);
				if(tile.hasToken() && (tile.getToken().getNumber() == 1 || tile.getToken().getNumber() == 2)) {
					_game.setCurrentPlayerMovedValue();
					_game.getCurrentPlayer().setTile(tile);
					_game.collectToken();
					incrementTurn();
					incrementTurn();
					incrementTurn();
					incrementTurn();
				}
			}
		}
		assertTrue(_game.getCurrentPlayer().calculateScore() == 3);
	}
}
