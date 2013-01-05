/**
 * 
 */
package ishoes.service.user;

import ishoes.domain.user.User;

/**
 * 权限控制模块中的用户实体的Manager
 * 
 * @author jayd
 * @since 1.0
 */
public interface UserManager {

	/**
	 * 根据登陆名查询用户
	 * 
	 * @param username
	 *            登录名
	 * @return 用户实体对象
	 */
	public User findUserByLoginName(String username);
	/**
	 * 保存用户信息。
	 * @param user 封装用户信息
	 */
	public void save(User user);
}
