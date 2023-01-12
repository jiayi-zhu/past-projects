package ngordnet.ngrams;

import java.util.Collection;
import java.util.TreeMap;

import edu.princeton.cs.algs4.In;


/** An object that provides utility methods for making queries on the
 *  Google NGrams dataset (or a subset thereof).
 *
 *  An NGramMap stores pertinent data from a "words file" and a "counts
 *  file". It is not a map in the strict sense, but it does provide additional
 *  functionality.
 *
 *  @author Josh Hug
 */
public class NGramMap {
    private TreeMap<String, TimeSeries> treeMapWords = new TreeMap<>();
    private TimeSeries tsCounts = new TimeSeries();

    /** Constructs an NGramMap from WORDSFILENAME and COUNTSFILENAME. */
    public NGramMap(String wordsFilename, String countsFilename) {
        In wordsFile = new In(String.format("%s", wordsFilename));
        In countsFile = new In(String.format("%s", countsFilename));

        String[] allLinesWordsFile = wordsFile.readAllLines();
        String prevWord = null;
        TimeSeries temp = new TimeSeries();
        for (int i = 0; i <= allLinesWordsFile.length; i += 1) {
            if (i == allLinesWordsFile.length) {
                treeMapWords.put(prevWord, temp);
            } else {
                String[] splitStr = allLinesWordsFile[i].split("\\s+");
                if (!splitStr[0].equals(prevWord)) {
                    if (prevWord != null) {
                        treeMapWords.put(prevWord, temp);
                        temp = new TimeSeries();
                    }
                    prevWord = splitStr[0];
                }
                temp.put(Integer.parseInt(splitStr[1]), Double.parseDouble(splitStr[2]));
            }

        }


        String[] allLinesCountsFile = countsFile.readAllLines();
        for (int i = 0; i < allLinesCountsFile.length; i += 1) {
            String[] splitStr = allLinesCountsFile[i].split(",");
            tsCounts.put(Integer.parseInt(splitStr[0]), Double.parseDouble(splitStr[1]));
        }
    }


    /** Provides the history of WORD. The returned TimeSeries should be a copy,
     *  not a link to this NGramMap's TimeSeries. In other words, changes made
     *  to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word) {
        return treeMapWords.get(word);
    }

    /** Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     *  returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     *  changes made to the object returned by this function should not also affect the
     *  NGramMap. This is also known as a "defensive copy". */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries returnTs = new TimeSeries(countHistory(word), startYear, endYear);
        return returnTs;
    }

    /** Returns a defensive copy of the total number of words recorded per year in all volumes. */
    public TimeSeries totalCountHistory() {
        return tsCounts;
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD compared to
     *  all words recorded in that year. */
    public TimeSeries weightHistory(String word) {
        TimeSeries wordTs = treeMapWords.get(word);
        return wordTs.dividedBy(tsCounts);
    }

    /** Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     *  and ENDYEAR, inclusive of both ends. */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries returnTs = new TimeSeries(weightHistory(word), startYear, endYear);
        return returnTs;
    }

    /** Returns the summed relative frequency per year of all words in WORDS. */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        TimeSeries returnTs = new TimeSeries();
        for (String word: words) {
            returnTs = returnTs.plus(weightHistory(word));
        }
        return returnTs;
    }

    /** Provides the summed relative frequency per year of all words in WORDS
     *  between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     *  this time frame, ignore it rather than throwing an exception. */
    public TimeSeries summedWeightHistory(Collection<String> words, int startYear, int endYear) {
        TimeSeries returnTs = new TimeSeries(summedWeightHistory(words), startYear, endYear);
        return returnTs;
    }


}
