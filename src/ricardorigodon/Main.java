package ricardorigodon;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main {


    final static String TEXT_FOLDER= "./txts/";
    final static String WORDS_FOLDER = "./words/";



    public static void main(String[] args) {




        if(args.length < 1 || args.length > 1){

            System.out.println("Enter in the year you want to process as an argument. Example : java IRFinalProject 1987");
            System.out.println("To examine the articles from 1987.");

            System.exit(-1);
        }




        int year = Integer.parseInt(args[0]);

        Map<Integer, ArrayList<Article>> yearMap = new TreeMap<Integer, ArrayList<Article>>();



            ArticleParser articleParser = ArticleParser.getArticleParser();

            ArticleProcessor articleProcessor = ArticleProcessor.getArticleProcessor();

            File file = new File(TEXT_FOLDER + year + ".txt");



            for(Article a : articleParser.txtToArticles(articleParser.readFile(file))){


                for(String s : a.getText()){

                    System.out.println(s);
                }

               a.setPosScore(articleProcessor.calcPosScore(a.getText()));





            }




    }






    }
