/**    
 * 文件名：AppClient.java    
 *    
 * 版本信息：    
 * 日期：2018年1月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite;

import org.apache.log4j.PropertyConfigurator;

import sicau.edu.cn.favorite.browser.BrowserOperation;
import sicau.edu.cn.favorite.constant.JettyConstant;
import sicau.edu.cn.favorite.servlet.JettyServer;
import sicau.edu.cn.favorite.task.InitDataTask;

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
		
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));
		// 1.启动服务器
		JettyServer.start();
		// 2.初始化数据
		InitDataTask task = new InitDataTask();
		task.init();
		// 3.打开游览器
		BrowserOperation.execUrl(JettyConstant.indexUrl);
	}
}
