package cn.edu.imut.jm.user.domain.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.imut.jm.user.domain.user.dao.UserDao;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User selectUserById(Integer userId) {
		if (userId != null && userId != 0) {
			return userDao.selectUserById(userId);
		}
		return null;
	}

	@Override
	public User userLogin(String userName, String userPwd) {
		if (userName != null && userName.length() > 0 && userPwd != null && userPwd.length() > 0) {
			return userDao.userLogin(userName, userPwd);
		}
		return null;
	}

	@Override
	public List<String> selectRoleByUserId(Integer userId) {
		if (userId != null && userId != 0) {
			return userDao.selectRoleByUserId(userId);
		}
		return null;
	}

	@Override
	public Integer updateUserLastTime(Integer userId, Date lastLoginTime) {
		if (userId != null && userId != 0 && lastLoginTime != null) {
			return userDao.updateUserLastTime(userId, lastLoginTime);
		}
		return null;
	}

}
