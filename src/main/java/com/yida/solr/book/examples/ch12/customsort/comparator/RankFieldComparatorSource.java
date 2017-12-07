package com.yida.solr.book.examples.ch12.customsort.comparator;

import com.yida.solr.book.examples.ch12.customsort.listener.RankUpdateListener;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.FieldComparator;
import org.apache.lucene.search.FieldComparatorSource;
import org.apache.lucene.search.LeafFieldComparator;
import org.apache.lucene.search.Scorer;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 */
public class RankFieldComparatorSource extends FieldComparatorSource {
    public FieldComparator newComparator(String fieldname, int numHits,
             int sortPos, boolean reversed) throws IOException {
        return new RankFieldComparator(numHits);
    }

    public static final class RankFieldComparator extends FieldComparator<Integer> implements LeafFieldComparator {
        private final int[] docIDs;
        private int docBase;
        private int bottom;
        private int topValue;

        RankFieldComparator(int numHits) {
            docIDs = new int[numHits];
        }

        public int compare(int slot1, int slot2) {
            return getRank(docIDs[slot1]) - getRank(docIDs[slot2]);
        }

        public void setTopValue(Integer value) {
            this.topValue = getRank(value);
        }

        public Integer value(int slot) {
            return getRank(docIDs[slot]);
        }

        public LeafFieldComparator getLeafComparator(LeafReaderContext context) throws IOException {
            this.docBase = context.docBase;
            return this;
        }

        public void setBottom(int slot) {
            this.bottom = docIDs[slot];
        }

        public int compareBottom(int doc) throws IOException {
            return getRank(bottom) - getRank(docBase + doc);
        }

        public int compareTop(int doc) throws IOException {
            return this.topValue - getRank(docBase + doc);
        }

        public void copy(int slot, int doc) throws IOException {
            docIDs[slot] = docBase + doc;
        }

        public void setScorer(Scorer scorer) {

        }

        private Integer getRank(int docId) {
            return RankUpdateListener.getRank(docId);
        }
    }
}
