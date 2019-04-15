package cn.edu.imut.jm.user.domain.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.edu.imut.jm.user.domain.user.entity.User;

@Mapper
public interface UserDao {

	User selectUserById(Integer userId);

	User userLogin(@Param("userName") String userName, @Param("userPwd") String userPwd);
}
