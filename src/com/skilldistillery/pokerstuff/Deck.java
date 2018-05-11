package com.skilldistillery.pokerstuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	List<Cards> deck = new ArrayList<>();
	
	public Deck() {
		for(Rank r: Rank.values()) {
			Cards spades = new Cards(Suit.SPADES, r);
			deck.add(spades);
		}
		for(Rank r: Rank.values()) {
			Cards hearts = new Cards(Suit.HEARTS, r);
			deck.add(hearts);
		}
		for(Rank r: Rank.values()) {
			Cards clubs = new Cards(Suit.CLUBS, r);
			deck.add(clubs);
		}
		for(Rank r: Rank.values()) {
			Cards diamonds = new Cards(Suit.DIAMONDS, r);
			deck.add(diamonds);
		}
	}
	
	public int checkDeckSize(){
		
		
		return deck.size();
	}
	public Cards dealCard() {
		return deck.remove(0);
	}
	
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
}
/*
Create a class Deck. It will hold a List of Cards.
In the constructor, initialize the List with all 52 cards.
Add a method checkDeckSize which returns the number of cards still in the deck.
Add a method dealCard that removes a Card from the deck.
Add a method shuffle to shuffle the deck.
*/