/**    
 * 文件名：JettyConstant.java    
 *    
 * 版本信息：    
 * 日期：2018年1月4日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite;

/**
 * 类名称：JettyConstant <br>
 * 类描述: 启动常量<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月4日 下午3:06:49 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月4日 下午3:06:49 <br>
 * 修改备注:
 * @version
 * @see
 */
public class JettyConstant {

	public static final int port = 8686;
	public static final String localhost = "localhost";
	public static final String http = "http";
	public static final String urlseparator = "/";
	public static final String indexUrl = http.concat("://").concat(localhost).concat(":")
			.concat(port + "");

}
