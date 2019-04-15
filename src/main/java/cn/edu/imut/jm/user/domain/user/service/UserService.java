package cn.edu.imut.jm.user.domain.user.service;

import cn.edu.imut.jm.user.domain.user.entity.User;

public interface UserService {

	User selectUserById(Integer userId);

	User userLogin(String userName, String userPwd);
}
