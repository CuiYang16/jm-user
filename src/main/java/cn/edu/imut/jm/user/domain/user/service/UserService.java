package cn.edu.imut.jm.user.domain.user.service;

import java.util.Date;
import java.util.List;

import cn.edu.imut.jm.user.domain.user.entity.User;

public interface UserService {

	User selectUserById(Integer userId);

	User userLogin(String userName, String userPwd);

	List<String> selectRoleByUserId(Integer userId);

	Integer updateUserLastTime(Integer userId, Date lastLoginTime);
}
