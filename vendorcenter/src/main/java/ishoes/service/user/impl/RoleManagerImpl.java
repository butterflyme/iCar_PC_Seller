/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.dao.user.RoleDAO;
import ishoes.domain.user.Role;
import ishoes.service.user.RoleManager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * 
 */
@Service("roleManager")
@Transactional
public class RoleManagerImpl implements RoleManager {

	private final Log log = LogFactory.getLog(RoleManagerImpl.class);
	@Autowired
	private RoleDAO roleDAO;

	public List<Role> getAllRoles() {
		return roleDAO.getAllRoles();
	}
}
