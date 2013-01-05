/**
 * 
 */
package ishoes.dao.user.impl;

import ishoes.dao.user.RoleDAO;
import ishoes.domain.user.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.dao.genericType.jdbc.impl.GenericTypeJdbcDAOImpl;

/**
 * RoleDAO接口的DAO实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Component
public class RoleDAOImpl extends GenericTypeJdbcDAOImpl<Role, Long> implements
		RoleDAO {

	public List<Role> getAllRoles() {
		String sql = "select * from role";
		List<Role> roles = this.query(sql, new RowMapper<Role>() {
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setRole_id(rs.getLong("role_id"));
				role.setRole_name(rs.getString("role_name"));
				role.setDescprtion(rs.getString("descprition"));
				return role;
			}
		});
		return roles;
	}

	public List<Role> resourceFetchRoles(long resource_id) {
		String sqlFetch = "select a.role_id,a.role_name from role a,resource_role c where c.resource_id=? and c.role_id=a.role_id";
		List<Role> roles = this.query(sqlFetch, new Object[] { resource_id },
				new RowMapper<Role>() {
					public Role mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Role role = new Role();
						role.setRole_id(rs.getLong("role_id"));
						role.setRole_name(rs.getString("role_name"));
						return role;
					}
				});
		return roles;
	}

	public List<Role> userFetchRoles(long user_id) {
		String sqlFetch = "select a.role_id,a.role_name from role a,user_role c where c.user_id=? and c.role_id=a.role_id";
		List<Role> roles = this.query(sqlFetch, new Object[] { user_id },
				new RowMapper<Role>() {
					public Role mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Role role = new Role();
						role.setRole_id(rs.getLong("role_id"));
						role.setRole_name(rs.getString("role_name"));
						return role;
					}
				});
		return roles;
	}
}
