/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.encode;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * <p>
 * 各种格式的编码加码工具类.
 * </p>
 * <p>
 * 继承commons-codec包中的DigestUtils类,能够使用其提供的#MD5(信息摘要算法)# SHA(安全散列算法)
 * </p>
 * <p>
 * 集成Commons-Codec,Commons-Lang及JDK提供的编解码方法.
 * </p>
 * 
 * @author dengjie
 * @version 1.0, 2012-8-24
 * @since 1.0
 */
public abstract class EncodeUtils extends DigestUtils {
    // 默认编码是UTF-8
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    /**
     * Hex编码.
     * 
     * @param input
     *            输入的byte数组
     * @return Hex编码 字符串
     */
    public static String hexEncode(byte[] input) {
        return Hex.encodeHexString(input);
    }

    /**
     * Hex解码.
     * 
     * @param input
     *            输入的byte数组
     * @return Hex解码byte数组
     */
    public static byte[] hexDecode(String input) {
        try {
            return Hex.decodeHex(input.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalStateException("Hex Decoder exception", e);
        }
    }

    /**
     * Base64编码.
     * @param input 源码输入
     * @return 返回编码
     * 
     */
    public static String base64Encode(byte[] input) {
        return Base64.encodeBase64String(input);
    }

    /**
     * Base64编码, URL安全(将Base64中的URL非法字符如+,/=转为其他字符, 见RFC3548).
     * @param input 源码输入
     * @return 返回编码
     */
    public static String base64UrlSafeEncode(byte[] input) {
        return Base64.encodeBase64URLSafeString(input);
    }

    /**
     * Base64解码.
     * @param input 源码输入
     * @return 返回编码
     */
    public static byte[] base64Decode(String input) {
        return Base64.decodeBase64(input);
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     * @param input 源码输入
     * @return 返回编码
     */
    public static String urlEncode(String input) {
        return urlEncode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 编码.
     * @param input 源码输入
     * @param encoding 编译码
     * @return 返回编码
     */
    public static String urlEncode(String input, String encoding) {
        try {
            return URLEncoder.encode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     * @param input 源码输入
     * @return 返回编码
     */
    public static String urlDecode(String input) {
        return urlDecode(input, DEFAULT_URL_ENCODING);
    }

    /**
     * URL 解码.
     * @param input 源码输入
     * @param encoding 编译码
     * @return 返回编码
     */
    public static String urlDecode(String input, String encoding) {
        try {
            return URLDecoder.decode(input, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * Html 转码.
     * @param html 源码输入
     * @return 返回编码
     */
    public static String htmlEscape(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * Html 解码.
     * @param htmlEscaped 源码输入
     * @return 返回编码
     */
    public static String htmlUnescape(String htmlEscaped) {
        return StringEscapeUtils.unescapeHtml(htmlEscaped);
    }

    /**
     * Xml 转码.
     * @param xml 源码输入
     * @return 返回编码
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 解码.
     * @param xmlEscaped 源码输入
     * @return 返回编码
     */
    public static String xtmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }
}
