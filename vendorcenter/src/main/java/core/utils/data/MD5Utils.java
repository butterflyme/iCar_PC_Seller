/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.data;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5消息摘要的工具类,支持加密
 * 
 * @author dengjie
 * @since 1.0
 */
public final class MD5Utils {

    private static final String MD5_PREFIX = "";

    private static final ThreadLocal<MD5Utils> LOCAL = new ThreadLocal<MD5Utils>();

    private MD5Utils() {
        super();
    }

    /**
     * 工厂方法获取MD5Utils实例
     * 
     * @return MD5Utils实例
     */
    public static MD5Utils getMD5Utils() {
        MD5Utils encrypt = LOCAL.get();
        if (encrypt == null) {
            encrypt = new MD5Utils();
            LOCAL.set(encrypt);
        }
        return encrypt;
    }

    /**
     * MD5前缀消息摘要
     * 
     * @param s
     *            需要加密的字符串
     * @return MD5前缀消息摘要
     */
    public static String encode(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex(MD5_PREFIX + s);
    }
    public static void main(String[] args){
        System.out.println(encode("198702"));
    }
}
