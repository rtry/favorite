/**    
 * 文件名：IRestClient.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

/**
 * 类名称：IRestClient <br>
 * 类描述: 接口方法<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月26日 下午3:17:27 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月26日 下午3:17:27 <br>
 * 修改备注:
 * @version
 * @see
 */
public interface IRestClient<T> {

	// 查询
	List<T> queryByDSL(JSONObject query);

	// 查询(分页)
	Page<T> getPageListByDSL(JSONObject query);

	// 新增
	String insert(T t);

	// 批量新增
	void bulkInsert(Collection<T> cs);

	// 通过ID删除
	String deleteById(String id);

	// 通过ID获取
	T getById(String id);

	// 预设 类
	Class<T> getClazz();

}
