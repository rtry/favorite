package org.wltea.analyzer.py;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class PinyinTokenFilter extends TokenFilter {

	CharTermAttribute charAttr = this.addAttribute(CharTermAttribute.class);

	protected PinyinTokenFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println(charAttr.toString());
		return input.incrementToken();
	}

}
