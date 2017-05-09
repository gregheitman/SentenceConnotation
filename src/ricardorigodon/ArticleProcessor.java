/*  Greg Heitman, Ricardo Rigodon, Brooks Wegmann
 *  CSC320 
 *  Final Project
 */
package ricardorigodon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ricardorigodon on 5/5/17.
 */
public class ArticleProcessor {

    
    static Map<String, Boolean> wordMap = new HashMap<String, Boolean>();

    final static File positiveWords = new File("./words/positive-words.txt");
    final static File negativeWords = new File( "./words/negative-words.txt");


  static XMLParser xmlParser = XMLParser.getParser();



    public ArticleProcessor(){

    }

    static ArticleProcessor articleProcessor = new ArticleProcessor();

    
    public static ArticleProcessor getArticleProcessor(){

        if(wordMap.isEmpty()){

            loadWords(positiveWords);
            loadWords(negativeWords);
        }


        return articleProcessor;
    }


    /**
     * Create map structure of positive and negative words from negative-words.txt and positive-words.txt. Boolean value true for positive
     * false for negative.
     * @param sentences
     * @return
     */
    public double calcPosScore(ArrayList<String> sentences){

        double score = 0;

        //total number of sentences
        double sentenceCount = sentences.size();

        //will count number of positive sentences
        double positiveCount = 0;

        for(String s : sentences){
            //Split on nonword characters
            String[] splitter = s.split("\\W+");

            int posWordCount = 0;
            int negWordCount = 0;

            // For each word in a sentence, check if it exists in the semantic word map
            for(String str : splitter){

                str.replaceAll("[^a-zA-Z0-9]", "");

                boolean inMap = wordMap.containsKey(str);
    
                // If the word exists in the map, increment corresponding neg/pos value
                if(inMap){

                    if(wordMap.get(str)){

                        posWordCount++;

                    }

                    else{

                        negWordCount++;
                    }
                }

            }
            // Increment an articles positive count if there are more positive words than negative words
            if(posWordCount > negWordCount){

                positiveCount++;
            }
        }

        // Total score is ratio of positive sentences to total sentences
        score = positiveCount / sentenceCount;





        return score;


    }


    /* Loads semantic word lists into HashMap */
    public static void loadWords(File file){

        /* Associates words from positive word list with true */
        if(file.getName().equals(positiveWords.getName())){

            System.out.println("Loading positive words.");

            String positiveWords = xmlParser.readFile(file);

            String[] positives = positiveWords.split("[\r\n]+");

            for(String s : positives){

                wordMap.put(s, true);
            }


            System.out.println("Positive words loaded.");



        }
        /* Associates words from negative word list with false */
        else if(file.getName().equals(negativeWords.getName())){

            System.out.println("Loading negative words.");

            String negativeWords = xmlParser.readFile(file);

            String[] negatives = negativeWords.split("[\r\n]+");

            for(String s : negatives){

                wordMap.put(s, false);
            }

            System.out.println("Negative words loaded.");

        }





    }

    public static Map<String, Boolean> getWordMap(){

        return wordMap;

    }


}
