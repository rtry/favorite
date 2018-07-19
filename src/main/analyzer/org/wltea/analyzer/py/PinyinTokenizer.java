/**    
 * 文件名：PinyinTokenizer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.py;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 类名称：PinyinTokenizer <br>
 * 类描述: Tokenizer：分词器，输入是Reader字符流的TokenStream，完成从流中分出分项 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午4:19:26 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午4:19:26 <br>
 * 修改备注:
 * @version
 * @see
 */
public class PinyinTokenizer extends Tokenizer {

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);

	/** 忽略非汉字，将汉字按单个字进行分词 */
	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("单子..");
		while (true) {
			int c = this.input.read();
			this.charAttr.setEmpty();

			if (c == -1)
				return false;

			if (c > 128) {
				// 中文
				this.charAttr.append((char) c);
				return true;
			}
		}
	}
}
