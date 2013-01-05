package ishoes.web.user;

import ishoes.domain.user.User;
import ishoes.service.user.RoleManager;
import ishoes.service.user.UserManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 注册用户控制器
 * 
 * @author jayd
 * @since 1.0
 */
@Controller
public class AddUserHandler {
	
	private final Log log = LogFactory.getLog(AddUserHandler.class);
	@Autowired
	private UserManager userManager;
	@Autowired
	private RoleManager roleManager;

	/**
	 * 进入新增用户页面控制器
	 * 
	 * @return 视图名字为register的资源。见view.properties
	 */
	@RequestMapping(value = "/user/register.do", method = RequestMethod.GET)
	public String getRegisterUser(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("entering 'getRegisterUser' method...");
		}
		model.addAttribute("roles", roleManager.getAllRoles());
		return "user/register";
	}

	/**
	 * 进入登陆页面控制器
	 * 
	 * @return 视图名字为register的资源。见view.properties
	 * @throws ServletRequestBindingException
	 *             ServletRequestBindingException异常
	 */
	@RequestMapping(value = "/user/register.do", method = RequestMethod.POST)
	public String postRegisterUser(@ModelAttribute("user")
	User user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("entering 'postRegisterUser' method...");
		}
		userManager.save(user);
		return "user/register";
	}
}
