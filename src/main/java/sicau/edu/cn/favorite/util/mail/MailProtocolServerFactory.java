/**    
 * 文件名：MailProtocolServerFactory.java    
 *    
 * 版本信息：    
 * 日期：2018年7月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util.mail;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 类名称：MailProtocolServerFactory <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月25日 上午10:37:11 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月25日 上午10:37:11 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MailProtocolServerFactory {

	public static ConcurrentHashMap<String, MailProtocolServer> store = new ConcurrentHashMap<String, MailProtocolServer>();

	/** 静态块初始化对象 */
	static {
		MailServerTypeEnum[] serverTypes = MailServerTypeEnum.values();
		for (MailServerTypeEnum serverType : serverTypes) {
			switch (serverType) {
			case NetEase163:
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.POP3.name()),
						new MailProtocolServer(MailProtocolTypeEnum.POP3, "pop.163.com", 995));
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.IMAP.name()),
						new MailProtocolServer(MailProtocolTypeEnum.IMAP, "imap.163.com", 993));
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.SMTP.name()),
						new MailProtocolServer(MailProtocolTypeEnum.SMTP, "smtp.163.com", 25));
				break;
			case OUTLOOK:
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.POP.name()),
						new MailProtocolServer(MailProtocolTypeEnum.POP, "outlook.office365.com",
								995));
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.IMAP.name()),
						new MailProtocolServer(MailProtocolTypeEnum.IMAP, "outlook.office365.com",
								993));
				store.put(serverType.name().concat(".").concat(MailProtocolTypeEnum.SMTP.name()),
						new MailProtocolServer(MailProtocolTypeEnum.SMTP, "smtp.office365.com", 587));
				break;

			default:
				break;
			}
		}
	}

	public static MailProtocolServer getInstance(MailServerTypeEnum serverType,
			MailProtocolTypeEnum protocolType) {
		String key = serverType.name().concat(".").concat(protocolType.name());
		if (store.containsKey(key))
			return store.get(key);
		else
			throw new RuntimeException("没有对应的服务器信息");
	}

	/**
	 * 类名称：MailServerTypeEnum <br>
	 * 类描述: 服务商枚举 <br>
	 * 创建人：felicity <br>
	 * 创建时间：2018年7月25日 上午10:39:21 <br>
	 * 修改人：felicity <br>
	 * 修改时间：2018年7月25日 上午10:39:21 <br>
	 * 修改备注:
	 * @version
	 * @see
	 */
	public enum MailServerTypeEnum {

		NetEase163("网易163"), OUTLOOK("微软outlook");

		private String des;

		public String getDes() {
			return des;
		}

		public void setDes(String des) {
			this.des = des;
		}

		private MailServerTypeEnum(String des) {
			this.des = des;
		}
	}

	/**
	 * 类名称：MailProtocolTypeEnum <br>
	 * 类描述: 协议枚举 <br>
	 * 创建人：felicity <br>
	 * 创建时间：2018年7月25日 上午10:39:36 <br>
	 * 修改人：felicity <br>
	 * 修改时间：2018年7月25日 上午10:39:36 <br>
	 * 修改备注:
	 * @version
	 * @see
	 */
	public enum MailProtocolTypeEnum {
		POP, POP3, IMAP, SMTP;
	}
}
