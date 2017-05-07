package ricardorigodon;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class Main {


    final static String TEXT_FOLDER= "./txts/";
    final static String DATA_FOLDER = "./data/";



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

            ArrayList<Article> articles = articleParser.txtToArticles(articleParser.readFile(file));



            for(Article a : articles){


               a.setPosScore(articleProcessor.calcPosScore(a.getText()));


            }




           double mean = calculateMean(articles);


            double SD = calculateSD(mean, articles);


            for(Article a : articles) {


                //normalize scores
                normalizeScore(a, mean, SD);

            }

            clearFile(DATA_FOLDER + year + ".txt");



            try {

                PrintWriter out = new PrintWriter(new FileWriter(DATA_FOLDER + year + ".txt", true));


                String formatStr =  "%-20s %15s%n";

                out.write(String.format(formatStr, "TITLE", "SCORE"));

                out.write("\n");

                for(Article a : articles) {

                    out.write(String.format(formatStr, a.getTitle(), a.getPosScore()));

                }

            } catch(Exception e){

                e.printStackTrace();
            }







    }


    public static double calculateMean(ArrayList<Article> articles){

        double sum = 0;

        for(Article a : articles){


            sum += a.getPosScore();


        }

        return sum / articles.size();
    }


    public static double calculateSD(double mean, ArrayList<Article> articles){


        double temp = 0;

        for(Article a : articles){

            Double d = a.getPosScore();

            temp += (d - mean) * (d - mean);
        }

        double variance = temp / articles.size();


        return Math.sqrt(variance);




    }

    public static void normalizeScore(Article a, double mean, double SD){


        double score = a.getPosScore();

        double zScore = (score - mean) / SD;

        a.setPosScore(zScore);
    }


    public static void clearFile(String filePath){

        try{

            PrintWriter out = new PrintWriter(new FileWriter(filePath, false));

            out.write("");


        }catch(Exception e){

            e.printStackTrace();
        }


    }












    }
