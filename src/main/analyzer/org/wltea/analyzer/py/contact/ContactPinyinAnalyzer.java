/**    
 * 文件名：ContactPinyinAnalyzer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月17日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.py.contact;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.ngram.NGramTokenFilter;

/**
 * 类名称：ContactPinyinAnalyzer <br>
 * 类描述: 性别类拼音分词器<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月17日 下午3:54:55 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月17日 下午3:54:55 <br>
 * 修改备注:
 * 
 * @version
 * @see
 */
public class ContactPinyinAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		Tokenizer source = new ContactPinyinTokenizer();
		// 对首字母，进行ngram操作
		NGramTokenFilter filter = new NGramTokenFilter(source, 1, 20);
		return new TokenStreamComponents(source, filter);
	}

}
