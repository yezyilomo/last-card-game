import java.util.*;

public class Pile{
 public static Stack<Card> pile;

 public Pile(){
  pile=new Stack<Card>();
 }

 public static void shufflePileCards(DeckWithoutJokers usedDeck){
    Card top=pile.pop();
    usedDeck.deck=pile;
    pile=new Stack<Card>();
    pile.push(top);

    Card cardArray[]=new Card[pile.size()];
    Random rand=new Random();

    for(int i=1;i<=pile.size();i++){

      int test=rand.nextInt(pile.size());
      while(cardArray[test]!=null){
          test=rand.nextInt(pile.size());
      }
      cardArray[test]=usedDeck.deck.pop();
    }

    for(int i=0;i<pile.size();i++){
     usedDeck.deck.push(cardArray[i]);
    }
 }
}
