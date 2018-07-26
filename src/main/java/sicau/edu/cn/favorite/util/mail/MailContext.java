/**    
 * 文件名：MailContext.java    
 *    
 * 版本信息：    
 * 日期：2018年7月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util.mail;

/**
 * 类名称：MailContext <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月25日 下午3:45:16 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月25日 下午3:45:16 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MailContext {

	/**
	 * type:内容类型，0-文本，1-HTML
	 * @since Ver 1.1
	 */
	private int type;

	/**
	 * subject:邮件主题
	 * @since Ver 1.1
	 */
	private String subject;

	/**
	 * context:邮件内容
	 * @since Ver 1.1
	 */
	private String context;

	/**
	 * personal:name
	 * @since Ver 1.1
	 */
	private String personal;

	public MailContext() {
		super();
	}

	public MailContext(int type, String subject, String context) {
		super();
		this.type = type;
		this.subject = subject;
		this.context = context;
	}

	public MailContext(int type, String subject, String context, String personal) {
		super();
		this.type = type;
		this.subject = subject;
		this.context = context;
		this.personal = personal;
	}

	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

}
