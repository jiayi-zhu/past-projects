package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeAList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeAListConstruction();
    }

    public static void timeAListConstruction() {
        AList<Integer> nList = new AList<Integer>();
        AList<Double> timeList = new AList<Double>();
        AList<Integer> countList = new AList<Integer>();
        for (int n = 1000; n <= 128000; n *= 2) {
            nList.addLast(n);
            AList x = new AList();
            int count = 0;
            Stopwatch sw = new Stopwatch();
            while (x.size() < n) {
                x.addLast(1);
                count += 1;
            }
            double timeInSeconds = sw.elapsedTime();
            countList.addLast(count);
            timeList.addLast((timeInSeconds));
        }
        printTimingTable(nList, timeList, countList);
    }

    }

