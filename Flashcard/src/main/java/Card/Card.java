package Card;

/**
 *
 * @author alex
 */
public class Card {
    private String front;
    private String sentence;
    private String back;
    private String backSentence;
    
    public Card(String front, String sentence, String back, String backSentence){
        this.front = front;
        this.sentence = sentence;
        this.back = back;
        this.backSentence = backSentence;
    }
    public String getFront(){
        return front;
    }
    public String getSentence(){
        return this.sentence;
    }
    public String getBack(){
        return this.back;
    }
    public String getBackSentence(){
        return this.backSentence;
    }
}
