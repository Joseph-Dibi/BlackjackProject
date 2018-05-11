package com.skilldistillery.blackjack;

import java.util.List;
import java.util.Scanner;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Deck;
import com.skilldistillery.pokerstuff.Hand;

public class BlackJackGame {
	BlackJackHand cards = new BlackJackHand();
	Deck deck = new Deck();
	

	public BlackJackGame() {
		int deckSize = deck.checkDeckSize();
		System.out.println();
		System.out.println("Welcome to Casino Royale, we just play for fun! (For now).");
		deck.shuffleDeck();
		List<Cards> playerHand = cards.getCards(deck.dealCard());
		List<Cards> dealerHand = cards.getCards(deck.dealCard());
		playerTurn(playerHand); // Player turn, players cards will be shown then player can hit or stand.

	}

	private void playerTurn(List<Cards> playerHand) {
		Scanner sc = new Scanner(System.in);
		displayPlayerHand(playerHand);
		System.out.println("What would you like to do next?\n1. Hit\n2.Stand");
		int choice = sc.nextInt();
		sc.nextLine();
		do {
			if (choice == 1) {
				Cards hit = deck.dealCard();
				playerHand.add(hit);
				int handValue = calculateHandValue(playerHand);
				if (handValue > 21) {
					System.out.println("You bust out with a hand value of: " + handValue);
					break;
				} else {
					displayPlayerHand(playerHand);
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void displayPlayerHand(List<Cards> playerHand) {// Displays player hand
		int cardValue = 0;
		for (Cards cards : playerHand) {
			System.out.println("You have the " + cards.toString());
			cardValue += cards.getValue();
		}
		System.out.println("You are showing: " + cardValue);
	}

	private int calculateHandValue(List<Cards> playerHand) {// This calculates the hand value. Can be used for
															// player/dealer.
		int cardValue = 0;
		for (Cards cards : playerHand) {
			cardValue += cards.getValue();
		}
		return cardValue;
	}
}
