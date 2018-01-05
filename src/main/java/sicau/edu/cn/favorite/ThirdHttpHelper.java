/**    
 * 文件名：ThirdHttpHelper.java    
 *    
 * 版本信息：    
 * 日期：2017年8月29日    
 * Copyright pxw Corporation 2017 版权所有   
 */
package sicau.edu.cn.favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * 类名称：ThirdHttpHelper <br>
 * 类描述: 调用请求<br>
 * 创建人：pxw <br>
 * 创建时间：2017年8月29日 下午3:04:23 <br>
 * 修改人：pxw <br>
 * 修改时间：2017年8月29日 下午3:04:23 <br>
 * 修改备注:
 * @version
 * @see
 */
public class ThirdHttpHelper {

	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static Logger log = Logger.getLogger(ThirdHttpHelper.class);

	private static final String CHARSET = "UTF-8";

	/**
	 * invokeGet 远程调用一个Get请求
	 * @param url 请求地址
	 * @param params 请求所需要的参数
	 * @return String 请求返回的结果
	 * @Exception 异常描述
	 */
	public static String invokeGet(String url, Map<String, String> params) {
		log.info("invoke third api.");
		log.info("url:" + url);
		log.info("params:" + params);
		String result = "";
		if (StringUtils.isBlank(url) || params == null || params.size() == 0)
			return result;

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (StringUtils.isNotBlank(value))
				nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		String paramString = URLEncodedUtils.format(nvps, "utf-8");
		if (url.indexOf('?') == -1)
			url = url + "?" + paramString;
		else
			url = url + "&" + paramString;

		HttpGet httpGet = new HttpGet(url);
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke third api result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}

	/**
	 * invokePost 远程调用一个Post请求
	 * @param url 请求地址
	 * @param params 请求所需要的参数
	 * @return String 请求返回的结果
	 * @Exception 异常描述
	 */
	public static String invokePost(String url, Map<String, String> params) {
		log.info("invoke third api.");
		log.info("url:" + url);
		log.info("params:" + params);
		String charset = CHARSET;
		String result = "";
		if (StringUtils.isBlank(url) || params == null || params.size() == 0)
			return result;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for (String key : params.keySet()) {
			String value = params.get(key);
			if (StringUtils.isNotBlank(value))
				nvps.add(new BasicNameValuePair(key, params.get(key)));
		}
		HttpPost httpPost = new HttpPost(url);
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, charset));
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, charset);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke third api result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}

}
