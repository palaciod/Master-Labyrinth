package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.Player;
import code.Tile;
import code.Token;

public class TokenTests {
	
	@Test public void resetNextTest() {
		Token.setNextToken(3);
		assertTrue(Token.getNextToken() == 3);
		Token.resetNextToken();
		assertTrue(Token.getNextToken() == 1);
	}
	
	@Test public void getNextTokenTest() {
		Token.resetNextToken();
		Token test = new Token(1);
		Tile t = new Tile();
		t.setToken(test);
		Player p = new Player("", 1, t);
		p.takeToken();
		assertTrue("", p.hasToken() && t.hasToken() == false && Token.getNextToken() == 2);
	}
	
	@Test public void setNextTokenTest() {
		Token.resetNextToken();
		assertTrue("", Token.getNextToken() == 1);
		Token.setNextToken(3);
		assertTrue("", Token.getNextToken() == 3);
	}
	
	@Test public void getTokenNumberTest() {
		Token test = new Token(1);
		assertTrue("", test.getNumber() == 1);
	}
	
	@Test public void incrementNextTest() {
		Token.resetNextToken();
		assertTrue(Token.getNextToken() == 1);
		Token.incrementNextToken();
		assertTrue(Token.getNextToken() == 2);
	}

	@Test public void creationAndGetNumberTest() {
		Token token = new Token(10);
		assertTrue(token.getNumber() == 10);
	}
	
	@Test public void toStringTest() {
		Token token = new Token(5);
		assertTrue(token.toString().equals("5"));
	}
}
