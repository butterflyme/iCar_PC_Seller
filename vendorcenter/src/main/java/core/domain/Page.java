/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.domain;

import java.util.List;

/**
 * <p>
 * 封装分页参数与结果集的分页对象，用来实现分页操作。
 * </p>
 * <p>
 * 需要主要的是里面参数的设置。
 * </p>
 * <p>
 * <ul>
 * <li>pageNo:需要查询的当前页号，比如说你想查询第2页的结果集，那么就要设置pageNo为2。其默认值是1</li>
 * <li>pageSize：每页的展示结果集的大小，默认为-1。一般都需要进行查询设置，设置的时候最好通过常量为其赋值，避免魔鬼数字。<br/>
 * 如：PAGE_SIZE=10;不要直接用10。在代码中都要避免魔鬼数字，有含义的数字需要用常量进行表达，不会消耗多少性能。</li>
 * <li>orderBy：需要进行排序的实体属性名。如果不设置就为null,那么asc属性也不会起作用。</li>
 * <li>asc：true的时候为升序，false表示降序。需要和orderBy属性配合。</li>
 * <li>autoCount：开启自动统计。默认为false。</li>
 * <li>result：封装结果集的List。</li>
 * <li>totalCount：总共查询出来的数量，用于分页计算。</li>
 * <li>minId:该属性是表示当前页中最小ID对象的ID（约定最小ID的靠前），该属性用户优化limit分页查询方法”findPageByReverseLookup（反向查找）“中用户往前翻页使用。 具体参考其方法。</li>
 * <li>maxId:该属性是表示当前页中最大ID对象的ID（约定最大ID的靠后），该属性用户优化limit分页查询方法”findPageByReverseLookup（反向查找）“中用户往后翻页使用。 具体参考其方法。</li>
 * </ul>
 * </p>
 * 
 * @param <T>
 *            泛型类型
 * @author dengjie
 * @version 1.0, 2012-8-24
 * @since 1.0
 */
public class Page<T> implements java.io.Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 2919972694034745636L;

    private int pageNo = 1;
    private int pageSize = -1;
    private String orderBy = null;
    private boolean asc = true;
    private boolean autoCount = false;
    private List<T> result = null;
    private int totalCount = -1;
    // 反向查找优化limit算法必须使用的属性,普通分页是相对第1页进行偏移，他是动态偏移。
    // 当前页最小的ID
    private long minId = 0;
    // 当前页最大的ID
    private long maxId = 0;
    private int currPageNo = 1;
    private int destPageNo = 1;

    /**
     * 构造函数
     */
    public Page() {

    }

    /**
     * 构造方法
     * 
     * @param pageSize
     *            每页的展示结果集的大小
     * @param autoCount
     *            自动统计
     */
    public Page(int pageSize, boolean autoCount) {
        this.pageSize = pageSize;
        this.autoCount = autoCount;
    }

    /**
     * 构造方法
     * 
     * @param pageSize
     *            每页的展示结果集的大小
     * @param autoCount
     *            自动统计
     * @param pageNo
     *            当前页号，下拉分页的时候由前端传递页号
     */
    public Page(int pageSize, int pageNo, boolean autoCount) {
        this.pageSize = pageSize;
        this.autoCount = autoCount;
        this.pageNo = pageNo;
    }

    /**
     * 得到当前分页页号
     * 
     * @return 当前页号
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置页号
     * 
     * @param pageNo
     *            页号
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 每页展示结果集的大小是否被设置
     * 
     * @return true表示pageSize大于-1,false表示还是默认值没有被设置
     */
    public boolean isPageSizeSetted() {
        return pageSize > -1;
    }

    /**
     * 获得pageSize
     * 
     * @return 每页结果集大小
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页展示结果集大小
     * 
     * @param pageSize
     *            每页展示结果集大小
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 当前页展示结果集从那个元素开始，criteria.setFirstResult(page.getFirst());
     * 
     * @return 得到需要展示的元素序号
     */
    public int getFirst() {
        int pageNow = -1;
        if (pageNo < 1 || pageSize < 1) {
            return pageNow;
        } else {
            pageNow = pageNo - 1;
            pageNow = pageNow * pageSize;
            return pageNow;
        }
    }

    /**
     * 是否自动计算了first也就是等价于是否设置了pageNo和pageSize
     * 
     * @return true 表示设置了，pageSize与pageNo都大于0
     */
    public boolean isFirstSetted() {
        boolean isFirst = false;
        isFirst = pageNo > 0;
        isFirst = pageSize > 0;
        return isFirst;
    }

    /**
     * 是否设置了orderBy
     * 
     * @return true | false
     */
    public boolean isOrderBySetted() {
        return this.orderBy != null;
    }

    /**
     * 获取排序属性
     * 
     * @return 排序属性名
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置排序属性
     * 
     * @param orderBy
     *            需要进行排序的实体属性名
     */
    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * 排序是否为升序
     * 
     * @return true|false
     */
    public boolean isAsc() {
        return asc;
    }

    /**
     * 为排序属性设置升序
     * 
     * @param asc
     *            升序
     */
    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    /**
     * 是否为自动统计
     * 
     * @return true|false
     */
    public boolean isAutoCount() {
        return autoCount;
    }

    /**
     * 设置自动统计
     * 
     * @param autoCount
     *            true|false
     */
    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    /**
     * 获取分页查询后封装在page对象中的result结果集。
     * 
     * @return 分页查询后的结果集
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置结果集，基本用不着。
     * 
     * @param result
     *            该泛型对象的结果集List
     */
    public void setResult(List<T> result) {
        this.result = result;
    }

    /**
     * 获取分页查询全部的总数，一般是由调用SQL Count函数完成。
     * 
     * @return 查询出来的总数
     */
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 设置总数，一般不用这个方法进行设置。
     * 
     * @param totalCount
     *            分页查询的总数
     */
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 计算分页查询的总页数函数
     * 
     * @return 总页数
     */
    public int getTotalPages() {
        if (totalCount == -1) {
            return -1;
        }
        int count = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            count++;
        }
        return count;
    }

    /**
     * 该函数测试是否还能进行翻页操作。
     * 
     * @return true表示还可以翻页到下一页，false表示现在已经是最末页不能下一页。
     */
    public boolean isHasNext() {
        int number = pageNo + 1;
        int totalPages = getTotalPages();
        boolean isHasNext = number <= totalPages;
        return isHasNext;
    }

    /**
     * 得到下一页
     * 
     * @return 下一页页号
     */
    public int getNextPage() {
        if (isHasNext()) {
            return pageNo + 1;
        } else {
            return pageNo;
        }
    }

    /**
     * 是否能往上一页
     * 
     * @return true|false
     */
    public boolean isHasPre() {
        int number = pageNo - 1;
        boolean isHasPre = number >= 1;
        return isHasPre;

    }

    /**
     * 得到上一页页号
     * 
     * @return 上一页页号
     */
    public int getPrePage() {
        if (isHasPre()) {
            return pageNo - 1;
        } else {
            return pageNo;
        }
    }

    public long getMinId() {
        return minId;
    }

    /**
     * 当前页的最小的ID，反向查找优化limit的分页查询方法的时候必须设置正确。
     * 
     * @param minId
     *            当前页的最大的ID
     */
    public void setMinId(long minId) {
        this.minId = minId;
    }

    public long getMaxId() {
        return maxId;
    }

    /**
     * 当前页的最大的ID，反向查找优化limit的分页查询方法的时候必须设置正确。
     * 
     * @param maxId
     *            当前页的最大的ID
     */
    public void setMaxId(long maxId) {
        this.maxId = maxId;
    }

    public int getCurrPageNo() {
        return currPageNo;
    }

    public void setCurrPageNo(int currPageNo) {
        this.currPageNo = currPageNo;
    }

    public int getDestPageNo() {
        return destPageNo;
    }

    public void setDestPageNo(int destPageNo) {
        this.destPageNo = destPageNo;
    }
}
