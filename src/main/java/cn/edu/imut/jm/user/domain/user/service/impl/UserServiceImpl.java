package cn.edu.imut.jm.user.domain.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.edu.imut.jm.user.domain.user.dao.UserDao;
import cn.edu.imut.jm.user.domain.user.entity.Role;
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
	public User userLogin(String userName) {
		if (userName != null && userName.length() > 0) {
			return userDao.userLogin(userName);
		}
		return null;
	}

	@Override
	public List<Role> selectRoleByUserId(Integer userId) {
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

	@Override
	public PageInfo<User> selectUsers(Integer pageNum, Integer pageSize) {

		PageHelper.startPage(pageNum, pageSize);
		List<User> selectUsers = userDao.selectUsers();
		PageInfo<User> pageInfo = new PageInfo<User>(selectUsers);
		pageInfo.setPageNum(pageNum);
		pageInfo.setPageSize(pageSize);
		return pageInfo;
	}

	@Override
	public List<Role> selectRoles() {

		return userDao.selectRoles();
	}

	@Override
	public Integer insertUser(User user) {
		if (user != null) {
			return userDao.insertUser(user);
		}
		return null;
	}

	@Override
	public Integer updateUserImg(Integer userId, String userHeadPortrait) {
		if (userId != null && userId != 0 && userHeadPortrait != null && userHeadPortrait.length() > 0) {
			return userDao.updateUserImg(userId, userHeadPortrait);
		}
		return null;
	}

	@Override
	public Integer insertUserRole(Integer userId, List<Integer> roleIds) {
		if (userId != null && userId != 0 && roleIds.size() > 0) {
			return userDao.insertUserRole(userId, roleIds);
		}
		return null;
	}

	@Override
	public Integer validatorUserName(String userName) {
		if (userName != null && userName.length() > 0) {
			return userDao.validatorUserName(userName);
		}
		return null;
	}

}
