package com.skilldistillery.pokerstuff;

public class Cards {
	private Rank rank;
	private Suit suit;
	
	public Cards(Suit s, Rank r) {
		this.suit = s;
		this.rank = r;
	}
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
	public int getValue(){
		
		return rank.getValue();
	}
	
}


/*
A card has a Suit and Rank. Set these in the constructor.
Generate the methods hashCode and equals
Add a toString which says rank + " of " + suit.
Add a method getValue to return the card's numeric value.
*/