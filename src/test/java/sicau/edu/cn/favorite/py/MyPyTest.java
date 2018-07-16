/**    
 * 文件名：MyPyTest.java    
 *    
 * 版本信息：    
 * 日期：2018年7月16日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.py;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;

import sicau.edu.cn.favorite.BaseTest;

/**
 * 类名称：MyPyTest <br>
 * 类描述: 拼音分词<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月16日 下午3:18:22 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月16日 下午3:18:22 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyPyTest extends BaseTest {

	Pinyin4jUtil pinyin4jUtil = new Pinyin4jUtil();

	@Test
	public void testPy() throws BadHanyuPinyinOutputFormatCombination {
		String str = "长沙市长";
		// String d1 = pinyin4jUtil.converterToSpell(str);
		// System.out.println(d1);

		String d2 = pinyin4jUtil.converterToFirst(str);
		System.out.println(d2);

	}

	@Test
	public void testPyAnalyzer() {

	}
}
