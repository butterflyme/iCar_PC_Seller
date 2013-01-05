/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.dao.user.AssetDAO;
import ishoes.domain.tree.Asset;
import ishoes.service.user.AssetManager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * AssetManager接口的实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Service("assetManager")
@Transactional
public class AssetManagerImpl implements AssetManager {

	private final Log log = LogFactory.getLog(AssetManagerImpl.class);
	@Autowired
	private AssetDAO assetDAO;

	public List<Asset> getAllAsset() {
		return assetDAO.getAllAsset();
	}
}
