package sicau.edu.cn.favorite.controller.form;

/**
 * 类名称：SearchPageForm <br>
 * 类描述: 分页查询数据 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年2月2日 上午11:18:19 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年2月2日 上午11:18:19 <br>
 * 修改备注:
 * @version
 * @see
 */
public class SearchPageForm {

	private int size = 11;

	private int page = 0;

	private String query;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
