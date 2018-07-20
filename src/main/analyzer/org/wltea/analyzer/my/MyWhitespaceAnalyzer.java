/**    
 * 文件名：MyWhitespaceAnalyzer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.my;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;

/**
 * 类名称：MyWhitespaceAnalyzer <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:56:58 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:56:58 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyWhitespaceAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String fieldName) {
		// Tokenizer source = new MyWhitespaceTokenizer2();
		Tokenizer source = new MyWhitespaceTokenizer();
		TokenStream filter = new MyLowerCaseTokenFilter(source);
		return new TokenStreamComponents(source, filter);
	}

}
