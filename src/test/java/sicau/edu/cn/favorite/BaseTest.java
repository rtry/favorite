/**    
 * 文件名：BaseTest.java    
 *    
 * 版本信息：    
 * 日期：2018年7月16日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;

/**
 * 类名称：BaseTest <br>
 * 类描述: 测试基类<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月16日 下午3:18:54 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月16日 下午3:18:54 <br>
 * 修改备注:
 * @version
 * @see
 */
public class BaseTest {
	@Before
	public void bf() {
		String log4jPath = "props/log4j.properties";
		PropertyConfigurator.configure(AppClient.class.getClassLoader().getResource(log4jPath));
	}
}
