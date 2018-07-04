/**    
 * 文件名：BookmarkLocalDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.local.impl;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexNotFoundException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.local.SuperDao;
import sicau.edu.cn.favorite.lucene.local.suggest.StringIterator;

/**
 * 类名称：BookmarkLocalDao <br>
 * 类描述: lucene 本地实现<br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月22日 下午3:38:29 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月22日 下午3:38:29 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BookmarkLocalDao extends BookmarkConvertDao implements SuperDao<Bookmark> {

	private Analyzer analyzer;
	private Directory dir;
	private static Logger log = Logger.getLogger(BookmarkLocalDao.class);
	private AnalyzingInfixSuggester suggester;

	public BookmarkLocalDao() {
		try {
			String indexPath = "F:\\lucene-4.10.2";
			dir = FSDirectory.open(Paths.get(indexPath));
			analyzer = new IKAnalyzer(true);
			suggester = new AnalyzingInfixSuggester(dir, analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String insert(Bookmark b) {
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			iwc.setOpenMode(OpenMode.CREATE);

			Document doc = this.convertToDoc(b);

			writer.addDocument(doc);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b.getId();
	}

	@Override
	public void bulkInsert(Collection<Bookmark> cs) {

		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			iwc.setOpenMode(OpenMode.CREATE);

			// 线程安全list,存放Document
			List<Document> safeList = new Vector<Document>();

			// 创建一个线程池，10线程
			ExecutorService service = Executors.newFixedThreadPool(10);

			// 主线程等待Latch 锁
			CountDownLatch latch = new CountDownLatch(cs.size());

			for (Bookmark b : cs) {
				// 线程池中线程执行逻辑
				service.execute(() -> {
					Document doc = this.convertToDoc(b);
					safeList.add(doc);
					// i--
					latch.countDown();
				});
			}

			// 主线程阻塞 等待所有执行完成...
			try {
				// await not wait
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("-------- " + cs.size() + "|" + safeList.size());
			for (Document doc : safeList) {
				writer.addDocument(doc);
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Page<Bookmark> getPageListByForm(SearchPageForm f) {
		Page<Bookmark> page = new Page<Bookmark>();
		QueryParser parser = new QueryParser("name", analyzer);
		try {
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			List<Bookmark> rt = new ArrayList<Bookmark>();

			if (f.getQuery() == null || f.getQuery().trim().equals("")) {
				Query q = IntPoint.newExactQuery("allFlag", 1);
				Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

				TopDocs results = searcher.search(q, f.getSize(), sort);
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					Bookmark b = this.convertFormDoc(doc);
					rt.add(b);
				}
			} else {

				Query query = parser.parse(f.getQuery());

				TopDocs results = searcher.search(query, f.getSize());
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					Bookmark b = this.convertFormDoc(doc);
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
	public String deleteById(String id) {
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			Term term = new Term("id", id);

			writer.deleteDocuments(term);
			writer.commit();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public Bookmark getById(String id) {
		try {
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			Term term = new Term("id", id);
			Query query = new TermQuery(term);

			TopDocs results = searcher.search(query, 1);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				Bookmark b = this.convertFormDoc(doc);
				return b;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Bookmark getLast() {
		try {

			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			Query q = IntPoint.newExactQuery("allFlag", 1);
			Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

			TopDocs results = searcher.search(q, 1, sort);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				Bookmark b = this.convertFormDoc(doc);
				return b;
			}
			reader.close();
		} catch (IndexNotFoundException e) {
			log.error(e);
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void buidSuggest() {
		try {
			log.info("构建热词...");
			// 收藏热词
			List<String> hots = new ArrayList<String>();

			// 整理
			IndexReader reader = DirectoryReader.open(dir);
			int max = reader.maxDoc();
			int min = 0;
			
			for (int i = 0; i < max; i++) {
				Terms terms = reader.getTermVector(i, "synopsis");

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
					System.out.print(sortedMap.get(j).getKey() + ":" + sortedMap.get(j).getValue()
							+ " | ");
				}
				System.out.println("");
				System.out.println("--------------");
			}
			System.out.println("------------------------max:" + max);
			System.out.println("------------------------min:" + min);
			// 构建
			// suggester.build(new StringIterator(hots.iterator()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> lookup(String keyword) {
		AnalyzingInfixSuggester suggester;
		try {
			HashSet<BytesRef> contexts = new HashSet<BytesRef>();
			suggester = new AnalyzingInfixSuggester(dir, analyzer);

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
