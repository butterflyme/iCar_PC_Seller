/**
 * 
 */
package ishoes.web.search;

import ishoes.service.user.ContentManager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 搜索控制器
 * 
 * @author jayd
 * @since 1.0
 */
@Controller
public class SearchController {

	private final Log log = LogFactory.getLog(SearchController.class);
	//JSON的contentType
	protected final static String CONTENT_JSON = "json/html;charset=UTF-8";

	@Autowired
	private ContentManager contentManager;

	/**
	 * 联想搜索控制器
	 * 
	 * @param q
	 *            jquery.autocomplete组件传给后台的搜索关键字
	 * @param response
	 *            response对象
	 */
	@RequestMapping(value = "/associateSearch.do", method = RequestMethod.GET)
	public void associateSearch(String q, HttpServletResponse response) {

		if (q != null && !q.trim().equals("")) {
			q = q.trim();
			String jsonStr = contentManager.associateSearch(q);

			response.setContentType(CONTENT_JSON);
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter printWrite = null;
			try {
				printWrite = response.getWriter();
				printWrite.write(jsonStr);
			} catch (IOException e) {
				log.error("render方法抛IO异常", e);
			} finally {
				printWrite.flush();
				printWrite.close();
			}
		}
	}
}
