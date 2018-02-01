/**    
 * 文件名：SearchController.java    
 *    
 * 版本信息：    
 * 日期：2018年2月1日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.myServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 类名称：SearchController <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年2月1日 下午3:52:23 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年2月1日 下午3:52:23 <br>
 * 修改备注:
 * @version
 * @see
 */
@WebServlet(name = "searchFilter", urlPatterns = { "/search" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SearchServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		String query = req.getParameter("query");
		PrintWriter out = resp.getWriter();
		logger.info("xxxxxxxxxxx1xxxxxxx");
		out.println("进入Servlet的参数：" + query + ".<br>");
		out.flush();
	}
}
