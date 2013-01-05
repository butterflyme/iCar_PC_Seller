/**
 * 
 */
package ishoes.web;

import ishoes.service.user.MailService;
import ishoes.service.user.UserManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用来做参考的DEMO控制器
 * 
 * @author jayd
 * @since 1.0
 */
@Controller
public class DemoController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserManager userManager;
	@Autowired
	private MailService mailService;

	/**
	 * 进入登陆页面控制器
	 * 
	 * @return 视图名字为index的资源。见view.properties
	 */
	@RequestMapping(value = "/demo.do", method = RequestMethod.GET)
	public String getLoginPage(ModelMap model) {
		/*RssReaderUtils rssReaderUtils=new RssReaderUtils();
		List<ChannelItem> channelItems = new ArrayList<ChannelItem>();
		try {
		    SyndFeed feed = rssReaderUtils.getFeed("http://news.163.com/special/00011K6L/rss_newstop.xml");
		    SyndImage image = rssReaderUtils.getFeedImage(feed);
		    String title = rssReaderUtils.getFeedTitle(feed);
		    channelItems = rssReaderUtils.getChannelItems(rssReaderUtils.getEntries(feed));
		}
		catch (IllegalArgumentException e) {
		    e.printStackTrace();
		}
		catch (FeedException e) {
		    e.printStackTrace();
		}
		catch (IOException e) {
		    e.printStackTrace();
		}
		Properties props=System.getProperties(); //获得系统属性集  
		String osName = props.getProperty("os.name"); //操作系统名称  
		String osArch = props.getProperty("os.arch"); //操作系统构架  
		String osVersion = props.getProperty("os.version"); //操作系统版本  
		try {
		    mailService.send();
		}
		catch (MessagingException e) {
		    log.warn("Usercontroller send mail failed", e);
		}*/
		model.addAttribute("mailStatus", "mail send success");

		//登陆成功后获得的用户信息
		/*User currentLoginUser=SecurityUserTool.getUser();
		Set<Role> currentRoles=currentLoginUser.getRoles();
		List<Resource> currentResources=new ArrayList<Resource>();
		for(Role currentRole:currentRoles){
			currentResources.addAll(currentRole.getResources());
		}
		model.addAttribute("currentResources", currentResources);*/

		return "index";
	}
}
