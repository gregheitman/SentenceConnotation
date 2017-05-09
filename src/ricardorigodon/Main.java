/*  Greg Heitman, Ricardo Rigodon, Brooks Wegmann
 *  CSC320 
 *  Final Project
 */

/* The purpose of this program is to determine the positivity of articles in the NYT Corpus from 1987-2007. 
 * The program takes in one argument (a year) and determines the positivity score for each article in that year.
 * Positivity scores are normalized using z-score.
 */
package ricardorigodon;


import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/* Driver Class for Final Project */
public class Main {


    final static String TEXT_FOLDER= "./txts/";
    final static String DATA_FOLDER = "./data/";



    public static void main(String[] args) {



        // Checks for valid input
        if(args.length < 1 || args.length > 1){

            System.out.println("Enter in the year you want to process as an argument. Example : java IRFinalProject 1987");
            System.out.println("To examine the articles from 1987.");

            System.exit(-1);
        }




        int year = Integer.parseInt(args[0]);



            // Reads in data for given year and parses out articles
        
            ArticleParser articleParser = ArticleParser.getArticleParser();

            ArticleProcessor articleProcessor = ArticleProcessor.getArticleProcessor();

            File file = new File(TEXT_FOLDER + year + ".txt");

            ArrayList<Article> articles = articleParser.txtToArticles(articleParser.readFile(file));

            System.out.println("Creating articles out of the .txt file");

            System.out.println("We have : " + articles.size() + " ARTICLES!");




            // Set initial positivity scores, values range between 0 and 1
            for(Article a : articles){

               a.setPosScore(articleProcessor.calcPosScore(a.getText()));


            }



           double mean = calculateMean(articles);


            double SD = calculateSD(mean, articles);


            System.out.println("Normalizing scores.");

            // Normalize positivity score for each article
            for(Article a : articles) {

                normalizeScore(a, mean, SD);

            }

            System.out.println("Scores are normalized.");

            clearFile(DATA_FOLDER + year + ".txt");

            System.out.println("Clearing : " +year + ".txt");


            // Output each the title of each article and its positivity score to corresponding .txt file for that year
            try {

                PrintWriter out = new PrintWriter(new FileWriter(DATA_FOLDER + year + ".txt", true));


              //  String formatStr =  "%-20s %15s%n";

                out.write( "TITLE" + "\t" + "SCORE");

                out.write("\n");

                for(Article a : articles) {

                    out.write(a.getTitle() + "\t" + a.getPosScore());
                    out.write("\n");

                }

            } catch(Exception e){

                e.printStackTrace();
            }


    }

    // Calculates the mean positivity score given a list of articles
    public static double calculateMean(ArrayList<Article> articles){

        double sum = 0;

        for(Article a : articles){


            sum += a.getPosScore();


        }

        return sum / articles.size();
    }

    // Calculates the standard deviation of positivity scores given a list of articles
    public static double calculateSD(double mean, ArrayList<Article> articles){


        double temp = 0;

        for(Article a : articles){

            Double d = a.getPosScore();

            temp += (d - mean) * (d - mean);
        }

        double variance = temp / articles.size();


        return Math.sqrt(variance);




    }

    // Normalize an article's positivity score through z-score calculation
    public static void normalizeScore(Article a, double mean, double SD){


        double score = a.getPosScore();

        double zScore = (score - mean) / SD;

        a.setPosScore(zScore);
    }

    // Clear out a given file to be used for output
    public static void clearFile(String filePath){

        try{

            PrintWriter out = new PrintWriter(new FileWriter(filePath, false));

            out.write("");


        }catch(Exception e){

            e.printStackTrace();
        }


    }












    }
