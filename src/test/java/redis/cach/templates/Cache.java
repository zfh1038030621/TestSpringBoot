package redis.cach.templates;

import java.util.List;

/**
 * cache操作接口
 * 
 * @author Robin 2014年6月28日 下午2:22:54
 */
public interface Cache {

    /**
     * 从缓存获取单条数据
     * 
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 向缓存put数据
     * 
     * @param key
     * @param value
     * @return
     */
    boolean put(String key, Object value);

    /**
     * remove数据
     * 
     * @param key
     * @return
     */
    boolean remove(String key);
    
    /**
     * 查询存在的keys，可以模式匹配如： *多个字符 ?单个字符
     * keys不支持序列化
     * @param key
     * @return
     */
    List<String> keysNS(String key);
    
    /**
     * 设置<key,value>,key和value不支持序列化
     * @param key
     * @param value
     * @return
     */
    boolean setNS(String key, String value);
    
    /**
     * 删除key，不支持序列化
     * @param key
     * @return
     */
    boolean delNS(final String key);
    
    /**
     * 获取value，不支持序列化
     */
    String getNS(final String key);
    
    public boolean delKeysLike(final String likeKey) ;

}
