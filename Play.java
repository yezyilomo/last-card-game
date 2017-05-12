import java.util.*;

public class Play {
 public static Player listOfPlayers[];

 public static void main(String []args){

   DeckWithoutJokers deck=new DeckWithoutJokers();
   Pile pile=new Pile();
   Player.numberOfPlayers=2;
   listOfPlayers=new Player[Player.numberOfPlayers];

   deck.shuffleCards();
   for(int i=0;i<Player.numberOfPlayers;i++){
     if(i==0)
    listOfPlayers[i]=new Player("Yezy",4,pile);
     else listOfPlayers[i]=new SimulatedPlayer("Ilomo",4,pile);
    listOfPlayers[0].usedDeck=deck;
    listOfPlayers[i].dealCards();
   }

   while(true){
   Player.nextPlayer=Player.nextPlayer%Player.numberOfPlayers;
   listOfPlayers[Player.nextPlayer].showHandCards();
   listOfPlayers[Player.nextPlayer].playCard();
   }
  }

}
