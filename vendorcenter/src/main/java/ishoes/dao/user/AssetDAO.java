/**
 * 
 */
package ishoes.dao.user;

import ishoes.domain.tree.Asset;

import java.util.List;

import core.dao.genericType.jdbc.GenericTypeJdbcDAO;

/**
 * 资产实体的DAO
 * 
 * @author jayd
 * @since 1.0
 */
public interface AssetDAO extends GenericTypeJdbcDAO<Asset, Long> {
	/**
	 * 获取全部资产
	 * 
	 * @return 全部资产实体的List集合
	 */
	public List<Asset> getAllAsset();
}
