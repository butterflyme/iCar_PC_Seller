/*
 * 版权：Copyright 2010-2011 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰（dKF37984）
 * 修改时间：2010-12-14
 * 修改内容：
 */
package core.utils.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 用于获取任意资源文件的工具类
 * 
 * @author dengjie
 * @version 1.0, 2012-08-10
 * @since 1.0
 */
public abstract class PropertiesUtils {

    private static final Log log = LogFactory.getLog(PropertiesUtils.class);

    /**
     * 加载资源文件
     * 
     * @param filepath
     *            资源文件地址
     * @return 加载后的properties对象
     */
    private static Properties loadProperties(String filepath) {
        Properties p = new Properties();
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filepath);
            p.load(fileReader);
        } catch (FileNotFoundException e) {
            log.error(e);
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }
        return p;
    }

    /**
     * 设置指定键的值
     * 
     * @param filepath
     *            资源文件地址
     * @param key
     *            指定的key
     * @param value
     *            需要设置的值
     */
    public static void setProperty(String filepath, String key, String value) {
        Properties p = loadProperties(filepath);
        p.setProperty(key, value);
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filepath);
            p.store(fos, "new language");
        } catch (IOException e) {
            log.error("修改环境文件的语言出错 ", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    log.error("修改环境文件的语言中关闭输出流出错 ", e1);
                }
            }
        }
    }

    /**
     * 根据给定的资源文件和key名称获取key对应的value
     * 
     * @param filepath
     *            资源文件路径
     * @param key
     *            资源文件的key
     * @return 对应key的value字符串
     */
    public static String getProperty(String filepath, String key) {
        Properties p = loadProperties(filepath);
        return p.getProperty(key);
    }
}
