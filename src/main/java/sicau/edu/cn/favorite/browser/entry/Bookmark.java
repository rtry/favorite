/**    
 * 文件名：Bookmark.java    
 *    
 * 版本信息：    
 * 日期：2018年1月18日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.browser.entry;

/**
 * 类名称：Bookmark <br>
 * 类描述: 收藏夹书签对象<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月18日 下午4:03:42 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月18日 下午4:03:42 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Bookmark {

	private String id;

	private String name;

	private String url;

	private Long createDate;

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Bookmark() {

	}

	public Bookmark(String id, String name, String url, Long createDate) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.createDate = createDate;
	}

	public Bookmark(String name, String url, Long createDate) {
		this.name = name;
		this.url = url;
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "Bookmark [id=" + id + ", name=" + name + ", url=" + url + ", createDate="
				+ createDate + "]";
	}

}
