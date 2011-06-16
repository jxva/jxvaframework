/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jxva.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-04-20 11:32:55 by Jxva
 */
public class Send {
	
	private final MailSmtp mailSmtp;
	
	public Send(MailSmtp mailSmtp){
		this.mailSmtp=mailSmtp;
	}
	
	private Session getSession(){
		Properties props = new Properties();
		props.put("mail.smtp.host",mailSmtp.getHost());
		props.put("mail.smtp.auth",mailSmtp.getAuth().toString());
		if (mailSmtp.getAuth()) {
			// smtp服务器需要验证，用MyAuthertiactor来创建mail session
			SimpleAuthenticator myauth = new SimpleAuthenticator(mailSmtp.getUsername(),mailSmtp.getPassword());
			return Session.getInstance(props, myauth);
		} else {
			return Session.getInstance(props);
		}
		//sendMailSession.setDebug(true);
	}
	
	private Message getTextMessage(String to, String subject, String text) throws AddressException, MessagingException{
		Message message = new MimeMessage(getSession());
		message.setFrom(new InternetAddress(mailSmtp.getFrom()));
		message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setText(text);
		message.saveChanges();
		return message;
	}
	private Message getHtmlMessage(String to, String subject, String text) throws AddressException, MessagingException{
		Message message = new MimeMessage(getSession());
		message.setFrom(new InternetAddress(mailSmtp.getFrom()));
		message.setRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
		message.setSubject(subject);
		message.setSentDate(new Date());
		
		
		// 新建一个MimeMultipart对象用来存放BodyPart对象(事实上可以存放多个) 
		MimeMultipart mm = new MimeMultipart(); 
		// 新建一个存放信件内容的BodyPart对象 
		BodyPart mdp = new MimeBodyPart(); 
		// 给BodyPart对象设置内容和格式/编码方式 
		mdp.setContent(text, "text/html;charset=UTF-8"); 
		// 这句很重要，千万不要忘了 
		mm.setSubType("related"); 
		mm.addBodyPart(mdp); 
		message.setContent(mm);
		message.saveChanges();
		return message;
	}
	
	
	public void html(String to,String subject,String text)throws MailException{
		try {
			Message message=getHtmlMessage(to,subject,text);
			Transport.send(message, message.getAllRecipients());
		} catch (AddressException e) {
			throw new MailException(e);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
	
	public void text(String to, String subject, String text) throws MailException{
		try {
			Message message=getTextMessage(to,subject,text);
			
//			Session session = Session.getDefaultInstance(props, null); 
//			javax.mail.Transport transport = session.getTransport("smtp"); 
//			transport.connect(smtpHost, name, password); 
//			transport.sendMessage(message, message.getAllRecipients()); 
//			transport.close(); 
			
			Transport.send(message, message.getAllRecipients());
		} catch (AddressException e) {
			throw new MailException(e);
		} catch (MessagingException e) {
			throw new MailException(e);
		}
	}
	
	public static void main(String[] args) throws ServletException{
		MailSmtp m=new MailSmtp();
		m.setAuth(true);
		m.setFrom("service@ztemc.com");
		m.setHost("mail.ztemc.com");
		m.setPassword("ztemt");
		m.setUsername("service@ztemc.com");
		new Send(m).html("jxva@qq.com","FFFFFF","ddd");
	}
}
