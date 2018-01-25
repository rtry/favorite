/**    
 * 文件名：ArticleDoc.java    
 *    
 * 版本信息：    
 * 日期：2018年1月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

/**
 * 类名称：ArticleDoc <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月25日 下午6:02:10 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月25日 下午6:02:10 <br>
 * 修改备注:
 * @version
 * @see
 */
public class ArticleDoc extends SuperESEntry<Article> {

	public ArticleDoc(Article t) {
		super(t);
	}

	@Override
	String getIndex() {
		return "article";
	}

	@Override
	String getType() {
		return "doc";
	}

}
