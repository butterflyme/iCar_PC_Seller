/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.dao.cache.memcached;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.CASOperation;
import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.Transcoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Memcached缓存服务工具类，封装xmemcached的抽象方法。集中处理缓存异常。使客户端代码调用干净。
 * 
 * @author dengjie
 * @since 1.0
 */
public class MemcachedUtils {

    private final Log log = LogFactory.getLog(MemcachedUtils.class);
    /**
     * 装配memcachedClient
     */
    private MemcachedClient memcachedClient;

    /**
     * spring注入memcachedClient对象服务
     * 
     * @param memcachedClient
     *            memcachedClient在application-memcached配置的bean ，提供对memcached的JAVA客户端操作
     */
    public void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    /**
     * MemcachedUtils可以提供直接获取memcachedClient其引用，方便直接调用其方法
     * 
     * @return MemcachedClient引用
     */
    public MemcachedClient getMemcachedClient() {
        return memcachedClient;
    }

    /**
     * 利用memcachedClient取memcached中的指定键的值
     * 
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @param key
     *            指定key
     * @param timeout
     *            设置超时时间,默认是1000毫秒
     * @param transcoder
     *            设置字节数组转换成Object的转换器，默认是配置的序列化转换器: net.rubyeye.xmemcached.transcoders.SerializingTranscoder
     * @return 键的值
     */
    public <T> T get(final String key, final long timeout, final Transcoder<T> transcoder) {
        T result = null;
        try {
            result = memcachedClient.get(key, timeout, transcoder);
        } catch (TimeoutException e) {
            log.error("get操作超时", e);
        } catch (InterruptedException e) {
            log.error("get操作被中断 ", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient取memcached中的指定键的值.字节数组转换成Object的转换器，默认是配置的序列化转换器:
     * net.rubyeye.xmemcached.transcoders.SerializingTranscoder
     * 
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @param key
     *            指定key
     * @param timeout
     *            设置超时时间,默认是1000毫秒
     * @return 键的值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key, final long timeout) {
        T result = null;
        try {
            result = (T) memcachedClient.get(key, timeout);
        } catch (TimeoutException e) {
            log.error("get操作超时", e);
        } catch (InterruptedException e) {
            log.error("get操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient取memcached中的指定键的值.字节数组转换成Object的转换器，默认是配置的序列化转换器:
     * net.rubyeye.xmemcached.transcoders.SerializingTranscoder 采用默认的超时时间：1000毫秒
     * 
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @param key
     *            指定key
     * @return 键的值
     */
    @SuppressWarnings("unchecked")
    public <T> T get(final String key) {
        T result = null;
        try {
            result = (T) memcachedClient.get(key);
        } catch (TimeoutException e) {
            log.error("get操作超时", e);
        } catch (InterruptedException e) {
            log.error("get操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient取memcached中的指定键的值并且重新设置键值的超时时间
     * 
     * @param key
     *            指定key
     * @param newExp
     *            过期时间
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 键的值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndTouch(final String key, final int newExp) {
        T result = null;
        try {
            result = (T) memcachedClient.getAndTouch(key, newExp);
        } catch (TimeoutException e) {
            log.error("getAndTouch操作超时", e);
        } catch (InterruptedException e) {
            log.error("getAndTouch操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient取memcached中的指定键的值并且重新设置键值的超时时间
     * 
     * @param key
     *            指定key
     * @param newExp
     *            过期时间
     * @param opTimeout
     *            操作超时时间
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 键的值
     */
    @SuppressWarnings("unchecked")
    public <T> T getAndTouch(final String key, final int newExp, final long opTimeout) {
        T result = null;
        try {
            result = (T) memcachedClient.getAndTouch(key, newExp, opTimeout);
        } catch (TimeoutException e) {
            log.error("getAndTouch操作超时", e);
        } catch (InterruptedException e) {
            log.error("getAndTouch操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient重新设置键值的超时时间
     * 
     * @param key
     *            指定key
     * @param newExp
     *            过期时间
     * @return 是否成功
     */
    public boolean touch(final String key, final int newExp) {
        boolean result = false;
        try {
            result = memcachedClient.touch(key, newExp);
        } catch (TimeoutException e) {
            log.error("touch操作超时", e);
        } catch (InterruptedException e) {
            log.error("touch操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient重新设置键值的超时时间
     * 
     * @param key
     *            指定key
     * @param newExp
     *            过期时间
     * @param opTimeout
     *            操作超时时间
     * @return 是否成功
     */
    public boolean touch(final String key, final int newExp, final long opTimeout) {
        boolean result = false;
        try {
            result = memcachedClient.touch(key, newExp, opTimeout);
        } catch (TimeoutException e) {
            log.error("touch操作超时", e);
        } catch (InterruptedException e) {
            log.error("touch操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * 利用memcachedClient的gets方法取memcached中的指定集合键的值
     * 
     * @param keyCollections
     *            封装键的集合
     * @param timeout
     *            超时时间，默认为1000毫秒
     * @param transcoder
     *            转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return Map<String, GetsResponse<Object>> GetsResponse中是对应键的值与其CAS值
     */
    public <T> Map<String, GetsResponse<T>> gets(Collection<String> keyCollections, final long timeout,
            final Transcoder<T> transcoder) {
        Map<String, GetsResponse<T>> resultsMap = new HashMap<String, GetsResponse<T>>();
        try {
            resultsMap = memcachedClient.gets(keyCollections, timeout, transcoder);
        } catch (TimeoutException e) {
            log.error("gets操作超时", e);
        } catch (InterruptedException e) {
            log.error("gets操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return resultsMap;
    }

    /**
     * 利用memcachedClient的gets方法取memcached中的指定集合键的值
     * 
     * @param keyCollections
     *            封装键的集合
     * @param timeout
     *            超时时间，默认为1000毫秒
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return Map<String, GetsResponse<Object>> GetsResponse中是对应键的值与其CAS值
     */
    public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections, final long timeout) {
        Map<String, GetsResponse<T>> resultsMap = new HashMap<String, GetsResponse<T>>();
        try {
            resultsMap = memcachedClient.gets(keyCollections, timeout);
        } catch (TimeoutException e) {
            log.error("gets操作超时", e);
        } catch (InterruptedException e) {
            log.error("gets操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return resultsMap;
    }

    /**
     * 利用memcachedClient的gets方法取memcached中的指定集合键的值
     * 
     * @param keyCollections
     *            封装键的集合
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return Map<String, GetsResponse<Object>> GetsResponse中是对应键的值与其CAS值
     */
    public <T> Map<String, GetsResponse<T>> gets(final Collection<String> keyCollections) {
        Map<String, GetsResponse<T>> resultsMap = new HashMap<String, GetsResponse<T>>();
        try {
            resultsMap = memcachedClient.gets(keyCollections);
        } catch (TimeoutException e) {
            log.error("gets操作超时", e);
        } catch (InterruptedException e) {
            log.error("gets操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return resultsMap;
    }

    /**
     * 利用memcachedClient的gets方法取memcached中的指定键的CAS值
     * 
     * @param key
     *            键
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return GetsResponse<T>中是对应键的值与其CAS值
     */
    public <T> GetsResponse<T> gets(final String key) {
        GetsResponse<T> getsResponse = null;
        try {
            getsResponse = memcachedClient.gets(key);
        } catch (TimeoutException e) {
            log.error("gets操作超时", e);
        } catch (InterruptedException e) {
            log.error("gets操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return getsResponse;
    }

    /**
     * 利用memcachedClient的gets方法取memcached中的指定键的CAS值
     * 
     * @param key
     *            键
     * @param timeout
     *            超时时间
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return GetsResponse<T>中是对应键的值与其CAS值
     */
    public <T> GetsResponse<T> gets(final String key, final long timeout) {
        GetsResponse<T> getsResponse = null;
        try {
            getsResponse = memcachedClient.gets(key, timeout);
        } catch (TimeoutException e) {
            log.error("gets操作超时", e);
        } catch (InterruptedException e) {
            log.error("gets操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return getsResponse;
    }

    /**
     * 利用memcachedClient新增memcached中的键和值 add方法：仅当存储空间中不存在键相同的数据时才保存
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param timeout
     *            超时时间
     * @return 增加是否成功，true:成功，false:失败
     */
    public boolean add(final String key, final int exp, final Object value, final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.add(key, exp, value, timeout);
            if (!success) {
                log.warn("增加键：" + key + " 的值为: " + value + " 操作失败,可能是已经存在相同的键");
            }
        } catch (TimeoutException e) {
            log.error("add操作超时", e);
        } catch (InterruptedException e) {
            log.error("add操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient新增memcached中的键和值,采用默认的超时时间1000毫秒 add方法：仅当存储空间中不存在键相同的数据时才保存
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @return 增加是否成功，true:成功，false:失败
     */
    public boolean add(final String key, final int exp, final Object value) {
        boolean success = false;
        try {
            success = memcachedClient.add(key, exp, value);
            if (!success) {
                log.warn("增加键：" + key + " 的值为: " + value + " 操作失败,可能是已经存在相同的键");
            }
        } catch (TimeoutException e) {
            log.error("add操作超时", e);
        } catch (InterruptedException e) {
            log.error("add操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient新增memcached中的键和值,采用默认的超时时间1000毫秒 add方法：仅当存储空间中不存在键相同的数据时才保存
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 增加是否成功，true:成功，false:失败
     */
    public <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        boolean success = false;
        try {
            success = memcachedClient.add(key, exp, value, transcoder);
            if (!success) {
                log.warn("增加键：" + key + " 的值为: " + value + " 操作失败,可能是已经存在相同的键");
            }
        } catch (TimeoutException e) {
            log.error("add操作超时", e);
        } catch (InterruptedException e) {
            log.error("add操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient新增memcached中的键和值 add方法：仅当存储空间中不存在键相同的数据时才保存
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            转换器
     * @param timeout
     *            超时时间，默认是1000毫秒
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 增加是否成功，true:成功，false:失败
     */
    public <T> boolean add(final String key, final int exp, final T value, final Transcoder<T> transcoder,
            final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.add(key, exp, value, transcoder, timeout);
            if (!success) {
                log.warn("增加键：" + key + " 的值为: " + value + " 操作失败,可能是已经存在相同的键");
            }
        } catch (TimeoutException e) {
            log.error("add操作超时", e);
        } catch (InterruptedException e) {
            log.error("add操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient新增memcached中的键和值 add方法：仅当存储空间中不存在键相同的数据时才保存 不需要等待响应的add操作
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     */
    public void addWithNoReply(final String key, final int exp, final Object value) {
        try {
            memcachedClient.addWithNoReply(key, exp, value);
        } catch (InterruptedException e) {
            log.error("addWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient新增memcached中的键和值,采用默认的超时时间1000毫秒 addWithNoReply方法：仅当存储空间中不存在键相同的数据时才保存 不需要等待响应的add操作
     * 
     * @param key
     *            新增的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     */
    public <T> void addWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        try {
            memcachedClient.addWithNoReply(key, exp, value, transcoder);
        } catch (InterruptedException e) {
            log.error("addWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient设置memcached中的键和值 set方法：与add和replace不同，无论何时都保存
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param timeout
     *            超时时间
     * @return 设置是否成功，true:成功，false:失败
     */
    public boolean set(final String key, final int exp, final Object value, final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.set(key, exp, value, timeout);
            if (!success) {
                log.warn("设置键：" + key + " 的值为: " + value + " 操作失败");
            }
        } catch (TimeoutException e) {
            log.error("set键值操作超时", e);
        } catch (InterruptedException e) {
            log.error("set键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient设置memcached中的键和值,采用超时时间为默认的1000毫秒 set方法：与add和replace不同，无论何时都保存
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @return 设置是否成功，true:成功，false:失败
     */
    public boolean set(final String key, final int exp, final Object value) {
        boolean success = false;
        try {
            success = memcachedClient.set(key, exp, value);
            if (!success) {
                log.warn("设置键：" + key + " 的值为: " + value + " 操作失败");
            }
        } catch (TimeoutException e) {
            log.error("set键值操作超时", e);
        } catch (InterruptedException e) {
            log.error("set键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient设置memcached中的键和值,采用超时时间为默认的1000毫秒 set方法：与add和replace不同，无论何时都保存
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            字节流转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 设置是否成功，true:成功，false:失败
     */
    public <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        boolean success = false;
        try {
            success = memcachedClient.set(key, exp, value, transcoder);
            if (!success) {
                log.warn("设置键：" + key + " 的值为: " + value + " 操作失败");
            }
        } catch (TimeoutException e) {
            log.error("set键值操作超时", e);
        } catch (InterruptedException e) {
            log.error("set键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient设置memcached中的键和值 set方法：与add和replace不同，无论何时都保存
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            字节流转换器
     * @param timeout
     *            超时时间
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 设置是否成功，true:成功，false:失败
     */
    public <T> boolean set(final String key, final int exp, final T value, final Transcoder<T> transcoder,
            final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.set(key, exp, value, transcoder, timeout);
            if (!success) {
                log.warn("设置键：" + key + " 的值为: " + value + " 操作失败");
            }
        } catch (TimeoutException e) {
            log.error("set键值操作超时", e);
        } catch (InterruptedException e) {
            log.error("set键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient设置memcached中的键和值 set方法：与add和replace不同，无论何时都保存 不需要响应的set操作
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     */
    public void setWithNoReply(final String key, final int exp, final Object value) {
        try {
            memcachedClient.setWithNoReply(key, exp, value);
        } catch (InterruptedException e) {
            log.error("setWithNoReply键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient设置memcached中的键和值 set方法：与add和replace不同，无论何时都保存 不需要响应的set操作
     * 
     * @param key
     *            设置的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            键对应的值
     * @param transcoder
     *            字节流转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     */
    public <T> void setWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        try {
            memcachedClient.setWithNoReply(key, exp, value, transcoder);
        } catch (InterruptedException e) {
            log.error("setWithNoReply键值操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值 replace方法：仅当存储空间中存在键相同的数据时才保存
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     * @param timeout
     *            超时时间
     * @return 更新是否成功，true:成功，false:失败
     */
    public boolean replace(final String key, final int exp, final Object value, final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.replace(key, exp, value, timeout);
            if (!success) {
                log.warn("替换更新键：" + key + " 的值为: " + value + " 操作失败，可能是键和值不存在");
            }
        } catch (TimeoutException e) {
            log.error("replace操作超时", e);
        } catch (InterruptedException e) {
            log.error("replace操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值,默认超时时间1000毫秒 replace方法：仅当存储空间中存在键相同的数据时才保存
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     * @return 更新是否成功，true:成功，false:失败
     */
    public boolean replace(final String key, final int exp, final Object value) {
        boolean success = false;
        try {
            success = memcachedClient.replace(key, exp, value);
            if (!success) {
                log.warn("替换更新键：" + key + " 的值为: " + value + " 操作失败，可能是键和值不存在");
            }
        } catch (TimeoutException e) {
            log.error("replace操作超时", e);
        } catch (InterruptedException e) {
            log.error("replace操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值 replace方法：仅当存储空间中存在键相同的数据时才保存
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     * @param transcoder
     *            转换器
     * @param timeout
     *            超时时间
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 更新是否成功，true:成功，false:失败
     */
    public <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder,
            final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.replace(key, exp, value, transcoder, timeout);
            if (!success) {
                log.warn("替换更新键：" + key + " 的值为: " + value + " 操作失败，可能是键和值不存在");
            }
        } catch (TimeoutException e) {
            log.error("replace操作超时", e);
        } catch (InterruptedException e) {
            log.error("replace操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值,默认超时时间1000毫秒 replace方法：仅当存储空间中存在键相同的数据时才保存
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     * @param transcoder
     *            转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     * @return 更新是否成功，true:成功，false:失败
     */
    public <T> boolean replace(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        boolean success = false;
        try {
            success = memcachedClient.replace(key, exp, value, transcoder);
            if (!success) {
                log.warn("替换更新键：" + key + " 的值为: " + value + " 操作失败，可能是键和值不存在");
            }
        } catch (TimeoutException e) {
            log.error("replace操作超时", e);
        } catch (InterruptedException e) {
            log.error("replace操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值 replace方法：仅当存储空间中存在键相同的数据时才保存 不需要响应的replace操作
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     */
    public void replaceWithNoReply(final String key, final int exp, final Object value) {
        try {
            memcachedClient.replaceWithNoReply(key, exp, value);
        } catch (InterruptedException e) {
            log.error("replaceWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient的replace方法更新memcached中键的值 replace方法：仅当存储空间中存在键相同的数据时才保存 不需要响应的replace方法
     * 
     * @param key
     *            要替换的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param value
     *            指定替换的值
     * @param transcoder
     *            转换器
     * @param <T>
     *            memcachedClient客户端代码的泛型指定类型
     */
    public <T> void replaceWithNoReply(final String key, final int exp, final T value, final Transcoder<T> transcoder) {
        try {
            memcachedClient.replaceWithNoReply(key, exp, value, transcoder);
        } catch (InterruptedException e) {
            log.error("replaceWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient删除memcached缓存中的指定键值
     * 
     * @param key
     *            指定要删除的键
     * @return 删除是否成功，true:成功，false:失败
     */
    public boolean delete(final String key) {
        boolean success = false;
        try {
            success = memcachedClient.delete(key);
            if (!success) {
                log.warn("删除键： " + key + " 操作失败，可能是由于该键值不存在");
            }
        } catch (TimeoutException e) {
            log.error("delete操作超时", e);
        } catch (InterruptedException e) {
            log.error("delete操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient删除memcached缓存中的指定键值 不需要响应的delete操作
     * 
     * @param key
     *            指定要删除的键
     */
    public void deleteWithNoReply(final String key) {
        try {
            memcachedClient.deleteWithNoReply(key);
        } catch (InterruptedException e) {
            log.error("deleteWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient进行append操作，在已经存在的键值中的值的后缀加入value.默认的超时时间1000毫秒
     * 
     * @param key
     *            指定要append的键
     * @param value
     *            指定要append的值
     * @return 后缀增加是否成功，true:成功，false:失败
     */
    public boolean append(final String key, final Object value) {
        boolean success = false;
        try {
            success = memcachedClient.append(key, value);
            if (!success) {
                log.warn("后缀增加的键： " + key + " 操作失败，可能是由于该键值不存在");
            }
        } catch (TimeoutException e) {
            log.error("append操作超时", e);
        } catch (InterruptedException e) {
            log.error("append操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行append操作，在已经存在的键值中的值的后缀加入value.
     * 
     * @param key
     *            指定要append的键
     * @param value
     *            指定要append的值
     * @param timeout
     *            超时时间
     * @return 后缀增加是否成功，true:成功，false:失败
     */
    public boolean append(final String key, final Object value, final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.append(key, value, timeout);
            if (!success) {
                log.warn("后缀增加的键： " + key + " 操作失败，可能是由于该键值不存在");
            }
        } catch (TimeoutException e) {
            log.error("append操作超时", e);
        } catch (InterruptedException e) {
            log.error("append操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行append操作，在已经存在的键值中的值的后缀加入value. 不需要响应的append操作
     * 
     * @param key
     *            指定要append的键
     * @param value
     *            指定要append的值
     */
    public void appendWithNoReply(final String key, final Object value) {
        try {
            memcachedClient.appendWithNoReply(key, value);
        } catch (InterruptedException e) {
            log.error("appendWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient进行prepend操作，在已经存在的键值中的值的前缀加入value.默认的超时时间1000毫秒
     * 
     * @param key
     *            指定要prepend的键
     * @param value
     *            指定要prepend的值
     * @return 前缀增加是否成功，true:成功，false:失败
     */
    public boolean prepend(final String key, final Object value) {
        boolean success = false;
        try {
            success = memcachedClient.prepend(key, value);
            if (!success) {
                log.warn("前缀增加的键： " + key + " 操作失败，可能是由于该键值不存在");
            }
        } catch (TimeoutException e) {
            log.error("prepend操作超时", e);
        } catch (InterruptedException e) {
            log.error("prepend操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行prepend操作，在已经存在的键值中的值的前缀加入value.
     * 
     * @param key
     *            指定要prepend的键
     * @param value
     *            指定要prepend的值
     * @param timeout
     *            超时时间
     * @return 前缀增加是否成功，true:成功，false:失败
     */
    public boolean prepend(final String key, final Object value, final long timeout) {
        boolean success = false;
        try {
            success = memcachedClient.prepend(key, value, timeout);
            if (!success) {
                log.warn("前缀增加的键： " + key + " 操作失败，可能是由于该键值不存在");
            }
        } catch (TimeoutException e) {
            log.error("prepend操作超时", e);
        } catch (InterruptedException e) {
            log.error("prepend操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行prepend操作，在已经存在的键值中的值的前缀加入value. 不需要响应的prepend操作
     * 
     * @param key
     *            指定要prepend的键
     * @param value
     *            指定要prepend的值
     */
    public void prependWithNoReply(final String key, final Object value) {
        try {
            memcachedClient.prependWithNoReply(key, value);
        } catch (InterruptedException e) {
            log.error("prependWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 利用memcachedClient进行CAS操作（实现原子更新），所谓原子更新就是compare and set， 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值，memcached比对这个cas值与
     * 当前存储数据的cas值是否相等，如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用。XMemcached提供了对CAS协议的支持（无论是文本协议还是二进制协议），
     * CAS协议其实是分为两个步骤：获取CAS值和尝试更新，
     * 
     * @param key
     *            指定要CAS更新的键
     * @param exp
     *            期限时间
     * @param value
     *            需要更新的值
     * @param cas
     *            cas值，用来标示唯一
     * @return 前缀增加是否成功，true:成功，false:失败
     */
    public boolean cas(final String key, final int exp, final Object value, final long cas) {
        boolean success = false;
        try {
            success = memcachedClient.cas(key, exp, value, cas);
            if (!success) {
                log.error("CAS更新的键： " + key + " 操作失败，可能是由于该键值不存在，或是CAS值已经被修改了");
            }
        } catch (TimeoutException e) {
            log.error("prepend操作超时", e);
        } catch (InterruptedException e) {
            log.error("prepend操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行CAS操作（实现原子更新），所谓原子更新就是compare and set， 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值，memcached比对这个cas值与
     * 当前存储数据的cas值是否相等，如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用。XMemcached提供了对CAS协议的支持（无论是文本协议还是二进制协议），
     * CAS协议其实是分为两个步骤：获取CAS值和尝试更新，
     * 
     * @param key
     *            指定要CAS更新的键
     * @param cASOperation
     *            CASOpertion接口只有两个方法，一个是设置最大尝试次数的getMaxTries方法， 这里是尝试一次，如果尝试超过这个次数没有更新成功将抛出一个TimeoutException，
     *            如果你想无限尝试（理论上），可以将返回值设定为 Integer.MAX_VALUE；另一个方 法是根据当前获得的GetsResponse来决定更新数据的getNewValue方法，
     *            如果更新成功，这个方法返回的值将存储成功，这个方法的两个参数是最新一次gets返回的GetsResponse结果。
     * @return 前缀增加是否成功，true:成功，false:失败
     */
    public boolean cas(final String key, final CASOperation<Object> cASOperation) {
        boolean success = false;
        try {
            success = memcachedClient.cas(key, cASOperation);
            if (!success) {
                log.error("CAS更新的键： " + key + " 操作失败，可能是由于该键值不存在，或是CAS值已经被修改了");
            }
        } catch (TimeoutException e) {
            log.error("prepend操作超时", e);
        } catch (InterruptedException e) {
            log.error("prepend操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行CAS操作（实现原子更新），所谓原子更新就是compare and set， 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值，memcached比对这个cas值与
     * 当前存储数据的cas值是否相等，如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用。XMemcached提供了对CAS协议的支持（无论是文本协议还是二进制协议），
     * CAS协议其实是分为两个步骤：获取CAS值和尝试更新，
     * 
     * @param key
     *            指定要CAS更新的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param cASOperation
     *            CASOpertion接口只有两个方法，一个是设置最大尝试次数的getMaxTries方法， 这里是尝试一次，如果尝试超过这个次数没有更新成功将抛出一个TimeoutException，
     *            如果你想无限尝试（理论上），可以将返回值设定为 Integer.MAX_VALUE；另一个方 法是根据当前获得的GetsResponse来决定更新数据的getNewValue方法，
     *            如果更新成功，这个方法返回的值将存储成功，这个方法的两个参数是最新一次gets返回的GetsResponse结果。
     * 
     * @return 前缀增加是否成功，true:成功，false:失败
     */
    public boolean cas(final String key, final int exp, final CASOperation<Object> cASOperation) {
        boolean success = false;
        try {
            success = memcachedClient.cas(key, exp, cASOperation);
            if (!success) {
                log.error("CAS更新的键： " + key + " 操作失败，可能是由于该键值不存在，或是CAS值已经被修改了");
            }
        } catch (TimeoutException e) {
            log.error("prepend操作超时", e);
        } catch (InterruptedException e) {
            log.error("prepend操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return success;
    }

    /**
     * 利用memcachedClient进行CAS操作（实现原子更新），所谓原子更新就是compare and set， 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值，memcached比对这个cas值与
     * 当前存储数据的cas值是否相等，如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用。XMemcached提供了对CAS协议的支持（无论是文本协议还是二进制协议），
     * CAS协议其实是分为两个步骤：获取CAS值和尝试更新， 不需要响应的CAS操作
     * 
     * @param key
     *            指定要CAS更新的键
     * @param cASOperation
     *            CASOpertion接口只有两个方法，一个是设置最大尝试次数的getMaxTries方法， 这里是尝试一次，如果尝试超过这个次数没有更新成功将抛出一个TimeoutException，
     *            如果你想无限尝试（理论上），可以将返回值设定为 Integer.MAX_VALUE；另一个方 法是根据当前获得的GetsResponse来决定更新数据的getNewValue方法，
     *            如果更新成功，这个方法返回的值将存储成功，这个方法的两个参数是最新一次gets返回的GetsResponse结果。
     */
    public void casWithNoReply(final String key, final CASOperation<Object> cASOperation) {
        try {
            memcachedClient.casWithNoReply(key, cASOperation);
        } catch (InterruptedException e) {
            log.error("casWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        } catch (TimeoutException e) {
            log.error("casWithNoReply操作超时", e);
        }
    }

    /**
     * 利用memcachedClient进行CAS操作（实现原子更新），所谓原子更新就是compare and set， 原理类似乐观锁，每次请求存储某个数据同时要附带一个cas值，memcached比对这个cas值与
     * 当前存储数据的cas值是否相等，如果相等就让新的数据覆盖老的数据，如果不相等就认为更新失败， 这在并发环境下特别有用。XMemcached提供了对CAS协议的支持（无论是文本协议还是二进制协议），
     * CAS协议其实是分为两个步骤：获取CAS值和尝试更新， 不需要响应的CAS操作
     * 
     * @param key
     *            指定要CAS更新的键
     * @param exp
     *            期限，不指定期限时候采用LRU算法保存数据。如果设置为0表示永久存储（默认是一个月）
     * @param cASOperation
     *            CASOpertion接口只有两个方法，一个是设置最大尝试次数的getMaxTries方法， 这里是尝试一次，如果尝试超过这个次数没有更新成功将抛出一个TimeoutException，
     *            如果你想无限尝试（理论上），可以将返回值设定为 Integer.MAX_VALUE；另一个方 法是根据当前获得的GetsResponse来决定更新数据的getNewValue方法，
     *            如果更新成功，这个方法返回的值将存储成功，这个方法的两个参数是最新一次gets返回的GetsResponse结果。
     */
    public void casWithNoReply(final String key, final int exp, final CASOperation<Object> cASOperation) {
        try {
            memcachedClient.casWithNoReply(key, exp, cASOperation);
        } catch (InterruptedException e) {
            log.error("casWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        } catch (TimeoutException e) {
            log.error("casWithNoReply操作超时", e);
        }
    }

    /**
     * incr方法提供了递增计数的操作。有三个参数的方法，第一个参数指定递增的key名称，第二个参数指定递增的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累加的幅度
     * @return 递增后的结果
     */
    public long incr(final String key, final long delta) {
        long result = 0;
        try {
            result = memcachedClient.incr(key, delta);
        } catch (TimeoutException e) {
            log.error("incr操作超时", e);
        } catch (InterruptedException e) {
            log.error("incr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * incr方法提供了递增计数的操作。有三个参数的方法，第一个参数指定递增的key名称，第二个参数指定递增的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累加的幅度
     * @param initValue
     *            递增的初始的值
     * @return 递增后的结果
     */
    public long incr(final String key, final long delta, final long initValue) {
        long result = 0;
        try {
            result = memcachedClient.incr(key, delta, initValue);
        } catch (TimeoutException e) {
            log.error("incr操作超时", e);
        } catch (InterruptedException e) {
            log.error("incr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * incr方法提供了递增计数的操作。有三个参数的方法，第一个参数指定递增的key名称，第二个参数指定递增的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累加的幅度
     * @param initValue
     *            递增的初始的值
     * @param timeout
     *            操作的超时时间
     * @return 递增后的结果
     */
    public long incr(final String key, final long delta, final long initValue, final long timeout) {
        long result = 0;
        try {
            result = memcachedClient.incr(key, delta, initValue, timeout);
        } catch (TimeoutException e) {
            log.error("incr操作超时", e);
        } catch (InterruptedException e) {
            log.error("incr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * incr方法提供了递增计数的操作。有三个参数的方法，第一个参数指定递增的key名称，第二个参数指定递增的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累加的幅度
     */
    public void incrWithNoReply(final String key, final long delta) {
        try {
            memcachedClient.incrWithNoReply(key, delta);
        } catch (InterruptedException e) {
            log.error("incrWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * decr方法提供了递减计数的操作。有三个参数的方法，第一个参数指定递减的key名称，第二个参数指定递减的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累减的幅度
     * @return 递减后的结果
     */
    public long decr(final String key, final long delta) {
        long result = 0;
        try {
            result = memcachedClient.decr(key, delta);
        } catch (TimeoutException e) {
            log.error("decr操作超时", e);
        } catch (InterruptedException e) {
            log.error("decr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * decr方法提供了递减计数的操作。有三个参数的方法，第一个参数指定递减的key名称，第二个参数指定递减的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累减的幅度
     * @param initValue
     *            递减的初始的值
     * @return 递减后的结果
     */
    public long decr(final String key, final long delta, final long initValue) {
        long result = 0;
        try {
            result = memcachedClient.decr(key, delta, initValue);
        } catch (TimeoutException e) {
            log.error("decr操作超时", e);
        } catch (InterruptedException e) {
            log.error("decr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * decr方法提供了递减计数的操作。有三个参数的方法，第一个参数指定递减的key名称，第二个参数指定递减的幅度大小， 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累减的幅度
     * @param initValue
     *            递减的初始的值
     * @param timeout
     *            操作的超时时间
     * @return 递减后的结果
     */
    public long decr(final String key, final long delta, final long initValue, final long timeout) {
        long result = 0;
        try {
            result = memcachedClient.decr(key, delta, initValue, timeout);
        } catch (TimeoutException e) {
            log.error("decr操作超时", e);
        } catch (InterruptedException e) {
            log.error("decr操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
        return result;
    }

    /**
     * decrWithNoReply方法提供了递减计数的操作。有三个参数的方法，第一个参数指定递减的key名称，第二个参数指定递减的幅度大小，
     * 第三个参数指定当key不存在的情况下的初始值。两个参数的重载方法省略了第三个参数，默认指定为0。
     * 
     * @param key
     *            键
     * @param delta
     *            累减的幅度
     */
    public void decrWithNoReply(final String key, final long delta) {
        try {
            memcachedClient.decrWithNoReply(key, delta);
        } catch (InterruptedException e) {
            log.error("decrWithNoReply操作被中断", e);
        } catch (MemcachedException e) {
            log.error(e);
        }
    }

    /**
     * 获取计数器，可以进行计数器操作不用自己去调用incr和decr操作
     * 
     * @param key
     *            键
     * @return 计数器
     */
    public Counter getCounter(final String key) {
        return memcachedClient.getCounter(key);
    }

    /**
     * 获取计数器，可以进行计数器操作不用自己去调用incr和decr操作
     * 
     * @param key
     *            键
     * @param initialValue
     *            当键不存在的时候的初始值
     * @return 计数器
     */
    public Counter getCounter(final String key, final long initialValue) {
        return memcachedClient.getCounter(key, initialValue);
    }
}
