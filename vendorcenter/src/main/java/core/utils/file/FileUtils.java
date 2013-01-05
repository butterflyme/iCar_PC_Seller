/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;

/**
 * <p>
 * 文件操作的工具类，提供一些文件操作，beta版完善中。提供NIO的文件拷贝等IO操作。
 * </p>
 * 
 * @author dengjie
 * @version 1.0, 2012-7-17
 * @since 1.0
 */
public final class FileUtils {

    private static Log log = LogFactory.getLog(FileUtils.class);

    /**
     * 设置成私有的构造方法防止被继承
     */
    private FileUtils() {

    }

    /**
     * 利用ant压缩文件，可以方便的解决中文问题。
     * 
     * @param srcPath
     *            要压缩的文件或目录地址,必须是文件夹
     * @param destFile
     *            压缩后的存储路径，必须是文件。带zip的
     */
    public static void zip(String srcPath, String destFile) {
        Project prj = new Project();
        Zip zip = new Zip();
        zip.setProject(prj);
        zip.setBasedir(new File(srcPath));
        zip.setDestFile(new File(destFile));
        zip.execute();
    }

    /**
     * 利用ant解压缩方法，可以方便的解决中文问题。
     * 
     * @param zipFileName
     *            要解压的文件
     * @param unZipPath
     *            解压到的路径
     */
    public static void unZip(String zipFileName, String unZipPath) {
        Project prj = new Project();
        Expand expand = new Expand();
        expand.setProject(prj);
        expand.setEncoding("gbk");
        expand.setSrc(new File(zipFileName));
        expand.setDest(new File(unZipPath));
        expand.execute();
    }

    /**
     * 将字节流转换成字符串返回
     * 
     * @param is
     *            输入流
     * @return 字符串
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        // 如果不需要同步append可以将StringBuffer替换成StringBuilder
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            String tempString = null;
            do {
                tempString = reader.readLine();
                sb.append(tempString + System.getProperty("line.separator"));
            } while (tempString != null);
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 将文件一行一行的读成List返回
     * 
     * @param file
     *            需要读取的文件
     * @return 文件的一行就是一个List的Item的返回
     */
    public static List<String> readFileToList(File file) {
        BufferedReader reader = null;
        List<String> list = new ArrayList<String>(0);
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = reader.readLine();

            while (tempString != null) {
                list.add(tempString);
                tempString = reader.readLine();
            }

        } catch (IOException e) {
            log.error(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }
        return list;
    }

    /**
     * 将文件按照一定的编码方式一行一行的读成List返回
     * 
     * @param file
     *            需要读取的文件
     * @param encodeType
     *            字符编码
     * @return 文件的一行就是一个List的Item的返回
     */
    public static List<String> readFileToList(File file, String encodeType) {
        BufferedReader reader = null;
        List<String> list = new ArrayList<String>(0);
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encodeType));
            String tempString = reader.readLine();
            while (tempString != null) {
                if (!(tempString.charAt(0) >= 'a' && tempString.charAt(0) <= 'z')) {
                    tempString = tempString.substring(1);
                }
                list.add(tempString);
            }
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }
        return list;
    }

    /**
     * 将指定的字符串内容以指定的方式写入到指定的文件中,如果文件不存在就自动生成对应文件。
     * 
     * @param file
     *            需要写人的文件
     * @param content
     *            需要写入的内容
     * @param flag
     *            是否追加写入
     */
    public static void writeFile(File file, String content, Boolean flag) {
        FileWriter writer = null;
        try {
            if (!file.exists()) {
                if (!file.createNewFile()) {
                    return;
                }
            }
            writer = new FileWriter(file, flag);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            log.error(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                    log.error(e1);
                }
            }
        }
    }

    /**
     * 通过NIO的方式将指定的字符串内容以指定的方式写入到指定的文件中,如果文件不存在就自动生成对应文件。
     * 
     * @param filePath
     *            需要写人的文件的地址
     * @param content
     *            需要写入的内容
     * @param flag
     *            是否追加写入,true是追加，false是不追加
     */
    public static void writeFileByNio(String filePath, String content, Boolean flag) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(filePath, flag);
            FileChannel fc = fout.getChannel();
            // 分配输入字符串的大小的临时缓冲区
            fc.write(ByteBuffer.wrap(content.getBytes()));
        } catch (FileNotFoundException e) {
            log.error("FileNotFoundException", e);
        } catch (IOException e) {
            log.error("FileOutputStream write error", e);
        } finally {
            try {
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                log.error("FileOutputStream close error", e);
            }
        }
    }

    /**
     * 将指定的字符串内容以指定的方式及编码写入到指定的文件中
     * 
     * @param file
     *            需要写人的文件
     * @param content
     *            需要写入的内容
     * @param flag
     *            是否追加写入
     * @param encodeType
     *            文件编码
     */
    public static void writeFile(File file, String content, Boolean flag, String encodeType) {
        try {
            FileOutputStream writerStream = new FileOutputStream(file, flag);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(writerStream, encodeType));
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 将文件重命名
     * 
     * @param oldName
     *            源文件名
     * @param newName
     *            新文件名
     */
    public static void reName(String oldName, String newName) {
        File oldF = new File(oldName);
        File newF = new File(newName);
        boolean renameTo = oldF.renameTo(newF);
        if (!renameTo) {
            return;
        }
    }

    /**
     * 将一个文件列表文件中所有文件拷贝到指定目录中
     * 
     * @param listFile
     *            包含需要拷贝的文件的列表的文件，每个文件写在一行
     * @param targetFloder
     *            目标目录
     */
    public static void copyFilesFromList(String listFile, String targetFloder) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(listFile));
            String tempString = reader.readLine();
            do {
                tempString = reader.readLine();
                copyFile(tempString, targetFloder);
            } while (tempString != null);
            reader.close();
        } catch (IOException e) {
            log.error("copy file error", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    log.error("reader close error", e1);
                }
            }
        }
    }

    /**
     * 拷贝文件
     * 
     * @param oldPath
     *            源文件
     * @param newPath
     *            目标文件
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            File temp = new File(oldPath);
            FileInputStream input = new FileInputStream(temp);
            FileOutputStream output = new FileOutputStream(newPath + '/' + temp.getName());
            byte[] b = new byte[1024 * 5];
            int len = 0;
            while (len != -1) {
                len = input.read(b);
                output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            log.error(e);
        }
    }

    /**
     * 批量删除指定的文件列表
     * 
     * @param files
     *            需要删除的文件/文件夹列表
     * @return 删除成功true，否则返回false
     */
    public static boolean deleteFiles(List<String> files) {
        boolean flag = true;
        int fileSize = files.size();
        for (int i = 0; i < fileSize; i++) {
            flag = delete(files.get(i));
            if (!flag) {
                break;
            }
        }
        return flag;
    }

    /**
     * 删除文件或文件夹
     * 
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return false;
        } else {
            if (file.isFile()) {
                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param fileName
     *            要删除的文件的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 删除目录及目录下的文件
     * 
     * @param dir
     *            要删除的目录路径
     * @return 删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        int filesLength = files.length;
        for (int i = 0; i < filesLength; i++) {
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            return false;
        }
        return dirFile.delete();
    }
}
