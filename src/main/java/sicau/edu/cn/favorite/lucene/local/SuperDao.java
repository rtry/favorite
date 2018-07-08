/**    
 * 文件名：SuperDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */

package sicau.edu.cn.favorite.lucene.local;

import java.util.Collection;
import java.util.List;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;

/**
 * 类名称：SuperDao <br>
 * 类描述: local父类 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午2:39:34 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午2:39:34 <br>
 * 修改备注:
 * @version
 * @see
 */

public interface SuperDao<T> {

	/** 新增 */
	String insert(T t);

	/** 批量新增 */
	void bulkInsert(Collection<T> cs);

	/** 通过ID删除 */
	String deleteById(String id);

	/** 通过ID获取 */
	T getById(String id);

	/** 通过最新一条 */
	T getLast();

	/** 搜索 */
	Page<Bookmark> getPageListByForm(SearchPageForm f);

	/** 创建搜索建议 */
	void buidSuggest();

	/** 搜索建议 */
	List<String> lookup(String keyword);
}
