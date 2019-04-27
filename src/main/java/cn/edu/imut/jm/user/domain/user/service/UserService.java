package cn.edu.imut.jm.user.domain.user.service;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;

public interface UserService {

	User selectUserById(Integer userId);

	PageInfo<User> selectUsers(Integer pageNum, Integer pageSize);

	User userLogin(String userName, String userPwd);

	List<Role> selectRoleByUserId(Integer userId);

	Integer updateUserLastTime(Integer userId, Date lastLoginTime);
}
