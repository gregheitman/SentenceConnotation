/*  Greg Heitman, Ricardo Rigodon, Brooks Wegmann
 *  CSC320 
 *  Final Project
 */
package ricardorigodon;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by ricardorigodon on 5/5/17.
 *
 * Class to read .txt files and break them up into individual articles.
 */
public class ArticleParser {


    static ArticleParser articleParser = new ArticleParser();

    static XMLParser xmlParser = XMLParser.getParser();


   final static int ZERO_VALUE = 0;
   final static int NO_FOUND_SUB = -999;

   //max length of titles
   final static int MAX_LENGTH = 300;

   // Stop list to ignore articles that only consist of stock/financial information
   ArrayList<String> ignoreTexts = new ArrayList(
        Arrays.asList("reports earnings for qtr", "reports earnings for year", "company reports"));


    private ArticleParser(){


    }


    public static ArticleParser getArticleParser(){


        return articleParser;

    }


    /* Takes in all of the text of a given year and parses out each article along with titles and sentences
     * yearText should consist of all articles delimited by \n
     */
    public ArrayList<Article> txtToArticles(String yearText){


        ArrayList<Article> articles = new ArrayList<Article>();


       String content[] = yearText.split("[\r\n]+");


       System.out.println("# of different articles : " + content.length);




       int count = 0;

       /* For each article, get its title and body content */
       for(String s : content){

          boolean value = false;

          /* Ignores articles that are not relevant */
          for(String str : ignoreTexts){


              if(s.toLowerCase().contains(str.toLowerCase())){

                  value = true;


              }


          }
           /* Parse out Title and Body content from relevant articles */
           if(!value) {

               /* Checks positions of keywords known to terminate titles */
               int byIndex = s.indexOf("By");

               //for some reason the leadIndex does not work
               int leadIndex = s.indexOf("LEAD:");

               
               int masterIndex = NO_FOUND_SUB;

               /* Use the earliest occurence of a keyword to get length of the title */
               if (byIndex > 0 && leadIndex > 0) {

                   if (byIndex < leadIndex) {

                       masterIndex = byIndex;
                   } else {

                       masterIndex = leadIndex;
                   }
               } else {

                   if (byIndex > 0) {

                       masterIndex = byIndex;
                   } else if (leadIndex > 0) {


                       masterIndex = leadIndex;
                   }


               }


               String title;
               String restOfText;

               
               if (masterIndex > 0) {


                   title = s.substring(0, masterIndex);
                   // Checks if first occurrence of keyword is outside of the actual title
                   // Eliminates very lengthy titles for articles
                   if(title.length() > MAX_LENGTH){

                       title = s.substring(0, Math.min(title.length(), MAX_LENGTH));
                   }


                   restOfText = s.substring(masterIndex);


                   //breaks up string into individual sentences
                   BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);

                   ArrayList<String> sentences = new ArrayList<String>();

                   iterator.setText(restOfText);
                   int start = iterator.first();
                   for (int end = iterator.next();
                        end != BreakIterator.DONE;
                        start = end, end = iterator.next()) {

                       sentences.add(restOfText.substring(start, end));


                   }

                   //create new article.


                   Article article = new Article(title, sentences, ZERO_VALUE);


                   articles.add(article);

               }

           }

           }



        return articles;


    }

    
    public String readFile(File file){


        //why not use a method we already wrote?
        return xmlParser.readFile(file);
    }


}
