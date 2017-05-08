README
Greg Heitman, Ricardo Rigodon, Brooks Wegmann
CSC320 - Final Project


----------------
Goal of Program
----------------

The goal of our program is to analyze sentence connotation in the data set of New York Times 
articles from 1987 to 2007. Our final result attempts to determine how positive or how negative a year was based on the tone of the articles in the respective years. 


---------------
Data Structures
---------------

Each file of the NYT Corpus contains all the articles for that given year. We parse out each article and store it in an Article Object. Each Article is kept in an ArrayList. The Article Object contains an ArrayList<String> to hold all of the body text as well as variables for the title and positivity score.

We store a semantic word list from <insert source here> in a HashMap that maps a word to a boolean. Positive words map to True, and negative words map to False.

----------
Algorithms
----------

- Parsing Article Titles and Sentences -

To parse out individual artices, we look for a certain pattern in the files. Nearly all articles begin with a Title followed by an author. These are separated through the first instance of the word "By". A smaller set of articles begin with a Title followed by the word "LEAD". LEAD will follow an author, so if this pattern exists, we separate this article on LEAD instead of "By". A very small set of articles have no Title or author. In these instances, we take the first N characters of the article and use it as the title. All remaining text after getting the title is body text which is used to calculate the positivity score. 

- Calculating Positivity Score -

To determine how positive an article is, we determine the ratio of positive sentences to total number of sentences. For each sentence, we search for each word in our semantic word list. We keep separate counts of when we come across a positive or negative word. Words that do not appear in our semanctic word list are ignored. When we reach the end of a sentence, the two counts are compared. If there are more positive words than negative words, we increment a separate counter that holds the number of positive sentences an article contains. The positivity score is then the result of positive sentences / negative sentences. The score at this time will be between 0 and 1.


- Normalizing Positivity Scores -

Because not all articles are the same length, the scores must be normalized. We use a z-score to get an accurate representation of our data. The z-score formula is : (x - µ) / σ
After normalizing the data, articles with a more negative connotation will be less than zero, articles with a positive connotation will be greater than zero, and neutral articles will be close to zero.

-----------------
Known Limitations
-----------------

Some of the articles in the NYT Corpus are too large for our machines to process. The JVM will run out of heap space when attempting to read in and store all the text data for these years. These years will not have any output data. 

A very small set of Articles contain the word "By" in the title. Due to our parsing techniques, these articles have shortened titles. 


-------------------
Running the Program
--------------------

The program should be run from an IDE with an added dependancy linking to JSOUP. The program takes 1 argument as input. This argument will be he year whose score will be caluclated. All output will be written to that years text file in the /data/ subdirectory. 




