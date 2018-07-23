/**    
 * 文件名：MyLowerCaseTokenFilter.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.testAnalyzer;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

/**
 * 类名称：MyLowerCaseTokenFilter <br>
 * 类描述: 过滤器<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:54:55 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:54:55 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyLowerCaseTokenFilter extends TokenFilter {

	protected MyLowerCaseTokenFilter(TokenStream input) {
		super(input);
	}

	MyCharAttribute charAttr = this.addAttribute(MyCharAttribute.class);

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("...小写转换...");
		boolean res = this.input.incrementToken();
		if (res) {
			char[] chars = charAttr.getChars();
			int length = charAttr.getLength();
			if (length > 0) {
				for (int i = 0; i < length; i++) {
					chars[i] = Character.toLowerCase(chars[i]);
				}
			}
		}
		return res;
	}

}
