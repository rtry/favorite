package sicau.edu.cn.favorite.lucene.contacts;

import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;

public interface IContactsDao<T> {

	/** 搜索 */
	Page<T> getPageListByForm(SearchPageForm f);

}
