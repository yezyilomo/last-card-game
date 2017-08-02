![Alt text](https://github.com/yezyilomo/Last-Card-Game/blob/master/Program_Look2.png?raw=true "Optional Title")

# Project Scenario
Boredom is situation that actually meets anyone at a certain point, it may either be for a short time, in a while or lifetime. The most affected people are those in situations such as admitted patients in hospital for certain treatments, disable people, older people and many other stigmatized people in the society, yet many of them have access to digital devices such as PCs, smart phones etc.To reduce the boredom state there is a need that a person should recreate.
In our own decision we decide the best way to solve the boredom problem is by playing LAST CARD game.

Last card is a popular card game played in Tanzania, especially in schools, public venues, at home and working places, So it is not just some kind of fancy name for a card game but it is a big hit. The game has a set of rules that are to be followed and these are similar to some other rules in games like Uno and Mau Mau though there are other several rules that differentiate it from the these games. Since the game uses a deck of card, it's appropriate to illustrate the structure of deck of card first before explaining the rules of the game

**Features of a Standard Deck of Cards

The deck of card that is being described by the name "standard deck" has a number of important features to be pointed out. The main items that are necessary to know for our scenario are the following

There are a total of 52 cards in a deck( sometimes 54, including jokers depending on the rules of a game )

There are 13 ranks of cards. These ranks include the numbers 2 through 10, jack, queen, king and ace. This ordering of the rank is called “ace high.”

In some situations ace ranks above king (ace high). In other situations the ace ranks below the 2 (ace low). Sometimes an ace can be both high and low.

There are four suits: hearts, diamonds, spades and clubs. Thus there are 13 hearts, 13 diamonds, 13 spades and 13 clubs.

The diamonds and hearts are printed in red. The spades and clubs are printed in black. So there are 26 red cards and 26 black cards.

Each rank has four cards in it (one for each of the four suits). This means there are four nines, four tens and so on.

The jacks, queens and kings are all considered face cards. Thus there are three face cards for each suit and a total of 12 face cards in the deck.

In addition to that there are two jokers, one red and one black, this makes 54 cards in a deck.

Cards can be shuffled, Shuffling is a procedure used to randomize a deck of playing cards to provide an element of chance in card games

**The basics of Last Card game

Last card is played with the normal 52 cards and the first person to play all the cards is the winner. It has some cards that are for special functions in the game, the 2, the 7, the 8, the J and Joker(if included). The cards are shuffled and each player receives an equal number of cards ranging from 4 to 8 cards. The game begins with any card by one of the players selected randomly.

**Rules of the game

The game is played by 2-4 players.

Each player gets 4-8 cards at the start of the game.

The objective is to be the first to get rid of all your cards by putting them onto the pile.

You can put down a card if it has the same suit or rank as the top card of the discard pile. E.g. if the card of the pile is a 5 of spades then you can play any spade or any 5.

If you have no cards you can play then you must drag one card from the stock and don't get to do anything else during that turn.

If you have two or more cards of the same rank then you can play them together. E.g if the card on top of the pile is a diamond and you have a four of diamonds and a four of spades then you can play them together, thereby changing the suit of the pile to spades.

To make the game more interesting there are a number of cards that are special:

2: If a player plays a two, then the next player must draw two cards and cannot play any cards, UNLESS he has a two as well in which case he can play it and the next player him must draw 4 cards. This can go on as long as players have two's, and increases by two each time, e.g. if three players have played two's in a row then the fourth player must pick up six cards.

7: When 7 is played one next player misses his turn if he has no seven too and the game continues as normal to the next player from the one missed the turn, if the next player plays 7 too then the condition goes to the player next to him and so on. Playing two or more 7s together causes multiple next players to miss their turn if they have no 7 too.

8: Reverses the direction of the game. E.g. if the game was going clockwise then it will start to go counter-clockwise and will continue like that until another 8 is played.

Joker: When a player plays a Joker the next player must draw 5 cards. Two Jokers can be played together, in which case the next player must draw 10 cards. If player 1 plays a Joker, then player 2 may also play a Joker if he has it and then player 3 will have to draw 10 cards.

Jack: Jack can be played no matter what suit is on the table, and when a player plays an Ace he gets to decide what suit the table changes to.

Endgame When a player plays his last card he wins the game. The one exception is if his last card is 2,7,8, Jack or Joker if it is then he plays that card and has to draw a new card in his next turn to win the game, and so is not finished. 2,7,8, Jack and Joker can never be the last card. When playing this game in real life the player must also call out "last card" when he has only one card left,

There are many, many variations of this game, but the rules above are the ones that we are going to implement for this project and they are actually common in Tanzania.


**Observed Objects in this problem are

1.Card Object
     **Attributes in this object are
       i. cardRank
       ii. cardName
       iii. cardSuit
       iv. cardColor

2.Deck Object
     **Attributes in this object are
       i. numberOfCards
       ii. deck- a container for the cards

     **Methods in this object are
       i. shuffle Cards- this is for mixing up cards to make it difficult to predict the structure of cards on deck

3.Player Object
     **Attributes in this object are
       i. player Name
       ii. cards On Hand
       iii. handSize
       iv. usedDeck
       v. usedPile
       vi. numberOfPlayers
       vii. nextPlayer
       viii. turn
       ix. semaphore
       x. specialCardContainer

     **Methods in this object are
       i. deal cards- this will be used to provide cards on hand to players
       ii. play Card
       iii. draw Card From deck
       iv. Handle penalty - this will be used in case the player has played wrong card
       v. Handle special cards- this will be used to control what to be done in case the player plays a special card eg 2,7,8,J or Joker
       vi. nextTurn- this will be used to determine who follows to play in the next turn

4.Pile Object
     **Attributes in this object are
       ii. pile- a container for the cards on pile

     **Methods in this object are
       i. shuffle Cards on a Pile- this will be used in case cards on deck have all been drawn up but the game isn't over

5.SimulatedPleyer Object
     **Attributes in this object are
       i.all attributes from class player

     **Methods in this Object are
       i. compareSpecial
       ii. compareToPlay
       iii. cardToPlay
       iv. specialCardToPlay
