import java.util.*;

public class Play extends Player{
 public static Play pl[];
 public Play(String name,int playerId,int hs,Pile usedPile){
   super(name,playerId,hs,usedPile);
 }

 public static void main(String []args){

   Deck dk=new Deck();
   Pile pp=new Pile();
   Player.numberOfPlayers=2;
   pl=new Play[Player.numberOfPlayers];

   dk.shuffleCards();
   for(int i=0;i<Player.numberOfPlayers;i++){
    pl[i]=new Play("Yezy",i,4,pp);
    pl[0].usedDeck=dk;
    pl[i].dealCards();
   }

   while(true){
     iterate=iterate%numberOfPlayers;
   pl[iterate].showHandCards();

   pl[iterate].playCard();
    System.out.println("next Player is :" +iterate);
   }
  }

}
