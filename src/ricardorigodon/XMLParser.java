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

    public List<String> XMLtoText(String xml){


        List<String> content = new ArrayList<String>();

        for(Element e : Jsoup.parse(xml, "",  Parser.xmlParser()).select("body")){

          content.add(e.text());

        }


        return content;


    }



    public char[] readFile(File file) throws IOException {


        RandomAccessFile aFile = new RandomAccessFile
                (file, "r");
        FileChannel inChannel = aFile.getChannel();
        MappedByteBuffer buffer = aFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());

        char[] charArray = new char[buffer.limit()];


        for(int i = 0; i < buffer.limit(); i++){

            charArray[i] = (char) buffer.get();


        }

        System.out.println("Size of char Array : " + charArray.length);



        inChannel.close();
        aFile.close();


        return charArray;

    }

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


                String xmlContent = String.valueOf(readFile(xmlDoc));

                System.out.println( "LENGTH OF STRING : " + xmlContent.length());



                String[] content = xmlContent.split("</nitf>");

                System.out.println("Size of XML content array : " + content.length);


                for (String s : content) {

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
