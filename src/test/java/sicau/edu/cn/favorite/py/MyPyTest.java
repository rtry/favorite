/**    
 * 文件名：MyPyTest.java    
 *    
 * 版本信息：    
 * 日期：2018年7月16日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.py;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.lucene.analysis.Analyzer;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.py.contact.ContactPinyinAnalyzer;

import sicau.edu.cn.favorite.BaseTest;
import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.contacts.Contacts;
import sicau.edu.cn.favorite.contacts.CorrespondenceRandomUtil;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.contacts.impl.ContactsDao;
import sicau.edu.cn.favorite.simple.MyAnalys;

/**
 * 类名称：MyPyTest <br>
 * 类描述: 拼音分词<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月16日 下午3:18:22 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月16日 下午3:18:22 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyPyTest extends BaseTest {

	Pinyin4jUtil pinyin4jUtil = new Pinyin4jUtil();

	@Test
	public void testPy() throws BadHanyuPinyinOutputFormatCombination {
		String str = "c万剑归宗";
		// String d1 = pinyin4jUtil.converterToSpell(str);
		// System.out.println(d1);

		String d2 = pinyin4jUtil.converterToFirst(str, false);
		System.out.println(d2);

	}

	@Test
	public void testPyAnalyzer() {
		String text = "2011年3月31日，孙燕姿与相恋5年多的男友纳迪姆在新加坡登记结婚";
		Analyzer analyzer = new ContactPinyinAnalyzer();
		List<String> lists = MyAnalys.getAnalyseResult(text, analyzer);
		for (String ss : lists)
			System.out.println(ss);
	}

	@Test
	public void testAnalyzer() {
		Chrome c = new Chrome();
		List<Bookmark> rt = c.getBookmarks();
		Analyzer analyzer = new IKAnalyzer(true);

		// 处理数据
		for (Bookmark b : rt) {
			System.out.println("------------------");
			String name = b.getName();
			System.out.println("变更前：" + name);
			if (name.indexOf("- ") != -1) {
				String ex = name.substring(0, name.indexOf("- "));
				System.out.println("变更后：" + ex);
//				List<String> lists = MyAnalys.getAnalyseResult(ex, analyzer);
//				for (String ss : lists)
//					System.out.println(ss);
			}
		}
	}

	@Test
	public void indexContact() {
		for (int i = 0; i < 100; i++) {
			System.out.println(CorrespondenceRandomUtil.getContacts());
		}
	}

	@Test
	public void searchContactDB() {

		ContactsDao dao = new ContactsDao();
		SearchPageForm f = new SearchPageForm();
		for (int i = 1; i <= 10; i++) {

			f.setPage(i);
			f.setQuery("zz");
			f.setSize(10);
			Page<Contacts> page = dao.getPageListByForm(f);
			page.getResults().forEach(e -> {
				System.out.println(e);
			});
			System.out.println(page);
			System.out.println("=---------------------------=");
			if (!page.isHasNext())
				break;
		}
	}

	@Test
	public void indexContactDB() {
		ContactsDao dao = new ContactsDao();
		List<Contacts> list = new ArrayList<Contacts>();
		for (int i = 0; i < 100; i++) {
			Contacts c = CorrespondenceRandomUtil.getContacts();
			System.out.println(c.toString());
			list.add(c);
		}
		dao.bulkInsert(list);

	}
}
