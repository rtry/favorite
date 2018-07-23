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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;

import sicau.edu.cn.favorite.contacts.Contacts;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.base.AbstractLocalDao;
import sicau.edu.cn.favorite.lucene.base.suggest.StringIterator;
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

	private AnalyzingInfixSuggester suggester;

	@Override
	public Document convertToDoc(Contacts b) {
		Document doc = new Document();
		Field name = new TextField("name", b.getName(), Field.Store.YES);
		Field email = new TextField("email", b.getEmail(), Field.Store.YES);
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
		return c;
	}

	@Override
	public Class<Contacts> getClazz() {
		return Contacts.class;
	}

	@Override
	public Analyzer getAnalyzer() {
		return new StandardAnalyzer();
		// return new PinyinAnalyzer();
	}

	@Override
	public Page<Contacts> getPageListByForm(SearchPageForm f) {
		Page<Contacts> page = new Page<Contacts>();
		QueryParser parser = new QueryParser("name", new WhitespaceAnalyzer());
		try {
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);

			List<Contacts> rt = new ArrayList<Contacts>();

			if (f.getQuery() == null || f.getQuery().trim().equals("")) {
				Query q = IntPoint.newExactQuery("allFlag", 1);
				Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

				TopDocs results = searcher.search(q, f.getSize(), sort);
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					Contacts b = this.convertFormDoc(doc);
					rt.add(b);
				}
			} else {

				Query query = parser.parse(f.getQuery());

				TopDocs results = searcher.search(query, f.getSize());
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					Contacts b = this.convertFormDoc(doc);
					rt.add(b);
				}
			}
			page.setHasNext(false);
			page.setCurrentPage(1);
			page.setResults(rt);
			page.setTotalNums(rt.size());
			page.setTotalPages(1);
			reader.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

	@Override
	public void buidSuggest() {
		try {
			log.info("构建热词...");
			// 收藏热词
			Set<String> hots = new HashSet<String>();

			// 整理
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);
			int max = reader.maxDoc();
			int min = 0;

			for (int i = 0; i < max; i++) {
				Terms terms = reader.getTermVector(i, "synopsis");

				Document doc = searcher.doc(i);
				String name = doc.get("name");
				int idx = name.indexOf("-");
				if (idx != -1) {
					name = name.substring(0, idx);
				}
				hots.add(name);

				if (terms == null)
					continue;
				// 遍历词项
				TermsEnum termsEnum = terms.iterator();
				BytesRef thisTerm = null;
				// 放词汇量
				Map<String, Integer> map = new HashMap<String, Integer>();
				while ((thisTerm = termsEnum.next()) != null) {
					// 词项
					String termText = thisTerm.utf8ToString();
					// 通过totalTermFreq()方法获取词项频率
					map.put(termText, (int) termsEnum.totalTermFreq());
				}

				// 按value排序
				List<Map.Entry<String, Integer>> sortedMap = new ArrayList<Map.Entry<String, Integer>>(
						map.entrySet());
				Collections.sort(sortedMap, new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
						return (o2.getValue() - o1.getValue());
					}
				});
				min++;
				int size = sortedMap.size() > 20 ? 20 : sortedMap.size();
				for (int j = 0; j < size; j++) {

					String keyword = sortedMap.get(j).getKey();
					// 如果是单字，不加入;
					if (keyword.length() == 1)
						continue;
					// 进入搜索热词
					hots.add(keyword);
					System.out.print(sortedMap.get(j).getKey() + ":" + sortedMap.get(j).getValue()
							+ " | ");
				}
				System.out.println();
				System.out.println("----------------------------");
			}
			System.out.println("------------------------max:" + max);
			System.out.println("------------------------min:" + min);
			for (String k : hots)
				System.out.println(k);
			// 构建
			suggester.build(new StringIterator(hots.iterator()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> lookup(String keyword) {
		try {
			HashSet<BytesRef> contexts = new HashSet<BytesRef>();

			List<LookupResult> results = suggester.lookup(keyword, contexts, 2, true, false);
			for (LookupResult result : results) {
				System.out.println(result.key);
				// 从payload中反序列化出Product对象
				BytesRef bytesRef = result.payload;
				System.out.println(": " + bytesRef.utf8ToString());
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
