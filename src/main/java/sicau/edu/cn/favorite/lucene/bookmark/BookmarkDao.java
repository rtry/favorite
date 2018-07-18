/**    
 * 文件名：BookmarkConvertDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.bookmark;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.lucene.base.AbstractLocalDao;

/**
 * 类名称：BookmarkConvertDao <br>
 * 类描述: 类型转换 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月26日 下午4:38:20 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月26日 下午4:38:20 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BookmarkDao extends AbstractLocalDao<Bookmark> {

	@Override
	public Analyzer getAnalyzer() {
		return new IKAnalyzer(true);
	}

	public Class<Bookmark> getClazz() {
		return Bookmark.class;
	}

	@Override
	public Document convertToDoc(Bookmark b) {

		Document doc = new Document();
		Field id = new StringField("id", b.getId(), Field.Store.YES);
		Field name = new TextField("name", b.getName(), Field.Store.YES);
		Field url = new StringField("url", b.getUrl(), Field.Store.YES);

		// createDate 用一个存储的字段
		Field storedCreateDate = new StoredField("createDate", b.getCreateDate());
		// 不用LongPoint StoredField 使用 NumericDocValuesField（索引，排序，不存储）
		Field createDate = new NumericDocValuesField("createDate", b.getCreateDate());

		// allFlag 便于查询全部（索引，不存储）
		Field allFlag = new IntPoint("allFlag", 1);

		doc.add(id);
		doc.add(name);
		doc.add(url);
		doc.add(storedCreateDate);
		doc.add(createDate);
		doc.add(allFlag);

		// FieldType type = new FieldType();
		// type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		// // 原始字符串全部被保存在索引中
		// type.setStored(true);
		// // 存储词项量
		// type.setStoreTermVectors(true);
		// // 词条化
		// type.setTokenized(true);
		//
		// // 网页爬虫,拉取简介 synopsis
		// String synopsis =
		// RequestAndResponseTool.getContext(b.getUrl().replace("#", ""));
		// if (StringUtils.isNotBlank(synopsis)) {
		// Field synopsisFile = new Field("synopsis", synopsis, type);
		// doc.add(synopsisFile);
		// }

		return doc;
	}

	@Override
	public Bookmark convertFormDoc(Document doc) {
		Bookmark b = new Bookmark(doc.get("id"), doc.get("name"), doc.get("url"), Long.valueOf(doc
				.get("createDate")));
		return b;
	}
}
