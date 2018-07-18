/**    
 * 文件名：ConvertDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.base;

import org.apache.lucene.document.Document;

/**
 * 类名称：ConvertDao <br>
 * 类描述: 转换文档超类 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午4:38:44 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午4:38:44 <br>
 * 修改备注:
 * @version
 * @see
 */
public interface ConvertDao<T> {

	/**
	 * convertToDoc 将普通对象转换为存储对象 <br>
	 * 需要设计那些字段需要索引
	 * @param t
	 * @return Document
	 * @Exception 异常描述
	 */
	public Document convertToDoc(T t);

	/**
	 * convertFormDoc 将存储对象转换为普通对象
	 * @param doc
	 * @return T
	 * @Exception 异常描述
	 */
	public T convertFormDoc(Document doc);

	/**
	 * getClazz 类名
	 * @return String
	 * @Exception 异常描述
	 */
	public Class<T> getClazz();
}
