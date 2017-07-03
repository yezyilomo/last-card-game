package sample;

import javafx.scene.layout.StackPane;
import sample.game.Card;
import sample.game.DeckWithoutJokers;
import sample.game.DeckWithJokers;
import sample.game.Player;
import sample.game.Pile;
import sample.game.SimulatedPlayer;
import java.util.*;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage myStage;
    public static Scene myScene;

    @Override
    public void start(Stage my) throws Exception{

        myStage=new Stage();
        myStage.setTitle("Card Game");
        StackPane root = new StackPane();
        myScene=new Scene(root,800,450);
        myScene.getStylesheets().add("sample/Decorate.css");
        myStage.setScene(myScene);
        myStage.show();

        Play play=new Play();
        play.start();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
