/**    
 * 文件名：PinyinTokenizer.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.py;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import sicau.edu.cn.favorite.py.Pinyin4jUtil;

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
public class PinyinTokenizer extends Tokenizer {

	Pinyin4jUtil pyUtil = new Pinyin4jUtil();

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);

	// 所有字符 緩存
	char[] allBuffer = new char[255];

	// 字符长度
	int length = 0;

	// 字符坐标
	int curnetIndex = 0;

	Iterator<String> itr;

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("..进入分词器..");

		// 1. 读取所有字符
		if (length == 0) {
			int buffer = 0;
			while ((buffer = this.input.read()) != -1) {
				allBuffer[length] = (char) buffer;
				length++;
			}
			String py = pyUtil.converterToFirst(new String(allBuffer));
			String[] pus = py.split(",");
			List<String> pys = Arrays.asList(pus);
			itr = pys.iterator();
		}

		// 2.分词第一步，单个分词
		if (curnetIndex < length) {
			charAttr.setEmpty();
			charAttr.append(allBuffer[curnetIndex]);
			curnetIndex++;
			return true;
		}

		// 3.分词第二步，首字母
		while (itr.hasNext()) {
			String temp = itr.next();
			charAttr.setEmpty();
			charAttr.append(temp);
			return true;
		}
		return false;
	}
}
