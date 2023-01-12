package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        correct.addLast(5);
        correct.addLast(10);
        correct.addLast(15);

        broken.addLast(5);
        broken.addLast(10);
        broken.addLast(15);

        assertEquals(correct.size(), broken.size());

        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
        assertEquals(correct.removeLast(), broken.removeLast());
    }
    @Test
    public void randomizedTest(){
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
                //System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // getLast
                if (broken.size() == 0) {
                    continue;
                }
                int lastValCorrect = correct.getLast();
                int lastValBroken = broken.getLast();
                assertEquals(lastValCorrect, lastValBroken);
            }
            else if (operationNumber == 2){
                // removeLast
                if (broken.size() == 0) {
                    continue;
                }
                int lastValCorrect = correct.getLast();
                int lastValBroken = broken.getLast();
                assertEquals(lastValCorrect, lastValBroken);
                //System.out.println("removeLast(" + lastValCorrect + ")");
            }
        }
    }
    /* else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            }*/


}
