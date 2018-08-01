/**    
 * 文件名：BookmarkConvertDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.bookmark.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
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
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.base.dao.AbstractLocalDao;
import sicau.edu.cn.favorite.lucene.base.suggest.StringIterator;
import sicau.edu.cn.favorite.lucene.base.util.Page;
import sicau.edu.cn.favorite.lucene.bookmark.IBookmarkDao;

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
public class BookmarkDao extends AbstractLocalDao<Bookmark> implements IBookmarkDao<Bookmark> {

	private AnalyzingInfixSuggester suggester;

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

	@Override
	public Page<Bookmark> getPageListByForm(SearchPageForm f) {
		Page<Bookmark> page = new Page<Bookmark>();
		page.setCurrentPage(f.getPage());
		QueryParser parser = new QueryParser("name", analyzer);
		try {
			IndexReader reader = DirectoryReader.open(indexDir);
			IndexSearcher searcher = new IndexSearcher(reader);

			List<Bookmark> rt = new ArrayList<Bookmark>();
			int index = (f.getPage() - 1) * f.getSize();
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
					Bookmark b = this.convertFormDoc(doc);
					rt.add(b);
				}
			} else {
				Query query = parser.parse(f.getQuery());
				if (index > 0) {
					TopDocs results = searcher.search(query, index);
					scoreDoc = results.scoreDocs[index - 1];
				}

				// ↓***************设置高亮格式****************↓
				QueryScorer scorer = new QueryScorer(query);
				Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
				SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(
						"<b><font color='red'>", "</font></b>");
				Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
				highlighter.setTextFragmenter(fragmenter);
				// ↑***************设置高亮格式****************↑

				hits = searcher.searchAfter(scoreDoc, query, f.getSize());
				for (int i = 0; i < hits.scoreDocs.length; i++) {
					ScoreDoc sdoc = hits.scoreDocs[i];
					Document doc = searcher.doc(sdoc.doc);
					Bookmark b = this.convertFormDoc(doc);

					// ↓***************设置高亮格式****************↓
					TokenStream tokenStream = analyzer.tokenStream("name",
							new StringReader(b.getName()));
					String name = highlighter.getBestFragment(tokenStream, b.getName());
					b.setName(name);
					// ↑***************设置高亮格式****************↑

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
		} catch (InvalidTokenOffsetsException e) {
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
				System.out.println(":::" + name);
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
