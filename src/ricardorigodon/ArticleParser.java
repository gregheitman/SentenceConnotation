package ricardorigodon;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Created by ricardorigodon on 5/5/17.
 *
 * Class to read .txt files and break them up into individual articles.
 */
public class ArticleParser {


    static ArticleParser articleParser = new ArticleParser();

    static XMLParser xmlParser = XMLParser.getParser();


    private ArticleParser(){


    }


    public static ArticleParser getArticleParser(){


        return articleParser;

    }


    public ArrayList<Article> txtToArticles(String yearText){


        ArrayList<Article> articles = new ArrayList<Article>();


       String content[] = yearText.split("[\r\n]+");


       int count = 1;

       for(String s : content){

           System.out.println(count + ") : " + s);
           count++;
       }











        return articles;


    }


    public String readFile(File file){


        //why not use a method we already wrote?
        return xmlParser.readFile(file);
    }


}
