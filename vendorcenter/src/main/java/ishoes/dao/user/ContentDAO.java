/**
 * 
 */
package ishoes.dao.user;

import ishoes.domain.search.Content;

import java.util.List;

import core.dao.genericType.jdbc.GenericTypeJdbcDAO;

/**
 * search内容content实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface ContentDAO extends GenericTypeJdbcDAO<Content, Long> {

	/**
	 * 根据关键字联想查询需要的content实体对象的List集合
	 * 
	 * @param keyword
	 *            客户端传来的关键字
	 * @return 联想到的content实体对象的List集合
	 */
	public List<Content> associateSearch(String keyword);
}
