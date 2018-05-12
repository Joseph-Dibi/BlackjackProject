## BlackJack Project

### Overview
This is a Blackjack simulator.

### Topics
* Object-oriented design
* Interfaces
* Enums
* Inheritance and Abstraction
* Java Collections

Sorry in advance...

 My BlackJack game works by initiating a method of startGame. Once startGame is initiated it will clear any cards in the players or dealers hand, the reason for that is once a player loses/wins they are given the option to play again. The game then creates a new deck and deals two cards apiece to the player and dealer. If the player or dealer has BlackJack they instantly win, player BlackJack pays out 3/2. If both have BlackJack it is a push. If neither has BlackJack then the game continues with the player turn. The player will see the first card in the dealers hand then both of theirs along with the players card total. They can decide to hit or stand based on this. If they hit, it will give them their new card then display all of their cards and the new total amount. If this amount is above 21 they instantly bust out and lose their bet. If the player stands, the dealer's turn commences. Both of the Dealer's cards are now shown. The dealer will hit if they are under 17, otherwise they will stand. If the dealer surpasses 21 they will instantly bust and the player wins their bet. If the dealer does not bust we proceed to the determine winner portion. The scores are then compared. Even scores result in a push, otherwise either the dealer or player will win and the players purse is adjusted accordingly. After a won/lost/push hand the player is told the amount of money they have left, then asked if they would like to play again. If yes, the money is carried over into a new game and everything begins again. The only way the player cannot continue playing is if they run out of money. If the player runs out of money they are told to hit the ATM and the program exits.

The program functions through a series of methods. The initial main will call the BlackJack app. This app will immediately start one run through via the start game function. Players are given an initial $1000 purse with which to bet. Any hands are wiped via the list clear method call. A new deck is created and then shuffled via a deck method call. The shuffle was very easy as I simply call collections.shuffle. That was a wonderfully easy way to have a random deck each run.

The Deck holds 52 cards which are created using enums for both rank and suit. The deck runs through a foreach loop of rank assigning the current rank as well as the suit to the deck. This is done four times so that every suit is fully represented.

Players are then asked for a bet amount. A while loop prevents players from betting more than they have in their purse. Player purse is modified by bet amount. After bets, the players are dealt cards by having the deck deal a card and then add it to the array lists which make up both hands. The Blackjack method is then run using both decks. PlayerTurn will run if Blackjack does not terminate startGame early. Player turns consist of a loop which allow then to continuously hit till stand is chosen or they bust. Dealer turns function the same way but dealer is automated and forced to hit till 17 or more. A displayCard function shows dealer cards, showing only one during the player turn. Busts instantly adjust player purse by bet*2. If neither player busts, the determineWinner method compares the values of two hands and awards accordingly. Both players hands are constantly evaluated before turns and after hits. This is done via a calculateHandValue method that takes the integer values of all the cards ranks that are present in the hand.

After every win/loss/tie the playAgain method is called. The player is told how much money they have and then asked if they would like to play again. A no results in the closing of scanners and termination of the program. (Scanners are also closed when money runs out). If the player chooses yes, the startGame method is called and money passed into it so the players purse remains constant.
