package cn.edu.imut.jm.user.interfaces.facade.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.edu.imut.infrastructrue.util.JwtTokenUtil;
import cn.edu.imut.infrastructrue.util.LoginResponseUtil;
import cn.edu.imut.infrastructrue.util.ResponseVo;
import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.service.UserService;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;
import cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi;

@RestController

public class UserController implements UserServiceRemoteApi {

	@Autowired
	private UserService userService;

	public UserLoginVo userLogin(@RequestBody String userLogin) {
		String userName = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userName");
		String userPwd = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userPwd");
		System.out.println(userName + userPwd);
		User loginUser = userService.userLogin(userName, userPwd);
		if (loginUser != null) {
			String token = JwtTokenUtil.generateToken(userName, loginUser.getUserId());
			if (token != null) {
				UserLoginVo userLoginVo = LoginResponseUtil.getResponse(token);
				userLoginVo.setAvatar(loginUser.getUserHeadPortrait());
				if (userLoginVo.getCode() == 20000) {
					userService.updateUserLastTime(loginUser.getUserId(), new Date());
				}
				return userLoginVo;
			}
		}
		return new UserLoginVo(50001, "登录失败", null);
	}

	@Override

	public ResponseVo<String> selectRoleByUserId(@RequestParam("token") String token) {
		if (!JwtTokenUtil.parseToken(token)) {
			return new ResponseVo<>(0);
		}
		Integer userId = JwtTokenUtil.getUserId(token);
		User user = userService.selectUserById(userId);
		List<Role> selectRoleByUserId = userService.selectRoleByUserId(userId);
		List<String> roles = new ArrayList<String>();
		for (Role role : selectRoleByUserId) {

			roles.add(role.getRoleName());
		}

		return new ResponseVo<String>(roles, user.getUserHeadPortrait(), user.getUserName());
	}

	@Override
	public ResponseVo<User> selectUsers(@RequestParam("pageNum") Integer pageNum,
			@RequestParam("pageSize") Integer pageSize) {

		return new ResponseVo<>(userService.selectUsers(pageNum, pageSize));
	}

	@Override
	public ResponseVo<Role> selectRole(@RequestParam("token") String token) {
		if (!JwtTokenUtil.parseToken(token)) {
			return new ResponseVo<>(0);
		}
		Integer userId = JwtTokenUtil.getUserId(token);
		List<Role> selectRoleByUserId = userService.selectRoleByUserId(userId);
		return new ResponseVo<>(selectRoleByUserId);
	}
}
