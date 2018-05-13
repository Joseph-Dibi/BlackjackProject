package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Deck;
import com.skilldistillery.pokerstuff.PlayerPurse;
import com.skilldistillery.pokerstuff.Rank;

public class BlackJackGame {
	// BlackJackHand cards = new BlackJackHand();

	public BlackJackGame() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Casino Royale, we just play for your real fake money.");
		PlayerPurse gamblingMoney = new PlayerPurse(1000);
		Deck deck = new Deck();
		System.out.println("How many decks would you like to play with?");
		int deckAmount = sc.nextInt();
		sc.nextLine();
		for (int i = 0; i < deckAmount; i++) {
			deck.addDeck();
		}
		startGame(gamblingMoney, sc, deck, deckAmount);
	}

	public void startGame(PlayerPurse money, Scanner sc, Deck deck, int deckAmount) {

		List<Cards> playerHand = new ArrayList<>();
		List<Cards> dealerHand = new ArrayList<>();
		List<Cards> splitHand = new ArrayList<>();
		playerHand.clear();
		dealerHand.clear();
		splitHand.clear();
		System.out.print("You have: $" + money.getMoney() + ". How much would you like to bet?\n$");
		double betAmount = sc.nextInt();
		sc.nextLine();
		while (money.getMoney() - betAmount < 0) {
			System.out.println("You can't bet more than you have!\nGive me a real bet.\n$");
			betAmount = sc.nextInt();
			sc.nextLine();
		}
		money.setMoney(money.getMoney() - betAmount);
		System.out.println("You bet: $" + betAmount + ". You have $" + money.getMoney() + " left.");

		int deckSize = deck.checkDeckSize();
		if (deckSize < (deckAmount * 52) / 4) {
			System.out.println("Time for a new shoe");
			deck.clearDeck();
			for (int i = 0; i < deckAmount; i++) {
				deck.addDeck();
			}
		}
		System.out.println("There are: " + deckSize + " cards left.");
		System.out.println("Dealing cards!");
		deck.shuffleDeck();
		playerHand.add(deck.dealCard());
		playerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		dealerHand.add(deck.dealCard());
		splitHandCheck(playerHand, sc, splitHand, deck, betAmount, money);
		blackJack(playerHand, dealerHand, money, betAmount, sc, deck, deckAmount);// checks for dealer/player blackjack.
		doubleAceCheck(playerHand);// checks if player or dealer have two aces to start. one is automatically set
									// low if so.
		doubleAceCheck(dealerHand);
		if (!splitHand.isEmpty()) {
			playerSplitTurn(splitHand, dealerHand, deck, money, betAmount, sc, deckSize);
		}
		playerTurn(playerHand, dealerHand, deck, money, betAmount, sc, deckAmount, splitHand); // Player turn, players
																								// cards will be shown
																								// then
		// player can hit or

		dealerTurn(dealerHand, deck, money, betAmount, sc, deckAmount);// runs dealer turn. dealer plays by blackjack
																		// logic.
		determineWinner(dealerHand, playerHand, money, betAmount, sc, deck, deckAmount, splitHand);
	}

	private void playerSplitTurn(List<Cards> splitHand, List<Cards> dealerHand, Deck deck, PlayerPurse money,
			double betAmount, Scanner sc, int deckSize) {
		displayDealerFirstCard(dealerHand);
		displayPlayerHand(splitHand);
		System.out.println("What would you like to do next?\n1. Hit\n2. Stand");
		int choice = sc.nextInt();
		sc.nextLine();
		do {
			if (choice == 1) {
				Cards hit = deck.dealCard();
				splitHand.add(hit);
				displayPlayerHand(splitHand);
				setAce(splitHand, sc);
				int handValue = calculateHandValue(splitHand);
				if (handValue > 21) {
					System.out.println("You bust out!");

				} else {
					displayPlayerHand(splitHand);
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void splitHandCheck(List<Cards> playerHand, Scanner sc, List<Cards> splitHand, Deck deck, double betAmount,
			PlayerPurse money) {
		if (playerHand.get(0).getRank() == playerHand.get(1).getRank()) {
			for (Cards cards : splitHand) {
				System.out.println("You have the " + cards.getRank() + " of " + cards.getSuit());
			}
			System.out.println("Would you like to split your hand?");
			int choice = sc.nextInt();
			sc.nextLine();
			if (money.getMoney() - betAmount >= 0 && choice == 1) {
				money.setMoney(money.getMoney() - betAmount);
				splitHand.add(playerHand.get(1));
				playerHand.remove(1);
				splitHand.add(deck.dealCard());
				playerHand.add(deck.dealCard());

			} else if (money.getMoney() - betAmount < 0 && choice == 1) {
				System.out.println("I am sorry, but you do not have enough money to split.");
			} else {

			}
		}

	}

	private void doubleAceCheck(List<Cards> playerHand) {
		int twoAcesCheck = calculateHandValue(playerHand);
		if (twoAcesCheck == 22) {
			Cards lowAce = new Cards(playerHand.get(0).getSuit(), Rank.ACELOW);
			playerHand.remove(0);
			playerHand.add(lowAce);
		}
	}

	private void determineWinner(List<Cards> dealerHand, List<Cards> playerHand, PlayerPurse money, double betAmount,
			Scanner sc, Deck deck, int deckAmount, List<Cards> splitHand) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		int splitHandValue = calculateHandValue(splitHand);
		if (!splitHand.isEmpty()) {
			if (splitHandValue > dealerHandValue && splitHandValue < 22) {
				System.out.println("Player Wins!");
				money.setMoney(money.getMoney() + (betAmount * 2));
			} else if (splitHandValue < dealerHandValue && dealerHandValue < 22) {
				System.out.println("Dealer Wins! Better luck next time.");
			}
		}
		if (playerHandValue > dealerHandValue && playerHandValue < 22) {
			System.out.println("Player Wins!");
			money.setMoney(money.getMoney() + (betAmount * 2));
			playAgain(money, deck, sc, deckAmount);
		} else if (playerHandValue < dealerHandValue && dealerHandValue < 22) {
			System.out.println("Dealer Wins! Better luck next time.");
			if (money.getMoney() == 0) {
				System.out.println("You're out of cash! Go hit up an ATM.");
				sc.close();
				System.exit(0);
			} else {
				playAgain(money, deck, sc, deckAmount);
			}
		} else {
			System.out.println("Push. You tied.");
			playAgain(money, deck, sc, deckAmount);
		}
	}

	private void playAgain(PlayerPurse money, Deck deck, Scanner sc, int deckAmount) {
		System.out.println("You have: $" + money.getMoney());
		System.out.println("Would you like to play again?\n1. Yes\2. No");
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1) {
			startGame(money, sc, deck, deckAmount);
		} else {
			System.out.println("Thank you for playing!");
			sc.close();
			System.exit(0);
		}
	}

	private void blackJack(List<Cards> playerHand, List<Cards> dealerHand, PlayerPurse money, double betAmount,
			Scanner sc, Deck deck, int deckAmount) {
		int playerHandValue = calculateHandValue(playerHand);
		int dealerHandValue = calculateHandValue(dealerHand);
		if (playerHandValue == 21 && dealerHandValue != 21) {
			System.out.println("BLACKJACK! You win!");
			money.setMoney(money.getMoney() + (betAmount * 2.5));
			playAgain(money, deck, sc, deckAmount);

		} else if (playerHandValue != 21 && dealerHandValue == 21) {
			System.out.println("Dealer BLACKJACK! That's rough buddy...");
			if (money.getMoney() == 0) {
				System.out.println("You're out of cash! Go hit up an ATM.");
				sc.close();
				System.exit(0);
			} else {
				playAgain(money, deck, sc, deckAmount);
			}

		} else if (playerHandValue == 21 && dealerHandValue == 21) {
			System.out.println("Push");
			playAgain(money, deck, sc, deckAmount);

		} else {

		}
	}

	private void dealerTurn(List<Cards> dealerHand, Deck deck, PlayerPurse money, double betAmount, Scanner sc,
			int deckAmount) {
		displayDealerHand(dealerHand);
		int handValue = calculateHandValue(dealerHand);

		if (handValue < 17) {
			while (handValue < 17) {
				System.out.println("Dealer hits.");
				dealerHand.add(deck.dealCard());
				setAceComputer(dealerHand);
				handValue = calculateHandValue(dealerHand);
				if (handValue > 21) {
					displayDealerHand(dealerHand);
					System.out.println("Dealer Busts!");
					money.setMoney(money.getMoney() + (2 * betAmount));
					playAgain(money, deck, sc, deckAmount);
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
			double betAmount, Scanner sc, int deckAmount, List<Cards> splitHand) {
		displayDealerFirstCard(dealerHand);
		displayPlayerHand(playerHand);
		System.out.println("What would you like to do next?\n1. Hit\n2. Stand");
		int choice = sc.nextInt();
		sc.nextLine();
		do {
			if (choice == 1) {
				Cards hit = deck.dealCard();
				playerHand.add(hit);
				displayPlayerHand(playerHand);
				setAce(playerHand, sc);
				int handValue = calculateHandValue(playerHand);
				if (handValue > 21) {
					System.out.println("You bust out!");
					if (splitHand.isEmpty()) {
						if (money.getMoney() == 0) {
							System.out.println("You're out of cash! Go hit up an ATM.");
							sc.close();
							System.exit(0);
						} else {
							playAgain(money, deck, sc, deckAmount);
						}
					}
				} else {
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void setAce(List<Cards> playerHand, Scanner sc) {
		for (Cards cards : playerHand) {
			if (cards.getRank() == Rank.ACE || cards.getRank() == Rank.ACELOW) {
				System.out.println("Would you like to set your ace low or high?\n1. Low\n2. High");
				int choice = sc.nextInt();
				sc.nextLine();
				if (choice == 2) {
					cards.setRank(Rank.ACE);
					continue;
				} else {
					cards.setRank(Rank.ACELOW);
				}
			}
		}
	}

	private void setAceComputer(List<Cards> playerHand) {
		int computerHandValue = calculateHandValue(playerHand);
		if (computerHandValue > 21) {
			for (Cards cards : playerHand) {
				if (cards.getRank() == Rank.ACE) {
					cards.setRank(Rank.ACELOW);
					System.out.println("Dealer has set an Ace to one.");
					if (calculateHandValue(playerHand) < 21) {
						break;
					}
				}
			}
		}

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
