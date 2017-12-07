package com.yida.solr.book.examples.ch12.customsort.fieldtype;

import com.yida.solr.book.examples.ch12.customsort.comparator.RankFieldComparatorSource;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.SortField;
import org.apache.lucene.uninverting.UninvertingReader;
import org.apache.solr.response.TextResponseWriter;
import org.apache.solr.schema.FieldType;
import org.apache.solr.schema.SchemaField;

import java.io.IOException;

/**
 * Created by Lanxiaowei
 * 自定义域类型
 */
public class RankFieldType extends FieldType {
    public UninvertingReader.Type getUninversionType(SchemaField schemaField) {
        return UninvertingReader.Type.LONG;
    }

    public void write(TextResponseWriter writer, String name, IndexableField indexableField) throws IOException {
        writer.writeStr(name, indexableField.stringValue(), false);
    }

    @Override
    public SortField getSortField(SchemaField field, boolean top) {
        return new SortField(field.getName(),
                new RankFieldComparatorSource(), top);
    }
}
