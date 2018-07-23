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

/**
 * 类名称：MyWhitespaceTokenizer <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:51:56 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:51:56 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyWhitespaceTokenizer extends Tokenizer {

	MyCharAttribute charAttr = this.addAttribute(MyCharAttribute.class);
	char[] buffer = new char[255];
	int length = 0;
	int c;

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("...F...");
		// 清除所有的词项属性
		clearAttributes();
		length = 0;
		while (true) {
			c = this.input.read();

			if (c == -1) {
				if (length > 0) {
					// 复制到charAttr
					this.charAttr.setChars(buffer, length);
					return true;
				} else {
					return false;
				}
			}

			if (Character.isWhitespace(c)) {
				if (length > 0) {
					// 复制到charAttr
					this.charAttr.setChars(buffer, length);
					return true;
				}
			}

			buffer[length++] = (char) c;
		}
	}
}
