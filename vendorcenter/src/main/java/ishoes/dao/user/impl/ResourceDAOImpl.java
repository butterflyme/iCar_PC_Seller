/**
 * 
 */
package ishoes.dao.user.impl;

import ishoes.dao.user.ResourceDAO;
import ishoes.dao.user.RoleDAO;
import ishoes.domain.user.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.dao.genericType.jdbc.impl.GenericTypeJdbcDAOImpl;

/**
 * ResourceDAO接口的DAO实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Component
public class ResourceDAOImpl extends GenericTypeJdbcDAOImpl<Resource, Long>
		implements ResourceDAO {
	@Autowired
	private RoleDAO roleDAO;

	public List<Resource> getUrlResourceWithAuthorities() {
		String sql = "select resource_id,res_string from resource where res_type=?";

		List<Resource> resources = this.query(sql,
				new Object[] { Resource.URL_TYPE }, new RowMapper<Resource>() {
					public Resource mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Resource resource = new Resource();
						resource.setResource_id(rs.getLong("resource_id"));
						resource.setRes_string(rs.getString("res_string"));
						return resource;
					}
				});
		for (Resource resource : resources) {
			resource.setRoles(roleDAO.resourceFetchRoles(resource
					.getResource_id()));
		}
		return resources;
	}
}
