/**    
 * 文件名：IBookmarkDao.java    
 *    
 * 版本信息：    
 * 日期：2018年7月23日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.bookmark;

import java.util.List;

import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.base.util.Page;

/**
 * 类名称：IBookmarkDao <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月23日 上午10:37:51 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月23日 上午10:37:51 <br>
 * 修改备注:
 * @version
 * @see
 */
public interface IBookmarkDao<T> {

	/** 搜索 */
	Page<T> getPageListByForm(SearchPageForm f);

	/** 创建搜索建议 */
	void buidSuggest();

	/** 搜索建议 */
	List<String> lookup(String keyword);
}
