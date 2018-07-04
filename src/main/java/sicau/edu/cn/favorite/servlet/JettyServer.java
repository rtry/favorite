/**    
 * 文件名：JettyServer.java    
 *    
 * 版本信息：    
 * 日期：2018年1月4日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.servlet;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import sicau.edu.cn.favorite.constant.JettyConstant;
import sicau.edu.cn.favorite.controller.SearchServlet;

/**
 * 类名称：JettyServer <br>
 * 类描述: jetty服务<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月4日 下午2:38:17 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月4日 下午2:38:17 <br>
 * 修改备注:
 * @version
 * @see
 */
public class JettyServer {

	private static volatile boolean flag = false;

	private static Server server;

	private static Logger logger = Logger.getLogger(JettyServer.class);

	/**
	 * start 启动容器<br>
	 * 添加服务<br>
	 * @Exception 异常描述
	 */
	public static void start() {
		if (!flag)
			flag = true;
		else {
			logger.warn("程序已经启动...");
			return;
		}

		// 定义服务器
		server = new Server(JettyConstant.port);

		WebAppContext context = new WebAppContext();
		context.setContextPath(JettyConstant.urlseparator);

		String proPath = System.getProperty("user.dir");
		logger.info("项目路径：" + proPath);
		context.setResourceBase(proPath + "/src/main/webapp");

		// 手动添加比注解发现，对项目发布，Servlet日志更友好
		context.addServlet(SearchServlet.class, "/search");

		server.setHandler(context);

		try {
			// 启动服务器
			server.start();
		} catch (Exception e) {
			logger.error("启动异常", e);
			JettyServer.close();
			System.exit(1);
		}
	}

	/**
	 * close 关闭容器
	 * @Exception 异常描述
	 */
	public static void close() {
		if (!server.isStopped() && !server.isStopping())
			try {
				server.stop();
			} catch (Exception e) {
				logger.error("关闭服务异常", e);
			}
	}
}
