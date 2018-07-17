package org.wltea.analyzer.py;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

public class PinyinNGramTokenFilter extends TokenFilter {

	protected PinyinNGramTokenFilter(TokenStream input) {
		super(input);
	}

	@Override
	public boolean incrementToken() throws IOException {
		return false;
	}

}
