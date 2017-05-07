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

            String[] splitter = s.split("\\W+");

            int posWordCount = 0;
            int negWordCount = 0;


            for(String str : splitter){

                str.replaceAll("[^a-zA-Z0-9]", "");

                boolean inMap = wordMap.containsKey(str);

                if(inMap){

                    if(wordMap.get(str)){

                        posWordCount++;

                    }

                    else{

                        negWordCount++;
                    }
                }

            }

            if(posWordCount > negWordCount){

                positiveCount++;
            }
        }


        score = positiveCount / sentenceCount;





        return score;


    }



    public static void loadWords(File file){


        if(file.getName().equals(positiveWords.getName())){

            System.out.println("Loading positive words.");

            String positiveWords = xmlParser.readFile(file);

            String[] positives = positiveWords.split("[\r\n]+");

            for(String s : positives){

                wordMap.put(s, true);
            }


            System.out.println("Positive words loaded.");



        }

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
