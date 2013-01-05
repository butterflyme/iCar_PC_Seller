/**
 * 
 */
package ishoes.dao;

/**
 * <p>
 * 缓存客户端使用的常量，避免魔鬼数字
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public class CacheConstants {

    /**
     * 1天，缓冲使用的期限时间，单位毫秒
     */
    public static final int ONE_DAY_MSEC = 86400;
    /**
     * 1小时，缓冲使用的期限时间，单位毫秒
     */
    public static final int ONE_HOUR_MSEC = 3600;
    /**
     * 1分钟，缓冲使用的期限时间，单位毫秒
     */
    public static final int ONE_MINUTE_MSEC = 60;
    /**
     * 5分钟，缓冲使用的期限时间，单位毫秒
     */
    public static final int FIVE_MINUTE_MSEC = 300;
    /**
     * 30分钟，缓冲使用的期限时间，单位毫秒
     */
    public static final int THIRTY_MINUTE_MSEC = 1800;
    /**
     * 1秒，缓冲使用的期限时间，单位毫秒
     */
    public static final int ONE_SECOND_MSEC = 1;

    /**
     * 私有构造方法，使该类不能被继承
     */
    private CacheConstants() {

    }
}
