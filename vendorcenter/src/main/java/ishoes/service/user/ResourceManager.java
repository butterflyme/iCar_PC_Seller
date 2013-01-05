/**
 * 
 */
package ishoes.service.user;

import ishoes.domain.user.Resource;

import java.util.List;

/**
 * 权限控制模块中的资源实体的Manager
 * 
 * @author jayd
 * @since 1.0
 */
public interface ResourceManager {

	/**
	 * 查询URL类型的资源并预加载可访问该资源的授权信息.
	 * 
	 * @return 资源实体对象的List集合
	 */
	public List<Resource> getUrlResourceWithAuthorities();
}
