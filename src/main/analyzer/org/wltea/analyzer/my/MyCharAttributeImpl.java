/**    
 * 文件名：MyCharAttributeImpl.java    
 *    
 * 版本信息：    
 * 日期：2018年7月19日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package org.wltea.analyzer.my;

import org.apache.lucene.util.AttributeImpl;
import org.apache.lucene.util.AttributeReflector;

/**
 * 类名称：MyCharAttributeImpl <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月19日 下午2:49:11 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月19日 下午2:49:11 <br>
 * 修改备注:
 * @version
 * @see
 */
public class MyCharAttributeImpl extends AttributeImpl implements MyCharAttribute {

	// ↓-----------------------AttributeImpl-------------------------------↓
	@Override
	public void clear() {
		this.length = 0;
	}

	@Override
	public void reflectWith(AttributeReflector reflector) {

	}

	@Override
	public void copyTo(AttributeImpl target) {

	}

	// ↑-----------------------AttributeImpl-------------------------------↑

	private char[] chatTerm = new char[255];
	private int length = 0;

	@Override
	public void setChars(char[] buffer, int length) {
		this.length = length;
		if (length > 0) {
			System.arraycopy(buffer, 0, this.chatTerm, 0, length);
		}
	}

	@Override
	public char[] getChars() {
		return this.chatTerm;

	}

	@Override
	public int getLength() {
		return this.length;
	}

	@Override
	public String toString() {
		if (this.length > 0) {
			return new String(this.chatTerm, 0, length);
		}
		return null;
	}

}
