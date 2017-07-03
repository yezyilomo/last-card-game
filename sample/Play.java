package sample;


import sample.game.DeckWithoutJokers;
import sample.game.Pile;
import sample.game.Player;
import sample.game.SimulatedPlayer;

public class Play extends Thread{
   // public static Player listOfPlayers[];
    public static Player p1;
    public static SimulatedPlayer p2;

    public void run(){
        try {
            play();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play() throws Exception{
        DeckWithoutJokers deck = new DeckWithoutJokers();
        Pile pile = new Pile();
        Player.numberOfPlayers = 2;
        //listOfPlayers = new Player[Player.numberOfPlayers];

        deck.shuffleCards();
        /*
        for (int i = 0; i < Player.numberOfPlayers; i++) {
            if (i == 0)
                listOfPlayers[i] = new Player("Yezy", 4, pile);
            else listOfPlayers[i] = new SimulatedPlayer("Ilomo", 4, pile);
            listOfPlayers[0].usedDeck = deck;
            listOfPlayers[i].dealCards();
        }
        */

        p1=new Player("Yezy",5,pile);
        p2=new SimulatedPlayer("SimulatedPlayer",6,pile);
        Player.usedDeck=deck;
        p1.dealCards();
        p2.dealCards();

        //while (true) {
            //Player.nextPlayer = Player.nextPlayer % Player.numberOfPlayers;
            //listOfPlayers[Player.nextPlayer].showHandCards();

            p1.showHandCards();
            //p2.playCard();

        //}

    }
}
