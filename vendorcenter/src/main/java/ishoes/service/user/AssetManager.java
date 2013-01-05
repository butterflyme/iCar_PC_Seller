/**
 * 
 */
package ishoes.service.user;

import ishoes.domain.tree.Asset;

import java.util.List;

/**
 * 资产实体的Manager
 * 
 * @author jayd
 * @since 1.0
 */
public interface AssetManager {
	/**
	 * 调用DAO获取全部资产
	 * 
	 * @return 全部资产实体的List集合
	 */
	public List<Asset> getAllAsset();
}
