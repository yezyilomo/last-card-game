package sample.game;

public class Card{
 private int cardRank;
 private String cardName;
 private String cardSuit;
 private String cardColor;

 public Card(int cardRank,String cardSuit){
    this.cardRank=cardRank;
    this.cardSuit=cardSuit;

    if(cardRank==1){this.cardName="A";}
    else
    if(cardRank==11){this.cardName="J";}
    else
    if(cardRank==12){this.cardName="K";}
    else
    if(cardRank==13){this.cardName="Q";}
    else
    if(cardRank==14){this.cardName="JOKER"; this.cardColor="RED";}
    else
    if(cardRank==15){this.cardName="JOKER"; this.cardColor="BLACK";}
    else
    this.cardName=Integer.toString(cardRank);

    if(cardSuit.equals("HEARTS") || cardSuit.equals("DIAMONDS")){
     this.cardColor="RED";
    }
    else
    if(cardSuit.equals("CLUBS") || cardSuit.equals("SPADES")){
      this.cardColor="BLACK";
    }
  }

  public int getCardRank(){
    return this.cardRank;
  }

  public String getCardName(){
    return this.cardName;
  }

    public String getCardSuit(){
    return this.cardSuit;
  }

    public String getCardColor(){
    return this.cardColor;
}

}
