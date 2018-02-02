package sicau.edu.cn.favorite.controller.enums;

/**
 * 类名称：ReturnCodeTypeEnum <br>
 * 类描述: 请求响应类型,除200，404，500外，其他业务异常，均从1000开始，为了兼容HttpServletResponse的code <br>
 * 创建人：felicity <br>
 * 创建时间：2017年8月25日 下午2:23:01 <br>
 * 修改人：felicity <br>
 * 修改时间：2017年8月25日 下午2:23:01 <br>
 * 修改备注:
 * @version
 * @see
 */
public enum ReturnCodeTypeEnum {

	/** 执行成功 */
	OPERATE_SUCCESS(200, "执行成功"),

	/** 兼容404 */
	OPERATE_NOT_FOUND(404, "未定位到资源"),

	/** 兼容500 */
	OPERATE_INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

	/** 参数错误 */
	PARA_ERROR(1400, "参数错误"),

	/** 网络超时 */
	SYSTEM_ERROR(1500, "网络超时"),

	/** 当前版本过低 */
	TERMINAL_VERSION_LOW_OVER(1223, "当前版本过低，升级到最新版本即可使用"),

	/** 用户不存在 */
	USER_NOT_EXISTS(1101, "用户不存在"),

	/** 此账号已经在其他设备上登录 */
	LOGIN_OTHER_TERMINAL(1125, "此账号已经在其他设备上登录"),

	/** 用户名或密码错误 */
	LOGINNAME_OR_PASSWORD_IS_NULL(1104, "用户名或密码错误"),

	/** 登录失败 */
	LOGIN_ERROR(1105, "登录失败"),

	/** 该账号已经被禁言 */
	LOGIN_GAG_ERROR(1106, "该账号已经被禁言"),

	/** 该账号已经被禁止登录 */
	LOGIN_BAN_ERROR(1107, "该账号已经被禁止登录"),

	/** 登录已过期 */
	LOGIN_NULL_ERROR(1108, "登录已过期"),

	/** 操作失败 */
	OPERATE_ERROR_FAIL(1113, "操作失败"),

	/** 该手机号已被注册 */
	REGISTER_PHONE_FAIL(1114, "该手机号已被注册"),

	/** 该手机号还未注册 */
	PHONE_NOT_REGISTER(1115, "该手机号还未注册"),

	/** 该手机号验证码已经发送 */
	PHONE_CODE_HAS_BEEN_SENT(1116, "验证码已发送"),

	/** 验证码未发送 */
	PHONE_CODE_NOT_SENT(1117, "验证码未发送"),

	/** 验证码错误 */
	PHONE_CODE_ERROR(1118, "验证码错误"),

	/** 非法操作 */
	ILLEGAL_OPERATE(1120, "非法操作"),

	/** 请求超时 */
	REQUEST_TIME_OUT(1122, "请求超时");

	private Integer code;

	private String msg;

	ReturnCodeTypeEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static ReturnCodeTypeEnum getByCode(Integer code) {
		for (ReturnCodeTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
}
