/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.dao.user.ResourceDAO;
import ishoes.domain.user.Resource;
import ishoes.service.user.ResourceManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Administrator
 * 
 */
@Service("resourceManager")
@Transactional
public class ResourceManagerImpl implements ResourceManager {
	@Autowired
	private ResourceDAO resourceDAO;

	/**
	 * 查询URL类型的资源并预加载可访问该资源的授权信息.
	 */
	@SuppressWarnings("unchecked")
	public List<Resource> getUrlResourceWithAuthorities() {

		return resourceDAO.getUrlResourceWithAuthorities();
	}
}
