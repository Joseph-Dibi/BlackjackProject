package com.skilldistillery.pokerstuff;

import java.util.ArrayList;
import java.util.List;

public abstract class Hand {
	//Deck deck = new Deck();
	List<Cards> hand = new ArrayList<>();
	public Hand(){
		
	}
	protected int getHandValue(){
		int handValue = 0;
		for (Cards cards : hand) {
			handValue += cards.getValue();
		}
		return handValue;
	}
	void clearHand() {
		hand.clear();
	}
	public List<Cards> getCards(Cards c){
		hand.add(c);
		
		return hand;
	}

	public String toString() {
		return hand.toString();
	}
	void addCard(Cards c){
		hand.add(c);
	}
}

