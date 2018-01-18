package sicau.edu.cn.favorite;

import org.junit.Test;

import sicau.edu.cn.favorite.browser.impl.Chrome;

public class TestJSON {

	@Test
	public void testJJ() {
		Chrome c = new Chrome();
		c.getBookmarks();
	}
}
