/**
 * 
 */
package ishoes;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 系统公用的常量类。提供各类使用的常量，避免魔鬼数字。
 * </p>
 * 
 * @author dengjie
 * @since 1.0
 */
public class AssertConstants {

    /**
     * WEB层controller中断言pageNo或其他ID不能为0，使用的int类型数字0
     */
    public static final int ASSERT_INT_ZERO = 0;
    /**
     * WEB层controller中断言pageNo或其他ID不能为0，使用的long类型0
     */
    public static final long ASSERT_LONG_ZERO = 0;
    /**
     * WEB层controller中断言中的标签搜索，client端传递给tag的类型。标签类型。1表示“最新”，2表示“热门”，3表示其他用户上传的热门标签。
     */
    public static final List<Integer> ASSERT_TAGTYPE_ARR = Arrays.asList(1, 2, 3);

    /**
     * 私有构造方法，使该类不能被继承
     */
    private AssertConstants() {

    }
}
