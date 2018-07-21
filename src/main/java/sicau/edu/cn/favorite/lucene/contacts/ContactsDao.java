/**    
 * 文件名：ContactsDao.java    
 *    
 * 版本信息：    
 * 日期：2018年7月18日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene.contacts;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.wltea.analyzer.py.PinyinAnalyzer;

import sicau.edu.cn.favorite.contacts.Contacts;
import sicau.edu.cn.favorite.lucene.base.AbstractLocalDao;

/**
 * 类名称：ContactsDao <br>
 * 类描述: 通信录<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月18日 下午4:51:51 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月18日 下午4:51:51 <br>
 * 修改备注:
 * @version
 * @see
 */
public class ContactsDao extends AbstractLocalDao<Contacts> {

	@Override
	public Document convertToDoc(Contacts b) {
		Document doc = new Document();
		Field name = new TextField("name", b.getName(), Field.Store.YES);
		Field email = new TextField("email", b.getEmail(), Field.Store.YES);
		Field road = new StringField("road", b.getRoad(), Field.Store.YES);
		Field tel = new StringField("tel", b.getTel(), Field.Store.YES);
		Field sex = new IntPoint("sex", b.getSex());

		// allFlag 便于查询全部（索引，不存储）
		Field allFlag = new IntPoint("allFlag", 1);

		doc.add(name);
		doc.add(email);
		doc.add(road);
		doc.add(tel);
		doc.add(sex);
		doc.add(allFlag);

		return doc;
	}

	@Override
	public Contacts convertFormDoc(Document doc) {
		Contacts c = new Contacts();
		c.setEmail(doc.get("email"));
		c.setName(doc.get("name"));
		c.setRoad(doc.get("road"));
//		c.setSex(Integer.valueOf(doc.get("sex")));
		c.setTel(doc.get("tel"));
		return c;
	}

	@Override
	public Class<Contacts> getClazz() {
		return Contacts.class;
	}

	@Override
	public Analyzer getAnalyzer() {
//		return new StandardAnalyzer();
		return new PinyinAnalyzer();
	}

}
