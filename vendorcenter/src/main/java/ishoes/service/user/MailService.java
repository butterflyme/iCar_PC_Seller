/**
 * 
 */
package ishoes.service.user;

import javax.mail.MessagingException;

/**
 * 邮件发送服务类
 * @author jayd
 * @since 1.0 
 */
public interface MailService {
	
	/**
	 * 邮件发送方法。
	 * @throws MessagingException javax.mail.MessagingException异常
	 */
    public void send() throws MessagingException;
}
