package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {

    private NGramMap map;
    private constructGraph graph;
    public HyponymsHandler(NGramMap map, constructGraph graph) {
        this.map = map;
        this.graph = graph;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        int k = q.k();
        List<String> allHypo = graph.getHypo(words);
        if (allHypo == null || allHypo.size() == 0) {
            return "[]";
        }
        if (k == 0) {
            String response = new String();
            response += "[";
            for (int i = 0; i < allHypo.size() - 1; i += 1) {
                response += allHypo.get(i);
                response += ", ";
            }
            response += allHypo.get(allHypo.size() - 1);
            response += "]";
            return response;
        } else {
            TreeMap<Double, ArrayList<String>> countToWord = new TreeMap<>();
            TreeSet<Double> allCounts = new TreeSet<>();
            ArrayList<String> result = new ArrayList<>();
            for (String x: allHypo) {
                if (map.countHistory(x, startYear, endYear).years().size() == 0) {
                    continue;
                }
                TimeSeries countTs = map.countHistory(x, startYear, endYear);
                double sum = 0;
                for (double count : countTs.data()) {
                    sum += count;
                }
                allCounts.add(sum);
                if (countToWord.containsKey(sum)) {
                    countToWord.get(sum).add(x);
                } else {
                    countToWord.put(sum, new ArrayList<>());
                    countToWord.get(sum).add(x);
                }
            }

            String response = new String();
            response += "[";
            if (allCounts.size() > 0) {
                List<Double> arr = new ArrayList<>(allCounts);
                Collections.sort(arr, Collections.reverseOrder());
                int listSize = 0;
                while (listSize < k && listSize < allCounts.size()) {
                    for (double count: arr) {
                        for (String word :countToWord.get(count)) {
                            result.add(word);
                            listSize += 1;
                            if (listSize >= k || listSize >= allCounts.size()) {
                                break;
                            }
                        }
                        if (listSize >= k || listSize >= allCounts.size()) {
                            break;
                        }
                    }
                }
                Collections.sort(result);
                for (int i = 0; i < result.size() - 1; i += 1) {
                    response += result.get(i);
                    response += ", ";
                }
                response += result.get(result.size() - 1);
            }
            response += "]";
            return response;

        }
    }


}
