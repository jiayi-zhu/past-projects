package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;

public class TestConstructGraph {

    @Test
    public void TestBase1() {
        constructGraph graph = new constructGraph("./data/wordnet/synsets16.txt","./data/wordnet/hyponyms16.txt");
        ArrayList<String> x = new ArrayList();
        x.add("change");
        graph.getHypo(x);
        //x.add("cat");
        //x.add("change");
        System.out.println(graph.getHypo(x));
    }


    @Test
    public void TestBase() {
        constructGraph graph = new constructGraph("./data/wordnet/synsets.txt","./data/wordnet/hyponyms.txt");
        ArrayList<String> x = new ArrayList();
        //x.add("whore");
        //x.add("cat");
        //x.add("chocolate");
        graph.getHypo(x);
        //x.add("cat");
        //x.add("change");
        System.out.println(graph.getHypo(x));
    }

    @Test
    public void TestMultipleWords() {
        constructGraph x16 = new constructGraph("./data/wordnet/synsets16.txt","./data/wordnet/hyponyms16.txt");
        ArrayList<String> words = new ArrayList<>();
        words.add("change");
        words.add("leap");
        System.out.println(x16.getHypo(words));
    }

    @Test
    public void Test() {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        TimeSeries x = ngm.countHistory("abdjs", 1950, 1990);
        System.out.println(x.years().size() == 0);

    }



}
