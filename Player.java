import java.util.*;

public class Player{
  protected static int handSize=5;              //default handSize
  public static Deck usedDeck;               //deck of card to be used
  public static Pile pl;                    //pile to be used
  public static int numberOfPlayers=0;     //default number of players
  public static int iterate=0;            //this is for who follows to play
  protected static int x=1;              //used to determine next players turn and to reverse the game
  public static boolean semaphore=false;//used to keep track whether a player has played 8 or 7 card
  public static Stack<Card> temp=new Stack<Card>(); //used to store cards to be passed to next player when the player plays 2
  public final String playerName;     //player name
  public LinkedList<Card> cardsOnHand;  //these are cards on players hand

  public Player(String name,int hs,Pile usedPile){
    this.playerName=name;
    Player.handSize=hs;
    Player.pl=usedPile;
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
    if(!pl.pile.isEmpty()){
     System.out.println("       On Pile=> "+ pl.pile.peek().getCardName()+","+pl.pile.peek().getCardSuit());
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
    temp.push(usedDeck.deck.pop());
    temp.push(usedDeck.deck.pop());
  }

  public void throwCard(Card cd){
    this.cardsOnHand.remove(cd);
    pl.pile.push(cd);
    if(this.cardsOnHand.isEmpty()){
      if(!pl.pile.peek().getCardName().equals("2") && !pl.pile.peek().getCardName().equals("7") && !pl.pile.peek().getCardName().equals("8") && !pl.pile.peek().getCardName().equals("J")){
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

     if(m=='D'){this.drawCard(); iterate=iterate+x;}
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

        semaphore=false;
        this.penalt(2);
        iterate=iterate+x;
        return;
       }
      }

     }
  }

}
