/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 提供单例模式获取Jackson的ObjectMapper对象.保证线程安全。
 * 
 * @author dengjie
 * @version 1.0, 2012-08-20
 * @since 1.0
 */
public class JacksonJsonMapper {
    static volatile ObjectMapper objectMapper = null;

    private JacksonJsonMapper() {
    }

    /**
     * 关键字 volatile 是在读取所申明的对象时，会要从内存中进行同步，但是不会对写时起作用，所以，还是需要synchronized 关键字的配合
     * 
     * @return Jackson的ObjectMapper对象单例
     */
    public static ObjectMapper getInstance() {
        if (objectMapper == null) {
            synchronized (ObjectMapper.class) {
                if (objectMapper == null) {
                    objectMapper = new ObjectMapper();
                }
            }
        }

        return objectMapper;
    }
}
