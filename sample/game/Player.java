package sample.game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.EventControl;
import javafx.scene.control.Label;
import sample.Main;
import sample.Play;

import java.awt.Color;

import java.io.FileInputStream;
import java.util.*;
import static sample.Main.myScene;
import static sample.Main.myStage;

public class Player {
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

  public void showHandCards() throws Exception{

       VBox root=new VBox((myScene.getHeight()-260)/3);
       root.setAlignment(Pos.TOP_LEFT);
       HBox hand=new HBox(5);
       root.getChildren().add(hand);

      root.setStyle("-fx-background-color: linear-gradient(to bottom right,red, #121212)");
      //root.setStyle("-fx-background-color: #121212;");

    for(int i=0;i<this.cardsOnHand.size();i++){

        String card=this.cardsOnHand.get(i).getCardName().toLowerCase()+"_of_"+this.cardsOnHand.get(i).getCardSuit().toLowerCase()+".png";

        Image img=new Image(new FileInputStream("/home/yezy/IdeaProjects/CardGame/src/sample/game/CardsPics/Normal/Playing Cards/PNG-cards-1.3/"+card));
        ImageView vw=new ImageView(img);
        //vw.setRotate(20);
        vw.setId("card");
        vw.setTranslateY(5*i+10);
        vw.setTranslateX((-70*i)+10);
        vw.setFitHeight(150);
        vw.setFitWidth(100);
        vw.setOnMouseClicked(new EventControl(this.cardsOnHand.get(i),this));
        hand.getChildren().add(vw);

    }



     HBox pilePic=new HBox(260);
      pilePic.setAlignment(Pos.CENTER_RIGHT);
     root.getChildren().add(pilePic);

    if(!usedPile.pile.isEmpty()){

     String cardOnPile=usedPile.pile.peek().getCardName().toLowerCase()+"_of_"+usedPile.pile.peek().getCardSuit().toLowerCase()+".png";
     System.out.println("On pile: "+cardOnPile);

        Image img2=new Image(new FileInputStream("/home/yezy/IdeaProjects/CardGame/src/sample/game/CardsPics/Normal/Playing Cards/PNG-cards-1.3/"+cardOnPile));
        ImageView pm=new ImageView(img2);
        //pm.setRotate(20);
        //pm.setId("pileCard");
        pm.setFitHeight(150);
        pm.setFitWidth(100);
        pilePic.getChildren().add(pm);

    }
    else{
        String emptyPile="Click Card\n  To Play";

        StackPane lm=new StackPane();
        Label message=new Label(emptyPile);
        lm.getChildren().add(message);
        lm.setAlignment(Pos.CENTER);
        lm.setId("pileCard");
        pilePic.getChildren().add(lm);
    }

    HBox controlButtons=new HBox(5);
    controlButtons.setAlignment(Pos.BASELINE_RIGHT);
    controlButtons.setTranslateX(-15);
    controlButtons.setTranslateY(-10);
    root.getChildren().add(controlButtons);

    Button draw=new Button("Deck");
    pilePic.getChildren().add(draw);
    draw.setId("deck");
    if(!specialCardContainer.isEmpty()){
        draw.setStyle("-fx-background-color: deepskyblue");
    }
    Button pass=new Button("Pass");
    pass.setId("pass");
    pass.setFocusTraversable(true);
    if(semaphore){
        pass.setStyle("-fx-background-color: deepskyblue");
    }
    String simulatedCards=String.valueOf(Play.p2.cardsOnHand.size());
    Label spc=new Label("Number of computer cards: "+simulatedCards);
    spc.setId("spc");
   // spc.setTranslateX();
    controlButtons.getChildren().addAll(spc,pass);

    draw.setOnAction(ActionEvent ->{
        if(semaphore) return;
        if(!specialCardContainer.isEmpty()){
            for(Card c:specialCardContainer){
                this.cardsOnHand.push(c);
            }
            specialCardContainer=new Stack<Card>();
            draw.setStyle("-fx-background-color: white");

        }
        else
             this.drawCard();

        Play.p2.playCard();
        try {
            Play.p1.showHandCards();
        }
        catch (Exception err){System.out.print("Update Error");}
        nextPlayer=nextPlayer+turn;
        return;

    });
    pass.setOnAction(ActionEvent -> {
        if(!semaphore) return;
        Player.semaphore=false;
        pass.setStyle("-fx-background-color: white");
        Play.p2.playCard();
        try {
            Play.p1.showHandCards();
        }
        catch (Exception err){System.out.print("Update Error");}
        nextPlayer=nextPlayer+turn;
        return;
    });

      myScene.setRoot(root);

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
                StackPane over=new StackPane();
                Scene scene=new Scene(over,Main.myScene.getWidth(),Main.myScene.getHeight());
                scene.getStylesheets().add("sample/Decorate.css");
                over.setId("over");
                Label ov;
                if(this.playerName.equals("SimulatedPlayer")){
                     ov=new Label("              Oooops\n Thanks for Playing"+Play.p1.playerName+"\n            You have lost!.");
                }
                else{
                     ov=new Label("Congratulation "+this.playerName+"  You have won!..");}

                ov.setId("overmessag");
                ov.setFont(new Font("ubuntu",30));
                ov.setTextFill(Paint.valueOf("BLACK"));
                over.getChildren().add(ov);
                Main.myStage.setScene(scene);

            }
        }
    }

    public void penalt(int pv){
        System.out.println("\nOoooops! Wrong card");
        for(int i=0;i<pv;i++){
            this.drawCard();
        }
    }

    public void playCard(Card plc){
        if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("7") && semaphore && plc.getCardName().equals("7")){

            semaphore=false;
            nextPlayer=nextPlayer+turn;
            return;
        }

        if((!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("2") && !specialCardContainer.isEmpty()) && plc.getCardName().equals("2")){

              /*  for(Card c:specialCardContainer){
                    this.cardsOnHand.push(c);
                }
                specialCardContainer=new Stack<Card>();
                */


            this.throwCard(plc);
            this.updateTemp();
            nextPlayer=nextPlayer+turn;
                return;

        }

        if(!usedPile.pile.isEmpty() && usedPile.pile.peek().getCardName().equals("8") && semaphore && plc.getCardName().equals("8")){

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


            String cardName=plc.getCardName();

            String cardSuit=plc.getCardSuit();

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

