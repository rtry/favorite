/**    
 * 文件名：BookmarkDao.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es.browser;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.es.AbstractEsDao;

/**
 * 类名称：BookmarkDao <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月26日 下午5:14:39 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月26日 下午5:14:39 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BookmarkDao extends AbstractEsDao<Bookmark> {

	@Override
	public String getIndex() {
		return "bookmark";
	}

	@Override
	public String getType() {
		return "doc";
	}

}
