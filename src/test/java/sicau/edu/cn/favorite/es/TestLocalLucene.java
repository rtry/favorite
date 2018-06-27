/**    
 * 文件名：TestLocalLucene.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import sicau.edu.cn.favorite.AppClient;
import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.local.SuperDao;
import sicau.edu.cn.favorite.lucene.local.impl.BookmarkLocalDao;

/**
 * 类名称：TestLocalLucene <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午2:45:17 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午2:45:17 <br>
 * 修改备注:
 * @version
 * @see
 */
public class TestLocalLucene {
	@Before
	public void bf() {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));
	}
	@Test
	public void tLocal() {
		System.out.println("xxx");

		SuperDao<Bookmark> bookmarkDao = new BookmarkLocalDao();
		for (int i = 1; i < 5; i++) {
			Bookmark b1 = new Bookmark("" + i, "Java性能的进一步调优" + i, "http://23432554.com/dfsdf",
					i + 0L);
			bookmarkDao.insert(b1);
		}

		bookmarkDao.getById("1");
		bookmarkDao.deleteById("41");

		bookmarkDao.getLast();
		
		SearchPageForm f = new SearchPageForm();
		f.setPage(1);
		f.setQuery("Java");
		f.setSize(10);
		bookmarkDao.getPageListByForm(f);
	}
}
