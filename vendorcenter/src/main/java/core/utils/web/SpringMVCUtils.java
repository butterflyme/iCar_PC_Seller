/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * spring mvc工具类.
 * 
 * 实现获取Request/Response/Session与绕过jsp/freemaker直接输出文本的简化函数.
 * 
 * @author dengjie
 * @since 1.0
 */
public class SpringMVCUtils {
    private static Log log = LogFactory.getLog(SpringMVCUtils.class);
    // -- header 常量定义 --//
    private static final String HEADER_ENCODING = "encoding";
    private static final String HEADER_NOCACHE = "no-cache";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_NOCACHE = true;

    // -- content-type 常量定义 --//
    private static final String TEXT_TYPE = "text/plain";
    private static final String JSON_TYPE = "application/json";
    private static final String XML_TYPE = "text/xml";
    private static final String HTML_TYPE = "text/html";
    private static final String JS_TYPE = "text/javascript";

    private static ObjectMapper mapper = new ObjectMapper();

    // -- 绕过jsp/freemaker直接输出文本的函数 --//
    /**
     * 直接输出内容的简便函数.
     * 
     * eg. render("text/plain", "hello", "encoding:GBK"); render("text/plain", "hello", "no-cache:false");
     * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
     * 
     * @param response
     *            response对象
     * @param headers
     *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
     * @param contentType
     *            输出的内容的类型是JSON还是XML还是HTML
     * @param content
     *            输出的内容
     */
    public static void render(HttpServletResponse response, final String contentType, final String content,
            final String... headers) {
        response = initResponse(response, contentType, headers);
        try {
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 直接输出文本.
     * 
     * @param response
     *            response对象
     * @param text
     *            text字符串内容
     * @param headers
     *            headers内容数组
     * @see #render(String, String, String...)
     */
    public static void renderText(HttpServletResponse response, final String text, final String... headers) {
        render(response, TEXT_TYPE, text, headers);
    }

    /**
     * 直接输出HTML.
     * 
     * @param response
     *            response对象
     * @param html
     *            html字符串内容
     * @param headers
     *            headers内容数组
     * @see #render(String, String, String...)
     */
    public static void renderHtml(HttpServletResponse response, final String html, final String... headers) {
        render(response, HTML_TYPE, html, headers);
    }

    /**
     * 直接输出XML.
     * 
     * @param response
     *            response对象
     * @param xml
     *            xml字符串内容
     * @param headers
     *            headers内容数组
     * @see #render(String, String, String...)
     */
    public static void renderXml(HttpServletResponse response, final String xml, final String... headers) {
        render(response, XML_TYPE, xml, headers);
    }

    /**
     * 直接输出JSON.
     * 
     * @param jsonString
     *            json字符串.
     * @param response
     *            response对象
     * @param headers
     *            headers内容数组
     * @see #render(String, String, String...)
     */
    public static void renderJson(HttpServletResponse response, final String jsonString, final String... headers) {
        render(response, JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     * 
     * @param data
     *            可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
     * @param response
     *            response对象
     * @param headers
     *            headers内容数组
     * @see #render(String, String, String...)
     */
    public static void renderJson(HttpServletResponse response, final Object data, final String... headers) {
        response = initResponse(response, JSON_TYPE, headers);
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出支持跨域Mashup的JSONP.
     * 
     * @param callbackName
     *            callback函数名.
     * @param object
     *            Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
     * @param response
     *            response对象
     * @param headers
     *            headers内容数组
     */
    public static void renderJsonp(HttpServletResponse response, final String callbackName, final Object object,
            final String... headers) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

        // 渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
        render(response, JS_TYPE, result, headers);
    }

    /**
     * 分析并设置contentType与headers.
     * 
     * @param response
     *            response对象
     * @param contentType
     *            contentType渲染内容类型
     * @param headers
     *            headers内容数组
     * @return 响应对象
     */
    public static HttpServletResponse initResponse(HttpServletResponse response, final String contentType,
            final String... headers) {
        // 分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        for (String header : headers) {
            String headerName = StringUtils.substringBefore(header, ":");
            String headerValue = StringUtils.substringAfter(header, ":");

            if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
                encoding = headerValue;
            } else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
                noCache = Boolean.parseBoolean(headerValue);
            } else {
                throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
            }
        }

        // 设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            WebUtils.setNoCacheHeader(response);
        }
        return response;
    }

    /**
     * 关闭Web输出流,销毁所有资源
     * 
     * @param response
     *            response对象
     */
    public static void closeResponse(HttpServletResponse response) {
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            if (pw != null) {
                pw.close();
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
