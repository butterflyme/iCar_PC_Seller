/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.utils.data;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 数据生成工具类.提供随机数据的生成.随机数也可以参考commons-math中的工具类.
 * 
 * @author dengjie
 * @since 1.0
 */
public class RandomDataUtils {
    private static Random random = new Random();

    /**
     * 返回随机ID.
     * 
     * @return long类型的随机ID
     */
    public static long randomId() {
        return random.nextLong();
    }

    /**
     * 返回随机名称, prefix字符串+随机数字.
     * 
     * @param prefix
     *            前缀
     * @return prefix字符串+随机数字
     */
    public static String randomName(String prefix) {
        return prefix + random.nextInt(10000);
    }

    /**
     * 从输入list中随机返回一个对象.
     * 
     * @param <T>
     *            泛型
     * @param list
     *            输入的数组
     * @return 返回随机的一个数组元素对象
     */
    public static <T> T randomOne(List<T> list) {
        return randomSome(list, 1).get(0);
    }

    /**
     * 从输入list中随机返回随机个对象.
     * 
     * @param <T>
     *            泛型
     * @param list
     *            输入的数组
     * @return 返回随机的一个数组元素对象集合
     */
    public static <T> List<T> randomSome(List<T> list) {
        return randomSome(list, random.nextInt(list.size()));
    }

    /**
     * 从输入list中随机返回count个对象.
     * 
     * @param <T>
     *            泛型
     * @param list
     *            输入的数组
     * @param count
     *            数量
     * @return 返回随机的数组元素count个对象的集合
     */
    public static <T> List<T> randomSome(List<T> list, int count) {
        Collections.shuffle(list);
        return list.subList(0, count);
    }
}
