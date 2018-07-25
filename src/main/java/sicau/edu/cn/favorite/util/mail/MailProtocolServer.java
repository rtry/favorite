/**    
 * 文件名：MailProtocolServer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util.mail;

import sicau.edu.cn.favorite.util.mail.MailProtocolServerFactory.MailProtocolTypeEnum;

/**
 * 类名称：MailProtocolServer <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月25日 上午10:38:13 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月25日 上午10:38:13 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MailProtocolServer {

	private MailProtocolTypeEnum type;
	private String host;
	private int port;

	public MailProtocolServer(MailProtocolTypeEnum type, String host, int port) {
		super();
		this.type = type;
		this.host = host;
		this.port = port;
	}

	public MailProtocolTypeEnum getType() {
		return type;
	}

	public void setType(MailProtocolTypeEnum type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
