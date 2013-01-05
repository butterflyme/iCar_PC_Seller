/*
 * 版权：Copyright 2010-2015 leze Tech. Co. Ltd. All Rights Reserved.
 * 修改人：邓杰
 * 修改时间：2012-7-17
 * 修改内容：
 */
package core.dao.genericType.jdbc.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import core.dao.genericType.jdbc.GenericTypeJdbcDAO;
import core.domain.Page;

/**
 * GenericTypeJdbcDAO接口的实现类。使用JdbcTemplate的泛型DAO实现。封装其方法，为各领域的DAO提供基础方法。
 * 
 * @param <T>
 *            泛型
 * @param <PK>
 *            主建
 * @author dengjie
 * @version 1.0, 2012-8-24
 * @since 1.0
 */
@Component
public class GenericTypeJdbcDAOImpl<T, PK extends Serializable> implements GenericTypeJdbcDAO<T, PK> {
    private static final Log log = LogFactory.getLog(GenericTypeJdbcDAOImpl.class);
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    /**
     * 循环中使用的批量更新SQL。一般不常用
     * 
     * @param sqls
     *            单个更新的SQL数组
     * @return 每个SQL更新的数量的数组
     */
    public int[] batchUpdate(String[] sqls) {
        return this.jdbcTemplate.batchUpdate(sqls);
    }

    /**
     * 批量更新方法。传入sql为批量更新SQL。使用BatchPreparedStatementSetter回调接口进行更新的操作。 例如： public int[] insertNewCustomers(final List
     * customers){ dao.batchUpdate("insert into customer value(?,?)",new BatchPreparedStatementSetter(){
     * //返回批量更新的数目，因为我们要通过List传入的所有顾客信息进行更新。所以当前批量更新的数目就是当前List中所有的顾客数目。 public int getBatchSize(){ return
     * customers.size(); } //设置具体的更新数据，其中第2个int型的参数i对应的每笔更新的索引，我们就是根据这个索引来渠道相应的信息来进行设置的。 public void
     * setValues(PreparedStatement ps,int i) throws SQLException { Customer customer = (Customer)customers.get(i);
     * ps.setString(1,customer.getFirstName()); ps.setString(2,customer.getLastName()); } }) }
     * 这个BatchPreparedStatementSetter回调可以采用一个静态的内部类中封装。如： private static final class CustomerBatchPStatementSetter
     * implements BatchPreparedStatementSetter{ //返回批量更新的数目，因为我们要通过List传入的所有顾客信息进行更新。所以当前批量更新的数目就是当前List中所有的顾客数目。 public
     * int getBatchSize(){ return customers.size(); } //设置具体的更新数据，其中第2个int型的参数i对应的每笔更新的索引，我们就是根据这个索引来渠道相应的信息来进行设置的。
     * public void setValues(PreparedStatement ps,int i) throws SQLException { Customer customer =
     * (Customer)customers.get(i); ps.setString(1,customer.getFirstName()); ps.setString(2,customer.getLastName()); } }
     * 这样在update方法中：dao.batchUpdate("insert into customer value(?,?)",new CustomerBatchPStatementSetter());
     * 
     * @param sql
     *            批量更新的SQL语句,可以是insert、update、delete
     * @param pss
     *            回调接口可以让我们进行细致的设置
     * @return an array of the number of rows affected by each statement
     */
    public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
        return this.jdbcTemplate.batchUpdate(sql, pss);
    }

    /**
     * 删除方法，必须传入delete的SQL语句。例如： delete("delete from customer where customerId between ? and ?",new Object[]{new
     * Integer(23),new Integer(101)});
     * 
     * @param sql
     *            delete的sql语句
     * @return 最终删除操作所影响的记录数目
     */
    public int delete(String sql) {
        if (!(StringUtils.contains(sql, "delete") || StringUtils.contains(sql, "DELETE"))) {
            log.error("delete方法必须使用delete的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql);
    }

    /**
     * 删除方法，必须传入delete的SQL语句。例如： delete("delete from customer where customerId between ? and ?",new Object[]{new
     * Integer(23),new Integer(101)});
     * 
     * @param sql
     *            delete的sql语句
     * 
     * @param args
     *            对象数组
     * @return 最终删除操作所影响的记录数目
     */
    public int delete(String sql, Object[] args) {
        if (!(StringUtils.contains(sql, "delete") || StringUtils.contains(sql, "DELETE"))) {
            log.error("delete方法必须使用delete的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql, args);
    }

    /**
     * DDL执行。
     * 
     * @param sql
     *            DDL的SQL，比如说用来建立视图，表等SQL
     */
    public void executeDDL(String sql) {
        this.jdbcTemplate.execute(sql);
    }

    /**
     * 使用RowMapper回调接口的查询方法，传入的必须是select的SQL语句。查询出来结果可以靠rowMapper接口进行处理。 例如：
     * dao.query("select * from customer where customerId=?",new Object[]{new Integer(120)}, new RowMapper(){ public
     * Object mapRow(ResultSet rs, int rowNum){ Customer customer = new Customer();
     * customer.setFirstName(rs.getString(1)); customer.setLastName(rs.getString(2)); return customer; } } );
     * 如果示例中的代码出现在同一段程序中，我们有必要去掉这些重复的RowMapper匿名类代码，将这些代码抽取到一个单独的类中（通常是一个静态的内部类）。
     * 这样，这个内部类就可以在DAO的方法中被共享。因而，最后2个示例写成如下的形式将更加好： private static final class CustomerMapper<Customer> implements
     * RowMapper<Customer> { public Object mapRow(ResultSet rs, int rowNum){ Customer customer = new Customer();
     * customer.setFirstName(rs.getString(1)); customer.setLastName(rs.getString(2)); return customer; } }
     * 这样方法可以改为：dao.query("select * from customer where customerId=?",new Object[]{new Integer(120),new
     * CustomerMapper<Customer>());
     * 
     * @param sql
     *            select查询SQL
     * @param args
     *            SQL中用？问号匹配的参数
     * @param rowMapper
     *            用来处理结果集的回调接口
     * @param <TT>
     *            泛型类型
     * @return 泛型的结果集
     */
    public <TT> List<TT> query(String sql, Object[] args, RowMapper<TT> rowMapper) {
        return this.jdbcTemplate.query(sql, args, rowMapper);
    }

    /**
     * 使用RowMapper回调接口的查询方法，传入的必须是select的SQL语句。查询出来结果可以靠rowMapper接口进行处理。 例如：
     * dao.query("select * from customer where customerId=?",new Object[]{new Integer(120)}, new RowMapper(){ public
     * Object mapRow(ResultSet rs, int rowNum){ Customer customer = new Customer();
     * customer.setFirstName(rs.getString(1)); customer.setLastName(rs.getString(2)); return customer; } } );
     * 如果示例中的代码出现在同一段程序中，我们有必要去掉这些重复的RowMapper匿名类代码，将这些代码抽取到一个单独的类中（通常是一个静态的内部类）。
     * 这样，这个内部类就可以在DAO的方法中被共享。因而，最后2个示例写成如下的形式将更加好： private static final class CustomerMapper<Customer> implements
     * RowMapper<Customer> { public Object mapRow(ResultSet rs, int rowNum){ Customer customer = new Customer();
     * customer.setFirstName(rs.getString(1)); customer.setLastName(rs.getString(2)); return customer; } }
     * 这样方法可以改为：dao.query("select * from customer where customerId=?",new Object[]{new Integer(120),new
     * CustomerMapper<Customer>());
     * 
     * @param sql
     *            select查询SQL
     * @param rowMapper
     *            用来处理结果集的回调接口
     * @return 泛型的结果集
     */
    public List<T> query(String sql, RowMapper<T> rowMapper) {
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    /**
     * 查询结果为一个int类型的SQL
     * 
     * @param sql
     *            查询SQL
     * @return 查询结果为int类型
     */
    public int queryForInt(String sql) {
        return this.jdbcTemplate.queryForInt(sql);
    }

    /**
     * 查询结果为一个int类型的SQL
     * 
     * @param sql
     *            查询SQL，用问号进行占位参数
     * @param args
     *            匹配的占位参数的值
     * @return 查询结果为int类型
     */
    public int queryForInt(String sql, Object... args) {
        return this.jdbcTemplate.queryForInt(sql, args);
    }

    /**
     * 使用一个封装好的查询SQL，查询结果必须是单一结果的查询。被自动封装成map格式，map的键对应所查询表的列名， map的值是对应键所在列的值。
     * 
     * @param sql
     *            静态SQL
     * @return map格式的单一结果
     */
    public List<Map<String, Object>> queryForList(String sql) {
        return this.jdbcTemplate.queryForList(sql);
    }

    /**
     * 使用带占位符的SQL，进行查询，查询结果是单一结果。被自动封装成map格式，map的键对应所查询表的列名， map的值是对应键所在列的值。
     * 
     * @param sql
     *            查询SQL
     * @param args
     *            占位符?的对应值
     * @return map格式的单一结果
     */
    public List<Map<String, Object>> queryForList(String sql, Object... args) {
        return this.jdbcTemplate.queryForList(sql, args);
    }

    /**
     * 查询结果为一个long类型的SQL
     * 
     * @param sql
     *            查询SQL
     * @return 查询结果为long类型
     */
    public long queryForLong(String sql) {
        return this.jdbcTemplate.queryForLong(sql);
    }

    /**
     * 查询结果为一个long类型的SQL
     * 
     * @param sql
     *            查询SQL，用问号进行占位参数
     * @param args
     *            匹配的占位参数的值
     * @return 查询结果为long类型
     */
    public long queryForLong(String sql, Object... args) {
        return this.jdbcTemplate.queryForLong(sql, args);
    }

    /**
     * 使用一个封装好的查询SQL，查询结果必须是单一结果的查询。被自动封装成map格式，map的键对应所查询表的列名， map的值是对应键所在列的值。
     * 
     * @param sql
     *            静态SQL
     * @return map格式的单一结果
     */
    public Map<String, Object> queryForMap(String sql) {
        return this.jdbcTemplate.queryForMap(sql);
    }

    /**
     * 使用带占位符的SQL，进行查询，查询结果是单一结果。被自动封装成map格式，map的键对应所查询表的列名， map的值是对应键所在列的值。
     * 
     * @param sql
     *            查询SQL
     * @param args
     *            占位符?的对应值
     * @return map格式的单一结果
     */
    public Map<String, Object> queryForMap(String sql, Object... args) {
        return this.jdbcTemplate.queryForMap(sql, args);
    }

    /**
     * 用预编译sql和rowMapper直接查询结果只为单个的对象。
     * 
     * @param sql
     *            预编译的SQL
     * @param rowMapper
     *            用来处理结果集的回调接口
     * @return 单个结果对象
     */
    public T queryForObject(String sql, RowMapper<T> rowMapper) {
        return this.jdbcTemplate.queryForObject(sql, rowMapper);
    }

    /**
     * 用带参数的sql和rowMapper直接查询结果只为单个对象
     * 
     * @param sql
     *            带占位符的预编译SQL
     * @param rowMapper
     *            用来处理结果集的回调接口
     * @param args
     *            占位符的参数值
     * @return 单个结果对象
     */
    public T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
        return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
    }

    /**
     * 利用反射避免使用rowMapper进行结果集封装，该查询是查询单个结果对象
     * 
     * @param sql
     *            预编译的SQL
     * @param requiredType
     *            想要查询的对象的CLASS对象
     * @return 单个结果对象
     */
    public T queryForObject(String sql, Class<T> requiredType) {
        return this.jdbcTemplate.queryForObject(sql, requiredType);
    }

    /**
     * 利用反射避免使用rowMapper进行结果集封装，该查询是查询单个结果对象。
     * 
     * @param sql
     *            预编译带占位符的SQL
     * @param requiredType
     *            想要查询的对象的CLASS对象
     * @param args
     *            占位符?的对应值
     * @return 单个结果对象
     */
    public T queryForObject(String sql, Class<T> requiredType, Object... args) {
        return this.jdbcTemplate.queryForObject(sql, requiredType, args);
    }

    /**
     * 保存方法，必须传入insert的SQL语句
     * 
     * @param sql
     *            必须传入insert的sql语句
     * @return 保存的记录数
     */
    public int save(String sql) {
        if (!(StringUtils.contains(sql, "insert") || StringUtils.contains(sql, "INSERT"))) {
            log.error("save方法必须使用insert的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql);
    }

    /**
     * 保存方法，必须传入insert的SQL语句
     * 
     * @param sql
     *            必须传入insert的sql语句
     * @param args
     *            sql中对应的参数
     * @return 保存的记录数
     */
    public int save(String sql, Object[] args) {
        if (!(StringUtils.contains(sql, "insert") || StringUtils.contains(sql, "INSERT"))) {
            log.error("save方法必须使用insert的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql, args);
    }

    /**
     * 保存方法，必须传入insert的SQL语句。用来获取insert的自增键的值。 例子：public long save(final User user) { final String sql =
     * "insert into user (email,password,status,real_name,birthdate,head_picture) VALUES (?, ?, ?, ?, ?, ?)";
     * 
     * return this.save(sql, new PreparedStatementCreator() { public PreparedStatement
     * createPreparedStatement(Connection con) throws SQLException { PreparedStatement ps = con.prepareStatement(sql,
     * new String[] {"user_id"}); ps.setString(1, user.getEmail()); ps.setString(2, user.getPassword()); ps.setInt(3,
     * user.getStatus()); ps.setString(4, user.getReal_name()); ps.setDate(5, (Date) user.getBirthdate());
     * ps.setString(6, user.getHead_picture()); return ps; } }); return 0; }
     * 
     * @param sql
     *            必须传入insert的sql语句
     * @param psc
     *            提供了封装好的SQL与参数值的对象
     * @return 自增键的值
     */
    public long save(final String sql, PreparedStatementCreator psc) {
        if (!(StringUtils.contains(sql, "insert") || StringUtils.contains(sql, "INSERT"))) {
            log.error("save方法必须使用insert的SQL语句，不能使用其他类型SQL");
        }
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    /**
     * 更新方法，必须传入update的SQL语句。例如： update("update customer set customerName='Jay',age=23 where customerId=123");
     * 
     * @param sql
     *            必须传入update的SQL语句
     * @return 最终更新操作所影响的记录数目
     */
    public int update(String sql) {
        if (!(StringUtils.contains(sql, "update") || StringUtils.contains(sql, "UPDATE"))) {
            log.error("update方法必须使用update的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql);
    }

    /**
     * 更新方法，必须传入update的SQL语句。例如： update("update customer set customerName=?,age=? where customerId=?",new
     * Object[]{"Jay",new Integer(23),new Integer(101)});
     * 
     * @param sql
     *            必须传入update的SQL语句
     * @param args
     *            问号匹配的参数,请注意一定要匹配位置。
     * @return 最终更新操作所影响的记录数目
     */
    public int update(String sql, Object[] args) {
        if (!(StringUtils.contains(sql, "update") || StringUtils.contains(sql, "UPDATE"))) {
            log.error("update方法必须使用update的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql, args);
    }

    /**
     * 如果我们有必要多更新操作进行更多的控制。那么我们可以使用与PreparedStatement相关的callback接口。 使用例子如下： int affectedRows =
     * dao.update("update customer set customerName=?,age=? where customerId=?", new PreparedStatementSetter(){ public
     * void setValues(PreparedStatement ps) throws SQLException { ps.setString(1,"Daniel"); ps.setInt(2,36);
     * ps.setInt(3,101); } } ); 这里的参数PreparedStatementSetter的实例最好写在一个静态的内部类中。便于代码阅读。
     * 
     * @param sql
     *            必须传入update的SQL语句
     * @param pss
     *            PreparedStatementSetter回调接口
     * @return 最终更新操作所影响的记录数目
     */
    public int update(String sql, PreparedStatementSetter pss) {
        if (!(StringUtils.contains(sql, "update") || StringUtils.contains(sql, "UPDATE"))) {
            log.error("update方法必须使用update的SQL语句，不能使用其他类型SQL");
        }
        return this.jdbcTemplate.update(sql, pss);
    }

    /**
     * 根据最新时间倒叙排列，这里时间采用ID进行查询。由于数据库有自增长的主键ID，ID越大说明数据越新。 查询出来的最新的一页。该方法实现的SQL举例：SELECT * FROM user ORDER BY user_id
     * DESC LIMIT 20 继续下一页的SQL：SELECT * FROM user ORDER BY user_id DESC LIMIT 20。这样可以最高效率的进行并发搜索。
     * 直接使用最小主键进行limit查询，不用借助page对象进行。
     * 
     * @param pageSize
     *            一页展示的数量
     * @param minKey
     *            上一次查询最小的主键
     * @param sql
     *            一般查询SQL，不要带有limit关键字，不需要手动去限制。例如：SELECT * FROM user
     * @param primaryKey
     *            用来排序的查询表的主键，因为主键是根据时间自动增长的，所以主键默认为索引进行排序效率最高。
     * @param rowMapper
     *            处理结果集的回调接口
     * @param values
     *            匹配SQL中占位符的值，注意位置要一致
     * @return 封装好查询结果集的集合
     */
    public List<T> findPageOrderByMinKey(final int pageSize, final long minKey, final String sql,
            final String primaryKey, RowMapper<T> rowMapper, final Object... values) {
        String pageSql = sql;
        // 默认设置60的能力因子保证不要让字符串超出后调用内存继续分配，节约效率。
        StringBuilder sqlSb = new StringBuilder(150);
        sqlSb.append(pageSql);
        if (pageSql.toLowerCase().contains("where")) {
            sqlSb.append(" and ").append(primaryKey).append("<").append(minKey);
        } else {
            sqlSb.append(" where ").append(primaryKey).append("<").append(minKey);
        }
        sqlSb.append(" order by ").append(primaryKey).append(" desc limit ").append(pageSize);
        List<T> result = this.query(sqlSb.toString(), values, rowMapper);
        // 频繁查询后，将字符串对象设置为null，便于GC回收。
        sqlSb = null;
        return result;
    }

    /**
     * 根据最新时间倒叙排列，这里时间采用ID进行查询。由于数据库有自增长的主键ID，ID越大说明数据越新。 查询出来的最新的一页。该方法实现的SQL举例：SELECT * FROM user ORDER BY user_id
     * DESC LIMIT 0,20 继续下一页的SQL：SELECT * FROM user ORDER BY user_id DESC LIMIT 20,20。这样可以最高效率的进行并发搜索。
     * 
     * @param page
     *            分页对象，封装了结果集与一些分页属性的对象
     * @param sql
     *            一般查询SQL，不要带有limit关键字，不需要手动去限制。例如：SELECT * FROM user
     * @param primaryKey
     *            用来排序的查询表的主键，因为主键是根据时间自动增长的，所以主键默认为索引进行排序效率最高。
     * @param rowMapper
     *            处理结果集的回调接口
     * @param values
     *            匹配SQL中占位符的值，注意位置要一致
     * @return 封装好查询结果集的page对象
     */
    public Page<T> findPageOrderByNew(final Page<T> page, final String sql, final String primaryKey,
            RowMapper<T> rowMapper, final Object... values) {
        Assert.notNull(page);
        String pageSql = sql;
        if (page.isAutoCount()) {
            int totalCount = countSqlResult(pageSql, values);
            page.setTotalCount(totalCount);
        }
        // 默认设置60的能力因子保证不要让字符串超出后调用内存继续分配，节约效率。
        StringBuilder sqlSb = new StringBuilder(150);
        if (page.isFirstSetted()) {
            sqlSb.append(pageSql).append(" order by ").append(primaryKey).append(" desc limit ")
                    .append(page.getFirst());
            // pageSql = pageSql + " ORDER BY " + primaryKey + " DESC limit " + page.getFirst();
        }
        if (page.isPageSizeSetted()) {
            sqlSb.append(",").append(page.getPageSize());
            // pageSql = pageSql + ',' + page.getPageSize();
        }
        List<T> result = this.query(sqlSb.toString(), values, rowMapper);
        page.setResult(result);
        // 频繁查询后，将字符串对象设置为null，便于GC回收。
        sqlSb = null;
        return page;
    }

    /**
     * 传入一般查询SQL，可以得到一个分页的Page对象。用于WEB层进行分页显示。只适合使用limit分页的数据库，且如果实际情况你需要使用到优化limit的方法：反向查找优化法，
     * 那么请使用findPageByReverseLookup()。
     * 
     * @param page
     *            分页对象，封装了结果集与一些分页属性的对象
     * @param sql
     *            一般查询SQL，不要带有limit关键字，不需要手动去限制。
     * @param rowMapper
     *            处理结果集的回调接口
     * @param values
     *            匹配SQL中占位符的值，注意位置要一致
     * @param <TT>
     *            泛型类型
     * @return 封装好查询结果集的page对象
     */
    public <TT> Page<TT> findPageCommon(final Page<TT> page, final String sql, RowMapper<TT> rowMapper,
            final Object... values) {
        Assert.notNull(page);
        String pageSql = sql;
        if (page.isAutoCount()) {
            int totalCount = countSqlResult(pageSql, values);
            page.setTotalCount(totalCount);
        }
        // 默认设置60的能力因子保证不要让字符串超出后调用内存继续分配，节约效率。
        StringBuilder sqlSb = new StringBuilder(150);
        if (page.isFirstSetted()) {
            sqlSb.append(pageSql).append(" limit ").append(page.getFirst());
            // pageSql = pageSql + " limit " + page.getFirst();
        }
        if (page.isPageSizeSetted()) {
            sqlSb.append(",").append(page.getPageSize());
            // pageSql = pageSql + ',' + page.getPageSize();
        }
        List<TT> result = this.query(sqlSb.toString(), values, rowMapper);
        page.setResult(result);
        // 频繁查询后，将字符串对象设置为null，便于GC回收。
        sqlSb = null;
        return page;
    }

    /**
     * 反向查找优化limit的分页查询方法。
     * <p>
     * limit 10000,20的意思扫描满足条件的10020行，扔掉前面的10000行，返回最后的20行，问题就在这里，如果是limit
     * 100000,100，需要扫描100100行，在一个高并发的应用里，每次查询需要扫描超过10W行，性能肯定大打折扣。文中还提到limit n性能是没问题的，因为只扫描n行。
     * 文中提到一种“clue”的做法，给翻页提供一些“线索”，比如还是SELECT * FROM message ORDER BY id
     * DESC，按id降序分页，每页20条，当前是第10页，当前页条目id最大的是9527，最小的是9500，如果我们只提供”上一页”、”下一页”这样的跳转（不提供到第N页的跳转），那么在处理”上一页”的时候SQL语句可以是：
     * SELECT * FROM message WHERE id > 9527 ORDER BY id ASC LIMIT 20; 处理”下一页”的时候SQL语句可以是： SELECT * FROM message WHERE
     * id < 9500 ORDER BY id DESC LIMIT 20; 不管翻多少页，每次查询只扫描20行。 缺点是只能提供”上一页”、”下一页”的链接形式，但是我们的产品经理非常喜欢”<上一页 1 2 3 4 5 6 7
     * 8 9 下一页>”这样的链接方式，怎么办呢？ 如果LIMIT m,n不可避免的话，要优化效率，只有尽可能的让m小一下，我们扩展前面的”clue”做法，还是SELECT * FROM message ORDER BY id
     * DESC，按id降序分页，每页20条，当前是第10页，当前页条目id最大的是9527，最小的是9500，比如要跳到第8页，我看的SQL语句可以这样写：SELECT * FROM message WHERE id > 9527
     * ORDER BY id ASC LIMIT 20,20; 跳转到第13页： SELECT * FROM message WHERE id < 9500 ORDER BY id DESC LIMIT 40,20;
     * 原理还是一样，记录住当前页id的最大值和最小值，计算跳转页面和当前页相对偏移，由于页面相近，这个偏移量不会很大，这样的话m值相对较小，大大减少扫描的行数。其实传统的limit
     * m,n，相对的偏移一直是第一页，这样的话越翻到后面，效率越差，而上面给出的方法就没有这样的问题。
     * </p>
     * <P>
     * limit偏移算法： 正向查找： (当前页 - 1) * 页长度 反向查找： 总记录 - 当前页 * 页长度
     * </P>
     * <p>
     * 注意：该方法要正常使用必须将当前页面查询出来的结果对象的最大ID和最小ID，这里的ID必须是关联到数据库的自增ID主键。将最大ID赋值给page对象中的maxId属性，将最小ID赋值给minId属性提供用于反向查找使用的ID值
     * 。 并且赋值当前页号currPageNo，点击要去的目的页号destPageNo。
     * </p>
     * 
     * @param page
     *            分页对象，封装了结果集与一些分页属性的对象
     * @param sql
     *            一般查询SQL，不要带有limit关键字，不需要手动去限制。
     * @param rowMapper
     *            处理结果集的回调接口
     * @param values
     *            匹配SQL中占位符的值，注意位置要一致
     * @param pkeyName
     *            表中的自增主键字段名字
     * @return 封装好查询结果集的page对象
     */
    public Page<T> findPageByReverseLookup(Page<T> page, String sql, String pkeyName, RowMapper<T> rowMapper,
            Object... values) {
        Assert.notNull(page);
        int currPageNo = page.getCurrPageNo();
        int destPageNo = page.getDestPageNo();
        int pageSize = page.getPageSize();
        if (pageSize <= -1) {
            return null;
        }
        String pageSql = sql;
        if (page.isAutoCount()) {
            int totalCount = countSqlResult(pageSql, values);
            page.setTotalCount(totalCount);
        }
        // 默认设置60的能力因子保证不要让字符串超出后调用内存继续分配，节约效率。
        StringBuilder sqlSb = new StringBuilder(150);
        if (pageSql.toLowerCase().contains("where")) {
            if (currPageNo > destPageNo) {
                sqlSb.append(pageSql).append(" and ").append(pkeyName).append("<").append(page.getMinId())
                        .append(" order by ").append(pkeyName).append(" desc limit ")
                        .append((currPageNo - destPageNo) * pageSize);
            } else {
                sqlSb.append(pageSql).append(" and ").append(pkeyName).append(">").append(page.getMaxId())
                        .append(" order by ").append(pkeyName).append(" asc limit ")
                        .append((destPageNo - currPageNo) * pageSize);
            }
        } else {
            if (currPageNo > destPageNo) {
                sqlSb.append(pageSql).append(" where ").append(pkeyName).append("<").append(page.getMinId())
                        .append(" order by ").append(pkeyName).append(" desc limit ")
                        .append((currPageNo - destPageNo) * pageSize);
            } else {
                sqlSb.append(pageSql).append(" where ").append(pkeyName).append(">").append(page.getMaxId())
                        .append(" order by ").append(pkeyName).append(" asc limit ")
                        .append((destPageNo - currPageNo) * pageSize);
            }
        }
        sqlSb.append(",").append(pageSize);
        List<T> result = this.query(sqlSb.toString(), values, rowMapper);
        page.setResult(result);
        // 频繁查询后，将字符串对象设置为null，便于GC回收。
        sqlSb = null;
        return page;
    }

    /**
     * 传入普通查询SQL,得到统计总数。使用的count函数
     * 
     * @param pageSql
     *            分页SQL
     * @param values
     *            匹配SQL中占位符的值，注意位置要一致
     * @return 查询SQL查询的总数
     */
    public int countSqlResult(final String pageSql, final Object... values) {
        int count = 0;
        String fromSql = pageSql.toLowerCase();

        fromSql = "from " + StringUtils.substringAfter(fromSql, "from");
        fromSql = StringUtils.substringBefore(fromSql, "order by");

        String countSql = "select count(*) " + fromSql;

        try {
            count = queryForInt(countSql, values);
        } catch (Exception e) {
            log.error("sql can't be auto count, sql is:" + countSql, e);
        }
        return count;
    }

    /**
     * 查询多个结果，每一行反射为对应的Class对象，返回对象的List
     * 
     * @param sql
     *            预编译sql
     * @param elementType
     *            一行数据对应的Class
     * @param <TT>
     *            需要返回的class
     * @param args
     *            sql的参数值
     * @return 对象List
     */
    public <TT> List<TT> queryForList(String sql, Class<TT> elementType, Object... args) {
        return jdbcTemplate.queryForList(sql, elementType, args);
    }
}
