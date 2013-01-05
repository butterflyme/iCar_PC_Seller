/**
 * 
 */
package core.utils.security;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <p>
 * 当资源表发生改变，比如说在新增一个资源与角色的对应关系后，需要调用刷新方法， 才能让该对象存入内存。
 * </p>
 * <p>
 * 也可以采用JSP刷新。WEB-INF/jsp/security/flushAfterResource.jsp
 * </p>
 * 
 * @author jayd
 * @since 1.0
 */
public final class FlushResource {

	protected static Log log = LogFactory.getLog(FlushResource.class);

	/**
	 * 刷新内存。
	 * 
	 * @param application
	 *            ServletContext应用上下文
	 */
	public static void flushResource(ServletContext application) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
		FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterInvocationDefinitionSource");
		FilterInvocationDefinitionSource fids = null;
		try {
			fids = (FilterInvocationDefinitionSource) factoryBean.getObject();
		} catch (Exception e) {
			log.error("flushResource method have exception in FlushResource ", e);
		}
		FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("filterSecurityInterceptor");
		filter.setObjectDefinitionSource(fids);
	}
}
