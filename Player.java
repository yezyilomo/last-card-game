import java.util.*;

public class Player{
  protected static int handSize=5;            //default handSize
  public static DeckWithoutJokers usedDeck;   //deck of card to be used
  public static Pile usedPile;                //pile to be used
  public static int numberOfPlayers=0;        //default number of players
  public static int nextPlayer=0;             //this is for determining who follows to play
  protected static int turn=1;                //used to turn the game clockwise or anticlockwise
  public static boolean semaphore=false;      //used to keep track whether a player has played 8 or 7 card
  public static Stack<Card> specialCardContainer=new Stack<Card>(); //used to store cards to be passed to next player when the player plays 2
  public final String playerName;             //used to store player name
  public LinkedList<Card> cardsOnHand;        //these are cards on players hand

  public Player(String name,int hs,Pile usedPile){
    this.playerName=name;
    Player.handSize=hs;
    Player.usedPile=usedPile;
  }

  public void dealCards(){
    this.cardsOnHand=new LinkedList<Card>();
    for(int i=0;i<Player.handSize;i++){
    this.drawCard();
    }
  }

  public Card drawCard(){
    if(this.usedDeck.deck.isEmpty()){
      Pile.shufflePileCards(usedDeck);
    }
    Card drawn=usedDeck.deck.pop();
    this.cardsOnHand.add(drawn);
    return drawn;
  }

  public void showHandCards(){
    System.out.println("\n************Your Cards************");
    for(int i=0;i<this.cardsOnHand.size();i++){
      System.out.println(this.cardsOnHand.get(i).getCardName()+","+this.cardsOnHand.get(i).getCardSuit());
    }
    System.out.println("************<<<<<>>>>>************");
    if(!usedPile.pile.isEmpty()){
     System.out.println("       On Pile=> "+ usedPile.pile.peek().getCardName()+","+usedPile.pile.peek().getCardSuit());
    }
    else{System.out.println("       On Pile=> Empty");}
     System.out.println("************<<<<<>>>>>************");
  }

  public boolean pass(){
    System.out.println("Enter P to pass or C to respond");
    Scanner input=new Scanner(System.in);
    char k=input.next().charAt(0);
    k=Character.toUpperCase(k);
    while(k!='P' && k!='C'){
    System.out.println("Invalid input \nEnter P to pass or C to respond");
    input=new Scanner(System.in);
    k=input.next().charAt(0);
    k=Character.toUpperCase(k);
    }

    if(k=='P') return true;
    return false;
  }

  public void updateTemp(){
    if(this.usedDeck.deck.size()<2){
      Pile.shufflePileCards(usedDeck);
    }
    specialCardContainer.push(usedDeck.deck.pop());
    specialCardContainer.push(usedDeck.deck.pop());
  }

  public void throwCard(Card cd){
    this.cardsOnHand.remove(cd);
    usedPile.pile.push(cd);
    if(this.cardsOnHand.isEmpty()){
      if(!usedPile.pile.peek().getCardName().equals("2") && !usedPile.pile.peek().getCardName().equals("7") && !usedPile.pile.peek().getCardName().equals("8") && !usedPile.pile.peek().getCardName().equals("J")){
        System.out.println("\nCongratulation "+this.playerName+" You have won\n");
        System.exit(0);
      }
    }
  }

  public void penalt(int pv){
    System.out.println("\nOoooops! Wrong card");
    for(int i=0;i<pv;i++){
      this.drawCard();
    }
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
     if(specialCardContainer.isEmpty() && !semaphore){
     System.out.println("Enter D to drawCard or C to continue");
     Scanner in=new Scanner(System.in);
     m=in.next().charAt(0);
     m=Character.toUpperCase(m);
     while(m!='D' && m!='C'){
         System.out.println("Invalid input \nEnter D to drawCard or C to continue");
         in=new Scanner(System.in);
         m=in.next().charAt(0);
         m=Character.toUpperCase(m);
     } }

     if(m=='D'){this.drawCard(); nextPlayer=nextPlayer+turn;}
     else{
      Scanner in=new Scanner(System.in);
      System.out.println("CardName");
      String cardName=in.next();
      cardName=cardName.toUpperCase();
      System.out.println("CardSuit");
      String cardSuit=in.next();
      cardSuit=cardSuit.toUpperCase();

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
               this.throwCard(card);    //Responding to played 7
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
              this.updateTemp();               //Playing 2 for the first time or respondig to the played 2
              nextPlayer=nextPlayer+turn;
              return;
            }
            else
            if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) && !card.getCardName().equals("2") ){
             this.penalt(2);
             for(Card c:specialCardContainer){        //Played wrong card instead of 2
             this.cardsOnHand.push(c);               //to be examined later
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
               this.cardsOnHand.push(c);                    //to be examined later
              }
              specialCardContainer=new Stack<Card>();
             }

             semaphore=false;
             this.penalt(2);
             nextPlayer=nextPlayer+turn;
             return;
           }

         }
       else if((i+1)==this.cardsOnHand.size()){
        if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) ){
           for(Card c:specialCardContainer){             //Played wrong card instead of 2
           this.cardsOnHand.push(c);                     //to be examined later
          }
          specialCardContainer=new Stack<Card>();
         }

        semaphore=false;
        this.penalt(2);
        nextPlayer=nextPlayer+turn;
        return;
       }
      }

     }
  }

}
