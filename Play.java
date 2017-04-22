import java.util.*;
public class Play extends Player{
 public static Play pl[];
 public Play(String name,int playerId,int hs,Pile usedPile){
   super(name,playerId,hs,usedPile);
 }
 
 
 public static void main(String []args){
 
   Deck dk=new Deck();
   Pile pp=new Pile();
   Player.numberOfPlayers=3;
   pl=new Play[3];
   
   dk.shuffleCards();
   for(int i=0;i<3;i++){
    pl[i]=new Play("Yezy",0,4,pp);
    pl[0].usedDeck=dk;
    pl[i].dealCards();
   }
        
   while(true){
   pl[iterate%numberOfPlayers].showHandCards();
   Scanner in=new Scanner(System.in);
 
   pl[iterate%numberOfPlayers].playCard();
   System.out.println(pl[0].cardsOnHand.size());
   System.out.println(dk.deck.size());
   System.out.println(pp.pile.size());
   }
  }

}
