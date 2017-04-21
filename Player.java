import java.util.*;
public class Player{
  public static final int handSize=6;
  public static final int numberOfPlayers=4;
  public final int playerId;
  public final String playerName;
  public LinkedList<Card> cardsOnHand;
  
  public Player(String name,int playerId){
    this.playerName=name;
    this.playerId=playerId;
  }
  
  public void dealCards(Deck usedDeck){
    this.cardsOnHand=new LinkedList<Card>();
    for(int i=0;i<handSize;i++){
    this.cardsOnHand.add(drawCard(usedDeck));
    }
  }
  
  public Card drawCard(Deck usedDeck){
    Card drawn=usedDeck.deck.pop();
    this.cardsOnHand.add(drawn);
    return drawn;
  }
  
  public void playCard(String cardName,String cardSuit,Pile pile){
     
    for(int i=0;i<pl.cardsOnHand.size();i++){
     if(this.cardsOnHand.get(i).getCardName().equals(cardName) && this.cardsOnHand.get(i).getCardSuit().equals(cardSuit) ){
         Card card=cardsOnHand.get(i)
         this.cardsOnHand.remove(card);
         pile.push(card);
     }
   }


  }
  
  public void specialCard(Card special){
    if(special.getRank()==2){
      nextTurn(2)
    }
  }
  
  public static void main(String []args){
   Deck dk=new Deck();
   dk.shuffleCards();
   Player pl=new Player("Yezy",0);
   
   pl.dealCards(dk);
   
   for(int i=0;i<pl.cardsOnHand.size();i++){
     System.out.println(pl.cardsOnHand.get(i).getCardName());
   }
   
   Pile pp=new Pile();
   pp.pile.push(pl.cardsOnHand.remove(0));
   pp.pile.push(pl.cardsOnHand.remove(0));
   pp.pile.push(pl.cardsOnHand.remove(0));
   pp.pile.push(pl.cardsOnHand.remove(0));
   
   pp.shufflePileCards(dk);

   System.out.println(pl.cardsOnHand.size());
   System.out.println(dk.deck.size());
   System.out.println(pp.pile.size());
  }
}
