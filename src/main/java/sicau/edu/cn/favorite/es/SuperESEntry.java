/**    
 * 文件名：ESEntry.java    
 *    
 * 版本信息：    
 * 日期：2018年1月24日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

/**
 * 类名称：ESEntry <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月24日 下午4:42:37 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月24日 下午4:42:37 <br>
 * 修改备注:
 * @version
 * @see
 */
public abstract class SuperESEntry<T> {

	private T source;

	public SuperESEntry(T t) {
		this.source = t;
	}

	/** 获取要操作的index */
	abstract String getIndex();

	/** 获取要操作的type */
	abstract String getType();

	/** 获取要操作的source */
	public T getSource() {
		if (this.source != null)
			return this.source;
		else
			return null;
	}

}
