/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.dao.user.UserDAO;
import ishoes.domain.user.User;
import ishoes.service.user.UserManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * 
 */
@Service("userManager")
@Transactional
public class UserManagerImpl implements UserManager {

	private final Log log = LogFactory.getLog(UserManagerImpl.class);
	@Autowired
	private UserDAO userDAO;

	public User findUserByLoginName(String username) {

		return userDAO.findUserByLoginName(username);
	}

	public void save(User user) {
		userDAO.save(user);
	}
}
