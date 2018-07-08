/**    
 * 文件名：MySuggest.java    
 *    
 * 版本信息：    
 * 日期：2018年6月28日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.simple;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.suggest.Lookup.LookupResult;
import org.apache.lucene.search.suggest.analyzing.AnalyzingInfixSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * 类名称：MySuggest <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月28日 下午2:44:17 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月28日 下午2:44:17 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MySuggest {
	private static void lookup(AnalyzingInfixSuggester suggester, String name, String region)
			throws IOException {
		HashSet<BytesRef> contexts = new HashSet<BytesRef>();
		// contexts.add(new BytesRef(region.getBytes("UTF8")));
		// 先以contexts为过滤条件进行过滤，再以name为关键字进行筛选，根据weight值排序返回前2条
		// 第3个布尔值即是否每个Term都要匹配，第4个参数表示是否需要关键字高亮
		List<LookupResult> results = suggester.lookup(name, contexts, 2, true, false);
		System.out.println("-- \"" + name + "\" (" + region + "):");
		for (LookupResult result : results) {
			System.out.println(result.key);
			// 从payload中反序列化出Product对象
			BytesRef bytesRef = result.payload;
			System.out.println(": "+bytesRef.utf8ToString());
		}
		System.out.println();
	}

	public static void main(String[] args) throws IOException {
		String indexPath = "F:\\lucene-4.10.2";

		Directory dir = FSDirectory.open(Paths.get(indexPath));
		Analyzer analyzer = new IKAnalyzer();

		AnalyzingInfixSuggester suggester = new AnalyzingInfixSuggester(dir, analyzer);
		List<Girl> gs = Girl.simple();
		suggester.build(new GirlIterator(gs.iterator()));

		lookup(suggester, "大地", "US");
	}
}
