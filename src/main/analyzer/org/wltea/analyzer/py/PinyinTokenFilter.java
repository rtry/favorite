package org.wltea.analyzer.py;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import sicau.edu.cn.favorite.py.Pinyin4jUtil;

public class PinyinTokenFilter extends TokenFilter {

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);
	StringBuffer sb = new StringBuffer();
	boolean over = false;
	Pinyin4jUtil pyUtil = new Pinyin4jUtil();

	protected PinyinTokenFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		if (over)
			return false;

		boolean flag = this.input.incrementToken();

		if (!flag) {
			// 单个词拆分完毕，进行拼音转换
			String name = sb.toString();

			// 首字母
			String pyFirst = pyUtil.converterToFirst(name);
			// System.out.println(pyFirst);
			String firstes[] = pyFirst.split(",");
			for (String t : firstes)
				charAttr.append(t);

			over = true;
			return over;
		} else {
			String s = charAttr.toString();
			sb.append(s);
		}

		return flag;
	}

}
