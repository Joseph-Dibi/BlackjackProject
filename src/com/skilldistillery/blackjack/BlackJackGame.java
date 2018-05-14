package com.skilldistillery.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.pokerstuff.Cards;
import com.skilldistillery.pokerstuff.Deck;
import com.skilldistillery.pokerstuff.PlayerPurse;
import com.skilldistillery.pokerstuff.Rank;

public class BlackJackGame {

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

		blackJack(playerHand, dealerHand, money, betAmount, sc, deck, deckAmount);// checks for dealer/player blackjack.
		splitHandCheck(playerHand, sc, splitHand, deck, betAmount, money);
		doubleAceCheck(playerHand);// checks if player or dealer have two aces to start. one is automatically set
		doubleAceCheck(splitHand); // low if so.
		doubleAceCheck(dealerHand);
		if (!splitHand.isEmpty()) {
			playerSplitTurn(splitHand, dealerHand, deck, money, betAmount, sc, deckSize);
		}
		playerTurn(playerHand, dealerHand, deck, money, betAmount, sc, deckAmount, splitHand); // Player turn, players
																								// cards will be shown
																								// then
		// player can hit or

		dealerTurn(dealerHand, deck, money, betAmount, sc, deckAmount, splitHand);// runs dealer turn. dealer plays by
																					// blackjack
		// logic.
		determineWinner(dealerHand, playerHand, money, betAmount, sc, deck, deckAmount, splitHand);
	}

	private void playerSplitTurn(List<Cards> splitHand, List<Cards> dealerHand, Deck deck, PlayerPurse money,
			double betAmount, Scanner sc, int deckSize) { //identical to player turn but only occurs if you got doubles and chose to split.
		displayDealerFirstCard(dealerHand);
		displayPlayerHand(splitHand);
		System.out.println("What would you like to do next?\n1. Hit\n2. Stand");
		int choice = sc.nextInt();
		sc.nextLine();
		do {
			if (choice == 1) {
				Cards hit = deck.dealCard();
				splitHand.add(hit);
				setAceComputer(splitHand);
				displayPlayerHand(splitHand);
				int handValue = calculateHandValue(splitHand);
				if (handValue > 21) {
					System.out.println("You bust out!");
					break;

				} else {
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void splitHandCheck(List<Cards> playerHand, Scanner sc, List<Cards> splitHand, Deck deck, double betAmount,
			PlayerPurse money) { // If you got a double rank of cards you can split hands. You must lay down original bet again and have enough to do that to split.
		if (playerHand.get(0).getRank() == playerHand.get(1).getRank()) {
			for (Cards cards : playerHand) {
				System.out.println("You have the " + cards.getRank() + " of " + cards.getSuit());
			}
			System.out.println("Would you like to split your hand?\n1. Yes\n2. No");
			int choice = sc.nextInt();
			sc.nextLine();
			if (money.getMoney() - betAmount >= 0 && choice == 1) {
				money.setMoney(money.getMoney() - betAmount);
				System.out
						.println("You bet an additional $" + betAmount + ". You have $" + money.getMoney() + " left.");
				splitHand.add(playerHand.get(1));
				playerHand.remove(1);
				splitHand.add(deck.dealCard());
				playerHand.add(deck.dealCard());

			} else if (money.getMoney() - betAmount < 0 && choice == 1) {
				System.out.println("I am sorry, but you do not have enough money to split.");
			} 
		}

	}

	private void doubleAceCheck(List<Cards> playerHand) { //checks if player hands or dealer have double aces and autoshift before any plays.
		int twoAcesCheck = calculateHandValue(playerHand);
		if (twoAcesCheck == 22) {
			Cards lowAce = new Cards(playerHand.get(0).getSuit(), Rank.ACELOW);
			playerHand.remove(0);
			playerHand.add(lowAce);
		}
	}

	private void determineWinner(List<Cards> dealerHand, List<Cards> playerHand, PlayerPurse money, double betAmount,
			Scanner sc, Deck deck, int deckAmount, List<Cards> splitHand) {
		int playerHandValue = calculateHandValue(playerHand); //gets hand values into ints for easy comparisons.
		int dealerHandValue = calculateHandValue(dealerHand);
		int splitHandValue = calculateHandValue(splitHand);
		if (!splitHand.isEmpty()) {
			if ((splitHandValue > dealerHandValue && splitHandValue < 22)
					|| (splitHandValue < 21 && dealerHandValue > 21)) {
				System.out.println("Player Split Hand Wins!");
				money.setMoney(money.getMoney() + (betAmount * 2));
				System.out.println("You win $" + betAmount * 2);
			} else if ((splitHandValue < dealerHandValue && dealerHandValue < 22) || splitHandValue > 21) {
				System.out.println("Dealer Wins! Maybe your other hand will win.");
			} else if (dealerHandValue == splitHandValue && splitHandValue < 22) {
				System.out.println("Push. You tied.");
			}
		}
		if ((playerHandValue > dealerHandValue && playerHandValue < 22)
				|| (playerHandValue < 21 && dealerHandValue > 21)) {
			System.out.println("Player Hand Wins!");
			money.setMoney(money.getMoney() + (betAmount * 2));
			System.out.println("You win $" + betAmount * 2);
		} else if ((playerHandValue < dealerHandValue && dealerHandValue < 22) || playerHandValue > 21) {
			System.out.println("Dealer Wins! Better luck next time.");
		} else if (playerHandValue == dealerHandValue) {
			System.out.println("Push. You tied.");
		}
		if (money.getMoney() > 0) {
			playAgain(money, deck, sc, deckAmount);
		}
		else {
			System.out.println("You're out of Cash!\nGo hit up an ATM!");
			sc.close();	//like an adult
			System.exit(0); //if youre out of cash you cant play again.
		}
	}

	private void playAgain(PlayerPurse money, Deck deck, Scanner sc, int deckAmount) {
		System.out.println("You have: $" + money.getMoney());
		System.out.println("Would you like to play again?\n1. Yes\2. No"); // option to play again. Used to be spread throughout program, now just once after each round
		int choice = sc.nextInt();
		sc.nextLine();
		if (choice == 1) {
			startGame(money, sc, deck, deckAmount);
		} else {
			System.out.println("Thank you for playing!");
			sc.close(); // like an adult
			System.exit(0);
		}
	}

	private void blackJack(List<Cards> playerHand, List<Cards> dealerHand, PlayerPurse money, double betAmount,
			Scanner sc, Deck deck, int deckAmount) {
		int playerHandValue = calculateHandValue(playerHand); // checks blackjack for player and dealer. Not split hand since not natural blackjack.
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
			int deckAmount, List<Cards> splitHand) {
		displayDealerHand(dealerHand);
		int handValue = calculateHandValue(dealerHand); //dealer turn is same as players but automated so Has to hit if < 17
		// hitOn17Check(dealerHand, deck);//Tried to initiate soft/hard 17 but ran into troubles and out of time.
		if (handValue < 17) {
			while (handValue < 17) {
				System.out.println("Dealer hits.");
				dealerHand.add(deck.dealCard());
				setAceComputer(dealerHand);
				handValue = calculateHandValue(dealerHand);
				displayDealerHand(dealerHand);
				if (handValue > 21) {
					System.out.println("Dealer Busts!");
					if (splitHand.isEmpty()) {
						money.setMoney(money.getMoney() + (2 * betAmount));
						playAgain(money, deck, sc, deckAmount);
					}
				}
			}
		}
	}

	// private void hitOn17Check(List<Cards> dealerHand, Deck deck) {
	// if (calculateHandValue(dealerHand) == 17) {
	// for (Cards cards : dealerHand) {
	// if(cards.getRank() == Rank.ACE) {
	// dealerHand.add(deck.dealCard());
	// setAceComputer(dealerHand);
	//
	// }
	// }
	// }
	// }

	private void displayDealerHand(List<Cards> dealerHand) { // displays dealer full hand on dealer turn
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
		displayPlayerHand(playerHand);//Player turn consists of seeing one dealer card and player cards. Then option to hit or stand
		System.out.println("What would you like to do next?\n1. Hit\n2. Stand"); // stand ends turn, hit delivers another card.
		int choice = sc.nextInt();
		sc.nextLine();
		do {//do while loop for multiple hits and at least one run through.
			if (choice == 1) {
				Cards hit = deck.dealCard(); 
				playerHand.add(hit);
				setAceComputer(playerHand);// automated set ace check after every hit and before every bust check.
				displayPlayerHand(playerHand);
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
					} else {
						break;
					}
				} else {
					System.out.println("Hit again?\n1.Yes\n2.No");
					choice = sc.nextInt();
					sc.nextLine();

				}
			}
		} while (choice != 2);

	}

	private void setAceComputer(List<Cards> playerHand) {// sets ace to low if hand value is over 21 and ace is present.
		int computerHandValue = calculateHandValue(playerHand);//player used to have the option to set, but i realized it didnt matter to give them a choice
		if (computerHandValue > 21) {
			for (Cards cards : playerHand) {
				if (cards.getRank() == Rank.ACE) {
					cards.setRank(Rank.ACELOW);
					System.out.println("An Ace has been set to one.");
					if (calculateHandValue(playerHand) < 21) {
						break;
					}
				}
			}
		}

	}

	private void displayDealerFirstCard(List<Cards> dealerHand) {//shows dealer first card
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
