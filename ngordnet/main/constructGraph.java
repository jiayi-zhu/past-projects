package ngordnet.main;

import com.sun.source.tree.Tree;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class constructGraph {
    private TreeMap<Integer,String[]> findWord;
    private TreeMap<String,ArrayList<Integer>> findId;
    private Graph graph;

    public constructGraph(String synsetsFileName, String hyponymsFileName) {
        In synsetsFile = new In(String.format("%s", synsetsFileName));
        In hyponymsFile = new In(String.format("%s", hyponymsFileName));


        String[] allLinesSynsetsFile = synsetsFile.readAllLines();
        graph = new Graph(allLinesSynsetsFile.length);
        findWord = new TreeMap<>();
        findId = new TreeMap<>();

        for (int i = 0; i < allLinesSynsetsFile.length; i += 1) {
            String[] splitStr = allLinesSynsetsFile[i].split(",");
            String[] temp = splitStr[1].split(" ");
            findWord.put(i,temp);
            for (String word: temp) {
                if (! findId.containsKey(word)) {
                    findId.put(word,new ArrayList<>());
                }
                findId.get(word).add(i);
            }
        }

        String[] allLinesHyponymsFile = hyponymsFile.readAllLines();

        for (int i = 0; i < allLinesHyponymsFile.length; i += 1) {
            String[] splitStr = allLinesHyponymsFile[i].split(",");
            for (int j = 1; j < splitStr.length; j += 1) {
                graph.addEdge(Integer.parseInt(splitStr[0]), Integer.parseInt(splitStr[j]));
            }
        }
    }

    public TreeSet<String> getHypoSetById(int a) {
        TreeSet set = new TreeSet();
        for (String syn : findWord.get(a)) {
                set.add(syn);
        }


        ArrayList<Integer> list = graph.adj(a);
        for (int i = 0; i < list.size(); i += 1) {

            int hypoId = list.get(i);

            for (String elem: findWord.get(hypoId)) {
                set.add(elem);
            }

            if (graph.adj(hypoId).size() != 0) {
                TreeSet<String> hyposet = getHypoSetById(hypoId);
                for (String elem: hyposet) {
                    set.add(elem);
                }
            }
        }
        return set;
    }



    public TreeSet<String> getHypoSetByWord(String word) {
        TreeSet set = new TreeSet();
        ArrayList<Integer> idList = findId.get(word);
        if (idList == null) {
            return null;
        }
        for (int id: idList) {
            TreeSet<String> x = getHypoSetById(id);
            for (String elem : x) {
                set.add(elem);
            }
        }
        return set;
    }

    public ArrayList<String> getHypo (String word) {
        TreeSet<String> set = getHypoSetByWord(word);
        if (set == null) {
            return null;
        }
        ArrayList<String> returnList = new ArrayList(set);
        Collections.sort(returnList);
        return returnList;
    }

    public ArrayList<String> getHypo (List<String> words) {
        if (words.size() == 0) {
            return null;
        }
        TreeSet<String> set = getHypoSetByWord(words.get(0));
        if (set == null) {
            return null;
        }
        for (int i = 1; i < words.size(); i += 1) {
            TreeSet x = getHypoSetByWord(words.get(i));
            if (x == null) {
                return null;
            }
            set.retainAll(x);
        }
        ArrayList<String> returnList = new ArrayList<>(set);
        Collections.sort(returnList);
        return returnList;
    }

}
