package sample;
import sample.game.Card;
import sample.game.Player;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class EventControl implements EventHandler<MouseEvent> {
    Player player;
    Card playedCard;

    public EventControl(Card cld, Player player) {
        this.playedCard = cld;
        this.player = player;
    }

    @Override
    public void handle(MouseEvent e) {
        System.out.println(playedCard.getCardName());
        player.playCard(playedCard);
        Play.p2.playCard();
        try {
            Play.p1.showHandCards();
        }
        catch (Exception ex){
            System.out.println("UI Update error...");
        }



    }
}