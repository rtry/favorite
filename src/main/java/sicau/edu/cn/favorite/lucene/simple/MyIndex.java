package sicau.edu.cn.favorite.lucene.simple;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class MyIndex {

	public static void main(String[] args) throws IOException {
		String indexPath = "F:\\lucene-4.10.2";
		Directory dir = FSDirectory.open(Paths.get(indexPath));

		Analyzer analyzer = new IKAnalyzer();
		// Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

		IndexWriter writer = new IndexWriter(dir, iwc);
		iwc.setOpenMode(OpenMode.CREATE);

		List<Girl> gs = Girl.simple();
		for (Girl g : gs) {
			Document doc = new Document();
			Field code = new StringField("code", g.getCode(), Field.Store.YES);
			Field name = new TextField("name", g.getName(), Field.Store.YES);
			Field desc = new TextField("des", g.getDes(), Field.Store.YES);
			doc.add(code);
			doc.add(name);
			doc.add(desc);
			System.out.println(doc);
			writer.addDocument(doc);
		}

		writer.close();
	}
}
