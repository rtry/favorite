package org.wltea.analyzer.py;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class PinyinTokenFilter extends TokenFilter {

	protected PinyinTokenFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		System.out.println("xx");
		return false;
	}

}
