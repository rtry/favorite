/**    
 * 文件名：PropertiesUtil.java    
 *    
 * 版本信息：    
 * 日期：2018年7月25日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 类名称：PropertiesUtil <br>
 * 类描述: properties文件读取<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月25日 下午2:00:15 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月25日 下午2:00:15 <br>
 * 修改备注:
 * @version
 * @see
 */
public class PropertiesUtil {

	private static final String fileName = "common.properties";
	private static Properties props = new Properties();
	private static Logger log = Logger.getLogger(PropertiesUtil.class);

	static {
		try {
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					"props/" + fileName);
			if (in != null) {
				props.load(in);
				in.close();
			}
		} catch (IOException e) {
			log.error(e);
		}
	}

	/**
	 * getPropertie 从properties文件获取属性
	 * @param key
	 * @return String
	 * @Exception 异常描述
	 */
	public static final String getPropertie(String key) {
		return props.getProperty(key);
	}
}
