package ricardorigodon;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class Main {


    final static String TEXT_FOLDER= "./txts/";



    public static void main(String[] args) {




        if(args.length != 1){

            System.out.println("Enter in the year you want to process as an argument. Example : java IRFinalProject 1987");
            System.out.println("To examine the articles from 1987.");
        }


        int year = Integer.parseInt(args[0]);

        Map<Integer, ArrayList<Article>> yearMap = new TreeMap<Integer, ArrayList<Article>>();



            ArticleParser articleParser = ArticleParser.getArticleParser();

            File file = new File(TEXT_FOLDER + year + ".txt");



            for(Article a : articleParser.txtToArticles(articleParser.readFile(file))){

            }




    }






    }
