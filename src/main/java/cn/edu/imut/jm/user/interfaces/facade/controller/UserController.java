package cn.edu.imut.jm.user.interfaces.facade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.edu.imut.infrastructrue.util.JwtTokenUtil;
import cn.edu.imut.infrastructrue.util.LoginResponseUtil;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.service.UserService;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public UserLoginVo userLogin(@RequestBody String userLogin) {
		String userName = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userName");
		String userPwd = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userPwd");
		System.out.println(userName + userPwd);
		User loginUser = userService.userLogin(userName, userPwd);
		if (loginUser != null) {
			String token = JwtTokenUtil.generateToken(userName, loginUser.getUserId());
			if (token != null) {
				return LoginResponseUtil.getResponse(token);
			}
		}
		return new UserLoginVo(50001, "登录失败", null);
	}
}
