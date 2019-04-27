package cn.edu.imut.jm.user.domain.user.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;

@Mapper
public interface UserDao {

	User selectUserById(Integer userId);

	List<User> selectUsers();

	User userLogin(@Param("userName") String userName, @Param("userPwd") String userPwd);

	List<Role> selectRoleByUserId(Integer userId);

	Integer updateUserLastTime(@Param("userId") Integer userId, @Param("lastLoginTime") Date lastLoginTime);
}
