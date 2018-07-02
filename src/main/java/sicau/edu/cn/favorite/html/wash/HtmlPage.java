/**    
 * 文件名：HtmlPage.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.html.wash;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 类名称：HtmlPage <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午11:17:08 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午11:17:08 <br>
 * 修改备注:
 * @version
 * @see
 */
public class HtmlPage {

	// 网页二进制
	private byte[] body;
	// 网页Dom文档
	private Document doc;
	// 字符编码
	private String charset;
	// url路径
	private String url;
	// 内容类型
	private String contentType;

	public String getUrl() {
		return url;
	}

	public String getContentType() {
		return contentType;
	}

	/** 构成器 */
	public HtmlPage(byte[] body, String url, String contentType) {
		super();
		this.body = body;
		this.url = url;
		this.contentType = contentType;
	}

	public Document getDoc() {
		if (doc != null)
			return doc;

		else {
			String charset = getCharset();
			try {
				String html = new String(body, charset);
				doc = Jsoup.parse(html);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return doc;
	}

	private String getCharset() {
		if (charset != null)
			return charset;

		if (contentType != null) {
			String temps[] = contentType.toLowerCase().split(";");
			for (String temp : temps) {
				if (temp.indexOf("charset=") != -1) {
					String ch = temp.split("=")[1].trim();
					if (ch != null)
						charset = ch;
				}
			}
		}

		if (charset == null) {
			// 根据内容来猜测 字符编码
			charset = CharsetDetector.guessEncoding(body);
		}
		return charset;
	}
}
