package cn.edu.imut.jm.user.interfaces.facade.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.edu.imut.infrastructrue.util.JwtTokenUtil;
import cn.edu.imut.infrastructrue.util.LoginResponseUtil;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.service.UserService;
import cn.edu.imut.jm.user.domain.user.valobj.ResponseVo;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;
import cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi;

@RestController
@RequestMapping("/user")
public class UserController implements UserServiceRemoteApi {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = { RequestMethod.POST, RequestMethod.GET })
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

	@Override
	@RequestMapping(value = "/get-info", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseVo<String> selectRoleByUserId(@RequestParam("token") String token) {
		Integer userId = JwtTokenUtil.getUserId(token);
		List<String> selectRoleByUserId = userService.selectRoleByUserId(userId);
		return new ResponseVo<String>(selectRoleByUserId);
	}

	@Override
	@RequestMapping(value = "/logout", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseVo updateUserLastTime(@RequestBody String token) {

		Integer userId = JwtTokenUtil.getUserId(JSON.parseObject(token).getString("token"));
		System.out.println(userId);
		return new ResponseVo<>(userService.updateUserLastTime(userId, new Date()));
	}
}
