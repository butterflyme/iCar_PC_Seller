/**
 * 
 */
package ishoes.dao.user;


import ishoes.domain.user.Role;

import java.util.List;

import core.dao.genericType.jdbc.GenericTypeJdbcDAO;

/**
 * 权限控制模块中的权限实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface RoleDAO extends GenericTypeJdbcDAO<Role, Long> {
    /**
     * 查询全部roles数据
     * @return roles列表
     */
	public List<Role> getAllRoles();
	/**
	 * 通过resource_id关联查询其关联的role列表。
	 * @return role的列表
	 */
	public List<Role> resourceFetchRoles(long resource_id);
	/**
	 * 通过user_id关联查询其关联的role列表。
	 * @param user_id
	 * @return role的列表
	 */
	public List<Role> userFetchRoles(long user_id);
}
