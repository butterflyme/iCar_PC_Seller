/**
 * 
 */
package ishoes.service.user;

import ishoes.domain.user.Role;

import java.util.List;


/**
 * 权限控制模块中的权限实体的Manager
 * 
 * @author jayd
 * @since 1.0
 */
public interface RoleManager {

	public List<Role> getAllRoles();
}
