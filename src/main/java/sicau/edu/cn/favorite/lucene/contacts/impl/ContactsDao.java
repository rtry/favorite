/**    
 * 文件名：ContactsDao.java    
 *    
 * 版本信息：    
 * 日期：2018年7月18日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.contacts.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.wltea.analyzer.py.contact.ContactPinyinAnalyzer;

import sicau.edu.cn.favorite.contacts.Contacts;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.base.AbstractLocalDao;
import sicau.edu.cn.favorite.lucene.contacts.IContactsDao;

/**
 * 类名称：ContactsDao <br>
 * 类描述: 通信录<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月18日 下午4:51:51 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月18日 下午4:51:51 <br>
 * 修改备注:
 * @version
 * @see
 */
public class ContactsDao extends AbstractLocalDao<Contacts> implements IContactsDao<Contacts> {

	@Override
	public Document convertToDoc(Contacts b) {
		Document doc = new Document();
		Field name = new TextField("name", b.getName(), Field.Store.YES);
		Field email = new StringField("email", b.getEmail(), Field.Store.YES);
		Field road = new StringField("road", b.getRoad(), Field.Store.YES);
		Field tel = new StringField("tel", b.getTel(), Field.Store.YES);
		Field sex = new StoredField("sex", b.getSex());

		doc.add(name);
		doc.add(email);
		doc.add(road);
		doc.add(tel);
		doc.add(sex);

		// 完成基本项
		super.consummateFile(doc);

		return doc;
	}

	@Override
	public Contacts convertFormDoc(Document doc) {
		Contacts c = new Contacts();
		c.setEmail(doc.get("email"));
		c.setName(doc.get("name"));
		c.setRoad(doc.get("road"));
		c.setSex(Integer.valueOf(doc.get("sex")));
		c.setTel(doc.get("tel"));
		c.setId(doc.get("id"));
		return c;
	}

	@Override
	public Class<Contacts> getClazz() {
		return Contacts.class;
	}

	@Override
	public Analyzer getAnalyzer() {
		// return new StandardAnalyzer();
		return new ContactPinyinAnalyzer();
		// return new MyWhitespaceAnalyzer();
	}

	/** 深度分页，排序等 */
	@Override
	public Page<Contacts> getPageListByForm(SearchPageForm f) {
		Page<Contacts> page = new Page<Contacts>();
		page.setCurrentPage(f.getPage());

		QueryParser parser = new QueryParser("name", new WhitespaceAnalyzer()); // 输入框非分词
		try {
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);

			List<Contacts> rt = new ArrayList<Contacts>();

			int index = (page.getCurrentPage() - 1) * f.getSize();
			ScoreDoc scoreDoc = null;
			TopDocs hits = null;
			if (f.getQuery() == null || f.getQuery().trim().equals("")) {
				Query query = IntPoint.newExactQuery("allFlag", 1);
				// 反序
				Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

				// 如果当前页是第一页面scoreDoc=null。
				if (index > 0) {
					TopDocs results = searcher.search(query, index, sort);
					// 因为索引是从0开始所以要index-1
					scoreDoc = results.scoreDocs[index - 1];
				}

				hits = searcher.searchAfter(scoreDoc, query, f.getSize(), sort);
				for (int i = 0; i < hits.scoreDocs.length; i++) {
					ScoreDoc sdoc = hits.scoreDocs[i];
					Document doc = searcher.doc(sdoc.doc);
					Contacts b = this.convertFormDoc(doc);
					rt.add(b);
				}
			} else {
				Query query = parser.parse(f.getQuery());
				if (index > 0) {
					TopDocs results = searcher.search(query, index);
					scoreDoc = results.scoreDocs[index - 1];
				}
				hits = searcher.searchAfter(scoreDoc, query, f.getSize());
				for (int i = 0; i < hits.scoreDocs.length; i++) {
					ScoreDoc sdoc = hits.scoreDocs[i];
					Document doc = searcher.doc(sdoc.doc);
					Contacts b = this.convertFormDoc(doc);
					rt.add(b);
				}
			}
			int totalNum = Long.valueOf(hits.totalHits).intValue();
			int totalPage = totalNum / f.getSize();
			page.setHasNext(totalNum > page.getCurrentPage() * f.getSize());
			page.setResults(rt);
			page.setTotalNums(totalNum);
			page.setTotalPages(totalPage + 1);
			reader.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

}
