import java.util.*;

public class ComputerPlayer extends Player{

  public ComputerPlayer(String name,int playerId,int hs,Pile usedPile){
    super(name,playerId,hs,usedPile);
  }

/*
  public void showHandCards(){
    System.out.println("               ************Computer Cards************");
    for(int i=0;i<this.cardsOnHand.size();i++){
      System.out.println("               "+this.cardsOnHand.get(i).getCardName()+","+this.cardsOnHand.get(i).getCardSuit());
    }
    System.out.println("               ************<<<<< "+this.playerId+" >>>>>************");
    if(!pl.pile.isEmpty()){
     System.out.println("               On Pile=> "+ pl.pile.peek().getCardName()+","+pl.pile.peek().getCardSuit());
    }
    else{System.out.println("               On Pile=> Empty");}
     System.out.println("               ************<<<<<>>>>>************");
  }

  */

  public boolean pass(){
    char k;
    if( compareSpecial( pl.pile.peek() ) ) return true;
    return false;
  }

  public boolean compareSpecial(Card cardOnPile){
    for(Card i:this.cardsOnHand){
      if(cardOnPile.getCardName().equals(i.getCardName()) ){
         return true;
      }
    }
    return false;
  }

  public boolean compareToPlay(Card cardOnPile){
    for(Card i:this.cardsOnHand){
      if(cardOnPile.getCardName().equals(i.getCardName()) || cardOnPile.getCardSuit().equals(i.getCardSuit()) ){
         return true;
      }
    }
    return false;
  }

  public Card cardToPlay(Card cardOnPile){
    for(Card i:this.cardsOnHand){
      if(cardOnPile.getCardName().equals(i.getCardName()) || cardOnPile.getCardSuit().equals(i.getCardSuit()) ){
         return i;
      }
    }
    return null;
  }

  public void playCard(){
       if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && semaphore ){

         if( pass() ){ semaphore=false; iterate=iterate+x;return;}
       }

       if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) ){
         if( pass() ){
         for(Card c:temp){
         this.cardsOnHand.push(c);
         }
         temp=new Stack<Card>();
         iterate=iterate+x;
         return;
         }
       }

       if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("8") && semaphore ){

         if( pass() ){
          semaphore=false;
          x=(-1)*x;
          iterate=iterate+x;
          if(iterate==0) {
            if(numberOfPlayers==2) {iterate=0; x=1; return;}
            else
            iterate=numberOfPlayers-1;
            return;
          }
          else
          if(iterate==-1) {
            if(numberOfPlayers==2){iterate=1; x=1; return;}
            else
            iterate=numberOfPlayers-2;
            return;
          }
          else
          iterate=iterate+x;
          return;
          }
       }

     char m='n';
     if(temp.isEmpty() && !semaphore){
       if(compareToPlay( pl.pile.peek() ) ){
         m='C';
       }
       else m='D';
     }

     if(m=='D'){this.drawCard(); iterate=iterate+x;}
     else{
      String cardName=cardToPlay(pl.pile.peek()).getCardName();
      String cardSuit=cardToPlay(pl.pile.peek()).getCardSuit();

       for(int i=0;i<this.cardsOnHand.size();i++){

       if(this.cardsOnHand.get(i).getCardName().equals(cardName) && this.cardsOnHand.get(i).getCardSuit().equals(cardSuit) ){
         Card card=cardsOnHand.get(i);
         if(pl.pile.isEmpty() || card.getCardName().equals(pl.pile.peek().getCardName()) || card.getCardSuit().equals(pl.pile.peek().getCardSuit()) || card.getCardName().equals("J") || pl.pile.peek().getCardName().equals("J") ){

            if( !pl.pile.isEmpty() && card.getCardName().equals("7") && !semaphore){
              semaphore=true;
              this.throwCard(card);          //Playing 7 for the first time
              iterate=iterate+x;
              return;
            }
            else
            if( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7") && semaphore ){
               semaphore=false;
               this.penalt(2);           //Responding wrong card instead of 7
               iterate=iterate+x;
               return;
            }
            else
            if( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && card.getCardName().equals("7") && semaphore ){
               //semaphore=false;       we don't set it false until the next player plays
               this.throwCard(card);    //Responding 7 to
               iterate=iterate+x;
               return;
            }

            if(!pl.pile.isEmpty() && !semaphore && card.getCardName().equals("8") ){
              semaphore=true;
              this.throwCard(card);               //FIRST TO PLAY 8
              iterate=iterate+x;
              return;
             }
            else
            if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("8") && semaphore && card.getCardName().equals("8") ){
             semaphore=false;
             this.throwCard(card);              //RESPONDING TO PLAYED 8
             iterate=iterate+x;
             return;
            }
            else
            if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("8") && semaphore && !card.getCardName().equals("8") ){
            semaphore=false;
            this.penalt(2);                    //HAVE'NT PLAYED 8
            x=(-1)*x;
            iterate=iterate+x;
          if(iterate==0) {
             if(numberOfPlayers==2){ iterate=0; x=1; return;}
            else
             iterate=numberOfPlayers-1;
            return;
          }
          else
          if(iterate==-1) {
             if(numberOfPlayers==2) {iterate=1; x=1; return;}
            else
             iterate=numberOfPlayers-2;
            return;
          }
          else
             iterate=iterate+x;
            return;
            }

            if( !pl.pile.isEmpty() && card.getCardName().equals("2") && (temp.isEmpty() || !temp.isEmpty()) ){
              this.throwCard(card);
              this.updateTemp();               //Playing 2 for the first time or respondig to the played 2
              iterate=iterate+x;
              return;
            }
            else
            if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) && !card.getCardName().equals("2") ){
             System.out.println("Ooooops! Wrong card");
             this.penalt(2);
             for(Card c:temp){             //Played wrong card instead of 2
             this.cardsOnHand.push(c);    //to be examined later
            }
            temp=new Stack<Card>();
            iterate=iterate+x;
             return;
            }

            this.throwCard(card);
            iterate=iterate+x;
            return;
          }
         else {
             if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) ){
               for(Card c:temp){             //Played wrong card instead of 2
               this.cardsOnHand.push(c);    //to be examined later
              }
              temp=new Stack<Card>();
             }

             System.out.println("Ooooops! Wrong card");
             semaphore=false;
             this.penalt(2);
             iterate=iterate+x;
             return;
           }

         }
       else if((i+1)==this.cardsOnHand.size()){
        if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) ){
           for(Card c:temp){             //Played wrong card instead of 2
           this.cardsOnHand.push(c);    //to be examined later
          }
          temp=new Stack<Card>();
         }

        System.out.println("Ooooops! Wrong card");
        semaphore=false;
        this.penalt(2);
        iterate=iterate+x;
        return;
       }
      }

     }
  }

}
