/**    
 * 文件名：BookmarkLocalDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.local;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.lucene.IRestClient;
import sicau.edu.cn.favorite.lucene.Page;

/**
 * 类名称：BookmarkLocalDao <br>
 * 类描述: lucene 本地实现<br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月22日 下午3:38:29 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月22日 下午3:38:29 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BookmarkLocalDao implements IRestClient<Bookmark> {

	@Override
	public List<Bookmark> queryByDSL(JSONObject query) {
		return null;
	}

	@Override
	public Page<Bookmark> getPageListByDSL(JSONObject query) {
		return null;
	}

	@Override
	public String insert(Bookmark t) {
		return null;
	}

	@Override
	public void bulkInsert(Collection<Bookmark> cs) {
	}

	@Override
	public String deleteById(String id) {
		return null;
	}

	@Override
	public Bookmark getById(String id) {
		return null;
	}

	@Override
	public Class<Bookmark> getClazz() {
		return null;
	}

}
