/**    
 * 文件名：Girl.java    
 *    
 * 版本信息：    
 * 日期：2018年6月20日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.simple;

import java.util.ArrayList;
import java.util.List;

/**
 * 类名称：Girl <br>
 * 类描述: <br>
 * 创建人：felicity <br>
 * 创建时间：2018年6月20日 下午5:13:55 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年6月20日 下午5:13:55 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Girl {

	private String code;
	private String name;
	private String des;

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Girl(String code, String name, String des) {
		super();
		this.code = code;
		this.name = name;
		this.des = des;
	}

	public static List<Girl> simple() {
		List<Girl> gs = new ArrayList<Girl>();
		gs.add(new Girl("CD1124543423", "大乔", "大乔的神态恬静和胴体曼妙。整体仰视，背景小广角，特别的地方在于大乔挺胸展现腰部曲美向上的趋势和姿态"));
		gs.add(new Girl("CD1435423673", "妲己", "妲己也是这么一位红颜祸水，依靠自身强大的法术给予敌人伤害，攻击能力出众"));
		gs.add(new Girl("CD1678447473", "雅典娜",
				"雅典娜，圣斗士星矢的智慧与战争女神。每当世界被邪恶入侵时，就会降生于大地上与圣斗士们一起为了守护大地的爱与正义而战斗!"));
		gs.add(new Girl("CD5678986765", "花木兰", "女性角色最有魅力之一的地方就是胯部了，这张就是个代表。俏皮性感的动作，加上背景一头巨龙的强烈对比"));
		gs.add(new Girl("CD5635334346","abc", "131346s46a4da4s6d4w6re6ef4s6d4f6sd4f64d6fsd"));
		gs.add(new Girl("CD5635334346","111", "11111111111111111111111111111111111"));
		return gs;
	}
}
