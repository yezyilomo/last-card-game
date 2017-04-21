public class Card{
 private final int CARD_RANK;
 private final String CARD_NAME;
 private final String CARD_SUIT;
 private final String CARD_COLOR;
 
 public Card(int CARD_RANK,String CARD_SUIT){
    this.CARD_RANK=CARD_RANK;
    this.CARD_SUIT=CARD_SUIT;
    
    if(CARD_RANK==1){this.CARD_NAME="A";}
    else
    if(CARD_RANK==11){this.CARD_NAME="J";}
    else
    if(CARD_RANK==12){this.CARD_NAME="K";}
    else
    if(CARD_RANK==13){this.CARD_NAME="Q";}
    else    
    this.CARD_NAME=Integer.toString(CARD_RANK);
    
    if(CARD_SUIT.equals("HEART") || CARD_SUIT.equals("DIAMOND")){
     this.CARD_COLOR="RED";
    }
    else{
      this.CARD_COLOR="BLACK";
    }

  }
  
  public int getCardRank(){
    return this.CARD_RANK;
  }
  
  public String getCardName(){
    return this.CARD_NAME;
  }

    public String getCardSuit(){
    return this.CARD_SUIT;
  }

    public String getCardColor(){
    return this.CARD_COLOR;
}
 
}
