import java.util.*;

public class Deck{
  public static Stack<Card> deck;
  public Deck(){
    
     deck=new Stack<Card>();
     
     for(int i=1;i<=13;i++){
       for(int j=1;j<=4;j++){
          deck.push(new Card(i,setSuit(j)));
       }
     }
  }
  
  public String setSuit(int i){
  
     if(i==1) return "DIAMOND";
     if(i==2) return "HEART";
     if(i==3) return "SPADE";
     if(i==4) return "CLUB";
     
     return "None";
  }
  
  public static void shuffleCards(){
    new Deck(); 
    Card cardArray[]=new Card[52];
    Random rand=new Random();
    
    for(int i=1;i<=52;i++){
      
      int test=rand.nextInt(52);
      while(cardArray[test]!=null){
          test=rand.nextInt(52);
      }
      cardArray[test]=deck.pop();
    }
    
    for(int i=0;i<52;i++){
     deck.push(cardArray[i]);
    }    
  }
  
}
