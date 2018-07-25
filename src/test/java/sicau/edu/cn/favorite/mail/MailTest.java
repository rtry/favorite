package sicau.edu.cn.favorite.mail;

import org.junit.Test;

import sicau.edu.cn.favorite.BaseTest;
import sicau.edu.cn.favorite.browser.impl.Chrome;
import sicau.edu.cn.favorite.util.Base64Utils;
import sicau.edu.cn.favorite.util.PropertiesUtil;
import sicau.edu.cn.favorite.util.mail.MailContext;
import sicau.edu.cn.favorite.util.mail.MailUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MailTest extends BaseTest {

	@Test
	public void testSend() {
		try {
			Chrome c = new Chrome();

			JSONObject json = (JSONObject) JSON.parse(c.getBuffer());

			String t = Base64Utils.decode2String(json.toJSONString());

			MailContext target = new MailContext(0, "33", t, null);
			String key = PropertiesUtil.getPropertie("mail.secret");

			// MailUtil.send("hmxq06@outlook.com", target);
			// MailUtil.send("dzpanxiwei@163.com", target);

			MailUtil.receive(null);

			// System.out.println(PropertiesUtil.getPropertie("mail.username"));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}