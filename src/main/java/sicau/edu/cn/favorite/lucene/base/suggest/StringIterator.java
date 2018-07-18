/**    
 * 文件名：StringIterator.java    
 *    
 * 版本信息：    
 * 日期：2018年6月28日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.base.suggest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

/**
 * 类名称：StringIterator <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月28日 下午2:36:09 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月28日 下午2:36:09 <br>
 * 修改备注:
 * @version
 * @see
 */
public class StringIterator implements InputIterator {

	private Iterator<String> suggestIterator;
	private String currentSuggest;

	public StringIterator(Iterator<String> girlIterator) {
		super();
		this.suggestIterator = girlIterator;
	}

	/** 设置有payload信息 */
	@Override
	public boolean hasPayloads() {
		return true;
	}

	/** 设置有Contexts信息 */
	@Override
	public boolean hasContexts() {
		return true;
	}

	@Override
	public BytesRef next() throws IOException {
		if (suggestIterator.hasNext()) {
			// 设置当前
			currentSuggest = suggestIterator.next();
			return new BytesRef(currentSuggest.getBytes("UTF8"));
		}
		return null;
	}

	@Override
	public long weight() {
		return 0;
	}

	@Override
	public BytesRef payload() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(currentSuggest);
			out.close();
			return new BytesRef(bos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Well that's unfortunate.");
		}
	}

	@Override
	public Set<BytesRef> contexts() {
		return null;
	}

}
