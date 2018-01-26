/**    
 * 文件名：ISuperESEntry.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

/**
 * 类名称：ISuperESEntry <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月26日 下午3:26:09 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月26日 下午3:26:09 <br>
 * 修改备注:此处的抽象方便必须使用publish,防止在非同包报错
 * @version
 * @see
 */
public abstract class EsEntry {

	/** 获取要操作的index */
	public abstract String getIndex();

	/** 获取要操作的type */
	public abstract String getType();

}
