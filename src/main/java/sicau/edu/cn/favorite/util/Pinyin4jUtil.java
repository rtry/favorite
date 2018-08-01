/**    
 * 文件名：Pinyin4jUtil.java    
 *    
 * 版本信息：    
 * 日期：2018年7月16日    
 * Copyright Felicity Corporation 2018 版权所有   
 */
package sicau.edu.cn.favorite.util;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 类名称：Pinyin4jUtil <br>
 * 类描述: 拼音类工具 <br>
 * 创建人：felicity <br>
 * 创建时间：2018年7月16日 下午4:22:07 <br>
 * 修改人：felicity <br>
 * 修改时间：2018年7月16日 下午4:22:07 <br>
 * 修改备注:
 * @version
 * @see
 */
public class Pinyin4jUtil {

	/** 无声调，小写拼音 */
	public static HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();

	static {

		// 默认格式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

	}

	/**
	 * converterToSpell 汉语转换全拼音，忽略非汉字，忽略声调
	 * @param str
	 * @return String
	 * @Exception 异常描述
	 */
	public String converterToSpell(String str, HanyuPinyinOutputFormat... formats) {
		HanyuPinyinOutputFormat frm = defaultFormat;
		if (formats != null && formats.length > 0)
			frm = formats[0];

		StringBuffer sb = new StringBuffer();
		char[] chars = str.toCharArray();
		try {

			// 无法处理多音字的情况
			// rt = PinyinHelper.toHanYuPinyinString(str, frm, "", false);

			for (char c : chars) {
				if (c > 128) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(c, frm);
					for (String t : temp)
						sb.append(t).append(",");
					sb.append(" ");
				}

			}

		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return parseTheChineseByObject(discountTheChinese(sb.toString()));
	}

	/**
	 * converterToFirst 汉语获取首字母，忽略非汉字，忽略声调
	 * @param str
	 * @return String
	 * @Exception 异常描述
	 */
	public String converterToFirst(String str) {
		return this.converterToFirst(str, true);
	}

	/**
	 * converterToFirst 汉语获取首字母，忽略声调
	 * @param str
	 * @param IgnoreEnglish true-忽略，false-不忽略
	 * @return String
	 * @Exception 异常描述
	 */
	public String converterToFirst(String str, boolean IgnoreEnglish) {
		char[] chars = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		try {

			for (char c : chars) {
				if (c > 128) { // 中文

					String[] s = PinyinHelper.toHanyuPinyinStringArray(c, defaultFormat);

					if (s != null && s.length > 0) {
						for (String temp : s)
							sb.append(temp.charAt(0)).append(",");
						sb.append(" ");
					}
				} else {
					if (!IgnoreEnglish)
						sb.append(c).append(", ");
				}
			}

		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return parseTheChineseByObject(discountTheChinese(sb.toString()));
	}

	/**
	 * 解析并组合拼音，对象合并方案(推荐使用)
	 * 
	 * @return
	 */
	private static String parseTheChineseByObject(List<Map<String, Integer>> list) {
		Map<String, Integer> first = null; // 用于统计每一次,集合组合数据
		// 遍历每一组集合
		for (int i = 0; i < list.size(); i++) {
			// 每一组集合与上一次组合的Map
			Map<String, Integer> temp = new Hashtable<String, Integer>();
			// 第一次循环，first为空
			if (first != null) {
				// 取出上次组合与此次集合的字符，并保存
				for (String s : first.keySet()) {
					for (String s1 : list.get(i).keySet()) {
						String str = s + s1;
						temp.put(str, 1);
					}
				}
				// 清理上一次组合数据
				if (temp != null && temp.size() > 0) {
					first.clear();
				}
			} else {
				for (String s : list.get(i).keySet()) {
					String str = s;
					temp.put(str, 1);
				}
			}
			// 保存组合数据以便下次循环使用
			if (temp != null && temp.size() > 0) {
				first = temp;
			}
		}
		String returnStr = "";
		if (first != null) {
			// 遍历取出组合字符串
			for (String str : first.keySet()) {
				returnStr += (str + ",");
			}
		}
		if (returnStr.length() > 0) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
	}

	private static List<Map<String, Integer>> discountTheChinese(String theStr) {
		// 去除重复拼音后的拼音列表
		List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
		// 用于处理每个字的多音字，去掉重复
		Map<String, Integer> onlyOne = null;
		String[] firsts = theStr.split(" ");
		// 读出每个汉字的拼音
		for (String str : firsts) {
			onlyOne = new Hashtable<String, Integer>();
			String[] china = str.split(",");
			// 多音字处理
			for (String s : china) {
				Integer count = onlyOne.get(s);
				if (count == null) {
					onlyOne.put(s, new Integer(1));
				} else {
					onlyOne.remove(s);
					count++;
					onlyOne.put(s, count);
				}
			}
			mapList.add(onlyOne);
		}
		return mapList;
	}

}
