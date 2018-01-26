package sicau.edu.cn.favorite.es.test;

import sicau.edu.cn.favorite.es.AbstractEsDao;

public class ArticleDocDao extends AbstractEsDao<Article> {

	@Override
	public String getIndex() {
		return "article";
	}

	@Override
	public String getType() {
		return "doc";
	}

}
