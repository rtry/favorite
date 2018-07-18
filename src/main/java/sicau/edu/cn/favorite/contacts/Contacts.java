/**    
 * 文件名：Contacts.java    
 *    
 * 版本信息：    
 * 日期：2018年7月18日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.contacts;

/**
 * 类名称：Contacts <br>
 * 类描述: 联系人<br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月18日 下午4:52:13 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月18日 下午4:52:13 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Contacts {

	private String name;
	private Integer sex;
	private String road;
	private String tel;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Contacts [name=" + name + ", sex=" + sex + ", road=" + road + ", tel=" + tel
				+ ", email=" + email + "]";
	}

}
