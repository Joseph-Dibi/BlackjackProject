package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Deck;

public class BlackJackGame {
	BlackJackHand cards = new BlackJackHand();

	public BlackJackGame() {
		System.out.println("Welcome to Casino Royale, we just play for fun! (For now).");
		startGame();
	}
	public void startGame() {	
		List<Cards> playerHand = new ArrayList<>();
		List<Cards> dealerHand = new ArrayList<>();
			playerHand.clear();
			dealerHand.clear();
			Deck deck = new Deck();
			int deckSize = deck.checkDeckSize();
			System.out.println("Dealing cards!");
			deck.shuffleDeck();
			playerHand.add(deck.dealCard());
			playerHand.add(deck.dealCard());
			dealerHand.add(deck.dealCard());
			dealerHand.add(deck.dealCard());
			blackJack(playerHand, dealerHand);
			
			playerTurn(playerHand, dealerHand, deck); // Player turn, players cards will be shown then player can hit or
			
			dealerTurn(dealerHand, deck);// runs dealer turn. dealer plays by blackjack logic.
			determineWinner(dealerHand, playerHand);
	}
	

	private void determineWinner(List<Cards> dealerHand, List<Cards> playerHand) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		if (playerHandValue > dealerHandValue && playerHandValue < 22) {
			System.out.println("Player Wins! Too bad its all for fun...");
			playAgain();
		} else if (playerHandValue < dealerHandValue && dealerHandValue < 22) {
			System.out.println("Dealer Wins! Aren't you glad it's just a game?");
			playAgain();
		} else
			System.out.println("Push. You tied.");
			playAgain();
	}

	private void playAgain() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Would you like to play again?\n1. Yes\2. No");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1) {
			startGame();
		}
		else {
			System.out.println("Thank you for playing!");
			sc.close();
			System.exit(0);
		}
	}

	private void blackJack(List<Cards> playerHand, List<Cards> dealerHand) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		if (playerHandValue == 21 && dealerHandValue != 21) {
			System.out.println("BLACKJACK! You win!");
			playAgain();

		} else if (playerHandValue != 21 && dealerHandValue == 21) {
			System.out.println("Dealer BLACKJACK! That's rough buddy...");
			playAgain();

		} else if (playerHandValue == 21 && dealerHandValue == 21) {
			System.out.println("Push");
			playAgain();

		} else {

		}
	}

	private void dealerTurn(List<Cards> dealerHand, Deck deck) {
		displayDealerhand(dealerHand);
		int handValue = calculateHandValue(dealerHand);
		if (handValue < 17) {
			while (handValue < 17) {
				System.out.println("Dealer hits.");
				dealerHand.add(deck.dealCard());
				handValue = calculateHandValue(dealerHand);
				if (handValue > 21) {
					System.out.println("The Dealer is showing: " + handValue);
					System.out.println("Dealer Busts!");
					break;
				}
			}
			displayDealerhand(dealerHand);
		}
	}

	private void displayDealerhand(List<Cards> dealerHand) {
		int cardValue = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			System.out.println("The Dealer is showing: " + dealerHand.get(i).toString());
			cardValue += dealerHand.get(i).getValue();
		}
		System.out.println("Dealer is showing: " + cardValue);
	}

	private void playerTurn(List<Cards> playerHand, List<Cards> dealerHand, Deck deck) {
		Scanner sc = new Scanner(System.in);
		displayDealerFirstCard(dealerHand);
		displayPlayerHand(playerHand);
		System.out.println("What would you like to do next?\n1. Hit\n2. Stand");
		int choice = sc.nextInt();
		sc.nextLine();
		do {
			if (choice == 1) {
				Cards hit = deck.dealCard();
				playerHand.add(hit);
				int handValue = calculateHandValue(playerHand);
				if (handValue > 21) {
					System.out.println("You bust out with a hand value of: " + handValue);
					playAgain();
				} else {
					displayPlayerHand(playerHand);
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void displayDealerFirstCard(List<Cards> dealerHand) {
		for (int i = 0; i < dealerHand.size() - 1; i++) {
			System.out.println("The Dealer is showing: " + dealerHand.get(i).toString());
		}
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
