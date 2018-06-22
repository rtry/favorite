/**    
 * 文件名：InitDataTask.java    
 *    
 * 版本信息：    
 * 日期：2018年2月1日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.lucene.es.impl.BookmarkDao;

import com.alibaba.fastjson.JSON;

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
		logger.info("启动时初始化数据：");
		BookmarkDao bdao = new BookmarkDao();
		// 查询ES中最新的一条数据
		String query = "{\"query\" : {\"bool\" : {\"must\": [{\"match_all\" : {}}]}}, \"from\": 0,\"size\": 1,\"sort\" : [{\"createDate\": \"desc\"}]}";
		List<Bookmark> bms = bdao.queryByDSL(JSON.parseObject(query));
		List<Bookmark> inserts = new ArrayList<Bookmark>();
		Chrome c = new Chrome();
		List<Bookmark> rt = c.getBookmarks();

		if (bms != null && bms.size() > 0) {
			Bookmark lastBm = bms.get(0);
			for (Bookmark r : rt) {
				if (r.getCreateDate() > lastBm.getCreateDate()) {
					inserts.add(r);
				}
			}
		} else {
			inserts = rt;
		}
		bdao.bulkInsert(inserts);
	}
}
