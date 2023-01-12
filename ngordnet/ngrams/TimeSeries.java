package ngordnet.ngrams;

import java.util.*;

/** An object for mapping a year number (e.g. 1996) to numerical data. Provides
 *  utility methods useful for data analysis.
 *  @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /** Constructs a new empty TimeSeries. */
    public TimeSeries() {
        super();
    }

    /** Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     *  inclusive of both end points. */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        List<Integer> years = ts.years();
        for (int year : years) {
            if (year < startYear || year > endYear) {
                continue;
            }
            this.put(year, ts.get(year));
        }
    }

    /** Returns all years for this TimeSeries (in any order). */
    public List<Integer> years() {
        Set<Integer> keys = keySet();
        List<Integer> l = new ArrayList<Integer>(keys);
        return l;
    }

    /** Returns all data for this TimeSeries (in any order).
     *  Must be in the same order as years(). */
    public List<Double> data() {
        List<Double> lst = new ArrayList<>();
        for (int key: years()) {
            lst.add(get(key));
        }
        return lst;
    }

    /** Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     *  each year, sum the data from this TimeSeries with the data from TS. Should return a
     *  new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries plus(TimeSeries ts) {
        if (this.years().size() == 0) {
            return ts;
        }

        TimeSeries mergedTs = new TimeSeries(this, firstKey(), lastKey());
        for (int year : ts.years()) {
            if (!this.containsKey(year)) {
                mergedTs.put(year, ts.get(year));
            } else {
                mergedTs.put(year, this.get(year) + ts.get(year));
            }
        }
        return mergedTs;
    }

    /** Returns the quotient of the value for each year this TimeSeries divided by the
      *  value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
      *  throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
      *  Should return a new TimeSeries (does not modify this TimeSeries). */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries quotientTs = new TimeSeries();
        for (int year : this.years()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException();
            } else {
                quotientTs.put(year, this.get(year) / ts.get(year));
            }
        }
        return quotientTs;
    }
}
