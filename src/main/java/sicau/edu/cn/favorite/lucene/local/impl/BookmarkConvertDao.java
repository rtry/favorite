/**    
 * 文件名：BookmarkConvertDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.local.impl;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.lucene.local.ConvertDao;

/**
 * 类名称：BookmarkConvertDao <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午4:38:20 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午4:38:20 <br>
 * 修改备注:
 * @version
 * @see
 */
public abstract class BookmarkConvertDao implements ConvertDao<Bookmark> {

	@Override
	public Document convertToDoc(Bookmark b) {

		Document doc = new Document();
		Field id = new StringField("id", b.getId(), Field.Store.YES);
		Field name = new TextField("name", b.getName(), Field.Store.YES);
		Field url = new StringField("url", b.getUrl(), Field.Store.YES);
		// 不用LongPoint StoredField 使用 NumericDocValuesField（索引，排序，不存储）
		Field storedCreateDate = new StoredField("createDate", b.getCreateDate());
		// createDate 再用一个存储的字段
		Field createDate = new NumericDocValuesField("createDate", b.getCreateDate());
		// allFlag 便于查询全部，索引，不存储
		Field allFlag = new IntPoint("allFlag", 1); // 为了方便查询全部,只索引，不存储

		doc.add(id);
		doc.add(name);
		doc.add(url);
		doc.add(storedCreateDate);
		doc.add(createDate);
		doc.add(allFlag);
		System.out.println(doc);
		return doc;
	}

	@Override
	public Bookmark convertFormDoc(Document doc) {
		Bookmark b = new Bookmark(doc.get("id"), doc.get("name"), doc.get("url"), Long.valueOf(doc
				.get("createDate")));
		return b;
	}
}
