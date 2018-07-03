/**    
 * 文件名：InitDataTask.java    
 *    
 * 版本信息：    
 * 日期：2018年2月1日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.html.crawl.RequestAndResponseTool;
import sicau.edu.cn.favorite.html.wash.BlogType;
import sicau.edu.cn.favorite.lucene.local.impl.BookmarkLocalDao;

/**
 * 类名称：InitDataTask <br>
 * 类描述: 初始化数据<br>
 * 创建人：felicity <br>
 * 创建时间：2018年2月1日 下午3:17:21 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年2月1日 下午3:17:21 <br>
 * 修改备注:
 * @version
 * @see
 */
public class InitDataTask {
	private static Logger logger = Logger.getLogger(InitDataTask.class);

	public void init() {
		logger.info("========================正在初始化数据...========================");
		BookmarkLocalDao bdao = new BookmarkLocalDao();
		// 查询最新的一条数据
		Bookmark lastBm = bdao.getLast();

		List<Bookmark> inserts = new ArrayList<Bookmark>();
		Chrome c = new Chrome();
		List<Bookmark> rt = c.getBookmarks();

		this.test(rt);

		if (lastBm == null) {
			inserts = rt;
		} else {
			for (Bookmark r : rt) {
				if (r.getCreateDate() > lastBm.getCreateDate())
					inserts.add(r);
			}
		}
		bdao.bulkInsert(inserts);
		logger.info("========================初始化数据完成...========================");
	}

	private void test(List<Bookmark> rt) {
		Map<String, Integer> rrr = new HashMap<String, Integer>();

		int ii = 0, j = 0;
		for (Bookmark b : rt) {
			String domainName = RequestAndResponseTool.getDomainName(b.getUrl());
			BlogType type = BlogType.getByDomainName(domainName);
			if (type == null) {
				System.out.println(b.getUrl());
				ii++;
			} else
				j++;

			if (rrr.containsKey(domainName)) {
				int ic = rrr.get(domainName) + 1;
				rrr.put(domainName, ic);
			} else {
				rrr.put(domainName, 1);
			}
		}
		System.out.println("总收藏条数：" + rt.size() + " 现有分析样式：" + j + " 未有分析样式：" + ii);
		// 按value排序
		List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(
				rrr.entrySet());
		Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue() - o1.getValue());
			}
		});

		for (int i = 0; i < sortedMap.size(); i++) {
			BlogType type = BlogType.getByDomainName(sortedMap.get(i).getKey());
			if (type == null)
				System.out.println(sortedMap.get(i).getKey() + ":" + sortedMap.get(i).getValue()
						+ "  " + (type == null ? " 无" : " 有"));
		}
	}
}
