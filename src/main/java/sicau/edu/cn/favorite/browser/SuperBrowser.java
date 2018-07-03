/**    
 * 文件名：Browser.java    
 *    
 * 版本信息：    
 * 日期：2018年1月4日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.browser;

import java.util.List;

import sicau.edu.cn.favorite.browser.entry.Bookmark;

/**
 * 类名称：Browser <br>
 * 类描述: 游览器抽象<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月4日 下午6:03:25 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月4日 下午6:03:25 <br>
 * 修改备注:
 * @version
 * @see
 */
public abstract class SuperBrowser {

	/**
	 * getName 获取游览器名
	 * @return String 游览器名
	 * @Exception 异常描述
	 */
	public abstract String getName();

	/**
	 * getBookmarksURI 获取收藏夹路径存储位置
	 * @return String 收藏夹路径存储位置
	 * @Exception 异常描述
	 */
	public abstract String getBookmarksURI();

	/**
	 * getBookmarks 获取收藏夹对象集合
	 * @return List<Bookmark> 收藏夹对象集合
	 * @Exception 异常描述
	 */
	public abstract List<Bookmark> getBookmarks();
}
