/**    
 * 文件名：Page.java    
 *    
 * 版本信息：    
 * 日期：2018年1月26日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.lucene;

import java.util.List;

/**
 * 类名称：Page <br>
 * 类描述: 分页查询结果<br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月26日 下午4:44:25 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月26日 下午4:44:25 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Page<T> {

	private List<T> results;

	private boolean hasNext = false;

	// 总条数
	private int totalNums = 0;

	// 总页数
	private int totalPages = 0;

	// 当前页
	private int currentPage = 0;

	public int getTotalNums() {
		return totalNums;
	}

	public void setTotalNums(int totalNums) {
		this.totalNums = totalNums;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

}
