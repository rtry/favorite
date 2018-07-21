/**    
 * 文件名：AnalyzerUtil.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 类名称：AnalyzerUtil <br>
 * 类描述: 分词器工具<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午3:00:14 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午3:00:14 <br>
 * 修改备注:
 * 
 * @version
 * @see
 */
public class AnalyzerUtil {

	/**
	 * getAnalyseResult 对字符串进行分词
	 * 
	 * @param analyzeStr
	 *            带分词的字符串
	 * @param analyzer
	 *            分词器
	 * @return List<String> 分词结果
	 * @Exception 异常描述
	 */
	public static List<String> getAnalyseResult(String analyzeStr, Analyzer analyzer) {
		List<String> response = new ArrayList<String>();
		try {
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(analyzeStr));
			CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				response.add(attr.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}
}
