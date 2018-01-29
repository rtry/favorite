package sicau.edu.cn.favorite.es.test;

import sicau.edu.cn.favorite.es.AbstractEsDao;

public class ArticleDocDao extends AbstractEsDao<Article> {

	private Class<Article> clazz = Article.class;

	@Override
	public String getIndex() {
		return "article";
	}

	@Override
	public String getType() {
		return "doc";
	}

	@Override
	public Class<Article> getClazz() {
		return clazz;
	}

}
