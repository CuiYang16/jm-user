package cn.edu.imut.jm.user.domain.user.entity;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class User {
	/**
	 * 
	 */
	private Integer userId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String userPwd;

	/**
	 * 用户电话
	 */
	private String userPhone;

	/**
	 * 用户邮箱
	 */
	private String userEmail;

	/**
	 * 性别
	 */
	private Boolean userSex;

	/**
	 * 用户头像
	 */
	private String userHeadPortrait;

	/**
	 * 最后一次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 是否删除
	 */
	private Boolean isDelete;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd == null ? null : userPwd.trim();
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone == null ? null : userPhone.trim();
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail == null ? null : userEmail.trim();
	}

	public Boolean getUserSex() {
		return userSex;
	}

	public void setUserSex(Boolean userSex) {
		this.userSex = userSex;
	}

	public String getUserHeadPortrait() {
		return userHeadPortrait;
	}

	public void setUserHeadPortrait(String userHeadPortrait) {
		this.userHeadPortrait = userHeadPortrait == null ? null : userHeadPortrait.trim();
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public User() {
		super();
	}

	public User(Integer userId, String userName, String userPwd, String userPhone, String userEmail, Boolean userSex,
			String userHeadPortrait, Date lastLoginTime, Boolean isDelete) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPwd = userPwd;
		this.userPhone = userPhone;
		this.userEmail = userEmail;
		this.userSex = userSex;
		this.userHeadPortrait = userHeadPortrait;
		this.lastLoginTime = lastLoginTime;
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}