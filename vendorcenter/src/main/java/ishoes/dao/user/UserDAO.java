/**
 * 
 */
package ishoes.dao.user;


import ishoes.domain.user.User;
import core.dao.genericType.jdbc.GenericTypeJdbcDAO;

/**
 * 权限控制模块中的用户实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface UserDAO extends GenericTypeJdbcDAO<User, Long> {

	public User findUserByLoginName(String username);
	
	public void save(User user);
}
