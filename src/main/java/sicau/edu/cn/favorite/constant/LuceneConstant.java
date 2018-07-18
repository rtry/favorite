/**    
 * 文件名：LuceneConstant.java    
 *    
 * 版本信息：    
 * 日期：2018年7月18日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.constant;

/**
 * 类名称：LuceneConstant <br>
 * 类描述: Lucene 常量<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月18日 下午4:07:28 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月18日 下午4:07:28 <br>
 * 修改备注:
 * @version
 * @see
 */
public class LuceneConstant {

	// 基础路径
	public static final String baseFile = "F:\\lucene-7.3.1";

	// 搜索建议模块
	public static final String suggest = "suggest";

	// 基础数据模块
	public static final String base = "base";

	// 文件分割
	public static final String reg = "/";

	/**
	 * getIndexPath 获取存储路径
	 * @param simpleName
	 * @Exception 异常描述
	 */
	public static String getIndexPath(String simpleName) {
		return baseFile + reg + simpleName + reg + base;
	}
}
