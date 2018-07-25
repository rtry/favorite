/**
 * Project Name:pt-springboot-ace
 * File Name:MailUtil.java
 * Package Name:pt.sicau.edu.cn.springboot.ace.common.util
 * Date:2018年5月19日
 * Copyright (c) 2018, Felicity All Rights Reserved.
 *
 */
package sicau.edu.cn.favorite.util.mail;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;

import sicau.edu.cn.favorite.util.PropertiesUtil;
import sicau.edu.cn.favorite.util.mail.MailProtocolServerFactory.MailProtocolTypeEnum;
import sicau.edu.cn.favorite.util.mail.MailProtocolServerFactory.MailServerTypeEnum;

import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.util.MailSSLSocketFactory;

/**
 * ClassName: MailUtil <br>
 * Function: 邮件工具<br>
 * date: 2018年5月19日 <br>
 *
 * @author Felicity
 * @version
 * @since JDK 1.8
 */
public class MailUtil {

	private static final String fromUserName = PropertiesUtil.getPropertie("mail.username");

	private static final String fromUserPw = PropertiesUtil.getPropertie("mail.password");

	private static final String key = PropertiesUtil.getPropertie("mail.key");

	private static final String secret = PropertiesUtil.getPropertie("mail.secret");

	/**
	 * send:发送邮件. <br>
	 * @author Felicity
	 * @param to 收件人邮箱(可为自己)
	 * @param subject 主题类型 {@link MailSubjectEnum}
	 * @param target 邮件内容
	 * @since JDK 1.8
	 */
	public static void send(String to, MailContext target) {
		try {

			if (StringUtils.isAnyBlank(fromUserName, fromUserPw)) {
				throw new RuntimeException("config mail.username and mail.password can't be empty");
			}

			// 连接mail
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");

			// 获取服务器配置信息
			MailProtocolServer smtp;
			if (fromUserName.endsWith("@163.com")) {
				smtp = MailProtocolServerFactory.getInstance(MailServerTypeEnum.NetEase163,
						MailProtocolTypeEnum.SMTP);
				// 非SSL协议，do nothing

			} else if (fromUserName.endsWith("@outlook.com")) {

				smtp = MailProtocolServerFactory.getInstance(MailServerTypeEnum.OUTLOOK,
						MailProtocolTypeEnum.SMTP);

				// STARTTLS 协议
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.port", smtp.getPort());
				props.put("mail.smtp.socketFactory.fallback", "false");

				try {
					MailSSLSocketFactory sf = new MailSSLSocketFactory();
					sf.setTrustAllHosts(true);
					props.put("mail.smtp.ssl.socketFactory", sf);
				} catch (GeneralSecurityException e) {
					e.printStackTrace();
				}
				props.put("mail.smtp.socketFactory.fallback", "true");

			} else {
				throw new RuntimeException("no config server");
			}

			props.put("mail.smtp.host", smtp.getHost());

			// 地址
			Address sendFrom = new InternetAddress(fromUserName, target.getPersonal(), "UTF-8");
			Address sendTo = new InternetAddress(to);

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(fromUserName, fromUserPw);
				}
			});
			session.setDebug(true);

			// 创建邮件
			Message msg = new MimeMessage(session);

			int m = Calendar.getInstance().get(Calendar.MILLISECOND);
			msg.setSubject(key + "." + m + "." + target.getSubject());

			if (target.getType() == 0) {
				// 纯文本
				msg.setText(target.getContext());
			} else {
				// HTML 格式
				msg.setContent(target.getContext(), "text/html;charset=UTF-8");
			}

			msg.setFrom(sendFrom);
			msg.addRecipient(Message.RecipientType.TO, sendTo);

			// 发送邮件
			Transport.send(msg);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * receive:接收新邮件<br>
	 * 如果一个邮件在程序中经过处理，下次不能再次被处理
	 * 
	 * @author Felicity
	 * @since JDK 1.8
	 */
	public static void receive(MailInvoke invoke) {
		try {

			if (StringUtils.isAnyBlank(fromUserName, fromUserPw)) {
				throw new RuntimeException("config mail.username and mail.password can't be empty");
			}

			// 获取服务器配置信息
			MailProtocolServer pop;
			// 连接mail
			Properties props = new Properties();

			if (fromUserName.endsWith("@163.com")) {
				pop = MailProtocolServerFactory.getInstance(MailServerTypeEnum.NetEase163,
						MailProtocolTypeEnum.POP3);
				// 非SSL协议

			} else if (fromUserName.endsWith("@outlook.com")) {

				pop = MailProtocolServerFactory.getInstance(MailServerTypeEnum.OUTLOOK,
						MailProtocolTypeEnum.POP);
				String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				props.setProperty("mail.pop3.socketFactory.fallback", "false");
				props.setProperty("mail.pop3.port", pop.getPort() + "");
				props.setProperty("mail.pop3.socketFactory.port", pop.getPort() + "");
				props.setProperty("mail.pop3.auth", "true");
			} else {
				throw new RuntimeException("no config server");
			}

			props.setProperty("mail.store.protocol", "pop3");
			props.setProperty("mail.pop3.host", pop.getHost());

			Session session = Session.getInstance(props);
			session.setDebug(true);

			// 接收邮件
			Store store = session.getStore("pop3");
			store.connect(pop.getHost(), fromUserName, fromUserPw);

			// 获得用户的邮件账户，注意通过pop3协议获取某个邮件夹的名称只能为inbox
			Folder folder = store.getFolder("inbox");
			// 设置对邮件账户的访问权限
			folder.open(Folder.READ_WRITE);

			// 得到邮件账户的所有邮件信息
			if (folder instanceof POP3Folder) {
				POP3Folder inbox = (POP3Folder) folder;
				Message[] messages = inbox.getMessages();
				for (int i = messages.length - 1; i >= 0; i--) {
					MimeMessage mimeMessage = (MimeMessage) messages[i];

					// 千万不要用mimeMessage.getMessageID();这个方法，这个方法会去下载邮件头，是一个很耗时的过程
					String uid = inbox.getUID(mimeMessage);
					System.out.println("邮件UID：" + uid);

					// 该方法会下载heander 头
					String subject = messages[i].getSubject();

					if (subject == null || !subject.startsWith(key))
						continue;

					// 获得邮件发件人
					Address[] from = messages[i].getFrom();

					// 获取邮件内容（包含邮件内容的html代码）,该方法会下载体
					String content = (String) messages[i].getContent();

					System.out.println("=================================");
					System.out.println("发件人：" + from[0].toString());
					System.out.println("主题：" + subject);
					System.out.println("内容：" + content);

					if (invoke != null)
						invoke.invoke(mimeMessage);
					break;
				}

				folder.close(false);
				store.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}