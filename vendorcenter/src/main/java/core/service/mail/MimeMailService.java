package core.service.mail;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.利用Freemarker模板引擎生成的html格式的邮件，并且支持附件
 * 您可以改变模板引擎，支持其他方式来生成html。
 * @author jayd
 * @since 1.0
 */

public class MimeMailService {
    //默认的编码格式
	private static final String DEFAULT_ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;
	private Template template;

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		//根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
	}

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setTo("dengjie200@163.com");
			helper.setFrom("dengjie200@gmail.com");
			helper.setSubject("用户修改通知");

			String content = generateContent(userName);
			helper.setText(content, true);

			File attachment = generateAttachment();
			helper.addAttachment("mailAttachment.txt", attachment);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至dengjie200@163.com");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.这里是重新写了一次Freemark生成String的方法，
	 * 也可以使用{@link com.mywish.core.utils.freemarker.FreemarkerUtils#generate(String templateFileName, Map<String, Object> propMap)}.
	 * 来做到这样的效果，这里使用了spring对Freemarker支持的工具类：FreeMarkerTemplateUtils
	 */
	@SuppressWarnings("unchecked")
	private String generateContent(String userName) throws MessagingException {

		try {
			Map context = Collections.singletonMap("userName", userName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 获取classpath中的附件.可以很容易对代码进行修改。
	 */
	private File generateAttachment() throws MessagingException {
		try {
			Resource resource = new ClassPathResource("/email/mailAttachment.txt");
			return resource.getFile();
		} catch (IOException e) {
			logger.error("构造邮件失败,附件文件不存在", e);
			throw new MessagingException("附件文件不存在", e);
		}
	}
}
