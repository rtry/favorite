/**    
 * 文件名：PinyinAnalyzer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月17日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.py;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;
import org.apache.lucene.analysis.ngram.NGramTokenizer;
import org.apache.lucene.analysis.util.CharTokenizer;

/**
 * 类名称：PinyinAnalyzer <br>
 * 类描述: 拼音分词器<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月17日 下午3:54:55 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月17日 下午3:54:55 <br>
 * 修改备注:
 * @version
 * @see
 */
public class PinyinAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer source = new PinyinTokenizer();
		// NGramTokenizer source = new NGramTokenizer(1,10);
		// NGramTokenFilter filter =new NGramTokenFilter(source, 1, 10);
		// PinyinNGramTokenFilter result = new PinyinNGramTokenFilter(source);
		return new TokenStreamComponents(source, source);
	}

}
