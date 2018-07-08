/**    
 * 文件名：GirlIterator.java    
 *    
 * 版本信息：    
 * 日期：2018年6月28日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.simple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

/**
 * 类名称：GirlIterator <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月28日 下午2:36:09 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月28日 下午2:36:09 <br>
 * 修改备注:
 * @version
 * @see
 */
public class GirlIterator implements InputIterator {

	private Iterator<Girl> girlIterator;
	private Girl currentGirl;

	public GirlIterator(Iterator<Girl> girlIterator) {
		super();
		this.girlIterator = girlIterator;
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
		System.out.println("next...");
		if (girlIterator.hasNext()) {
			// 设置当前
			currentGirl = girlIterator.next();
			return new BytesRef(currentGirl.getDes().getBytes("UTF8"));
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
			out.writeObject(currentGirl.getDes());
			out.close();
			return new BytesRef(bos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("Well that's unfortunate.");
		}
	}

	@Override
	public Set<BytesRef> contexts() {
		return null;
		// try {
		// Set<BytesRef> regions = new HashSet<BytesRef>();
		// for (String region : currentProduct.getRegions()) {
		// regions.add(new BytesRef(region.getBytes("UTF8")));
		// }
		// return regions;
		// } catch (UnsupportedEncodingException e) {
		// throw new RuntimeException("Couldn't convert to UTF-8");
		// }
	}

}
