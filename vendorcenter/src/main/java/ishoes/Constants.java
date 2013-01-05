/**
 * 
 */
package ishoes;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.DefaultResourceLoader;

import core.utils.file.PropertiesUtils;

/**
 * <p>
 * 系统公用的常量类。提供各类使用的常量，避免魔鬼数字。
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public class Constants {

    /**
     * 工程运行的CLASS目录。
     */
    public static final String PROJECT_CLASS_ROOT;
    static {
        try {
            PROJECT_CLASS_ROOT = new DefaultResourceLoader().getResource("classpath:/").getURI().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 私有构造方法，使该类不能被继承
     */
    private Constants() {

    }
}
