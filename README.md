## BlackJack Project

### Overview
This is a Blackjack simulator.

### Topics
* Object-oriented design
* Interfaces
* Enums
* Inheritance and Abstraction
* Java Collections

### Program Function
The program functions through a series of methods. The initial main will call the BlackJack app. This app will immediately start one run through via the start game function. Players are given an initial $1000 purse with which to bet. Any previous hands are wiped via the list clear method call. Players are given an option for the number of decks in the shoe. The shuffle is done via calling collections.shuffle.

Each deck holds 52 cards which are created using enums for both rank and suit. The deck runs through a for loop of rank size - 1. Assigning the current rank as well as the suit to the deck. This is done four times so that every suit is fully represented. The reason it is size minus one is because another enum for rank exists called ACELOW. Ace low is used to adjust the value for ace and therefore not dealt in initially.

Players are then asked for a bet amount. A while loop prevents players from betting more than they have in their purse. Player purse is modified by bet amount. After bets, the players are dealt cards by having the deck deal a card and then add it to the array lists which make up both hands. If the player happens to get two cards of the same rank they are allowed to split (if they have the required funds to make another bet). They will then take two turns, one for each hand. If the splitHand array is empty, the splithand methods will not function.The Blackjack method is then run using both hands. PlayerTurn will run if Blackjack does not terminate startGame early. Player turns consist of a loop which allow then to continuously hit till stand is chosen or they bust. Though they are given the chance to turn Aces low before busting. Dealer turns function the same way but dealer is automated and forced to hit till 17 or more. If dealer hits and goes over 21 but has an ace, that ace will instantly revert to acelow. A displayCard function shows dealer cards, showing only one during the player turn. Dealer busts instantly adjust player purse by bet*2. If neither player busts, the determineWinner method compares the values of two hands and awards accordingly. Both players hands are constantly evaluated before turns and after hits. This is done via a calculateHandValue method that takes the integer values of all the cards ranks that are present in the hand.

After every win/loss/tie the playAgain method is called. The player is told how much money they have and then asked if they would like to play again. A no results in the closing of scanners and termination of the program. (Scanners are also closed when money runs out). If the player chooses yes, the startGame method is called and money passed into it so the players purse remains constant.

### What I learned
For this project I went hard on the methods. This made it easy to do things repeatedly and to make changes on my program. The methods each had a specific function and I would simply modify the method to modify the behavior in every instance of its use. This worked out well, the only issue I had with the methods were the playAgain/startGame method since any change to what was put in startGame required me to change every instance of playAgain to match it. I also learned how great enums are. They are very similar to regular objects but with immutable fields. I couldn't change the enum, but I could change the enum that was located within the cards, that's why I was able to modify Aces.

### Stretch Goals for next time.

I hit a lot of the goals that I wanted for this project, but there were two items I was missing. Dealer Hit on soft 17 did not work for me. I wrote out the logic for a method that I thought would work, but it was a bit buggy. I'd made the mistake of working on soft 17 while I did split hand and I eventually had to put soft 17 on the back burner to get split hands working and did not come back to it in time. The other feature I'd wanted to implement was multiple players. I already had the player purse, I would've extended that to include a name and use that class for different players. My split function running based on the premise if it exists would've been the basis for multiple players. If more than one player was used, extra hands are created for the extra players, and if the extra hands are created the extra turns will run as well. Since I'd used methods for everything, it would not have been too complicated to create new turns as I could copy the existing methods and pass them different hands to use.
