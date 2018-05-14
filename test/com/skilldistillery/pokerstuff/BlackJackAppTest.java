package com.skilldistillery.pokerstuff;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.skilldistillery.blackjack.BlackJackGame;

public class BlackJackAppTest {
	private BlackJackGame bjg;
	List<Cards> testList = new ArrayList<>();
	private PlayerPurse money;
	private Deck deck;
	Scanner sc = new Scanner(System.in);
	private int deckAmount;
	
	@Before
	public void setUp() {
		bjg = new BlackJackGame();
		bjg.startGame(money, sc, deck, deckAmount);
	}
	////////
	@After
	public void tearDown() {
		bjg = null;
	}
	@Test
	public void two_aces_causes_one_ace_to_equal_eleven() {
//		testList.add(new Cards(Suit.CLUBS, Rank.ACE));
//		testList.add(new Cards(Suit.CLUBS, Rank.ACE));
//		bjg.startGame(money, sc, deck, deckAmount);
//		bjg.doubleAceCheck(testList);
//		assertEquals(12, bjg.calculateHandValue(testList));
//		//assertEquals(calculate), actual);		
		//assertEquals(Cards, doubleAceCheck());
	}

	
}
