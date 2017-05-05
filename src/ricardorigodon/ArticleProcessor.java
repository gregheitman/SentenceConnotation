package ricardorigodon;

import java.util.ArrayList;

/**
 * Created by ricardorigodon on 5/5/17.
 */
public class ArticleProcessor {


    public ArticleProcessor(){

    }

    static ArticleProcessor articleProcessor = new ArticleProcessor();


    public static ArticleProcessor getArticleProcessor(){

        return articleProcessor;
    }


    /**
     * Create map structure of positive and negative words from negative-words.txt and positive-words.txt. Boolean value true for positive
     * false for negative.
     * @param sentences
     * @return
     */
    public int calcPosScore(ArrayList<String> sentences){

        int score = 0;





        return score;


    }


}
