/**    
 * 文件名：FelicityTuser.java    
 *    
 * 版本信息：    
 * 日期：2018年1月24日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.es;

/**
 * 类名称：FelicityTuser <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年1月24日 下午5:05:59 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年1月24日 下午5:05:59 <br>
 * 修改备注:
 * @version
 * @see
 */
public class FelicityTuser extends SuperESEntry<Felicity> {

	public FelicityTuser(Felicity t) {
		super(t);
	}

	@Override
	public String getIndex() {
		return "felicity";
	}

	@Override
	public String getType() {
		return "tuser";
	}

}
