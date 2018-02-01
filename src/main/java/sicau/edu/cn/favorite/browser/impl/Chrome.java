package sicau.edu.cn.favorite.browser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import sicau.edu.cn.favorite.browser.Browser;
import sicau.edu.cn.favorite.browser.entry.Bookmark;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Chrome extends Browser {

	private String name = "Google 游览器";

	private List<Bookmark> bookmarks;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getBookmarksURI() {
		Properties sysProperty = System.getProperties();
		String home = sysProperty.getProperty("user.home");
		return home.concat("/AppData/Local/Google/Chrome/User Data/Default/Bookmarks");
	}

	@Override
	public List<Bookmark> getBookmarks() {
		// if (bookmarks != null) {
		// return bookmarks;
		// } else {
		File file = new File(getBookmarksURI());
		try {
			FileInputStream fis = new FileInputStream(file);
			// local file
			int length = fis.available();
			byte[] buffer = new byte[length];
			fis.read(buffer);
			fis.close();
			JSONObject json = (JSONObject) JSON.parse(buffer);
			JSONObject root = json.getJSONObject("roots");
			JSONObject bars = root.getJSONObject("bookmark_bar");
			List<Bookmark> bookmarks = new ArrayList<Bookmark>();
			getChildren(bars, bookmarks);
			this.bookmarks = bookmarks;
			// other 其他书签
			// synced 移动设备书签
			return bookmarks;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// }
		return null;
	}

	private void getChildren(JSONObject parent, List<Bookmark> store) {
		String type = parent.getString("type");
		if (type.equals("folder")) {
			JSONArray children = parent.getJSONArray("children");
			int size = children.size();
			if (size > 0) {
				for (int i = 0; i < size; i++) {
					JSONObject json = children.getJSONObject(i);
					getChildren(json, store);
				}
			}
		} else if (type.equals("url")) {
			String url = parent.getString("url");
			String name = parent.getString("name");
			Long createDate = parent.getLong("date_added");
			Bookmark bookmark = new Bookmark(name, url, createDate);
			store.add(bookmark);
		}
	}
}
