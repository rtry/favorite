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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.bookmark.IBookmarkDao;
import sicau.edu.cn.favorite.lucene.bookmark.impl.BookmarkDao;
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
public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IBookmarkDao<Bookmark> bookmarkDao = new BookmarkDao();

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

		SearchPageForm f = new SearchPageForm();
		f.setPage(pageNum);
		f.setQuery(query);

		Page<Bookmark> back = bookmarkDao.getPageListByForm(f);
		ServiceUtil.setResponseVaule(resp, ServiceUtil.returnSuccess(back));
		return;
	}

}
