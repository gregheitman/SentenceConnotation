/*  Greg Heitman, Ricardo Rigodon, Brooks Wegmann
 *  CSC320 
 *  Final Project
 */
package ricardorigodon;

import com.google.common.base.Joiner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ricardorigodon on 4/25/17.
 */
public class XMLParser {


    public static XMLParser parser = new XMLParser();



    final static String XMLfolder = "./XMLdocs/";
    final static String XML_Text = "./txts/";


    private XMLParser(){


    }

    public static XMLParser getParser(){

        return parser;
    }

    // Parses xml text and returns an ArrayList of Strings.
    // Each position corresponds to an article
    public List<String> XMLtoText(String xml){


        List<String> content = new ArrayList<String>();
        // Grabs body content from xml files, no other information needed
        for(Element e : Jsoup.parse(xml, "",  Parser.xmlParser()).select("body")){

          content.add(e.text());
        }


        return content;


    }


    // Reads in a given file and returns result as a single string
    public String readFile(File file){


        try {
            RandomAccessFile aFile = new RandomAccessFile
                    (file, "r");
            FileChannel inChannel = aFile.getChannel();
            MappedByteBuffer buffer = aFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());

            char[] charArray = new char[buffer.limit()];


            for (int i = 0; i < buffer.limit(); i++) {

                charArray[i] = (char) buffer.get();



            }

            System.out.println("Size of char Array : " + charArray.length);


            inChannel.close();
            aFile.close();

            return String.valueOf(charArray);

        }catch(Exception e){

            e.printStackTrace();
        }


        return "";

    }

    // Reads in .xml file and writes article data to .txt file
    public void XMLtoText(char[] charArray){


        //list of years
       /* ArrayList<Integer> yearList = new ArrayList<Integer>(Arrays.asList(1987, 1988, 1989, 1990, 1991, 1992,
                1993, 1994, 1995, 1996, 1997, 1998, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007));
                */

        //list of years that can't be processed
        ArrayList<Integer> yearList = new ArrayList<Integer>(Arrays.asList(  2002, 2002, 2003, 2004, 2005, 2006, 2007));



        for(int year : yearList) {


            File xmlDoc = new File(XMLfolder + year + ".xml");


            try {


                String xmlContent = readFile(xmlDoc);

                System.out.println( "LENGTH OF STRING : " + xmlContent.length());


                //Splits xml file into different articles
                String[] content = xmlContent.split("</nitf>");

                System.out.println("Size of XML content array : " + content.length);

                //Writes data to .txt file
                for (String s : content) {
                    //gets body context
                    Joiner.on("\t").join(XMLtoText(s));

                    try(FileWriter fw = new FileWriter(XML_Text + year + ".txt", true);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw))
                    {
                        out.println(Joiner.on("\t").join(XMLtoText(s)));


                    } catch (IOException e) {

                        e.printStackTrace();
                    }


                }


            } catch (Exception e) {

                e.printStackTrace();
            }

            System.out.println("Done reading " + year + ".xml");



            System.out.println("Wrote text to : " + year + ".txt");

        }



    }

}
