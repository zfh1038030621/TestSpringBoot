package com.zfh.boot_redis.templates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


/**
 * 缓存默认实现模板
 * 
 * @author Robin 2014年6月28日 下午2:24:49
 */
public class RedisCacheTemplate implements Cache, InitializingBean {

    // 日志对象
    protected static Logger       log     = LoggerFactory.getLogger(RedisCacheTemplate.class);
    private final static int      dbIndex = 0;
    private String                prefix;                                                     // key的前缀，加前缀是为了防止key的重复或者生成新的缓存，该值在整个应用里面不能重复
    private long                  expiry;                                                     // 缓存过期时间(秒)

    RedisTemplate<String, Object> cacheRedis;

    public RedisTemplate<String, Object> getCacheRedis(){
        return cacheRedis;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        cacheRedis.keys();
        if (cacheRedis == null) {
            throw new Exception("\"cacheRedis\" must be configurated");
        }
        if (StringUtils.isEmpty(prefix)) {
            throw new Exception("\"prefix\" must be configurated");
        }
        if (expiry == 0) {
            throw new Exception("\"expiry\" must be configurated or cannot be zero");
        }

    }

    @Override
    public Object get(final String key) {
        Long time = System.currentTimeMillis();
        Object result = cacheRedis.execute(new RedisCallback<Object>() {

            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                RedisSerializer serializer = cacheRedis.getDefaultSerializer();
                // byte[] keyBytes = serializer.serialize(rebuildKey(key));
                byte[] keyBytes = rebuildKey(key).getBytes();// modify by wma 20150302
                byte[] valueBytes = connection.get(keyBytes);
                if (valueBytes == null) {
                    return null;
                }
                Object object = serializer.deserialize(valueBytes);
                return object;
            }
        });
        return result;
    }

    @Override
    public boolean put(final String key, final Object value) {
        Long time = System.currentTimeMillis();
        boolean result = cacheRedis.execute(new RedisCallback<Boolean>() {

            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                RedisSerializer serializer = cacheRedis.getDefaultSerializer();
                // byte[] keyBytes = serializer.serialize(rebuildKey(key));
                byte[] keyBytes = rebuildKey(key).getBytes(); // modify by wma 20150302
                byte[] valueBytes = serializer.serialize(value);
                connection.setEx(keyBytes, expiry, valueBytes);
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean remove(final String key) {
        // cacheRedis.delete(rebuildKey(key));
        boolean result = (boolean) cacheRedis.execute(new RedisCallback<Object>() {

            public Boolean doInRedis(RedisConnection connection) {
                connection.select(dbIndex);
                final byte[] rawKey = rebuildKey(key).getBytes();
                long flag = connection.del(rawKey);
                if (get(key) != null && flag <= 0) {
                    log.info("remove cache fail : key = " + key);
                    return false;
                }
                return true;
            }
        });
        return result;
    }

    @Override
    public boolean setNS(final String key, final String value) {
        boolean result = cacheRedis.execute(new RedisCallback<Boolean>() {

            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                connection.setEx(rebuildKey(key).getBytes(), expiry, value.getBytes());
                return true;
            }
        });
        return result;
    }

    @Override
    public List<String> keysNS(final String key) {
        List<String> result = cacheRedis.execute(new RedisCallback<List<String>>() {

            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                Set<byte[]> valueBytes = connection.keys(rebuildKey(key).getBytes());
                if (valueBytes == null) {
                    return null;
                }
                List<String> objs = new ArrayList<String>();
                for (byte[] b : valueBytes) {
                    objs.add(new String(b));
                }
                return objs;
            }
        });
        return result;
    }

    @Override
    public boolean delNS(final String key) {
        boolean result = cacheRedis.execute(new RedisCallback<Boolean>() {

            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                connection.del(rebuildKey(key).getBytes());
                return true;
            }
        });
        return result;
    }

    @Override
    public String getNS(final String key) {
        String result = cacheRedis.execute(new RedisCallback<String>() {

            @Override
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.select(dbIndex);
                byte[] valueBytes = connection.get(rebuildKey(key).getBytes());
                if (valueBytes == null) {
                    return null;
                }
                String object = new String(valueBytes);
                return object;
            }
        });
        return result;
    }

    /**
     * 重新生成key，加入前缀
     * 
     * @param key
     * @return
     */
    private String rebuildKey(String key) {
        return prefix + "_" + key;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setCacheRedis(RedisTemplate cacheRedis) {
        this.cacheRedis = cacheRedis;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean delKeysLike(final String likeKey) {

        return (boolean) cacheRedis.execute(new RedisCallback<Object>() {

            public Object doInRedis(RedisConnection connection) {
                connection.select(dbIndex);
                byte[] keyBytes = rebuildKey(likeKey + "*").getBytes();// modify by wma 20150302
                Set<byte[]> keysArray = connection.keys(keyBytes);
                while (keysArray.iterator().hasNext()) {
                    connection.del(keysArray.iterator().next());
                }

                return true;
            }
        }, true);
    }

}
