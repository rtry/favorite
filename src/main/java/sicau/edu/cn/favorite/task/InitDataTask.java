/**    
 * 文件名：InitDataTask.java    
 *    
 * 版本信息：    
 * 日期：2018年2月1日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.constant.LuceneConstant;
import sicau.edu.cn.favorite.lucene.bookmark.impl.BookmarkDao;

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
		BookmarkDao bdao = new BookmarkDao();
		// 查询最新的一条数据
		Bookmark lastBm = bdao.getLast();
		List<Bookmark> inserts = new ArrayList<Bookmark>();
		Chrome c = new Chrome();
		List<Bookmark> rt = c.getBookmarks();

		if (lastBm == null) {
			inserts = rt;
		} else {
			for (Bookmark r : rt) {
				if (r.getCreateDate() > lastBm.getCreateDate())
					inserts.add(r);
			}
		}
		// 每次重新构建
		clear();
		// 构建索引
		bdao.bulkInsert(inserts);

		// 构建Suggest
		// bdao.buidSuggest();

		logger.info("========================初始化数据完成...========================");
	}

	/**
	 * 删除文件夹下的所有文件，便于重新生成索引
	 * @Exception 异常描述
	 */
	private void clear() {
		String indexPath = LuceneConstant.getIndexPath(Bookmark.class.getSimpleName());
		File base = new File(indexPath);
		if (base.exists() && base.isDirectory()) {
			File[] files = base.listFiles();
			for (File f : files) {
				if (f.canExecute()) {
					System.out.println(indexPath + "/" + f.getName() + " is delete " + f.delete());
				}
			}
		}
	}

	public static void main(String[] args) {
		InitDataTask d = new InitDataTask();
		d.clear();
	}
}
