package com.skilldistillery.pokerstuff;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skilldistillery.blackjack.BlackJackGame;

public class BlackJackAppTest {
	private BlackJackGame bjg;
	
	@Before
	public void setUp() {
		bjg = new BlackJackGame();
	}
	
	@After
	public void tearDown() {
		bjg = null;
	}
	@Test
	public void two_aces_causes_one_ace_to_equal_eleven() {
		
		//assertEquals(Cards, doubleAceCheck());
	}

	
}
