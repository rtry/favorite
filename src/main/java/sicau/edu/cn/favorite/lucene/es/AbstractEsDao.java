/**    
 * 文件名：ArticleDocEsDao.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.es;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

import sicau.edu.cn.favorite.lucene.IRestClient;
import sicau.edu.cn.favorite.lucene.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

	private static Logger log = Logger.getLogger(AbstractEsDao.class);

	private static final String CHARSET = "UTF-8";

	static ContentType requestContentType = ContentType.APPLICATION_JSON.withCharset("utf-8");

	private static final String esUrl = "http://192.168.253.104:9200";

	String baseUrl = esUrl + "/" + getIndex() + "/" + getType();

	@Override
	public List<T> queryByDSL(JSONObject query) {
		String url = baseUrl + "/_search";
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
		JSONObject rt = JSON.parseObject(result);
		if (rt.containsKey("error"))
			return null;
		JSONObject hits = rt.getJSONObject("hits");
		JSONArray hitsArray = hits.getJSONArray("hits");
		List<T> rts = new ArrayList<T>();
		for (int i = 0; i < hitsArray.size(); i++) {
			JSONObject temp = hitsArray.getJSONObject(i);
			String id = temp.getString("_id");
			temp.getJSONObject("_source").put("id", id);
			T t = temp.getObject("_source", getClazz());
			rts.add(t);
		}
		return rts;
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
	public T getById(String id) {
		String url = baseUrl + "/" + id;
		HttpGet httpGet = new HttpGet(url);
		String result = "";
		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke getById api error", e);
		}
		log.info("invoke es getById result = " + result);
		log.info("------------------------------------------------------------------------");
		return JSON.parseObject(result).getObject("_source", getClazz());
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
			log.error("invoke deleteById api error", e);
		}
		log.info("invoke es deleteById result = " + result);
		log.info("------------------------------------------------------------------------");
		return result;
	}

	@Override
	public Page<T> getPageListByDSL(JSONObject query) {
		String url = baseUrl + "/_search";
		String result = "";
		int from = query.getIntValue("from");
		int size = query.getIntValue("size");
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
		JSONObject rt = JSON.parseObject(result);
		Page<T> pages = new Page<T>();
		if (rt.containsKey("error"))
			return pages;

		JSONObject hits = rt.getJSONObject("hits");
		int totalNums = hits.getIntValue("total");
		pages.setTotalNums(totalNums);
		pages.setCurrentPage(from / size);
		pages.setHasNext((from + size) < totalNums);
		pages.setTotalPages(Math.round(totalNums / size));
		JSONArray hitsArray = hits.getJSONArray("hits");
		List<T> rts = new ArrayList<T>();
		for (int i = 0; i < hitsArray.size(); i++) {
			JSONObject temp = hitsArray.getJSONObject(i);
			String id = temp.getString("_id");
			temp.getJSONObject("_source").put("id", id);
			T t = temp.getObject("_source", getClazz());
			rts.add(t);
		}
		pages.setResults(rts);
		log.info("------------------------------------------------------------------------");
		return pages;
	}

	@Override
	public void bulkInsert(Collection<T> cs) {
		StringBuffer sb = new StringBuffer();
		String url = baseUrl + "/_bulk";
		HttpPost httpPost = new HttpPost(url);
		String result = "";

		String idx = "{\"index\": {}}\r\n";
		for (T t : cs) {
			String temp = JSON.toJSONString(t);
			sb.append(idx).append(temp).append("\r\n");
		}
		if (sb.length() == 0)
			return;
		EntityBuilder entityBuilder = EntityBuilder.create().setText(sb.toString())
				.setContentType(requestContentType);
		httpPost.setEntity(entityBuilder.build());
		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			HttpEntity entity = resp.getEntity();
			result = EntityUtils.toString(entity, CHARSET);
			resp.close();
		} catch (Exception e) {
			log.error("invoke bulkInsert api error", e);
		}
		log.info("invoke es bulkInsert result = " + result);
		log.info("------------------------------------------------------------------------");
	}
}
