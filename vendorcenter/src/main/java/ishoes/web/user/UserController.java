/**
 * 
 */
package ishoes.web.user;

import ishoes.domain.tree.Asset;
import ishoes.service.user.AssetManager;
import ishoes.service.user.MailService;
import ishoes.service.user.UserManager;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.utils.tree.TreeBuilderNotHibernate;

/**
 * 用户控制器
 * 
 * @author jayd
 * @since 1.0
 */
@Controller
public class UserController {

	private final Log log = LogFactory.getLog(getClass());

	@Autowired
	private UserManager userManager;
	@Autowired
	private MailService mailService;
	@Autowired
	private AssetManager assetManager;

	/**
	 * 进入登陆页面控制器
	 * 
	 * @param model
	 *            spring用来封装数据交互的model
	 * @return 视图名字为index的资源。见view.properties
	 */
	@RequestMapping(value = "/user/login.do", method = RequestMethod.GET)
	public String getLoginPage(ModelMap model) {

		//登陆成功后获得的用户信息
		/*User currentLoginUser=SecurityUserTool.getUser();
		Set<Role> currentRoles=currentLoginUser.getRoles();
		List<Resource> currentResources=new ArrayList<Resource>();
		for(Role currentRole:currentRoles){
			currentResources.addAll(currentRole.getResources());
		}
		model.addAttribute("currentResources", currentResources);*/
		//使用SQL查询ID的树
		List<Asset> assets = assetManager.getAllAsset();
		TreeBuilderNotHibernate treeBuilderNotHibernate = new TreeBuilderNotHibernate();
		String treeHtmlNotHibernate = treeBuilderNotHibernate.build(assets);
		model.addAttribute("treeHtmlNotHibernate", treeHtmlNotHibernate);
        
		return "index";
	}
}
