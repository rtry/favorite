/**    
 * 文件名：ArticleDocEsDao.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import sicau.edu.cn.favorite.util.ThirdHttpHelper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 类名称：ArticleDocEsDao <br>
 * 类描述: ES对象基本操作方法，基类<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月26日 下午3:30:40 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月26日 下午3:30:40 <br>
 * 修改备注:
 * @version
 * @see
 */
public abstract class AbstractEsDao<T> extends EsEntry implements IRestClient<T> {

	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	private static Logger log = Logger.getLogger(ThirdHttpHelper.class);

	private static final String CHARSET = "UTF-8";

	static ContentType requestContentType = ContentType.APPLICATION_JSON.withCharset("utf-8");

	private static final String esUrl = "http://192.168.253.104:9200";

	String baseUrl = esUrl + "/" + getIndex() + "/" + getType();

	@Override
	public String queryByDSL(JSONObject query) {
		String url = baseUrl + "/_search";
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

	@Override
	public String insert(T t) {
		if (t == null) {
			log.info("对象为空，不能Insert...");
			return null;
		}
		String json = JSON.toJSONString(t);
		HttpPost httpPost = new HttpPost(baseUrl);
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
		String id = JSON.parseObject(result).get("_id") + "";
		return id;
	}

	@Override
	public T getById(String id, Class<T> t) {
		String url = baseUrl + "/" + id;
		HttpGet httpGet = new HttpGet(url);
		String result = "";
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke third api error", e);
		}
		log.info("invoke es insert result = " + result);
		log.info("------------------------------------------------------------------------");
		return JSON.parseObject(result).getObject("_source", t);
	}

	@Override
	public String deleteById(String id) {
		String url = baseUrl + "/" + id;
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
