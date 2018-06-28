package sicau.edu.cn.favorite.lucene.simple;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MySearch {

	public static void main(String[] args) throws IOException, ParseException {
		String indexPath = "F:\\lucene-4.10.2";

		Directory dir = FSDirectory.open(Paths.get(indexPath));
		Analyzer analyzer = new IKAnalyzer();

		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);

		QueryParser parser = new QueryParser("des", analyzer);
		Query query = parser.parse("大地");

		TopDocs results = searcher.search(query, 5);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = Math.toIntExact(results.totalHits);
		System.out.println("命中：" + numTotalHits);
		for (ScoreDoc hit : hits) {
			Document doc = searcher.doc(hit.doc);
			System.out.println("   |||: " + doc);
		}

	}
}
