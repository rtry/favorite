/**    
 * 文件名：MyWhitespaceAnalyzerTest.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.myanalyzer;

import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;
import org.wltea.analyzer.my.MyWhitespaceAnalyzer;
import org.wltea.analyzer.py.PinyinAnalyzer;

import sicau.edu.cn.favorite.BaseTest;
import sicau.edu.cn.favorite.lucene.contacts.ContactsDao;
import sicau.edu.cn.favorite.lucene.util.AnalyzerUtil;

/**
 * 类名称：MyWhitespaceAnalyzerTest <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:58:26 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:58:26 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyWhitespaceAnalyzerTest extends BaseTest {

	@Test
	public void testAny() {
		// String analyzeStr = "hi  this is a good start ";
		String analyzeStr = "hi  thIS IS A good start ";
		// Analyzer analyzer = new IKAnalyzer();
		// Analyzer analyzer = new PinyinAnalyzer();
		// Analyzer analyzer = new MyWhitespaceAnalyzer();
		Analyzer analyzer = new StandardAnalyzer();
		// Analyzer analyzer = new IKAnalyzer();
		List<String> rt = AnalyzerUtil.getAnalyseResult(analyzeStr, analyzer);
		for (String s : rt)
			System.out.print(s + "|");
	}
	
	@Test
	public void search(){
		ContactsDao dao = new ContactsDao();
//		dao.getPageListByForm(f)
	}
}
