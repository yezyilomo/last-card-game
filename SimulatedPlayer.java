import java.util.*;

public class SimulatedPlayer extends Player{

  public SimulatedPlayer(String name,int hs,Pile usedPile){
    super(name,hs,usedPile);
  }

public void showHandCards(){
  System.out.println("\n          ************Computers Cards************");
  for(int i=0;i<this.cardsOnHand.size();i++){
    System.out.println("          "+this.cardsOnHand.get(i).getCardName()+","+this.cardsOnHand.get(i).getCardSuit());
  }
  System.out.println("          ************<<<<<>>>>>************");
  if(!usedPile.pile.isEmpty()){
   System.out.println("          On Pile=> "+ usedPile.pile.peek().getCardName()+","+usedPile.pile.peek().getCardSuit());
  }
  else{System.out.println("       On Pile=> Empty");}
   System.out.println("          ************<<<<<>>>>>************");
}



  public boolean pass(){
    if( compareSpecial( usedPile.pile.peek() ) ) return true;
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

  public Card specialCardToPlay(Card cardOnPile){
    for(Card i:this.cardsOnHand){
      if(cardOnPile.getCardName().equals(i.getCardName()) ){
         return i;
      }
    }
    return null;
  }

  public void playCard(){
       if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("7") && semaphore ){

         if( pass() ){ semaphore=false; nextPlayer=nextPlayer+turn;return;}
       }

       if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) ){
         if( pass() ){
         for(Card c:specialCardContainer){
         this.cardsOnHand.push(c);
         }
         specialCardContainer=new Stack<Card>();
         nextPlayer=nextPlayer+turn;
         return;
         }
       }

       if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("8") && semaphore ){

         if( pass() ){
          semaphore=false;
          turn=(-1)*turn;
          nextPlayer=nextPlayer+turn;
          if(nextPlayer==0) {
            if(numberOfPlayers==2) {nextPlayer=0; turn=1; return;}
            else
            nextPlayer=numberOfPlayers-1;
            return;
          }
          else
          if(nextPlayer==-1) {
            if(numberOfPlayers==2){nextPlayer=1; turn=1; return;}
            else
            nextPlayer=numberOfPlayers-2;
            return;
          }
          else
          nextPlayer=nextPlayer+turn;
          return;
          }
       }

     char m='n';
     if(specialCardContainer.isEmpty() && !semaphore){ //For non special cards
       if(usedPile.pile.isEmpty() || compareToPlay( usedPile.pile.peek() ) ){
         m='N';
       }
       else m='D';
     }
     else if(compareSpecial(usedPile.pile.peek())){ //For Special cards
       m='S';
     }
     else m='P';

     if(m=='D'){this.drawCard(); nextPlayer=nextPlayer+turn; return;}
     if(m=='P'){
       if(usedPile.pile.peek().getCardName().equals("2")){
        for(Card c:specialCardContainer){
         this.cardsOnHand.push(c);
         }
         specialCardContainer=new Stack<Card>();
         nextPlayer=nextPlayer+turn;
         return;
       }
       semaphore=false;
       nextPlayer=nextPlayer+turn;
       return;
     }
     else{
        String cardName;
        String cardSuit;
      if(!specialCardContainer.isEmpty() || semaphore){
        if(usedPile.pile.isEmpty()){
         cardName=this.cardsOnHand.get(0).getCardName();
         cardSuit=this.cardsOnHand.get(0).getCardSuit();
        }
        else{
        cardName=specialCardToPlay(usedPile.pile.peek()).getCardName();
        cardSuit=specialCardToPlay(usedPile.pile.peek()).getCardSuit();
        }
      }
      else{
        if(usedPile.pile.isEmpty()){
         cardName=this.cardsOnHand.get(0).getCardName();
         cardSuit=this.cardsOnHand.get(0).getCardSuit();
        }
        else{
        cardName=cardToPlay(usedPile.pile.peek()).getCardName();
        cardSuit=cardToPlay(usedPile.pile.peek()).getCardSuit();
        }
      }

       for(int i=0;i<this.cardsOnHand.size();i++){

       if(this.cardsOnHand.get(i).getCardName().equals(cardName) && this.cardsOnHand.get(i).getCardSuit().equals(cardSuit) ){
         Card card=cardsOnHand.get(i);
         if(usedPile.pile.isEmpty() || card.getCardName().equals(usedPile.pile.peek().getCardName()) || card.getCardSuit().equals(usedPile.pile.peek().getCardSuit()) || card.getCardName().equals("J") || usedPile.pile.peek().getCardName().equals("J") ){

            if( !usedPile.pile.isEmpty() && card.getCardName().equals("7") && !semaphore){
              semaphore=true;
              this.throwCard(card);          //Playing 7 for the first time
              nextPlayer=nextPlayer+turn;
              return;
            }
            else
            if( !usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7") && semaphore ){
               semaphore=false;
               this.penalt(2);           //Responding wrong card instead of 7
               nextPlayer=nextPlayer+turn;
               return;
            }
            else
            if( !usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("7") && card.getCardName().equals("7") && semaphore ){
               //semaphore=false;       we don't set it false until the neturnt Player plays
               this.throwCard(card);    //Responding 7 to
               nextPlayer=nextPlayer+turn;
               return;
            }

            if(!usedPile.pile.isEmpty() && !semaphore && card.getCardName().equals("8") ){
              semaphore=true;
              this.throwCard(card);               //FIRST TO PLAY 8
              nextPlayer=nextPlayer+turn;
              return;
             }
            else
            if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("8") && semaphore && card.getCardName().equals("8") ){
             semaphore=false;
             this.throwCard(card);              //RESPONDING TO PLAYED 8
             nextPlayer=nextPlayer+turn;
             return;
            }
            else
            if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("8") && semaphore && !card.getCardName().equals("8") ){
            semaphore=false;
            this.penalt(2);                    //HAVE'NT PLAYED 8
            turn=(-1)*turn;
            nextPlayer=nextPlayer+turn;
          if(nextPlayer==0) {
             if(numberOfPlayers==2){ nextPlayer=0; turn=1; return;}
            else
             nextPlayer=numberOfPlayers-1;
            return;
          }
          else
          if(nextPlayer==-1) {
             if(numberOfPlayers==2) {nextPlayer=1; turn=1; return;}
            else
             nextPlayer=numberOfPlayers-2;
            return;
          }
          else
             nextPlayer=nextPlayer+turn;
            return;
            }

            if( !usedPile.pile.isEmpty() && card.getCardName().equals("2") && (specialCardContainer.isEmpty() || !specialCardContainer.isEmpty()) ){
              this.throwCard(card);
              this.updateTemp();               //Playing 2 for the first time or respondig to the usedPileayed 2
              nextPlayer=nextPlayer+turn;
              return;
            }
            else
            if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) && !card.getCardName().equals("2") ){
             System.out.println("Ooooops! Wrong card");
             this.penalt(2);
             for(Card c:specialCardContainer){             //Played wrong card instead of 2
             this.cardsOnHand.push(c);    //to be examined later
            }
            specialCardContainer=new Stack<Card>();
            nextPlayer=nextPlayer+turn;
             return;
            }

            this.throwCard(card);
            nextPlayer=nextPlayer+turn;
            return;
          }
         else {
             if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) ){
               for(Card c:specialCardContainer){             //Played wrong card instead of 2
               this.cardsOnHand.push(c);    //to be examined later
              }
              specialCardContainer=new Stack<Card>();
             }

             System.out.println("Ooooops! Wrong card");
             semaphore=false;
             this.penalt(2);
             nextPlayer=nextPlayer+turn;
             return;
           }

         }
       else if((i+1)==this.cardsOnHand.size()){
        if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) ){
           for(Card c:specialCardContainer){             //Played wrong card instead of 2
           this.cardsOnHand.push(c);    //to be examined later
          }
          specialCardContainer=new Stack<Card>();
         }

        System.out.println("Ooooops! Wrong card");
        semaphore=false;
        this.penalt(2);
        nextPlayer=nextPlayer+turn;
        return;
       }
      }

     }
  }

}
