/**    
 * 文件名：AbstractLocalDao.java    
 *    
 * 版本信息：    
 * 日期：2018年6月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.base;

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
import java.util.Set;
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

import sicau.edu.cn.favorite.constant.LuceneConstant;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.base.suggest.StringIterator;

/**
 * 类名称：AbstractLocalDao <br>
 * 类描述: lucene 本地实现<br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月22日 下午3:38:29 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月22日 下午3:38:29 <br>
 * 修改备注:
 * @version
 * @see
 */
public abstract class AbstractLocalDao<T> implements SuperCrudDao<T>, ConvertDao<T> {

	private Analyzer analyzer;
	private Directory dir;
	private static Logger log = Logger.getLogger(AbstractLocalDao.class);
	private AnalyzingInfixSuggester suggester;

	// 可能不同的模块 需要的分词器不同
	public abstract Analyzer getAnalyzer();

	public AbstractLocalDao() {
		try {
			String indexPath = LuceneConstant.getIndexPath(getClazz().getSimpleName());
			dir = FSDirectory.open(Paths.get(indexPath));
			analyzer = getAnalyzer();
			suggester = new AnalyzingInfixSuggester(dir, analyzer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String insert(T t) {
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(dir, iwc);
			iwc.setOpenMode(OpenMode.CREATE);

			Document doc = this.convertToDoc(t);

			writer.addDocument(doc);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void bulkInsert(Collection<T> cs) {

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

			for (T b : cs) {
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
	public Page<T> getPageListByForm(SearchPageForm f) {
		Page<T> page = new Page<T>();
		QueryParser parser = new QueryParser("name", analyzer);
		try {
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			List<T> rt = new ArrayList<T>();

			if (f.getQuery() == null || f.getQuery().trim().equals("")) {
				Query q = IntPoint.newExactQuery("allFlag", 1);
				Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

				TopDocs results = searcher.search(q, f.getSize(), sort);
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					T b = this.convertFormDoc(doc);
					rt.add(b);
				}
			} else {

				Query query = parser.parse(f.getQuery());

				TopDocs results = searcher.search(query, f.getSize());
				ScoreDoc[] hits = results.scoreDocs;
				for (ScoreDoc hit : hits) {
					Document doc = searcher.doc(hit.doc);
					T b = this.convertFormDoc(doc);
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
	public T getById(String id) {
		try {
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			Term term = new Term("id", id);
			Query query = new TermQuery(term);

			TopDocs results = searcher.search(query, 1);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				T b = this.convertFormDoc(doc);
				return b;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public T getLast() {
		try {

			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);

			Query q = IntPoint.newExactQuery("allFlag", 1);
			Sort sort = new Sort(new SortField("createDate", SortField.Type.LONG, true));

			TopDocs results = searcher.search(q, 1, sort);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				T b = this.convertFormDoc(doc);
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
			Set<String> hots = new HashSet<String>();

			// 整理
			IndexReader reader = DirectoryReader.open(dir);
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
