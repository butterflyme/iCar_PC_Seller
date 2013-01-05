package ishoes.service.security;

import ishoes.domain.user.Resource;
import ishoes.service.user.ResourceManager;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

/**
 * 从数据库查询URL--授权定义Map的实现类.
 * 
 * @author jayd
 * @since 1.0
 */
@Transactional(readOnly = true)
public class ResourceDetailsServiceImpl implements ResourceDetailsService {

	private ResourceManager resourceManager;

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	/**
	 * @see ResourceDetailsService#getRequestMap()
	 */
	public LinkedHashMap<String, String> getRequestMap() throws Exception {
		List<Resource> resourceList = resourceManager.getUrlResourceWithAuthorities();

		LinkedHashMap<String, String> requestMap = new LinkedHashMap<String, String>(resourceList.size());
		for (Resource resource : resourceList) {
			requestMap.put(resource.getRes_string(), resource.getAuthNames());
		}
		return requestMap;
	}
}
