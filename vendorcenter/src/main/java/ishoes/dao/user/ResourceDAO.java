/**
 * 
 */
package ishoes.dao.user;

import ishoes.domain.user.Resource;

import java.util.List;

import core.dao.genericType.jdbc.GenericTypeJdbcDAO;

/**
 * 权限控制模块中的资源实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface ResourceDAO extends GenericTypeJdbcDAO<Resource, Long> {

	public List<Resource> getUrlResourceWithAuthorities();
}
