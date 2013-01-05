/**
 * 
 */
package ishoes.dao.user.impl;

import ishoes.dao.user.ContentDAO;
import ishoes.domain.search.Content;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import core.dao.genericType.jdbc.impl.GenericTypeJdbcDAOImpl;

/**
 * ContentDAO接口的DAO实现类
 * 
 * @author jayd
 * @since 1.0
 */
@Component
public class ContentDAOImpl extends GenericTypeJdbcDAOImpl<Content, Long> implements ContentDAO {

	public List<Content> associateSearch(String keyword) {
		String sql = "select content_id,name,code from content where code like ? or name like ? limit 0, 10 ";
		List<Content> contents = this.query(sql, new Object[]{keyword+"%",keyword+"%"}, new RowMapper<Content>() {
			public Content mapRow(ResultSet rs, int rowNum) throws SQLException {
				Content content = new Content();
				content.setContent_id(rs.getLong("content_id"));
				content.setName(rs.getString("name"));
				content.setCode(rs.getString("code"));
				return content;
			}
		});
		return contents;
	}
}
