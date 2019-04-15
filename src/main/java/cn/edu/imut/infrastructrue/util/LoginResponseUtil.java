package cn.edu.imut.infrastructrue.util;

import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;

public class LoginResponseUtil {

	public static UserLoginVo getResponse(String token) {
		if (!JwtTokenUtil.parseToken(token)) {
			return new UserLoginVo(50008, "token不合法", null);
		}
		if (JwtTokenUtil.isExpiration(token)) {
			return new UserLoginVo(50014, "token已过期", null);
		}
		return new UserLoginVo(20000, "请求成功", token);
	}
}
