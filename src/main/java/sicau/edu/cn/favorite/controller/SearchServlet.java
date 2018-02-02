/**    
 * 文件名：SearchController.java    
 *    
 * 版本信息：    
 * 日期：2018年2月1日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.es.EsPage;
import sicau.edu.cn.favorite.es.browser.BookmarkDao;
import sicau.edu.cn.favorite.util.ServiceUtil;

/**
 * 类名称：SearchController <br>
 * 类描述: 搜索入口<br>
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

	private BookmarkDao bookmarkDao = new BookmarkDao();

	private final int pageSize = 11;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
			IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 组装查询分页数据
		String page = req.getParameter("page");
		if (StringUtils.isBlank(page))
			page = "1";
		Integer pageNum = Integer.parseInt(page);

		String query = req.getParameter("query");
		String qStr = "";
		if (StringUtils.isBlank(query)) {
			// 按条件查询
			qStr = "{\"query\" : {\"match\" : {\"name\" : \"" + query + "\"}}, \"from\": "
					+ (pageNum - 1) * pageSize + ",\"size\": " + pageSize + "}";

		} else {
			// 查询全部，按收录时间倒序
			qStr = "{\"query\" : {\"bool\" : {\"must\": [{\"match_all\" : {}}]}}, \"from\": "
					+ (pageNum - 1) * pageSize + ",\"size\": " + pageSize
					+ ",\"sort\" : [{\"createDate\": \"desc\"}]}";
		}
		JSONObject qObt = JSON.parseObject(qStr);
		EsPage<Bookmark> back = bookmarkDao.getPageListByDSL(qObt);
		ServiceUtil.setResponseVaule(resp, ServiceUtil.returnSuccess(back));
		return;
	}

}
