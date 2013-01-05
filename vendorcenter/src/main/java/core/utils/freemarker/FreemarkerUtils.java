/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * <p>
 * Freemarker模板的调用工具类，提供的生成的两种不同方法。提供生成HTML与字符串的方法。 在调用方法之前可以修改设置模板文件所在路径TEMPALTE_PATH与编码方式ENCODING。
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public final class FreemarkerUtils {

    /** 模板文件所在路径,该文件路径是从根目录开始的 */
    public static final String TEMPALTE_PATH = "template";
    /** 编码格式 UTF-8 */
    public static final String ENCODING = "UTF-8";

    private static final Log log = LogFactory.getLog(FreemarkerUtils.class);

    private static Configuration cfg = null;

    private FreemarkerUtils() {

    }

    /**
     * 设置配置对象,单例工厂方法.
     * 
     * @return Configuration对象
     */
    public static synchronized Configuration getConfiguration() {
        if (cfg == null) {
            cfg = new Configuration();
            // cfg.setClassForTemplateLoading(FreemarkerUtils.class, TEMPALTE_PATH);
            try {
                cfg.setDirectoryForTemplateLoading(new File(TEMPALTE_PATH));
            } catch (IOException e) {
                log.error(e);
            }
            cfg.setDefaultEncoding(ENCODING);
        }
        return cfg;
    }

    /**
     * 根据输入参数和模板文件生成字符串数据
     * 
     * @param templateFileName
     *            模板文件名称，模板文件都放在根目录下的template包里
     * @param propMap
     *            输入模板的参数封装
     * @return 模板组合后的字符串
     */
    public static String generate(String templateFileName, Map<String, Object> propMap) {
        Writer out = null;
        try {
            Template t = getConfiguration().getTemplate(templateFileName);
            out = new StringWriter();
            t.process(propMap, out);
            return out.toString();
        } catch (TemplateException e) {
            log.error("A error occured when generate String!", e);
        } catch (IOException e) {
            log.error("A error occured when generate String!", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("A error occured when generate String!", e);
                }
            }
        }
        return null;
    }

    /**
     * 生成文件，可以用FreeMarker生成批量的HTML页面，js文件，动态SQL等。
     * 
     * @param templateFileName
     *            模板文件名称，模板文件都放在根目录下的template包里
     * @param propMap
     *            输入模板的参数封装
     * @param filePath
     *            输出的文件路径
     * @param fileName
     *            输出的文件名称
     * @return true|false
     */
    public static boolean getHtmlFile(String templateFileName, Map<String, Object> propMap, String filePath,
            String fileName) {
        Writer out = null;
        try {
            Template t = getConfiguration().getTemplate(templateFileName);
            if (!new File(filePath).mkdirs()) {
                return false;
            }
            File file = new File(filePath + File.separator + fileName);

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            t.process(propMap, out);
            return true;
        } catch (TemplateException e) {
            log.error("A error occured when generate file!", e);
        } catch (IOException e) {
            log.error("A error occured when generate file!", e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("A error occured when generate file!", e);
                }
            }
        }
        return false;
    }

}
