/**
 * 
 */
package ishoes.dao;

/**
 * <p>
 * 系统公用的常量类。提供各类使用的常量，避免魔鬼数字。
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public class DaoConstants {

    /**
     * 分页查询对象的每页查询数量24个
     */
    public static final int PAGE_SIZE_TWENTY_FOUR = 24;
    /**
     * 分页查询对象的每页查询数量20个
     */
    public static final int PAGE_SIZE_TWENTY = 20;
    /**
     * 分页查询对象的每页查询数量10个
     */
    public static final int PAGE_SIZE_TEN = 10;
    /**
     * 分页查询对象的每页查询数量5个
     */
    public static final int PAGE_SIZE_FIVE = 5;
    /**
     * 分页查询对象的每页查询数量4个
     */
    public static final int PAGE_SIZE_FOUR = 4;
    /**
     * 分页查询对象的每页查询数量3个
     */
    public static final int PAGE_SIZE_THREE = 3;
    /**
     * 分页查询对象的每页查询数量300个
     */
    public static final int PAGE_SIZE_THREE_HUNDRED = 300;
    /**
     * 分页查询对象的每页查询数量13个
     */
    public static final int PAGE_SIZE_THIRTEEN = 13;
    /**
     * 分页查询对象的当前页号常量为1,一般采用最新过滤分页查询中使用取定量最新的记录
     */
    public static final int CURRENT_PAGE_NO_ONE = 1;
    /**
     * 用户登陆在线状态码为1
     */
    public static final int USER_LOGIN_STATUS = 1;
    /**
     * 用户登陆离线状态码为0
     */
    public static final int USER_LOGOUT_STATUS = 0;
    /**
     * 美鞋线或橱窗中美鞋详细情况下的该作品是否被当前用户所喜爱的状态码为1，表示喜爱
     */
    public static final int CURUSER_LIKE_STATUS = 1;
    /**
     * 美鞋线或橱窗中美鞋详细情况下的该作品是否被当前用户所喜爱的状态码为0，表示不喜爱
     */
    public static final int CURUSER_UN_LIKE_STATUS = 0;
    /**
     * 美鞋线或橱窗中美鞋详细情况下的该用户是否被当前登陆用户所关注的状态码为1，表示已经关注
     */
    public static final int CURUSER_FOLLOW_STATUS = 1;
    /**
     * 美鞋线或橱窗中美鞋详细情况下的该用户是否被当前登陆用户所关注的状态码为0，表示未关注
     */
    public static final int CURUSER_UN_FOLLOW_STATUS = 0;
    /**
     * 美鞋线或橱窗中美鞋详细情况下的该用户是否被当前登陆用户所关注的状态码为0，表示未关注
     */
    public static final String QUERY_PRODUCTS_BY_TAG_INDEX = "queryProductByOtherTag";
    /**
     * 分页查询对象的每页查询数量50个
     */
    public static final int PAGE_SIZE_FIFTY = 50;

    /**
     * 私有构造方法，使该类不能被继承
     */
    private DaoConstants() {

    }
}
