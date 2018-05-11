package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Hand;

public class BlackJackHand extends Hand{
	Hand startHand;
	List<Cards> hand = new ArrayList<>();
	BlackJackHand(){
		
	}
	protected int getHandValue(){
		int handValue = 0;
		for (Cards cards : hand) {
			handValue += cards.getValue();
		}
		return handValue;
	}
	
}
