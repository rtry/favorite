/**    
 * 文件名：HtmlHelpfulContext.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.html.wash;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import sicau.edu.cn.favorite.html.crawl.RequestAndResponseTool;

/**
 * 类名称：HtmlHelpfulContext <br>
 * 类描述: 有用的文本 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午11:32:28 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午11:32:28 <br>
 * 修改备注:
 * @version
 * @see
 */
public class HtmlHelpfulContextFactory {

	public static String getContext(HtmlPage page) {
		String url = page.getUrl();
		String domainName = RequestAndResponseTool.getDomainName(url);
		BlogType type = BlogType.getByDomainName(domainName);
		if (type == null)
			System.out.println("无法找到模板，不能解析");

		return helpFul(page, type);
	}

	private static String helpFul(HtmlPage page, BlogType type) {
		Document doc = page.getDoc();
		String text = null;
		switch (type) {
		case bscn:
			Element bscn = doc.selectFirst(".articalContent");
			if (bscn != null && bscn.hasText())
				text = bscn.text();
			break;

		case cnblogs:

			Element cnblogs = doc.selectFirst(".postBody");
			if (cnblogs != null && cnblogs.hasText())
				text = cnblogs.text();
			break;

		default:
			break;
		}

		return text;
	}
}
