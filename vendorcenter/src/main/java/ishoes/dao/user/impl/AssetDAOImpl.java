/**
 * 
 */
package ishoes.dao.user.impl;

import ishoes.dao.user.AssetDAO;
import ishoes.domain.tree.Asset;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.dao.genericType.jdbc.impl.GenericTypeJdbcDAOImpl;
import core.domain.Page;

/**
 * AssetDAO接口的DAO实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Component
public class AssetDAOImpl extends GenericTypeJdbcDAOImpl<Asset, Long> implements
		AssetDAO {
	@Autowired
	private MemcachedClient memcachedClient;

	public List<Asset> getAllAsset() {
		try {
			List<Asset> allAssets = memcachedClient.get("allAssets");
			if (allAssets != null) {
				return allAssets;
			}
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		String sql = "select * from asset";
		Page<Asset> page = new Page<Asset>();
		page.setAutoCount(true);
		page.setPageSize(2);

		page = this.findPageCommon(page, sql, new RowMapper<Asset>() {
			public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
				Asset asset = new Asset();
				asset.setId(rs.getLong("id"));
				asset.setName(rs.getString("name"));
				asset.setStyleName(rs.getString("styleName"));
				asset.setParentId(rs.getLong("parentId"));
				return asset;
			}
		}, new Object[] {});
		page.getTotalPages();

		List<Asset> assets = this.query(sql, new RowMapper<Asset>() {
			public Asset mapRow(ResultSet rs, int rowNum) throws SQLException {
				Asset asset = new Asset();
				asset.setId(rs.getLong("id"));
				asset.setName(rs.getString("name"));
				asset.setStyleName(rs.getString("styleName"));
				asset.setParentId(rs.getLong("parentId"));
				return asset;
			}
		});
		try {
			memcachedClient.set("allAssets", 0, assets);
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (MemcachedException e) {
			e.printStackTrace();
		}
		return assets;
	}
}
