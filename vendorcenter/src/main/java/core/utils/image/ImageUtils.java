package core.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 图片文件工具类
 * 
 * @author dengjie
 * @since 1.0
 */
public class ImageUtils {
    private static final Log log = LogFactory.getLog(ImageUtils.class);

    /**
     * 计算图片尺寸大小等信息：w宽、h高、s大小。异常时返回null。
     * 
     * @param imgpath
     *            图片路径
     * @return 图片信息map
     */
    public static Map<String, Long> getImgInfo(String imgpath) {
        Map<String, Long> map = new HashMap<String, Long>(3);
        File imgfile = new File(imgpath);
        try {
            FileInputStream fis = new FileInputStream(imgfile);
            BufferedImage buff = ImageIO.read(imgfile);
            map.put("w", buff.getWidth() * 1L);
            map.put("h", buff.getHeight() * 1L);
            map.put("s", imgfile.length());
            fis.close();
        } catch (FileNotFoundException e) {
            log.error("所给的图片文件" + imgfile.getPath() + "不存在！计算图片尺寸大小信息失败！");
            map = null;
        } catch (IOException e) {
            log.error("计算图片" + imgfile.getPath() + "尺寸大小信息失败！");
            map = null;
        }
        return map;
    }

    /**
     * 主程序
     * 
     * @param args
     *            参数
     */
    public static void main(String[] args) {
        String p = "C:\\Documents and Settings\\dengjie\\桌面\\tupian\\product\\detail\\h\\1347338063_01_800x800_1_h.jpg";
        Map<String, Long> m = getImgInfo(p);
        for (Map.Entry<String, Long> entry : m.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
