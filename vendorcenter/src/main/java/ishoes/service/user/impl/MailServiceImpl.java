/**
 * 
 */
package ishoes.service.user.impl;

import ishoes.service.user.MailService;

import java.io.File;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service("mailService")
public class MailServiceImpl implements MailService{

	private final Log log = LogFactory.getLog(MailServiceImpl.class);
	
    private JavaMailSender mailSender;
    
    /**
     * 利用setter方法进行DI注入
     * @param mailSender spring容器中实例的mailSender对象
     */
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    public void send() throws MessagingException {
        //创建一个多媒体的邮件
        MimeMessage me=mailSender.createMimeMessage();
        MimeMessageHelper message=new MimeMessageHelper(me,true);

        message.setTo("douq@bjsrd.com.cn");
        message.setSubject("test");
        message.setText("这是一个测试");
        message.setSentDate(new Date(110, 2, 25, 15, 32, 23));
        FileSystemResource img=new FileSystemResource(new File("E:\\渠道管理系统架构设计说明书.rar"));
        //message.addInline("png", img);
        message.addAttachment("qudao",img); //将文件作为附件发送
        mailSender.send(me);
        System.out.println("邮件发送成功");
    }
}
