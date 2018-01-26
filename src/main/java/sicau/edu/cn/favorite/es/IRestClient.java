/**    
 * 文件名：IRestClient.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

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
	String queryByDSL(JSONObject query);

	// 新增
	String insert(T t);

	// 通过ID删除
	String deleteById(String id);

	// 通过ID获取
	T getById(String id, Class<T> t);
}
