package com.yida.solr.book.examples.ch16.cache;

import net.spy.memcached.CachedData;
import net.spy.memcached.transcoders.SerializingTranscoder;
import redis.clients.jedis.BinaryJedis;

public class RedisCache<K, V> {
    private BinaryJedis jedis;
    private SerializingTranscoder encoder;
    private final byte[] hashName;
    private final byte[] dataField;
    private final byte[] flagField;

    public RedisCache(String host, int port) throws java.io.UnsupportedEncodingException {
        jedis = new BinaryJedis(host, port);
        encoder = new SerializingTranscoder();
        hashName = "rediscache".getBytes("UTF-8");
        flagField = "flag".getBytes("UTF-8");
        dataField = "data".getBytes("UTF-8");
    }

    public V put(String key, V value) throws java.io.UnsupportedEncodingException {
        CachedData cd = encoder.encode(value);
        byte[] bytevalue = cd.getData();

        String flagValue = Integer.toString(cd.getFlags());

        jedis.hset(hashName, buildKey(key, dataField), bytevalue);
        jedis.hset(hashName, buildKey(key, flagField), flagValue.getBytes("UTF-8"));

        return value;
    }

    public V get(String key) throws java.io.UnsupportedEncodingException {
        byte[] dataValue = jedis.hget(hashName, buildKey(key, dataField));
        if (dataValue == null) {
            return null;
        }
        byte[] _flagValue = jedis.hget(hashName, buildKey(key, flagField));

        int flagValue = new Integer(new String(_flagValue, "UTF-8"));
        CachedData cd = new CachedData(flagValue, dataValue, CachedData.MAX_SIZE);
        V res = (V) encoder.decode(cd);
        return res;
    }

    private byte[] buildKey(String key, byte[] suffix) throws java.io.UnsupportedEncodingException {
        byte[] keyByte = key.getBytes("UTF-8");
        byte[] res = new byte[keyByte.length + suffix.length];
        System.arraycopy(keyByte, 0, res, 0, keyByte.length);
        System.arraycopy(suffix, 0, res, keyByte.length - 1, suffix.length);
        return res;
    }

    public long size() {
        return jedis.hlen(hashName);
    }

    public void clear() throws java.io.UnsupportedEncodingException {
        jedis.del(hashName);
    }
}