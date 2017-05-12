public class DeckWithJokers extends DeckWithoutJokers{
  public DeckWithJokers(){
    super();
    deck.push(new Card(14,"NONE"));
    deck.push(new Card(15,"NONE"));

  }
}
