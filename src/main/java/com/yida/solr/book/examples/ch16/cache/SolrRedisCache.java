package com.yida.solr.book.examples.ch16.cache;

import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.core.SolrCore;
import org.apache.solr.search.CacheRegenerator;
import org.apache.solr.search.SolrCache;
import org.apache.solr.search.SolrCacheBase;
import org.apache.solr.search.SolrIndexSearcher;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class SolrRedisCache<K, V> extends SolrCacheBase implements SolrCache<K, V> {
    public static Logger log = Logger.getLogger(SolrRedisCache.class.getName());
    private static class CumulativeStats {
        AtomicLong lookups = new AtomicLong();
        AtomicLong hits = new AtomicLong();
        AtomicLong inserts = new AtomicLong();
        AtomicLong evictions = new AtomicLong();
    }

    private CumulativeStats stats;

    private long lookups;
    private long hits;
    private long inserts;
    private long evictions;
    private long warmupTime = 0;
    //private MemcachedClient memcachedClient;
    private RedisCache cache;

    private String name;
    private int port;
    private String keyPrefix = "";
    private String description = "Redis Cache";

    public Object init(Map args, Object persistence, CacheRegenerator regenerator) {
        super.init(args, regenerator);
        name = (String) args.get("name");

        String prefix = (String) args.get("keyPrefix");
        if (prefix != null) {
            keyPrefix = prefix;
        }

        //获取Redis Server的主机ip或者域名
        String host = (String) args.get("host");
        if (host == null) {
            host = "localhost";
        }

        //获取Redis Server主机监听的端口号
        String portStr = (String) args.get("port");
        if (portStr == null) {
            port = 6379;
        } else {
            port = Integer.parseInt(portStr);
        }
        description = "RedisCache(" + host + ", Port:" + port + ", keyPrefix:" + keyPrefix + ")";
        try {
            cache = new RedisCache(host, port);
        } catch (java.io.UnsupportedEncodingException e) {

        }
        if (persistence == null) {
            persistence = new CumulativeStats();
        }
        stats = (CumulativeStats) persistence;
        return persistence;
    }

    public String name() {
        return name;
    }

    public int size() {
        return (int) cache.size();
    }

    public V put(K key, V value) {
        if (getState() == State.LIVE) {
            stats.inserts.incrementAndGet();
        }
        try {
            cache.put(toKeyString(key), value);
            inserts++;
        } catch (Exception e) {
            System.out.println(e);
        }
        return value;
    }

    public V get(K key) {
        V val = null;
        try {
            val = (V) cache.get(toKeyString(key));
        } catch (java.io.UnsupportedEncodingException e) {

        }
        if (getState() == State.LIVE) {
            lookups++;
            stats.lookups.incrementAndGet();
            if (val != null) {
                hits++;
                stats.hits.incrementAndGet();
            }
        }
        return val;
    }

    private String toValueString(V value) {
        return keyPrefix + ":" + value.hashCode();
    }

    private String toKeyString(K key) {
        return keyPrefix + ":" + key.hashCode();
    }

    public void clear() {
        try {
            cache.clear();
        } catch (java.io.UnsupportedEncodingException e) {
            return;
        }
    }

    public void warm(SolrIndexSearcher searcher, SolrCache old) {
    }

    public void close() {
    }


    //////////////////////// SolrInfoMBeans methods //////////////////////
    public String getName() {
        return SolrRedisCache.class.getName();
    }

    public String getVersion() {
        return SolrCore.version;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return Category.CACHE;
    }

    public String getSourceId() {
        return "$Id: SolrRedisCache.java $";
    }

    public String getSource() {
        return "";
    }

    public URL[] getDocs() {
        return null;
    }

    public NamedList getStatistics() {
        NamedList lst = new SimpleOrderedMap();
        lst.add("lookups", lookups);
        lst.add("hits", hits);
        lst.add("hitratio", calcHitRatio(lookups, hits));
        lst.add("inserts", inserts);
        lst.add("evictions", evictions);
        lst.add("size", size());
        lst.add("warmupTime", warmupTime);
        long clookups = stats.lookups.get();
        long chits = stats.hits.get();
        lst.add("cumulative_lookups", clookups);
        lst.add("cumulative_hits", chits);
        lst.add("cumulative_hitratio", calcHitRatio(clookups, chits));
        lst.add("cumulative_inserts", stats.inserts.get());
        lst.add("cumulative_evictions", stats.evictions.get());
        //lst.add("indexVersion", indexVersion);
        return lst;
    }
}
