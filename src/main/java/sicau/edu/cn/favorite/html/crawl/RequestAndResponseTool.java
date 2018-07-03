/**    
 * 文件名：RequestAndResponseTool.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.html.crawl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpStatus;

import sicau.edu.cn.favorite.html.wash.BlogType;
import sicau.edu.cn.favorite.html.wash.HtmlPage;

/**
 * 类名称：RequestAndResponseTool <br>
 * 类描述: 请求与响应<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午11:11:08 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午11:11:08 <br>
 * 修改备注:
 * @version
 * @see
 */
public class RequestAndResponseTool {

	private static final String regex = "(http(s)?://)?(www.)?";
	private static final String last = "/";

	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static Logger log = Logger.getLogger(RequestAndResponseTool.class);

	/**
	 * getDomainName 解析域名
	 * @param url
	 * @return String
	 * @Exception 异常描述
	 */
	public static String getDomainName(String url) {

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(url);

		if (m.find()) {
			String temp = url.substring(m.end());
			Pattern endPattern = Pattern.compile(last);
			Matcher endMatcher = endPattern.matcher(temp);
			if (endMatcher.find() && endMatcher.end() > 0) {
				temp = temp.substring(0, endMatcher.end() - 1);
			}
			return temp;
		}
		return url;
	}

	/**
	 * invoke 请求页面
	 * @param url 地址
	 * @return HtmlPage 页面对象
	 * @Exception 异常描述
	 */
	public static HtmlPage invoke(String url) {

		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);

			if (resp.getStatusLine().getStatusCode() == HttpStatus.OK_200) {
				HttpEntity entity = resp.getEntity();
				byte[] body = EntityUtils.toByteArray(entity);
				String contentType = null;
				if (entity.getContentType() != null)
					contentType = entity.getContentType().getValue();
				HtmlPage page = new HtmlPage(body, url, contentType);
				return page;
			} else {
				log.error("请求异常：" + url + " " + resp.getStatusLine());
			}
			resp.close();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	/**
	 * getContext 获取精简后的内容（用于统计热词）
	 * @param url 地址
	 * @return String 简后的内容
	 * @Exception 异常描述
	 */
	public static String getContext(String url) {
		HtmlPage page = invoke(url);
		String domainName = RequestAndResponseTool.getDomainName(url);
		BlogType type = BlogType.getByDomainName(domainName);
		if (type == null)
			System.out.println("无法找到模板，不能解析");

		return BlogType.helpFul(page, type);
	}
}
