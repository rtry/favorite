package sicau.edu.cn.favorite;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.es.test.ArticleDocDao;

public class TestJSON {

	/**
	 * testJJ 测试Chrome的收藏标签
	 * @Exception 异常描述
	 */
	@Test
	public void testJJ() {
		Chrome c = new Chrome();
		c.getBookmarks();
	}

	@Test
	public void testEs() {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));

		// 插入
		// Felicity felicity = new Felicity();
		// felicity.setAge(44);
		// felicity.setName("大当家");
		// FelicityTuser fe = new FelicityTuser();
		//
		// System.out.println(JSON.toJSONString(fe));

		// RestClient.insert(fe);

		// 搜索
		// String str = "{\"query\" : {\"match\" : {\"name\" : \"大当家\"}}}";
		// System.out.println(str);
		// JSONObject query = JSONObject.parseObject(str);
		// String tt = RestClient.queryByDSL(null, query, new
		// FelicityTuser(null));
		// System.out.println(tt);

		// 删除
		// String tt1 = RestClient.deleteById(new ArticleDoc(null),
		// "AWEueXeKDIbBed4asij4");
		// System.out.println(tt1);
		//
		// String esId = "";
		// RestClient.getById(new ArticleDoc(null), esId);

		// 新插入
		ArticleDocDao aDao = new ArticleDocDao();
		// Article t = new Article();
		// t.setAuthor("a");
		// t.setContent("b");
		// t.setId(214124);
		// t.setPubdate(new Date());
		// t.setSource("c");
		// t.setTitle("d");
		// t.setUrl("f");
		// aDao.insert(t);

		// 新查询1
		// Article c = aDao.getById("AWEzI5O0g7H2InaNY8WP", Article.class);
		// System.out.println(c.getAuthor());

		// 首席记者
		// 新查询2
		// String str = "{\"query\" : {\"match\" : {\"content\" : \"首席记者\"}}}";
		// aDao.queryByDSL(JSON.parseObject(str));

		aDao.deleteById("AWEzI5O0g7H2InaNY8WP");

	}
}
