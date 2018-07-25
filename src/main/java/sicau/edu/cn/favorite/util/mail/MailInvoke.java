/**    
 * 文件名：MailInvoke.java    
 *    
 * 版本信息：    
 * 日期：2018年7月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util.mail;

import javax.mail.internet.MimeMessage;

/**
 * 类名称：MailInvoke <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月25日 下午3:57:37 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月25日 下午3:57:37 <br>
 * 修改备注:
 * @version
 * @see
 */

@FunctionalInterface
public interface MailInvoke {

	public void invoke(MimeMessage mimeMessage);
}
