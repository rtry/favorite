/**    
 * 文件名：RestClient.java    
 *    
 * 版本信息：    
 * 日期：2018年1月24日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sicau.edu.cn.favorite.AppClient;
import sicau.edu.cn.favorite.util.ThirdHttpHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 类名称：RestClient <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月24日 下午2:33:40 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月24日 下午2:33:40 <br>
 * 修改备注:
 * @version
 * @see
 */
public class RestClient {
	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static Logger log = Logger.getLogger(ThirdHttpHelper.class);

	private static final String CHARSET = "UTF-8";

	static ContentType requestContentType = ContentType.APPLICATION_JSON.withCharset("utf-8");

	private static final String esUrl = "http://192.168.253.104:9200";

	public static String queryByDSL(String url, JSONObject query, SuperESEntry<?> entry) {
		url = esUrl + "/" + entry.getIndex() + "/" + entry.getType() + "/_search";
		log.info("invoke es query ...");
		log.info("url:" + url);
		String result = "";
		String json = query.toJSONString();
		HttpPost httpPost = new HttpPost(url);
		EntityBuilder entityBuilder = EntityBuilder.create().setText(json)
				.setContentType(requestContentType);
		httpPost.setEntity(entityBuilder.build());
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke es query result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}

	public static String insert(SuperESEntry<?> entry) {
		if (entry.getSource() == null) {
			log.info("source为空，不能插入");
			return null;
		}
		String json = JSON.toJSONString(entry.getSource());
		String url = esUrl.concat("/").concat(entry.getIndex()).concat("/").concat(entry.getType());
		HttpPost httpPost = new HttpPost(url);
		String result = "";

		EntityBuilder entityBuilder = EntityBuilder.create().setText(json)
				.setContentType(requestContentType);
		httpPost.setEntity(entityBuilder.build());
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke es insert result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}

	public static void main(String[] args) {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));

		// 插入
		// Felicity felicity = new Felicity();
		// felicity.setAge(44);
		// felicity.setName("大当家");
		// FelicityTuser fe = new FelicityTuser(felicity);
		// RestClient.insert1(fe);

		// 搜索
		// String str = "{\"query\" : {\"match\" : {\"name\" : \"大当家\"}}}";
		// System.out.println(str);
		// JSONObject query = JSONObject.parseObject(str);
		// String tt = RestClient.queryByDSL(null, query, new
		// FelicityTuser(null));
		// System.out.println(tt);

		// 删除
		String tt1 = RestClient.deleteById(new ArticleDoc(null), "AWEueXeKDIbBed4asij4");
		System.out.println(tt1);

	}

	public static String deleteById(SuperESEntry<?> entry, String id) {
		String url = esUrl.concat("/").concat(entry.getIndex()).concat("/").concat(entry.getType())
				.concat("/").concat(id);
		HttpDelete httpPost = new HttpDelete(url);
		String result = "";
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke es insert result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}
}
