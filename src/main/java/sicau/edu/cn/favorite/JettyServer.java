/**    
 * 文件名：JettyServer.java    
 *    
 * 版本信息：    
 * 日期：2018年1月4日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * 类名称：JettyServer <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月4日 下午2:38:17 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月4日 下午2:38:17 <br>
 * 修改备注:
 * @version
 * @see
 */
public class JettyServer {
	public static void main(String[] args) {
		try {
			Server server = new Server(JettyConstant.port);
			WebAppContext context = new WebAppContext();
			context.setContextPath(JettyConstant.urlseparator);
			String proPath = System.getProperty("user.dir");
			System.out.println("项目路径：" + proPath);
			context.setResourceBase(proPath + "/src/main/webapp");
			context.setParentLoaderPriority(true);
			server.setHandler(context);
			server.start();
			Thread execThread = new Thread(new ExecThread(server));
			execThread.start();
			server.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
