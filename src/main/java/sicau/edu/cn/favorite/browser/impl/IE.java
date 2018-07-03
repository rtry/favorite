/**    
 * 文件名：IE.java    
 *    
 * 版本信息：    
 * 日期：2018年7月3日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.browser.impl;

import java.util.List;

import sicau.edu.cn.favorite.browser.SuperBrowser;
import sicau.edu.cn.favorite.browser.entry.Bookmark;

/**
 * 类名称：IE <br>
 * 类描述: IE 游览器<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月3日 下午4:42:47 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月3日 下午4:42:47 <br>
 * 修改备注:
 * @version
 * @see
 */
public class IE extends SuperBrowser {

	@Override
	public String getName() {
		return "微软 IE 游览器";
	}

	@Override
	public String getBookmarksURI() {
		return null;
	}

	@Override
	public List<Bookmark> getBookmarks() {
		return null;
	}

}
