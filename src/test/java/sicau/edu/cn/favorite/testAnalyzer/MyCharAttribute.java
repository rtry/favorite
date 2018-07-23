package sicau.edu.cn.favorite.testAnalyzer;

import org.apache.lucene.util.Attribute;

public interface MyCharAttribute extends Attribute {
	void setChars(char[] buffer, int length);

	char[] getChars();

	int getLength();

	String toString();
}
