/**    
 * 文件名：BlogType.java    
 *    
 * 版本信息：    
 * 日期：2018年7月2日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.html.wash;

/**
 * 类名称：BlogType <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月2日 上午11:33:09 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月2日 上午11:33:09 <br>
 * 修改备注:
 * @version
 * @see
 */
public enum BlogType {

	bscn("blog.sina.com.cn"),

	cnblogs("cnblogs.com");

	private String domainName;

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	private BlogType(String domainName) {
		this.domainName = domainName;
	}

	public static BlogType getByDomainName(String name) {
		BlogType[] types = BlogType.values();
		for (BlogType type : types) {
			if (type.getDomainName().equalsIgnoreCase(name)) {
				return type;
			}
		}
		return null;
	}
}
