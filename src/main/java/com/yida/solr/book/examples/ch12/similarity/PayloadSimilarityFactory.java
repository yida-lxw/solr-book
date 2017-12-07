package com.yida.solr.book.examples.ch12.similarity;

import org.apache.lucene.search.similarities.Similarity;
import org.apache.solr.search.similarities.DefaultSimilarityFactory;

/**
 * Created by Lanxiaowei
 */
public class PayloadSimilarityFactory extends DefaultSimilarityFactory {
    @Override
    public Similarity getSimilarity() {
        return new PayloadSimilarity();
    }
}
