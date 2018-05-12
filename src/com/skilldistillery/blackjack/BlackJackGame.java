package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Deck;
import com.skilldistillery.pokerstuff.PlayerPurse;

public class BlackJackGame {
	// BlackJackHand cards = new BlackJackHand();

	public BlackJackGame() {
		System.out.println("Welcome to Casino Royale, we just play for your real fake money.");
		PlayerPurse gamblingMoney = new PlayerPurse(1000);
		startGame(gamblingMoney);
	}

	public void startGame(PlayerPurse money) {
		Scanner sc = new Scanner(System.in);
		List<Cards> playerHand = new ArrayList<>();
		List<Cards> dealerHand = new ArrayList<>();
		playerHand.clear();
		dealerHand.clear();
		System.out.print("You have: $" + money.getMoney() + ". How much would you like to bet?\n$");
		double betAmount = sc.nextInt();
		sc.nextLine();
		while(money.getMoney()-betAmount < 0) {
			System.out.println("You can't bet more than you have!\nGive me a real bet.\n$");
			betAmount = sc.nextInt();
			sc.nextLine();
		}
		money.setMoney(money.getMoney() - betAmount);
		System.out.println("You bet: $" + betAmount + ". You have $" + money.getMoney() + " left.");
		Deck deck = new Deck();
		// int deckSize = deck.checkDeckSize();
		// System.out.println(deckSize);
		System.out.println("Dealing cards!");
		deck.shuffleDeck();
		playerHand.add(deck.dealCard());
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		
		blackJack(playerHand, dealerHand, money, betAmount, sc);

		playerTurn(playerHand, dealerHand, deck, money, betAmount, sc); // Player turn, players cards will be shown then
																	// player can hit or

		dealerTurn(dealerHand, deck, money, betAmount);// runs dealer turn. dealer plays by blackjack logic.
		determineWinner(dealerHand, playerHand, money, betAmount, sc);
	}

	private void determineWinner(List<Cards> dealerHand, List<Cards> playerHand, PlayerPurse money, double betAmount,
			Scanner sc) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		if (playerHandValue > dealerHandValue && playerHandValue < 22) {
			System.out.println("Player Wins!");
			money.setMoney(money.getMoney() + (betAmount*2));
			playAgain(money);
		} else if (playerHandValue < dealerHandValue && dealerHandValue < 22) {
			System.out.println("Dealer Wins! Aren't you glad it's just a game?");
			if (money.getMoney() == 0) {
				System.out.println("You're out of cash! Go hit up an ATM.");
				sc.close();
				System.exit(0);
			} else {
				playAgain(money);
			}
		} else {
			System.out.println("Push. You tied.");
			playAgain(money);
		}
	}

	private void playAgain(PlayerPurse money) {
		Scanner sc = new Scanner(System.in);
		System.out.println("You have: $" + money.getMoney());
		System.out.println("Would you like to play again?\n1. Yes\2. No");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1) {
			startGame(money);
		} else {
			System.out.println("Thank you for playing!");
			sc.close();
			System.exit(0);
		}
	}

	private void blackJack(List<Cards> playerHand, List<Cards> dealerHand, PlayerPurse money, double betAmount,
			Scanner sc) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		if (playerHandValue == 21 && dealerHandValue != 21) {
			System.out.println("BLACKJACK! You win!");
			money.setMoney(money.getMoney() + (betAmount * 2.5));
			playAgain(money);

		} else if (playerHandValue != 21 && dealerHandValue == 21) {
			System.out.println("Dealer BLACKJACK! That's rough buddy...");
			if (money.getMoney() == 0) {
				System.out.println("You're out of cash! Go hit up an ATM.");
				sc.close();
				System.exit(0);
			} else {
				playAgain(money);
			}

		} else if (playerHandValue == 21 && dealerHandValue == 21) {
			System.out.println("Push");
			playAgain(money);

		} else {

		}
	}

	private void dealerTurn(List<Cards> dealerHand, Deck deck, PlayerPurse money, double betAmount) {
		displayDealerHand(dealerHand);
		int handValue = calculateHandValue(dealerHand);
		
		if (handValue < 17) {
			while (handValue < 17) {
				System.out.println("Dealer hits.");
				dealerHand.add(deck.dealCard());
				handValue = calculateHandValue(dealerHand);
				if (handValue > 21) {
					displayDealerHand(dealerHand);
					System.out.println("Dealer Busts!");
					money.setMoney(money.getMoney() + (2*betAmount));
					playAgain(money);
				}
			}
			displayDealerHand(dealerHand);
		}
	}

	private void displayDealerHand(List<Cards> dealerHand) {
		int cardValue = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			System.out.println("The Dealer has the: " + dealerHand.get(i).toString());
			cardValue += dealerHand.get(i).getValue();
		}
		System.out.println("The Dealer is showing: " + cardValue);
	}

	private void playerTurn(List<Cards> playerHand, List<Cards> dealerHand, Deck deck, PlayerPurse money,
			double betAmount, Scanner sc) {
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
					if (money.getMoney() == 0) {
						System.out.println("You're out of cash! Go hit up an ATM.");
						sc.close();
						System.exit(0);
					} else {
						playAgain(money);
					}

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
			System.out.println("You have the: " + cards.toString());
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
