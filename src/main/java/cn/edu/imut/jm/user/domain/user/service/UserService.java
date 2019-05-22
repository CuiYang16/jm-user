package cn.edu.imut.jm.user.domain.user.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;

public interface UserService {

	User selectUserById(Integer userId);

	List<User> selectUserByIds(List<Integer> userIds);

	PageInfo<User> selectUsers(Integer pageNum, Integer pageSize, boolean isDel);

	User userLogin(String userName);

	Integer validatorUserName(String userName);

	List<Role> selectRoleByUserId(Integer userId);

	List<Role> selectRoles();

	Integer updateUserLastTime(Integer userId, Date lastLoginTime);

	Integer insertUser(User user);

	Integer insertUserRole(Integer userId, List<Integer> roleIds);

	Integer updateUserImg(Integer userId, String userHeadPortrait);

	Integer resetUserPwd(Integer userId, String userPwd);

	Integer updateUser(User user);

	Integer deleteByUserId(Integer userId);

	Integer updateUserDel(Integer userId);

	Integer deleteUser(Integer userId);

	Integer updateMultipleUserDel(List<Integer> delIds);

	Integer deleteMultipleUser(List<Integer> delIds);

//	前端门户请求
	User doorUserLogin(String userName);

	Integer updatePwdByUserName(String userName, String userPwd);

	String userChart();

}
