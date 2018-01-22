/**    
 * 文件名：AppClient.java    
 *    
 * 版本信息：    
 * 日期：2018年1月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite;

import sicau.edu.cn.favorite.browser.BrowserOperation;
import sicau.edu.cn.favorite.constant.JettyConstant;
import sicau.edu.cn.favorite.servlet.JettyServer;

/**
 * 类名称：AppClient <br>
 * 类描述: 程序启动项<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月22日 上午10:20:35 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月22日 上午10:20:35 <br>
 * 修改备注:
 * @version
 * @see
 */
public class AppClient {

	public static void main(String[] args) {
		JettyServer.start();
		BrowserOperation.execUrl(JettyConstant.indexUrl);

	}
}
