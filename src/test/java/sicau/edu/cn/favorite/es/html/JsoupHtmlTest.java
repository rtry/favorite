/**    
 * 文件名：JsoupHtmlTest.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es.html;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;

import sicau.edu.cn.favorite.AppClient;
import sicau.edu.cn.favorite.html.crawl.RequestAndResponseTool;

/**
 * 类名称：JsoupHtmlTest <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午10:55:16 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午10:55:16 <br>
 * 修改备注:
 * @version
 * @see
 */
public class JsoupHtmlTest {

	@Test
	public void first() {
		String html = "<html><head><title>First parse</title></head>"
				+ "<body><p>Parsed HTML into a doc.<b>A1<b>A11</b></b></p></body></html>";
		Document doc = Jsoup.parse(html);
		String t = doc.select("body").text();
		System.out.println(t);
	}

	@Test
	public void post() {
		try {
			Document doc = Jsoup.connect("https://www.cnblogs.com/zhangyinhua/p/8037599.html")
					.timeout(5000).post();
			System.out.println(doc.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void bf() {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));
	}

	@Test
	public void testDomainName() {

		String names[] = {

		"http://yychao.iteye.com/blog/1751583",
		// "https://developers.google.com/speed/docs/insights/EnableCompression",

		// "https://blog.csdn.net/u011054333/article/details/57179999"

		};

		for (String n : names) {
			System.out.println(RequestAndResponseTool.getContext(n));
		}
		// for (String n : names) {
		// System.out.println(RequestAndResponseTool.getDomainName(n));
		// }

	}
}
