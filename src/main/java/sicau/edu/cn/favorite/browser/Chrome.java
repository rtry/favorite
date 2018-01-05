package sicau.edu.cn.favorite.browser;

import java.util.Properties;

public class Chrome extends Browser {

	@Override
	public String getName() {
		return "Google 游览器";
	}

	@Override
	public String getBookmarksURI() {
		Properties sysProperty = System.getProperties();
		String home = sysProperty.getProperty("user.home");
		return home.concat("/AppData/Local/Google/Chrome/User Data/Default/Bookmarks");
	}
}
