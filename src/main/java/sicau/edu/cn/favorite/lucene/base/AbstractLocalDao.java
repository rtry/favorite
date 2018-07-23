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
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexNotFoundException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import sicau.edu.cn.favorite.constant.LuceneConstant;

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

	protected static Logger log = Logger.getLogger(AbstractLocalDao.class);

	/** 分词器 */
	protected Analyzer analyzer;
	/** 文档类容存储位置 */
	protected Directory indexDir;

	private AtomicInteger ids = new AtomicInteger(0);

	/**
	 * getAnalyzer<br>
	 * 请在实现类中实现该方法<br>
	 * 可能不同的模块 需要的分词器不同
	 * @return Analyzer
	 * @Exception 异常描述
	 */
	public abstract Analyzer getAnalyzer();

	public AbstractLocalDao() {
		try {
			String indexPath = LuceneConstant.getIndexPath(getClazz().getSimpleName());
			indexDir = FSDirectory.open(Paths.get(indexPath));
			analyzer = getAnalyzer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String insert(T t) {
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(indexDir, iwc);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

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
			IndexWriter writer = new IndexWriter(indexDir, iwc);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);

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

			for (Document doc : safeList) {
				writer.addDocument(doc);
			}
			System.out.println("-------- " + cs.size() + "|" + safeList.size());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String deleteById(String id) {
		try {
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			IndexWriter writer = new IndexWriter(indexDir, iwc);
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
			IndexReader reader = DirectoryReader.open(indexDir);
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

			IndexReader reader = DirectoryReader.open(indexDir);
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

	/**
	 * consummateFile 完善基础项<br>
	 * id,allFlag,createDate
	 * @param doc
	 * @Exception 异常描述
	 */
	public void consummateFile(Document doc) {

		int id = ids.incrementAndGet();
		IndexableField idFile = doc.getField("id");
		if (idFile == null) {
			idFile = new StringField("id", id + "", Field.Store.YES);
			doc.add(idFile);
		}

		IndexableField allFlag = doc.getField("allFlag");
		if (allFlag == null) {
			// allFlag 便于查询全部（索引，不存储）
			allFlag = new IntPoint("allFlag", 1);
			doc.add(allFlag);
		}
		Long createTime = System.currentTimeMillis();
		createTime = createTime + id;
		IndexableField storedCreateDate = doc.getField("createDate");
		if (storedCreateDate == null) {
			// createDate 用一个存储的字段
			storedCreateDate = new StoredField("createDate", createTime);
			// 不用LongPoint StoredField 使用 NumericDocValuesField（索引，排序，不存储）
			Field createDate = new NumericDocValuesField("createDate", createTime);
			doc.add(storedCreateDate);
			doc.add(createDate);
		}
	}
}
