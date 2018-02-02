package sicau.edu.cn.favorite.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import sicau.edu.cn.favorite.constant.JettyConstant;
import sicau.edu.cn.favorite.controller.enums.ReturnCodeTypeEnum;

import com.alibaba.fastjson.JSON;

/**
 * 类名称：ServiceUtil <br>
 * 类描述: 返回结果Map封装 <br>
 * 创建人：felicity <br>
 * 创建时间：2017年8月25日 下午2:25:03 <br>
 * 修改人：felicity <br>
 * 修改时间：2017年8月25日 下午2:25:03 <br>
 * 修改备注:
 * @version
 * @see
 */
public class ServiceUtil {

	/**
	 * setResponseVaule 返回
	 * @param response
	 * @param rt
	 * @throws IOException void
	 * @Exception 异常描述
	 */
	public static void setResponseVaule(HttpServletResponse response, Map<String, Object> rt)
			throws IOException {
		// 设置返回类型
		response.setContentType(JettyConstant.APPLICATION_JSON_VALUE);
		// 设置相应值
		response.getWriter().write(JSON.toJSONString(rt));
		// 设置字符编码
		response.setCharacterEncoding("UTF-8");
		// 设置响应状态
		response.setStatus(200);
	}

	/** 返回错误信息 */
	public static Map<String, Object> returnError(Integer code, String msg) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", code);
		result.put("msg", msg);
		return result;
	}

	/** 返回错误信息 */
	public static Map<String, Object> returnError(ReturnCodeTypeEnum returnCodeTypeEnum) {
		return returnError(returnCodeTypeEnum.getCode(), returnCodeTypeEnum.getMsg());
	}

	/** 返回成功 */
	public static Map<String, Object> returnSuccess() {
		return returnSuccess(null);
	}

	public static boolean isSuccess(Map<String, Object> resultMap) {
		boolean isSuccess = true;
		Integer code = (Integer) resultMap.get("code");
		if (ReturnCodeTypeEnum.OPERATE_SUCCESS.getCode() != code) {
			isSuccess = false;
		}
		return isSuccess;
	}

	/** 返回成功后的值 不进行加密 */
	public static Map<String, Object> returnSuccess(Object value) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ReturnCodeTypeEnum.OPERATE_SUCCESS.getCode());
		result.put("msg", ReturnCodeTypeEnum.OPERATE_SUCCESS.getMsg());
		if (value != null) {
			result.put("data", value);
		}
		return result;
	}

	public static Map<String, Object> returnSuccessSysTime(Object value) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", ReturnCodeTypeEnum.OPERATE_SUCCESS.getCode());
		result.put("msg", ReturnCodeTypeEnum.OPERATE_SUCCESS.getMsg());
		if (value != null) {
			result.put("data", value);
		}
		result.put("systemTime", System.currentTimeMillis());

		return result;
	}

	public static Object returnError(Integer code, String msg, Object value) {
		Map<String, Object> result = returnError(code, msg);
		if (value != null) {
			result.put("data", value);
		}
		return result;
	}

	/** 返回失败后的值 */
	public static Map<String, Object> returnError(ReturnCodeTypeEnum returnCodeTypeEnum,
			Object value) {
		Map<String, Object> result = returnError(returnCodeTypeEnum);
		if (value != null) {
			result.put("data", value);
		}
		return result;
	}

}
