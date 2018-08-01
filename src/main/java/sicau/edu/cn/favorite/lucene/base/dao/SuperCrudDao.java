/**    
 * 文件名：SuperCrudDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */

package sicau.edu.cn.favorite.lucene.base.dao;

import java.util.Collection;

/**
 * 类名称：SuperCrudDao <br>
 * 类描述: local父类 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午2:39:34 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午2:39:34 <br>
 * 修改备注:
 * @version
 * @see
 */

public interface SuperCrudDao<T> {

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

}
