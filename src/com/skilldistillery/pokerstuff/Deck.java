package com.skilldistillery.pokerstuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
	List<Cards> deck = new ArrayList<>();
	
	public Deck() {
		
	}
	public void addDeck() {
		Rank[] rank = Rank.values();
		for (int i = 0; i < rank.length - 1; i++) {
			Rank rankTemp = rank[i];
			Cards spades = new Cards(Suit.HEARTS, rank[i]);
			deck.add(spades);
			
		}
		for (int i = 0; i < rank.length - 1; i++) {
			Rank rankTemp = rank[i];
			Cards spades = new Cards(Suit.HEARTS, rank[i]);
			deck.add(spades);
			
		}
		for (int i = 0; i < rank.length - 1; i++) {
			Rank rankTemp = rank[i];
			Cards spades = new Cards(Suit.HEARTS, rank[i]);
			deck.add(spades);
			
		}
		for (int i = 0; i < rank.length - 1; i++) {
			Rank rankTemp = rank[i];
			Cards spades = new Cards(Suit.HEARTS, rank[i]);
			deck.add(spades);
			
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