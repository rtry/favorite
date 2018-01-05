package sicau.edu.cn.favorite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import sicau.edu.cn.favorite.browser.Chrome;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test1 {

	public static void main(String[] args) {
		Chrome c = new Chrome();
		System.out.println(c.getName());
		String uri = c.getBookmarksURI();
		System.out.println(c.getBookmarksURI());
		File file = new File(uri);

		System.out.println(file.isFile());
		try {
			FileInputStream fis = new FileInputStream(file);
			// local file
			int length = fis.available();
			byte[] buffer = new byte[length];
			fis.read(buffer);
			fis.close();
			JSONObject json = (JSONObject) JSON.parse(buffer);
			System.out.println(json.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
