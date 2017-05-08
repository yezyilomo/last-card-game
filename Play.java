import java.util.*;

public class Play {
 public static Player pl[];

 public static void main(String []args){

   Deck dk=new Deck();
   Pile pp=new Pile();
   Player.numberOfPlayers=2;
   pl=new Player[Player.numberOfPlayers];

   dk.shuffleCards();
   for(int i=0;i<Player.numberOfPlayers;i++){
     if(i==0)
    pl[i]=new Player("Yezy",i,4,pp);
     else pl[i]=new ComputerPlayer("Ilomo",i,4,pp);
    pl[0].usedDeck=dk;
    pl[i].dealCards();
   }

   while(true){
   Player.iterate=Player.iterate%Player.numberOfPlayers;
   pl[Player.iterate].showHandCards();
   pl[Player.iterate].playCard();
   }
  }

}
