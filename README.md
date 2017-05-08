README
Greg Heitman, Ricardo Rigodon, Brooks Wegmann
CSC320 - Final Project


----------------
Goal of Project
----------------

The goal of our program is to analyze sentence connotation in the data set of New York Times 
articles from 1987 to 2007. Our final result attempts to determine how positive or how negative a year was based on the tone of the articles in the respective years. 

We hope this will tell us the most positive years according to the NYT corpus, also the connection between sentence connotation and the topic of the article. Is it positive or negative?


---------------
Data Structures
---------------

Each file of the NYT Corpus contains all the articles for that given year. We parse out each article and store it in an Article Object. Each Article is kept in an ArrayList. The Article Object contains an ArrayList<String> to hold all of the body text as well as variables for the title and positivity score.

We create a List of these Article objects to represent all the articles in the year's txt file.

We used BreakIterator which allowed us to split the sentences of each article to separate the title from the rest of the sentences in the article. This list we operate on to generate our positivity score.

We store +/- semantic word lists from <https://www.cs.uic.edu/~liub/FBS/sentiment-analysis.html> where they used to detect fake reviews into a HashMap that maps a word to a boolean. Positive words map to True, and negative words map to False.

----------
Algorithms
----------

- Parsing Article Titles and Sentences -

To parse out individual artices, we look for a certain pattern in the files. Nearly all articles begin with a Title followed by an author. These are separated through the first instance of the word "By". A smaller set of articles begin with a Title followed by the word "LEAD". LEAD will follow an author, so if this pattern exists, we separate this article on LEAD instead of "By". We use indexOf to find where By and LEAD is located, comparing their indexes to each other to find out where the title lies. A very small set of articles have no Title or author or By or LEAD is farther along the article. In these instances, we take the first N characters of the article and use it as the title. All remaining text after getting the title is body text which is used to calculate the positivity score. 

- Calculating Positivity Score -

To determine how positive an article is, we determine the ratio of positive sentences to total number of sentences. For each sentence, we search for each word in our semantic word list. We keep separate counts of when we come across a positive or negative word. Words that do not appear in our semanctic word list are ignored. When we reach the end of a sentence, the two counts are compared. If there are more positive words than negative words, we increment a separate counter that holds the number of positive sentences an article contains. The positivity score is then the result of positive sentences / negative sentences. The score at this time will be between 0 and 1.


- Normalizing Positivity Scores -

Because not all articles are the same length, the scores must be normalized. We use a z-score to get an accurate representation of our data. The z-score formula is : (x - µ) / σ where x is the score, µ is the mean, and σ is the standard deviation.
After normalizing the data, articles with a more negative connotation will be less than zero, articles with a positive connotation will be greater than zero, and neutral articles will be close to zero. We thought this was pretty neat and much easier to visualize than 0 and 1 assuming .5 was neutral. 

-----------------
Known Limitations
-----------------

Some of the articles in the NYT Corpus are too large for our machines to process. The JVM will run out of heap space when attempting to read in and store all the text data for these years. These years will not have any output data.

We tried Memory Mapped Buffer which seemed to resolve the issue for most years, but a select few still have issues. Our charArray reached too large of a size for the Heap to transform that into a string.

We could have tried also writing Javascript/Python script that could go through the xml and split the gigantic .har files into valid xml. 

We tried everything to try and get the text from these years maybe in the future we could refine our parsing process or use another library outside of JSoup to parse the XML. 


-------------------
Running the Program
--------------------

The program should be run from an IDE with an added dependancy linking to JSOUP. Along with dependencies to Guava,Apache Commons 2.5, Log4j, Apache Commons-lang. The program takes 1 argument as input which is the year you are looking for. This argument will be the year whose score will be caluclated. All output will be written to that years text file in the /data/ subdirectory. You will need the txt files for each year in a directory txts- not in github by request- along with the words directory included here.

We wanted to have it run for a select number of years or for all the years at once, but we were limited by exceeding our heap memory size after processing 1 year. Each year contains thousands of articles so the memory usage grows pretty fast. The total NYT Corpus size is 1.8 million articles large.

A list of years that are supported:

Out of the years 1987-2007 only the years 2001-2004 are not supported. We could not get the text from our parser for these years.



