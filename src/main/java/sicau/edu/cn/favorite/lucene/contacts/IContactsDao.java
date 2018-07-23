package sicau.edu.cn.favorite.lucene.contacts;

import java.util.List;

import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;

public interface IContactsDao<T> {

	/** 搜索 */
	Page<T> getPageListByForm(SearchPageForm f);

	/** 创建搜索建议 */
	void buidSuggest();

	/** 搜索建议 */
	List<String> lookup(String keyword);
}
