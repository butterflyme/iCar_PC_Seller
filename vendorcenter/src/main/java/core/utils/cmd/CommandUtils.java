/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.cmd;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * WINDOWS本地命令行调用工具类。可以用来调用批处理程序。
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public final class CommandUtils {

    protected static Log logger = LogFactory.getLog(CommandUtils.class);

    /**
     * 执行window命令
     * 
     * @param command
     *            命令
     * @return 执行的命令字符串
     * @throws IOException
     *             IO异常
     */
    public String exec(String command) throws IOException {
        return exec(null, command);
    }

    /**
     * 传入地址和要运行的命令后异步执行
     * 
     * @param directory
     *            路径
     * @param command
     *            命令
     * @return 执行的命令字符串
     * @throws IOException
     *             IO异常
     */
    public String exec(String directory, String command) throws IOException {
        InputStream in = null;
        Reader reader = null;
        BufferedReader br = null;
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", command);
            if (StringUtils.isNotBlank(directory)) {
                pb.directory(new File(directory));
            }
            pb.redirectErrorStream(true);
            logger.debug("Now executing:" + command);
            Process p = pb.start();
            in = p.getInputStream();
            reader = new InputStreamReader(in, "gb2312");
            br = new BufferedReader(reader);
            StringBuffer lines = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                lines.append(line).append("\n");
            }
            return lines.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (br != null) {
                br.close();
            }
            if (in != null) {
                in.close();
            }
        }

    }
}
