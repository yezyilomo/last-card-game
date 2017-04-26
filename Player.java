import java.util.*;
public abstract class Player{
  private static int handSize=5; //default handSize
  public static Deck usedDeck;   //deck of card to be used
  public static Pile pl;        // pile to be used
  public static int numberOfPlayers=0;
  public static int iterate=0;
  private static int x=1;
  public static boolean semaphore=false;
  public static Stack<Card> temp=new Stack<Card>();

  public final int playerId;
  public final String playerName;
  public LinkedList<Card> cardsOnHand;

  public Player(String name,int playerId,int hs,Pile usedPile){
    this.playerName=name;
    this.playerId=playerId;
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
    Card drawn=usedDeck.deck.pop();
    this.cardsOnHand.add(drawn);
    return drawn;
  }

  public void showHandCards(){
    System.out.println("************Your Cards************");
    for(int i=0;i<this.cardsOnHand.size();i++){
      System.out.println(this.cardsOnHand.get(i).getCardName()+","+this.cardsOnHand.get(i).getCardSuit());
    }
    System.out.println("************<<<<< "+this.playerId+" >>>>>************");
    if(!pl.pile.isEmpty()){
    System.out.println("On Pile=> "+ pl.pile.peek().getCardName()+","+pl.pile.peek().getCardSuit());
    }
    else{System.out.println("On Pile=> Empty");}
    System.out.println("************<<<<<>>>>>************");
  }

  public boolean passOrPlay(){
    System.out.println("Enter P to pass or C to respond");
    Scanner input=new Scanner(System.in);
    char k=input.next().charAt(0);
    while(k!='P' && k!='C'){
    System.out.println("Invalid input \nEnter P to pass or C to respond");
    input=new Scanner(System.in);
    k=input.next().charAt(0);}

    if(k=='P') return true;
    return false;
  }

  public void playCard(){
       if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") ){

         if( passOrPlay() ){iterate=iterate+x;return;}
       }

       if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) ){
         if( passOrPlay() ){
         for(Card c:temp){
         this.cardsOnHand.push(c);
         }
         temp=new Stack<Card>();
         iterate=iterate+x;
         return;
         }
         }


       if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("8") && semaphore ){

         if( passOrPlay() ){
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
     if(temp.isEmpty() && !semaphore && temp.isEmpty()){
     System.out.println("Enter D to drawCard or C to continue");
     Scanner in=new Scanner(System.in);
     m=in.next().charAt(0);
     while(m!='D' && m!='C'){
         System.out.println("Invalid input \nEnter C to drawCard or C to continue");
         in=new Scanner(System.in);
         m=in.next().charAt(0);
     } }

     if(m=='D'){this.drawCard(); iterate=iterate+x;}
     else{
      Scanner in=new Scanner(System.in);
      System.out.println("CardName");
      String cardName=in.next();
      System.out.println("CardSuit");
      String cardSuit=in.next();

       for(int i=0;i<this.cardsOnHand.size();i++){

       if(this.cardsOnHand.get(i).getCardName().equals(cardName) && this.cardsOnHand.get(i).getCardSuit().equals(cardSuit) ){
         Card card=cardsOnHand.get(i);
         if(pl.pile.isEmpty() || card.getCardName().equals(pl.pile.peek().getCardName()) || card.getCardSuit().equals(pl.pile.peek().getCardSuit()) || card.getCardName().equals("J") || pl.pile.peek().getCardName().equals("J") ){

            if(( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7")) || ( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7") )){
               this.penalt(2);
               iterate=iterate+x;
               return;
            }

            if(!pl.pile.isEmpty() && !semaphore && card.getCardName().equals("8") ){
              semaphore=true;
              this.cardsOnHand.remove(card);   //FIRST TO PLAY 8
              pl.pile.push(card);
              iterate=iterate+x;
              return;
             }
            else
            if(!pl.pile.isEmpty() && semaphore && card.getCardName().equals("8") ){
             semaphore=false;
             this.cardsOnHand.remove(card);
             pl.pile.push(card);              //RESPONDING TO PLAYED 8
             iterate=iterate+x;
             return;
            }
            else
            if(!pl.pile.isEmpty() && semaphore && !card.getCardName().equals("8") ){
            semaphore=false;
            this.penalt(2);                  //HAVE'NT PLAYED 8
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

            if( !pl.pile.isEmpty() && card.getCardName().equals("2") && temp.isEmpty() ){
              this.cardsOnHand.remove(card);
              pl.pile.push(card);

              temp.push(usedDeck.deck.pop());
              temp.push(usedDeck.deck.pop());

              iterate=iterate+x;
              return;
            }
            else
            if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) && !card.getCardName().equals("2") ){
             System.out.println("Ooooops! Wrong card");
             this.penalt(2);
             for(Card c:temp){
             this.cardsOnHand.push(c);
            }
            temp=new Stack<Card>();
            iterate=iterate+x;
             return;
            }

            this.cardsOnHand.remove(card);
            pl.pile.push(card);
            iterate=iterate+x;
            return;
          }
         else {
             System.out.println("Ooooops! Wrong card");
             this.penalt(2);
             iterate=iterate+x;
             return;
           }

         }
       else if((i+1)==this.cardsOnHand.size()){
        System.out.println("Ooooops! Wrong card");
        this.penalt(2);
        iterate=iterate+x;
        return;
       }
      }

     }
  }


 public void penalt(int pv){
   for(int i=0;i<pv;i++){
     this.drawCard();
   }
 }


}
