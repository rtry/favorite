/**    
 * 文件名：MyWhitespaceTokenizer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.testAnalyzer;

import java.io.IOException;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 类名称：MyWhitespaceTokenizer <br>
 * 类描述: 分词器<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:51:56 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:51:56 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyWhitespaceTokenizer2 extends Tokenizer {

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);
	char[] buffer = new char[255];
	int length = 0;
	int c;

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("...F1...");
		// 清除所有的词项属性
		clearAttributes();
		length = 0;
		while (true) {
			c = this.input.read();
			System.out.println();
			if (c == -1) {
				if (length > 0) {
					// 复制到charAttr
					this.charAttr.copyBuffer(buffer, 0, length);
					return true;
				} else {
					return false;
				}
			}

			if (Character.isWhitespace(c)) {
				if (length > 0) {
					// 复制到charAttr
					this.charAttr.copyBuffer(buffer, 0, length);
					return true;
				}
			}

			buffer[length++] = (char) c;
		}
	}
}
