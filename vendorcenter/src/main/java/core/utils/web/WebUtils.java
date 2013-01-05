/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Http与Servlet工具类.利用spring web包中的类做到
 * 
 * @author jayd
 * @since 1.0
 */
public class WebUtils {
    private static Log log = LogFactory.getLog(WebUtils.class);

    /**
     * 设置客户端缓存过期时间 Header.
     * 
     * @param response
     *            需要设置过期时间的response
     * @param expiresSeconds
     *            过期时间，单位秒
     */
    public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds) {
        // Http 1.0 header
        response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000);
        // Http 1.1 header
        response.setHeader("Cache-Control", "max-age=" + expiresSeconds);
    }

    /**
     * 设置客户端无缓存Header.
     * 
     * @param response
     *            响应对象
     */
    public static void setNoCacheHeader(HttpServletResponse response) {
        // Http 1.0 header
        response.setDateHeader("Expires", 0);
        // Http 1.1 header
        response.setHeader("Cache-Control", "no-cache");
    }

    /**
     * 设置LastModified Header.
     * 
     * @param response
     *            响应对象
     * @param lastModifiedDate
     *            最后修改日期的时间戳
     */
    public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate) {
        response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    /**
     * 设置Etag Header.
     * 
     * @param response
     *            需要设置etag的response
     * @param etag
     *            http中的etag
     */
    public static void setEtag(HttpServletResponse response, String etag) {
        response.setHeader("ETag", etag);
    }

    /**
     * 根据浏览器If-Modified-Since Header, 计算文件是否已被修改.
     * 
     * 如果无修改, checkIfModify返回false ,设置304 not modify status.
     * 
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param lastModified
     *            内容的最后修改时间.
     * @return 已经修改返回true，未修改返回false
     */
    public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response,
            long lastModified) {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        if ((ifModifiedSince != -1) && (lastModified < ifModifiedSince + 1000)) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return false;
        }
        return true;
    }

    /**
     * 根据浏览器 If-None-Match Header, 计算Etag是否已无效.
     * 
     * 如果Etag有效, checkIfNoneMatch返回false, 设置304 not modify status.
     * 
     * @param request
     *            请求对象
     * @param response
     *            响应对象
     * @param etag
     *            内容的ETag.
     * @return 根据头文件返回是否Etag有效
     */
    public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag) {
        String headerValue = request.getHeader("If-None-Match");
        if (headerValue != null) {
            boolean conditionSatisfied = false;
            if (!"*".equals(headerValue)) {
                StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");

                while (!conditionSatisfied && commaTokenizer.hasMoreTokens()) {
                    String currentToken = commaTokenizer.nextToken();
                    if (currentToken.trim().equals(etag)) {
                        conditionSatisfied = true;
                    }
                }
            } else {
                conditionSatisfied = true;
            }

            if (conditionSatisfied) {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.setHeader("ETag", etag);
                return false;
            }
        }
        return true;
    }

    /**
     * 检查浏览器客户端是否支持gzip编码.
     * 
     * @param request
     *            请求对象F
     * @return 检查客户端是否支持gzip编码
     */
    public static boolean checkAccetptGzip(HttpServletRequest request) {
        // Http1.1 header
        String acceptEncoding = request.getHeader("Accept-Encoding");

        if (StringUtils.contains(acceptEncoding, "gzip")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置Gzip Header并返回GZIPOutputStream.
     * 
     * @param response
     *            响应对象
     * @return 设置成功后的输出流
     * @exception IOException
     *                IO异常
     */
    public static OutputStream buildGzipOutputStream(HttpServletResponse response) throws IOException {
        response.setHeader("Content-Encoding", "gzip");
        response.setHeader("Vary", "Accept-Encoding");
        return new GZIPOutputStream(response.getOutputStream());
    }

    /**
     * 设置让浏览器弹出下载对话框的Header.
     * 
     * @param response
     *            响应对象
     * @param fileName
     *            下载后的文件名.
     */
    public static void setDownloadableHeader(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * 
     * 返回的结果Parameter名已去除前缀.
     * 
     * @param request
     *            请求对象
     * @param prefix
     *            前缀
     * @return 键值对
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getParametersStartingWith(HttpServletRequest request, String prefix) {
        return org.springframework.web.util.WebUtils.getParametersStartingWith(request, prefix);
    }

    /**
     * 获取HttpServletRequest中的POST内容
     * 
     * @param request
     *            post请求
     * @return 获取到内容字符串
     */
    public static String getRequestBody(HttpServletRequest request) {
        int read;
        StringBuffer inputb = new StringBuffer();
        InputStream is;
        try {
            is = request.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
            while ((read = inputStreamReader.read()) >= 0) {
                inputb.append((char) read);
            }
        } catch (IOException e) {
            log.error(e);
        }
        return inputb.toString();
    }
}
