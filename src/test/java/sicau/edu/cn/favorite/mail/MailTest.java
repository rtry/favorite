package sicau.edu.cn.favorite.mail;

import org.junit.Test;

import sicau.edu.cn.favorite.BaseTest;
import sicau.edu.cn.favorite.util.mail.MailContext;
import sicau.edu.cn.favorite.util.mail.MailUtil;

public class MailTest extends BaseTest {

	@Test
	public void testSend() {

		MailContext target = new MailContext(0, "123566333",
				"214444444444443254368095633412413434134", null);

		// MailUtil.send("hmxq06@outlook.com", target);
		// MailUtil.send("dzpanxiwei@163.com", target);

		// MailUtil.receive(null);

		// System.out.println(PropertiesUtil.getPropertie("mail.username"));
	}

}
