package org.jxva.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Jvamail {
	public static void main(String args[]) {
		Jvamail frist = new Jvamail();
		frist.send(); //
		System.out.println("发送成功");
	}

	public void send() {
		String ttitle = "你好,";
		String tcontent = "你好，我的用java发送邮件已经成功了，现在就是用程序发过去的，爽吧";
		Properties props = new Properties(); // 也可用
		props.put("mail.diselife.com", "mail.diselife.com"); // 存储发送邮件服务器的信息
		props.put("mail.diselife.com", "true"); // 同时通过验证
		Session s = Session.getInstance(props, null);
		// 根据属性新建一个邮件会话，null参数是一种Authenticator(验证程序) 对象
		//s.setDebug(true); // 设置调试标志,要查看经过邮件服务器邮件命令，可以用该方法
		Message message = new MimeMessage(s); // 由邮件会话新建一个消息对象
		try {
			Address from = new InternetAddress("yejj@diselife.com"); // 发件人的邮件地址
			message.setFrom(from); // 设置发件人
			Address to = new InternetAddress("asdex1999@hotmail.com"); // 收件人的邮件地址
			message.setRecipient(Message.RecipientType.TO, to); // 设置收件人,并设置其接收类型为TO,还有3种预定义类型如下：
			message.setSubject(ttitle); // 设置主题
			message.setText(tcontent); // 设置信件内容
			message.setSentDate(new Date()); // 设置发信时间
			message.saveChanges(); // 存储邮件信息
			Transport transport = s.getTransport("mail");
			
			transport.connect("mail.diselife.com", "yejj@diselife.com", "yejj"); // 要填入你的用户名和密码；
			
			transport.sendMessage(message, message.getAllRecipients()); // 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
