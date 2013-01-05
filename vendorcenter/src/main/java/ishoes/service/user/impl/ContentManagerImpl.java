/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.dao.user.ContentDAO;
import ishoes.domain.search.Content;
import ishoes.service.user.ContentManager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.utils.json.JSONUtil;

/**
 * @author Administrator
 * 
 */
@Service("contentManager")
@Transactional
public class ContentManagerImpl implements ContentManager {

	private final Log log = LogFactory.getLog(ContentManagerImpl.class);
	@Autowired
	private ContentDAO contentDAO;

	public String associateSearch(String keyword) {
		List<Content> contents = contentDAO.associateSearch(keyword);

		StringBuffer jsonStr = new StringBuffer();
		try {
			JSONUtil.object2JSON(contents, jsonStr,
					new Class[] { Content.class });
		} catch (Exception e1) {
			log.error(e1.toString());
		}
		return jsonStr.toString();
	}
}
