package cn.edu.imut.jm.user.interfaces.facade.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
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

/**
 * 
 * @ClassName: UserController.java
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Cy
 */
@RestController
public class UserController implements UserServiceRemoteApi {

	@Autowired
	private UserService userService;
	// private static final String USER_IMG_FILE_PATH =
	// "C:/Users/Administrator/Desktop/journal-door/static/avatar-img/";
	private static final String USER_IMG_FILE_PATH = "F:/MyWorkSpace/bishe-vue/journal-door/static/avatar-img/";
	private static final String USER_PWD = "abc_123456";
	private String avatarImgName = "";
	private String avatarUserImgName = "";

	/**
	 * @Title: userLogin
	 * @Description: 后台用户登录
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#userLogin(java.lang.String)
	 */
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

	/**
	 * @Title: selectRoleByUserId
	 * @Description: 根据用户token查询用户角色信息，返回角色数组
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#selectRoleByUserId(java.lang.String)
	 */
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

	/**
	 * @Title: selectUsers
	 * @Description: 条件分页查询用户信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#selectUsers(java.lang.Integer,
	 *           java.lang.Integer, boolean)
	 */
	@Override
	public ResponseVo<User> selectUsers(@RequestParam("pageNum") Integer pageNum,
			@RequestParam("pageSize") Integer pageSize, @RequestParam("isDel") boolean isDel) {

		return new ResponseVo<>(userService.selectUsers(pageNum, pageSize, isDel));
	}

	/**
	 * 
	 * @Title: selectRole
	 * @Description: 根据用户id查询查询角色信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#selectRole(java.lang.Integer)
	 */
	@Override
	public ResponseVo<Role> selectRole(@RequestParam("userId") Integer userId) {

		List<Role> selectRoleByUserId = userService.selectRoleByUserId(userId);
		return new ResponseVo<>(selectRoleByUserId);
	}

	/**
	 * @Title: selectRoles
	 * @Description: 查询所有角色信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#selectRoles()
	 */
	@Override
	public ResponseVo<Role> selectRoles() {

		return new ResponseVo<>(userService.selectRoles());
	}

	/**
	 * @Title: insertUser
	 * @Description: 新增用户
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#insertUser(java.lang.String)
	 */
	@Override
	public ResponseVo insertUser(@RequestBody String json) {
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

	/**
	 * @Title: insertUserImg
	 * @Description: 上传用户头像
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#insertUserImg(java.lang.Integer,
	 *           org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public ResponseVo insertUserImg(@RequestParam("userId") Integer userId,
			@RequestParam("file") MultipartFile userImage) {
		if (userImage == null || userImage.isEmpty()) {
			return new ResponseVo<>(0, "文件为空");
		}

		User selectUserById = userService.selectUserById(userId);
		if (selectUserById != null) {
			if (selectUserById.getUserHeadPortrait() != null && selectUserById.getUserHeadPortrait().length() > 0) {
				File delFile = new File(USER_IMG_FILE_PATH + selectUserById.getUserHeadPortrait());
				if (delFile.exists() && delFile.isFile()) {
					if (!delFile.delete()) {
						return new ResponseVo<>(0, "图片操作失败");
					}

				}
			}
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

	/**
	 * @Title: validatorUserName
	 * @Description: 验证用户名是否重复
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#validatorUserName(java.lang.String)
	 */
	@Override
	public ResponseVo validatorUserName(@RequestParam("userName") String userName) {
		Integer validatorUserName = userService.validatorUserName(userName);
		if (validatorUserName != null && validatorUserName != 0) {
			return new ResponseVo<>(1);
		}
		return new ResponseVo<>(0);

	}

	/**
	 * @Title: resetUserPwd
	 * @Description: 重置密码
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#resetUserPwd(java.lang.String)
	 */
	@Override
	public ResponseVo resetUserPwd(@RequestBody String json) {

		Integer userId = JSON.parseObject(json).getInteger("userId");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return new ResponseVo<>(userService.resetUserPwd(userId, passwordEncoder.encode(USER_PWD)));
	}

	/**
	 * @Title: updateUser
	 * @Description: 编辑用户信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updateUser(java.lang.String)
	 */
	@Override
	public ResponseVo updateUser(@RequestBody String json) {
		User user = JSON.toJavaObject(JSON.parseObject(json).getJSONObject("user"), User.class);
		List<Integer> roleIds = JSON.parseArray(
				JSON.toJSONString(JSON.parseObject(json).getJSONObject("user").getJSONArray("roles")), Integer.class);
		if (user.getUserPwd() != null && user.getUserPwd().length() > 7) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		}

		Integer updateUser = userService.updateUser(user);
		if (updateUser == 1) {
			Integer deleteByUserId = userService.deleteByUserId(user.getUserId());
			Integer insertUserRole = userService.insertUserRole(user.getUserId(), roleIds);
			return new ResponseVo<>(1);
		}

		return new ResponseVo<>(0);
	}

	/**
	 * @Title: updateUserDel
	 * @Description: 去激活用户
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updateUserDel(java.lang.String)
	 */
	@Override
	public ResponseVo updateUserDel(@RequestBody String json) {
		Integer userId = JSON.parseObject(json).getInteger("userId");
		return new ResponseVo<>(userService.updateUserDel(userId));
	}

	/**
	 * @Title: deleteUser
	 * @Description: 彻底删除用户信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#deleteUser(java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseVo deleteUser(@RequestBody String json) {
		Integer userId = JSON.parseObject(json).getInteger("userId");
		User user = userService.selectUserById(userId);

		try {
			Integer deleteUser = userService.deleteUser(userId);
			// 删除角色信息
			Integer deleteByUserId = userService.deleteByUserId(userId);
			if (user != null) {
				if (user.getUserHeadPortrait() != null && user.getUserHeadPortrait().length() > 0) {
					File delFile = new File(USER_IMG_FILE_PATH + user.getUserHeadPortrait());
					if (delFile.exists() && delFile.isFile()) {
						if (!delFile.delete()) {
							return new ResponseVo<>(0, "删除头像操作失败");
						}

					}
				}
			}
			return new ResponseVo<>(deleteUser);
		} catch (Exception e) {
			return new ResponseVo<>(-1);
		}

	}

	/**
	 * 
	 * @Title: updateMultipleUserDel
	 * @Description: 批量去激活用户
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updateMultipleUserDel(java.lang.String)
	 */
	@Override
	public ResponseVo updateMultipleUserDel(@RequestBody String json) {
		List<Integer> delIds = JSON.parseArray(JSON.toJSONString(JSON.parseObject(json).getJSONArray("delIds")),
				Integer.class);
		return new ResponseVo<>(userService.updateMultipleUserDel(delIds));
	}

	/**
	 * 
	 * @Title: deleteMultipleUser
	 * @Description: 批量删除用户
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#deleteMultipleUser(java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseVo deleteMultipleUser(@RequestBody String json) {
		List<Integer> delIds = JSON.parseArray(JSON.toJSONString(JSON.parseObject(json).getJSONArray("delIds")),
				Integer.class);

		List<User> userByIds = userService.selectUserByIds(delIds);
		try {
			Integer deleteMultipleUser = userService.deleteMultipleUser(delIds);

			for (User user : userByIds) {
				userService.deleteByUserId(user.getUserId());
				File delFile = new File(USER_IMG_FILE_PATH + user.getUserHeadPortrait());
				if (delFile.exists() && delFile.isFile()) {
					delFile.delete();
				}
			}
			return new ResponseVo<>(deleteMultipleUser);
		} catch (Exception e) {
			return new ResponseVo<>(-1);
		}

	}

	/**
	 * 
	 * @Title: userChart
	 * @Description: 用户统计信息，用户总数和激活用户数
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#userChart()
	 */
	@Override
	public ResponseVo userChart() {

		return new ResponseVo<>(userService.userChart());
	}

	/**
	 * 
	 * @Title: doorUserLogin
	 * @Description: 门户网站登录
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#doorUserLogin(java.lang.String)
	 */
	@Override
	public UserLoginVo doorUserLogin(@RequestBody String userLogin) {
		String userName = JSON.parseObject(userLogin).getString("userName");
		String userPwd = JSON.parseObject(userLogin).getString("userPwd");
		User loginUser = userService.doorUserLogin(userName);
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

	/**
	 * 
	 * @Title: insertDoorUser
	 * @Description: 门户网站注册
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#insertDoorUser(java.lang.String)
	 */
	@Override
	public Integer insertDoorUser(@RequestBody String json) {
		User user = JSON.toJavaObject(JSON.parseObject(json).getJSONObject("user"), User.class);
		List<Integer> roleIds = new ArrayList<>();
		roleIds.add(3);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		user.setUserHeadPortrait(avatarImgName);
		Integer insertUser = userService.insertUser(user);

		if (insertUser == 1) {

			Integer insertUserRole = userService.insertUserRole(user.getUserId(), roleIds);
			avatarImgName = "";
			return user.getUserId();
		}
		return 0;
	}

	/**
	 * 
	 * @Title: insertDoorUserImg
	 * @Description: 门户网站头像上传
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#insertDoorUserImg(org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public ResponseVo insertDoorUserImg(@RequestParam("file") MultipartFile userImage) {
		if (userImage == null || userImage.isEmpty()) {
			return new ResponseVo<>(0);
		}
		File delFile = new File(USER_IMG_FILE_PATH + avatarImgName);
		if (delFile.exists() && delFile.isFile()) {
			if (!delFile.delete()) {
				return new ResponseVo<>(0);
			}

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
				avatarImgName = fileName;
				return new ResponseVo<>(1, fileName);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
				return new ResponseVo<>(0);
			}
		} else {
			return new ResponseVo<>(0);
		}
	}

	/**
	 * 
	 * @Title: deleteDoorUser
	 * @Description: 删除本地头像文件
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#deleteDoorUser(java.lang.String)
	 */
	@Override
	public Integer deleteDoorUser(@RequestBody String json) {
		String fileName = JSON.parseObject(json).getString("fileName");
		File delFile = new File(USER_IMG_FILE_PATH + fileName);
		if (delFile.exists() && delFile.isFile()) {
			if (!delFile.delete()) {
				return 0;
			} else {
				return 1;
			}

		}
		return 0;
	}

	/**
	 * 
	 * @Title: updatePwdByUserName
	 * @Description: 根据用户名修改密码
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updatePwdByUserName(java.lang.String)
	 */
	@Override
	public Integer updatePwdByUserName(@RequestBody String json) {
		String userName = JSON.parseObject(json).getString("userName");
		String userPwd = JSON.parseObject(json).getString("userPwd");
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return userService.updatePwdByUserName(userName, passwordEncoder.encode(userPwd));
	}

	/**
	 * 
	 * @Title: updateDoorUser
	 * @Description: 更新个人信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updateDoorUser(java.lang.String)
	 */
	@Override
	public Integer updateDoorUser(@RequestBody String json) {
		User user = JSON.toJavaObject(JSON.parseObject(json).getJSONObject("user"), User.class);
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		if (avatarImgName != null && avatarImgName.length() > 0) {
			user.setUserHeadPortrait(avatarImgName);
		}

		Integer updateUser = userService.updateUser(user);

		if (updateUser == 1) {
			avatarImgName = "";
			return 1;
		}
		return 0;
	}

	/**
	 * 
	 * @Title: selectUserById
	 * @Description: 查询个人信息
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#selectUserById(java.lang.String)
	 */
	@Override
	public User selectUserById(@RequestParam("token") String token) {
		Integer userId = JwtTokenUtil.getUserId(token);
		return userService.selectUserById(userId);
	}

	/**
	 * 
	 * @Title: updateDoorUserImg
	 * @Description: 更新头像
	 * @override @see
	 *           cn.edu.imut.jm.user.interfaces.facade.controller.api.UserServiceRemoteApi#updateDoorUserImg(org.springframework.web.multipart.MultipartFile)
	 */
	@Override
	public String updateDoorUserImg(@RequestParam("file") MultipartFile userImage) {
		String fileName = System.currentTimeMillis() + "-user-avatar"
				+ userImage.getOriginalFilename().substring(userImage.getOriginalFilename().lastIndexOf("."));
		this.avatarUserImgName = fileName;
		return fileName;
	}

	@Override
	public Integer deleteDoorUserImg(@RequestBody String json) {
		avatarImgName = "";
		return 1;
	}

}
