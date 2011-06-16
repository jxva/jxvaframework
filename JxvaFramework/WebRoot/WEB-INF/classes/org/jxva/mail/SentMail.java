package org.jxva.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class SentMail {

	public static void main(String[] args) {
		new SentMail().sendMail("jxva@qq.com", "dfsa", "fdsa");
	}

	public boolean sendMail(String mailTo, String mailSubject, String mailBody) {
		try {
			String smtpServer = "mail.jxva.com";
			String smtpAuth = "true";
			String smtpUser = "hi@jxva.com";
			String smtpPassword = "lovejxva";
			String From = "hi@jxva.com";
			String Subject = mailSubject;
			String Text = mailBody;

			Properties props = new Properties();
			Session sendMailSession;
			// Transport transport;
			props.put("mail.smtp.host", smtpServer);
			props.put("mail.smtp.auth", smtpAuth);
			if ("true".equals(smtpAuth)) {
				// smtp服务器需要验证，用MyAuthertiactor来创建mail session
				MyAuthenticator myauth = new MyAuthenticator(smtpUser,smtpPassword);
				sendMailSession = Session.getInstance(props, myauth);
			} else {
				sendMailSession = Session.getInstance(props);
			}
			// Debug
			//sendMailSession.setDebug(true);
			Message message = new MimeMessage(sendMailSession);
			message.setFrom(new InternetAddress(From));
			message.setRecipient(Message.RecipientType.TO,new InternetAddress(mailTo));
			//message.setRecipients(Message.RecipientType.TO,new InternetAddress{});
			message.setSubject(Subject);
			message.setSentDate(new Date());
			
			// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个) 
			MimeMultipart mm = new MimeMultipart(); 
			// 新建一个存放信件内容的BodyPart对象 
			BodyPart mdp = new MimeBodyPart(); 
			// 给BodyPart对象设置内容和格式/编码方式 
			mdp.setContent(Text, "text/html;charset=UTF-8"); 
			// 这句很重要，千万不要忘了 
			mm.setSubType("related"); 

			mm.addBodyPart(mdp); 
			
			List<byte[]> attachList =new ArrayList<byte[]>(); // 附件的list,它的element都是byte[],即图片的二进制流 
			// add the attachments 
			for( int i=0; i<attachList.size(); i++) { 
				// 新建一个存放附件的BodyPart 
				mdp = new MimeBodyPart(); 
				DataHandler dh = new DataHandler(new ByteArrayDataSource(attachList.get(i),"application/octet-stream")); 
				mdp.setDataHandler(dh); 
				// 加上这句将作为附件发送,否则将作为信件的文本内容 
				mdp.setFileName(new Integer(i).toString() + ".jpg"); 
				mdp.setHeader("Content-ID", "IMG" + new Integer(i).toString()); 
				// 将含有附件的BodyPart加入到MimeMultipart对象中 
				mm.addBodyPart(mdp); 
			} 
			
			message.setText(Text);
			message.saveChanges();
			// transport = sendMailSession.getTransport("smtp");
			Transport.send(message, message.getAllRecipients());
			// transport.close();
		} catch (Exception mailEx) {
			System.err.println("Send Mail Error:" + mailEx.getMessage());
			return false;
		}
		return true;
	}
}
	
	// smtp需要验证时候的验证类
	class MyAuthenticator extends Authenticator {
		private String username;
		private String password;
	
		public MyAuthenticator(String user, String password) {
			this.username = user;
			this.password = password;
		}
	
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	}
