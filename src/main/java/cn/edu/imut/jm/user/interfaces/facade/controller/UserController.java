package cn.edu.imut.jm.user.interfaces.facade.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	private static final String USER_IMG_FILE_PATH = "E:/img/";

	public UserLoginVo userLogin(@RequestBody String userLogin) {
		String userName = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userName");
		String userPwd = JSON.parseObject(userLogin).getJSONObject("userLogin").getString("userPwd");
		System.out.println(userName + userPwd);
		User loginUser = userService.userLogin(userName);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		if (loginUser != null) {
			boolean matches = passwordEncoder.matches(userPwd, loginUser.getUserPwd());
			if (!matches) {
				return new UserLoginVo(50000, "密码错误", null);
			}
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
	public ResponseVo<Role> selectRole(@RequestParam("userId") Integer userId) {

		List<Role> selectRoleByUserId = userService.selectRoleByUserId(userId);
		return new ResponseVo<>(selectRoleByUserId);
	}

	@Override
	public ResponseVo<Role> selectRoles() {

		return new ResponseVo<>(userService.selectRoles());
	}

	@Override
	public ResponseVo insertUser(@RequestBody String json) {
		System.out.println(json);
		User user = JSON.toJavaObject(JSON.parseObject(json).getJSONObject("user"), User.class);
		List<Integer> roleIds = JSON.parseArray(
				JSON.toJSONString(JSON.parseObject(json).getJSONObject("user").getJSONArray("roles")), Integer.class);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		Integer insertUser = userService.insertUser(user);
		if (insertUser == 1) {
			Integer insertUserRole = userService.insertUserRole(user.getUserId(), roleIds);
			return new ResponseVo<>(user.getUserId());
		}
		return new ResponseVo<>(0);
	}

	@Override
	public ResponseVo insertUserImg(@RequestParam("userId") Integer userId,
			@RequestParam("file") MultipartFile userImage) {
		if (userImage == null || userImage.isEmpty()) {
			return new ResponseVo<>(0, "文件为空");
		}
		String fileName = System.currentTimeMillis() + "-user-avatar"
				+ userImage.getOriginalFilename().substring(userImage.getOriginalFilename().lastIndexOf("."));
		String filePath = USER_IMG_FILE_PATH + fileName;
		File file = new File(filePath);
		if (!file.getParentFile().exists()) { // 判断文件父目录是否存在
			file.getParentFile().mkdir();
		}
		if (userImage.getOriginalFilename().endsWith(".jpg") || userImage.getOriginalFilename().endsWith(".jpeg")
				|| userImage.getOriginalFilename().endsWith(".png")) {
			try {
				userImage.transferTo(file);
				return new ResponseVo<>(userService.updateUserImg(userId, fileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return new ResponseVo(0, "上传失败，请重试");
			}
		} else {
			return new ResponseVo(0, "上传失败，只允许上传.jpg/.jpeg/.png图片");
		}
	}

	@Override
	public ResponseVo validatorUserName(@RequestParam("userName") String userName) {
		Integer validatorUserName = userService.validatorUserName(userName);
		if (validatorUserName != null && validatorUserName != 0) {
			return new ResponseVo<>(1);
		}
		return new ResponseVo<>(0);

	}
}
