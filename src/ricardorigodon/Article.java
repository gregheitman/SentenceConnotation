package ricardorigodon;

import java.util.ArrayList;

/**
 * Created by ricardorigodon on 5/5/17.
 */
public class Article {


    String title = "";
    ArrayList<String> text = new ArrayList<String>();
    double posScore = 0;


    /**
     * Class for each article in NYT corpus.
     * @param title Title of the article
     * @param text Text of the article
     * @param posScore positivity score of the article
     */
    public Article(String title, ArrayList<String> text, double posScore){

        this.title = title;
        this.text = text;
        this.posScore = posScore;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public void setText(ArrayList<String> text) {
        this.text = text;
    }

    public double getPosScore() {
        return posScore;
    }

    public void setPosScore(double posScore) {
        this.posScore = posScore;
    }
}
