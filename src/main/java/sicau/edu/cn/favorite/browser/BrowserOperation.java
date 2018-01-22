/**    
 * 文件名：BrowserOperation.java    
 *    
 * 版本信息：    
 * 日期：2018年1月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.browser;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 类名称：BrowserOperation <br>
 * 类描述: 游览器操作<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月22日 上午10:37:18 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月22日 上午10:37:18 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BrowserOperation {

	/**
	 * execUrl 游览器打开url
	 * @param url
	 * @Exception 异常描述
	 */
	public static void execUrl(String url) {
		try {
			URI uri = new URI(url);
			Desktop.getDesktop().browse(uri);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
