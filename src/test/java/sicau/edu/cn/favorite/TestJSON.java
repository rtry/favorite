package sicau.edu.cn.favorite;

import org.junit.Test;

import sicau.edu.cn.favorite.browser.impl.Chrome;

public class TestJSON {

	/**
	 * testJJ 测试Chrome的收藏标签
	 * @Exception 异常描述
	 */
	@Test
	public void testJJ() {
		Chrome c = new Chrome();
		c.getBookmarks();
	}
}
