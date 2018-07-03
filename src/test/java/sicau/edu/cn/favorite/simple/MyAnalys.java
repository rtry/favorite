/**    
 * 文件名：MyAnalys.java    
 *    
 * 版本信息：    
 * 日期：2018年6月22日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.simple;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.PropertyConfigurator;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import sicau.edu.cn.favorite.AppClient;
import sicau.edu.cn.favorite.html.crawl.RequestAndResponseTool;

/**
 * 类名称：MyAnalys <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月22日 下午2:03:17 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月22日 下午2:03:17 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyAnalys {

	public static void main(String[] args) {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));

		// Analyzer analyzer = new StandardAnalyzer();
		Analyzer analyzer = new IKAnalyzer(true);

		// SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

		String srt = RequestAndResponseTool
				.getContext("https://www.cnblogs.com/sanmubird/p/7857474.html");

		List<String> rt = MyAnalys.getAnalyseResult(srt, analyzer);
		for (String str : rt) {
			System.out.print(" |  " + str);
		}
	}

	public static List<String> getAnalyseResult(String analyzeStr, Analyzer analyzer) {
		List<String> response = new ArrayList<String>();
		TokenStream tokenStream = null;
		try {
			tokenStream = analyzer.tokenStream("content", new StringReader(analyzeStr));
			CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while (tokenStream.incrementToken()) {
				response.add(attr.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (tokenStream != null) {
				try {
					tokenStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return response;
	}

}
