/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

/**
 * 文件复制的工具类进行文件复制操作。
 * 
 * @author dengjie
 * @since 1.0
 */
public abstract class FileCopyUtils {

    /**
     * 缓冲区大小，默认是4096
     */
    public static final int BUFFER_SIZE = 4096;
    private static Log log = LogFactory.getLog(FileCopyUtils.class);

    /**
     * 从输入文件复制内容到输出文件
     * 
     * @param in
     *            输入文件
     * @param out
     *            输出文件
     * @return 已经复制的字节长度
     * @throws IOException
     *             IO异常
     */
    public static int copy(File in, File out) throws IOException {
        Assert.notNull(in, "No input File specified");
        Assert.notNull(out, "No output File specified");
        return copy(new BufferedInputStream(new FileInputStream(in)), new BufferedOutputStream(
                new FileOutputStream(out)));
    }

    /**
     * 从字节数组输入流到输出的文件中.
     * 
     * @param in
     *            输入流的字节数组
     * @param out
     *            需要复制到的文件
     * @throws IOException
     *             IO异常
     */
    public static void copy(byte[] in, File out) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        Assert.notNull(out, "No output File specified");
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
        copy(inStream, outStream);
    }

    /**
     * 复制内容到一个字节数组中
     * 
     * @param in
     *            输入文件
     * @return 新的已经复制内容的字节数组
     * @throws IOException
     *             IO异常
     */
    public static byte[] copyToByteArray(File in) throws IOException {
        Assert.notNull(in, "No input File specified");
        return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
    }

    /**
     * 从输入流中复制内容到一个指定的输出流中,并且完成后关闭两个流
     * 
     * @param in
     *            输入流
     * @param out
     *            复制到的输出流
     * @return 已经复制的字节数
     * @throws IOException
     *             IO异常
     */
    public static int copy(InputStream in, OutputStream out) throws IOException {
        Assert.notNull(in, "No InputStream specified");
        Assert.notNull(out, "No OutputStream specified");
        try {
            int byteCount = 0;
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                log.error(ex);
            }
            try {
                out.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * 
     * 从字节数组中复制内容到一个输出流中
     * 
     * @param in
     *            字节数组
     * @param out
     *            输出流
     * @throws IOException
     *             IO异常
     */
    public static void copy(byte[] in, OutputStream out) throws IOException {
        Assert.notNull(in, "No input byte array specified");
        Assert.notNull(out, "No OutputStream specified");
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * 从输入流中复制字节内容到字节数组中
     * 
     * @param in
     *            输入流
     * @return 接受复制内容的字节数组
     * @throws IOException
     *             IO异常
     */
    public static byte[] copyToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
        copy(in, out);
        return out.toByteArray();
    }

    /**
     * 从Reader输入流中复制内容到一个Writer的输出流
     * 
     * @param in
     *            Reader输入流
     * @param out
     *            Writer的输出流
     * @return 复制的字节数组长度
     * @throws IOException
     *             IO异常
     */
    public static int copy(Reader in, Writer out) throws IOException {
        Assert.notNull(in, "No Reader specified");
        Assert.notNull(out, "No Writer specified");
        try {
            int byteCount = 0;
            char[] buffer = new char[BUFFER_SIZE];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                log.error(ex);
            }
            try {
                out.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * 复制字符串内容到Writer的输出流
     * 
     * @param in
     *            需要被复制的字符串内容
     * @param out
     *            Writer的输出流
     * @throws IOException
     *             IO异常
     */
    public static void copy(String in, Writer out) throws IOException {
        Assert.notNull(in, "No input String specified");
        Assert.notNull(out, "No Writer specified");
        try {
            out.write(in);
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
    }

    /**
     * 从Reader输入流中复制到一个字符串中
     * 
     * @param in
     *            Reader的输入流
     * @return 字符串
     * @throws IOException
     *             IO异常
     */
    public static String copyToString(Reader in) throws IOException {
        StringWriter out = new StringWriter();
        copy(in, out);
        return out.toString();
    }

}
