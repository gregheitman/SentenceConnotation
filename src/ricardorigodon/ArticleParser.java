package ricardorigodon;

import java.io.File;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
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


   ArrayList<String> ignoreTexts = new ArrayList(
        Arrays.asList("reports earnings for qtr", "reports earnings for year", "company reports"));


    private ArticleParser(){


    }


    public static ArticleParser getArticleParser(){


        return articleParser;

    }


    public ArrayList<Article> txtToArticles(String yearText){


        ArrayList<Article> articles = new ArrayList<Article>();


       String content[] = yearText.split("[\r\n]+");

       System.out.println("# of different articles : " + content.length);




       int count = 0;

       for(String s : content){

          boolean value = false;


          for(String str : ignoreTexts){


              if(s.toLowerCase().contains(str.toLowerCase())){

                  value = true;


              }


          }

           if(!value) {


               int byIndex = s.indexOf("By");

               //for some reason the leadIndex does not work
               int leadIndex = s.indexOf("LEAD:");


               int masterIndex = NO_FOUND_SUB;

               if(byIndex > 0 && leadIndex > 0) {

                   if (byIndex < leadIndex) {

                       masterIndex = byIndex;
                   } else {

                       masterIndex = leadIndex;
                   }
               }

               else{

                   if(leadIndex > 0 || byIndex > 0)
                   if (byIndex > leadIndex) {

                       masterIndex = byIndex;
                   } else{

                       if(leadIndex > 0) {
                           masterIndex = leadIndex;
                       }
                   }


               }



               if (masterIndex > 0) {

                   count++;

                   String title = s.substring(0, masterIndex);

                   String restOfText = s.substring(masterIndex);


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
