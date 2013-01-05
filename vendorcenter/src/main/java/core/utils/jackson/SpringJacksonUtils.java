/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.jackson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import core.utils.BizAssert;
import core.utils.spring.SpringContextHolder;
import core.utils.web.SpringMVCUtils;

/**
 * Spring mvc与jackson集成的工具类，提供方便获取容器初始化时候的jackson映射对象
 * 
 * @author dengjie
 * @version 1.0, 2012-7-17
 * @since 1.0
 */
public final class SpringJacksonUtils {

    private static Log log = LogFactory.getLog(SpringJacksonUtils.class);
    // -- content-type 常量定义 --//
    private static final String JSON_TYPE = "application/json";
    // 默认使用applicationContext-jackson.xml中默认的ObjectMapper
    private static final String DEFAULT_OBJECTMAPPER = "commonOm";

    /**
     * 设置成私有的构造方法防止被继承
     */
    private SpringJacksonUtils() {

    }

    /**
     * 获取spring mvc中容器初始化好的Jackson databind的ObjectMapper对象.不同的控制器对应不同的ObjectMapper，这样避免了单例引来的线程安全的问题
     * 做到了一个业务控制器一个ObjectMapper
     * 
     * @return Jackson databind的ObjectMapper对象
     */
    private static ObjectMapper getObjectMapper(String objectMapperName) {
        ObjectMapper objectMapper = SpringContextHolder.getBean(objectMapperName);
        return objectMapper;
    }

    /**
     * ObjectMapper写方法，输出到HTTP的response的输出流中，默认字符集是UTF-8
     * 
     * @param object
     *            需要被转换成JSON的Object对象
     * @param response
     *            HTTP响应对象,通过他的输出流把JSON字符串输出
     * @param objectMapperName
     *            配置在application-jackson.xml配置文件中的ObjectMapper的bean id名称，选取不同的ObjectMapper进行字段过滤业务处理
     */
    public static void writeValue(Object object, HttpServletResponse response, String objectMapperName) {
        ObjectMapper objectMapper = getObjectMapper(objectMapperName);

        SpringMVCUtils.initResponse(response, JSON_TYPE, new String[] {});
        JsonGenerator jsonGenerator = null;
        try {
            jsonGenerator = objectMapper.getFactory()
                    .createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            FilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false);
            objectMapper.writer(filters).writeValue(jsonGenerator, object);
            // objectMapper.writeValue(jsonGenerator, object);
            clearObject(object);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * ObjectMapper写方法，输出到HTTP的response的输出流中，默认字符集是UTF-8
     * 
     * @param object
     *            需要被转换成JSON的Object对象
     * @param response
     *            HTTP响应对象,通过他的输出流把JSON字符串输出
     * @param userfulProperties
     *            过滤属性的白名单，如果数组为null或是空元素，则不进行白名单过滤.键值对，键是对应作用哪个实体类上面的@JsonFilter的名称，例如ClientVersionConfigFilter的@JsonFilter名称是
     *            ClientVersionConfigFilter，值是代表白名单属性。
     */
    public static void writeValue(Object object, HttpServletResponse response, Map<String, String[]> userfulProperties) {
        BizAssert.notNull(userfulProperties);
        writeValue(object, response, DEFAULT_OBJECTMAPPER, userfulProperties);

    }

    /**
     * ObjectMapper写方法，输出到HTTP的response的输出流中，默认字符集是UTF-8
     * 
     * @param object
     *            需要被转换成JSON的Object对象
     * @param response
     *            HTTP响应对象,通过他的输出流把JSON字符串输出
     * @param objectMapperName
     *            配置在application-jackson.xml配置文件中的ObjectMapper的bean id名称，选取不同的ObjectMapper进行字段过滤业务处理
     * @param userfulProperties
     *            过滤属性的白名单，如果数组为null或是空元素，则不进行白名单过滤.键值对，键是对应作用哪个实体类，值是代表白名单属性。
     */
    public static void writeValue(Object object, HttpServletResponse response, String objectMapperName,
            Map<String, String[]> userfulProperties) {
        ObjectMapper objectMapper = getObjectMapper(objectMapperName);

        SpringMVCUtils.initResponse(response, JSON_TYPE, new String[] {});
        JsonGenerator jsonGenerator = null;
        try {
            jsonGenerator = objectMapper.getFactory()
                    .createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
            ObjectWriter ow = null;
            if (userfulProperties != null && userfulProperties.size() != 0) {
                FilterProvider filters = null;
                SimpleFilterProvider sfp = new SimpleFilterProvider().setFailOnUnknownId(false);
                for (Map.Entry<String, String[]> entry : userfulProperties.entrySet()) {
                    sfp.addFilter(entry.getKey(), SimpleBeanPropertyFilter.filterOutAllExcept(entry.getValue()));
                }
                filters = sfp;
                ow = objectMapper.writer(filters);
            } else {
                ow = objectMapper.writer();
            }
            ow.writeValue(jsonGenerator, object);
            clearObject(object);
            clearObject(ow);
            clearObject(userfulProperties);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * 清除对象，自动判断是什么类型.保证构造JSON的对象能及时清空得到GC收回。集合采用clear方法。
     * 
     * @param object
     *            需要清空设置为NULL的对象
     */
    private static void clearObject(Object object) {
        // 如果本来就是null则直接返回
        if (object == null) {
            return;
        }
        Class<?> obClass = object.getClass();
        if (obClass == ArrayList.class) {
            List<?> o = (ArrayList<?>) object;
            o.clear();
        }
        if (obClass == LinkedList.class) {
            List<?> o = (LinkedList<?>) object;
            o.clear();
        }
        if (obClass == Vector.class) {
            List<?> o = (Vector<?>) object;
            o.clear();
        }
        if (obClass == HashSet.class) {
            Set<?> o = (HashSet<?>) object;
            o.clear();
        }
        if (obClass == LinkedHashSet.class) {
            Set<?> o = (LinkedHashSet<?>) object;
            o.clear();
        }
        if (obClass == LinkedHashSet.class) {
            Set<?> o = (LinkedHashSet<?>) object;
            o.clear();
        }
        if (obClass == HashMap.class) {
            Map<?, ?> o = (HashMap<?, ?>) object;
            o.clear();
        }
        object = null;

    }

}
