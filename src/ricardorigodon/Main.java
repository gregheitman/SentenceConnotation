package ricardorigodon;


import java.io.File;

public class Main {


    final static String TEXT_FOLDER= "./txts/";


    public static void main(String[] args) {


        ArticleParser articleParser = ArticleParser.getArticleParser();

        File file = new File(TEXT_FOLDER + "1987.txt");

        String content = articleParser.readFile(file);


        articleParser.txtToArticles(content);



    }






    }
