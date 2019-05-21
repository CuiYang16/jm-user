package cn.edu.imut.jm.user.domain.user.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	@Transactional(rollbackFor = Exception.class)
	public Integer updateUserLastTime(Integer userId, Date lastLoginTime) {
		if (userId != null && userId != 0 && lastLoginTime != null) {
			return userDao.updateUserLastTime(userId, lastLoginTime);
		}
		return null;
	}

	@Override
	public PageInfo<User> selectUsers(Integer pageNum, Integer pageSize, boolean isDel) {

		PageHelper.startPage(pageNum, pageSize);
		List<User> selectUsers = userDao.selectUsers(isDel == true ? 1 : 0);
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
	@Transactional(rollbackFor = Exception.class)
	public Integer insertUser(User user) {
		if (user != null) {
			return userDao.insertUser(user);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateUserImg(Integer userId, String userHeadPortrait) {
		if (userId != null && userId != 0 && userHeadPortrait != null && userHeadPortrait.length() > 0) {
			return userDao.updateUserImg(userId, userHeadPortrait);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
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

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer resetUserPwd(Integer userId, String userPwd) {
		if (userId != null && userId != 0 && userPwd != null && userPwd.length() > 0) {
			return userDao.resetUserPwd(userId, userPwd);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateUser(User user) {
		if (user != null) {
			return userDao.updateUser(user);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer deleteByUserId(Integer userId) {
		if (userId != null && userId != 0) {
			return userDao.deleteByUserId(userId);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateUserDel(Integer userId) {
		if (userId != null && userId != 0) {
			return userDao.updateUserDel(userId);
		}
		return null;
	}

	@Override
	public Integer deleteUser(Integer userId) {
		Integer deleteUser = null;
		if (userId != null && userId != 0) {
			deleteUser = userDao.deleteUser(userId);
		}
		return deleteUser;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updateMultipleUserDel(List<Integer> delIds) {
		if (delIds != null && delIds.size() > 0) {
			return userDao.updateMultipleUserDel(delIds);
		}
		return null;
	}

	@Override
	public Integer deleteMultipleUser(List<Integer> delIds) {
		Integer deleteMultipleUser = null;
		if (delIds != null && delIds.size() > 0) {
			deleteMultipleUser = userDao.deleteMultipleUser(delIds);
		}
		return deleteMultipleUser;

	}

	@Override
	public List<User> selectUserByIds(List<Integer> userIds) {
		if (userIds != null && userIds.size() > 0) {
			return userDao.selectUserByIds(userIds);
		}
		return null;
	}

	@Override
	public User doorUserLogin(String userName) {
		if (userName != null && userName.length() > 0) {
			return userDao.doorUserLogin(userName);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer updatePwdByUserName(String userName, String userPwd) {
		if (userName != null && userName.length() >= 4 && userPwd != null && userPwd.length() > 0) {
			return userDao.updatePwdByUserName(userName, userPwd);
		}
		return null;
	}

}
