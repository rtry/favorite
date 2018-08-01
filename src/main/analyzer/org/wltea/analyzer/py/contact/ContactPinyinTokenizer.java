/**    
 * 文件名：PinyinTokenizer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.py.contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import sicau.edu.cn.favorite.util.Pinyin4jUtil;

/**
 * 类名称：PinyinTokenizer <br>
 * 类描述: Tokenizer：分词器，输入是Reader字符流的TokenStream，完成从流中分出分项 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午4:19:26 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午4:19:26 <br>
 * 修改备注:
 * 
 * @version
 * @see
 */
public class ContactPinyinTokenizer extends Tokenizer {

	Pinyin4jUtil pyUtil = new Pinyin4jUtil();

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);

	StringBuffer sb = new StringBuffer();

	// 所有字符 緩存
	char[] allBuffer = new char[255];

	// 字符长度
	int length = 0;

	// 字符坐标
	int curnetIndex = 0;

	Iterator<String> itr;

	@Override
	public boolean incrementToken() throws IOException {
		// System.out.println("..进入分词器..");
		//此方法不可少
		clearAttributes();
		// 1. 读取所有字符
		if (length == 0) {
			int buffer = 0;
			while ((buffer = this.input.read()) != -1) {
				allBuffer[length] = (char) buffer;
				sb.append(allBuffer[length]);
				length++;
			}
			String py = pyUtil.converterToFirst(sb.toString(), false);
			String[] pus = py.split(",");
			List<String> pys = new ArrayList<String>();
			for (String temp : pus) {
				pys.add(temp);
			}
			pys.add(sb.toString());
			itr = pys.iterator();
		}

		// 2.分词第一步，单个分词
		if (curnetIndex < length) {
			charAttr.append(allBuffer[curnetIndex]);
			curnetIndex++;
			return true;
		}

		// 3.分词第二步，首字母
		while (itr.hasNext()) {
			String temp = itr.next();
			charAttr.append(temp);
			return true;
		}
		// 清除所有的词项属性
		this.init();
		return false;
	}

	private void init() {
		clearAttributes();
		allBuffer = new char[255];
		length = 0;
		curnetIndex = 0;
		sb = new StringBuffer();
	}
}
