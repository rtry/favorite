package sicau.edu.cn.favorite.es.lucene;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.html.crawl.RequestAndResponseTool;
import sicau.edu.cn.favorite.simple.Girl;

public class IndexTop {

	public static void main(String[] args) throws IOException {
		String indexPath = "F:\\lucene-4.10.2";
		Directory dir = FSDirectory.open(Paths.get(indexPath));

		Analyzer analyzer = new IKAnalyzer(true);
		// Analyzer analyzer = new StandardAnalyzer();

		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

		IndexWriter writer = new IndexWriter(dir, iwc);
		iwc.setOpenMode(OpenMode.CREATE);

		List<Girl> gs = Girl.simple();
		for (Girl g : gs.subList(0, 1)) {
			Document doc = new Document();

			FieldType type = new FieldType();
			type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
			type.setStored(true);// 原始字符串全部被保存在索引中
			type.setStoreTermVectors(true);// 存储词项量
			type.setTokenized(true);// 词条化

			String context = RequestAndResponseTool
					.getContext("http://aokunsang.iteye.com/blog/2053719");

			Field code = new StringField("code", g.getCode(), Field.Store.YES);
			Field name = new TextField("name", g.getName(), Field.Store.YES);
			Field desc = new TextField("des", g.getDes(), Field.Store.YES);
			Field field1 = new Field("content", context, type);
			doc.add(code);
			doc.add(name);
			doc.add(desc);
			doc.add(field1);
			System.out.println(doc);
			writer.addDocument(doc);
		}

		writer.close();
	}
}
