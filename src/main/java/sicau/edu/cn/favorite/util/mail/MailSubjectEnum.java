/**
 * Project Name:MailSubjectEnum
 * File Name:MailSubjectEnum.java
 * Package Name:sicau.edu.cn.favorite.util
 * Date:2018年5月19日
 * Copyright (c) 2018, Felicity All Rights Reserved.
 *
 */
package sicau.edu.cn.favorite.util.mail;

/**
 * ClassName: MailSubjectEnum <br>
 * Function: 邮件主题<br>
 * date: 2018年5月19日 <br>
 *
 * @author Felicity
 * @version
 * @since JDK 1.8
 */
public enum MailSubjectEnum {

	REGISTER("注册激活"), RETRIEVE("找回密码");

	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	private MailSubjectEnum(String des) {
		this.des = des;
	}

}
