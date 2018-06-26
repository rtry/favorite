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
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.browser.entry.Bookmark;
import sicau.edu.cn.favorite.controller.form.SearchPageForm;
import sicau.edu.cn.favorite.lucene.Page;
import sicau.edu.cn.favorite.lucene.local.SuperDao;

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

	public BookmarkLocalDao() {
		try {
			String indexPath = "F:\\lucene-4.10.2";
			dir = FSDirectory.open(Paths.get(indexPath));
			analyzer = new IKAnalyzer();

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
			for (Bookmark b : cs) {
				Document doc = this.convertToDoc(b);
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
			Query query = parser.parse(f.getQuery());

			TopDocs results = searcher.search(query, f.getSize());
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = Math.toIntExact(results.totalHits);
			log.info("命中：" + numTotalHits + " 条");
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				Bookmark b = this.convertFormDoc(doc);
				System.out.println(b);
			}

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
			int numTotalHits = Math.toIntExact(results.totalHits);
			log.info("命中：" + numTotalHits + " 条");
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
			// int count = reader.maxDoc();// 所有文档数
			// Document doc = searcher.doc(count-1);
			// Bookmark b = this.convertFormDoc(doc);

			Query q = IntPoint.newExactQuery("allFlag", 1);
			Sort sort = new Sort(new SortField("createDate", SortField.Type.DOC));  

			TopDocs results = searcher.search(q, 1,sort);
			ScoreDoc[] hits = results.scoreDocs;
			for (ScoreDoc hit : hits) {
				Document doc = searcher.doc(hit.doc);
				Bookmark b = this.convertFormDoc(doc);
				System.out.println(b);
				return b;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
