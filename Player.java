import java.util.*;
public abstract class Player{
  private static int handSize=5; //default handSize
  public static Deck usedDeck;   //deck of card to be used
  public static Pile pl;        // pile to be used
  public static int numberOfPlayers=0;
  public static int iterate=0;
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
    System.out.println("************<<<<<>>>>>************");
    if(!pl.pile.isEmpty()){
    System.out.println("On Pile=> "+ pl.pile.peek().getCardName()+","+pl.pile.peek().getCardSuit());
    }
    else{System.out.println("On Pile=> Empty");}
    System.out.println("************<<<<<>>>>>************");
  }
  
  public void playCard(){
        
       if(!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") ){
         System.out.println("Enter p to pass or c to respond");
         Scanner input=new Scanner(System.in);
         char s=input.next().charAt(0);
         while(s!='p' && s!='c'){
         System.out.println("Invalid input \nEnter p to pass or c to respond");
         input=new Scanner(System.in);
         s=input.next().charAt(0);
         }
         
         if(s=='p'){iterate++;return;}
       }
       
       if(!pl.pile.isEmpty() &&pl.pile.peek().getCardName().equals("8") ){
         System.out.println("Enter p to pass or c to respond");
         Scanner input=new Scanner(System.in);
         char l=input.next().charAt(0);
         while(l!='p' && l!='c'){
         System.out.println("Invalid input \nEnter  p to pass or c to respond");
         input=new Scanner(System.in);
         l=input.next().charAt(0);
         }
         
         if(l=='p'){
          iterate=(-1)*iterate;
          iterate++;
          return;
          }
       }
     
     System.out.println("Enter d to drawCard or c to continue");
     Scanner in=new Scanner(System.in);
     char m=in.next().charAt(0);
     while(m!='d' && m!='c'){
         System.out.println("Invalid input \nEnter d to drawCard or c to continue");
         in=new Scanner(System.in);
         m=in.next().charAt(0);
     }  
     
     if(m=='d'){this.drawCard(); iterate++;}
     else{
      System.out.println("CardName");
      String cardName=in.next();
      System.out.println("CardSuit");
      String cardSuit=in.next();
   
     
       for(int i=0;i<this.cardsOnHand.size();i++){
     
       if(this.cardsOnHand.get(i).getCardName().equals(cardName) && this.cardsOnHand.get(i).getCardSuit().equals(cardSuit) ){
         Card card=cardsOnHand.get(i);
         if(pl.pile.isEmpty() || card.getCardName().equals(pl.pile.peek().getCardName()) || card.getCardSuit().equals(pl.pile.peek().getCardSuit()) || card.getCardName().equals("J") || pl.pile.peek().getCardName().equals("J") ){
         
            if((!pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("2") && !temp.isEmpty()) || card.getCardName().equals("2") ){ //**2
                if(card.getCardName().equals("2")){
                 for(int h=0;h<2;h++){
                  temp.push( usedDeck.deck.pop() );
                  }
                  this.cardsOnHand.remove(card);
                  pl.pile.push(card);
                  iterate++;
                  return;
                }
                else{
                  this.penalt(2);
                  int j=temp.size();
                  for(int p=0;p<j;p++ ){
                    this.cardsOnHand.add(temp.pop());
                  }
                  iterate++;
                  return;
                }
            }                          //**2
            
            if(( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7")) || ( !pl.pile.isEmpty() && pl.pile.peek().getCardName().equals("7") && !card.getCardName().equals("7") )){
               this.penalt(2);
               iterate++;
               return;
            }
            
                        
            this.cardsOnHand.remove(card);
            pl.pile.push(card);
            iterate++;
            return;
          }
         else {
             this.penalt(2);   
             iterate++;
             return;
           }
         
         }
       else if((i+1)==this.cardsOnHand.size()){
        this.penalt(2);
        iterate++;
        return;
       }
      }
     
     }  
  }
  
 
 public void penalt(int x){
   for(int i=0;i<x;i++){
     this.drawCard();
   }
 }  
 /* public static void specialCard(Card special){
    if(special.getRank()==2){
      nextTurn(2)
    }
  }
  */
  
}
