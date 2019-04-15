package cn.edu.imut.jm.user.domain.user.valobj;

import cn.edu.imut.jm.user.domain.user.entity.Role;

public class UserRoleVo {
	/**
	 * 用户角色关联
	 */
	private Integer userRoleId;

	/**
	 * 
	 */
	private Integer userId;

	/**
	 * 
	 */
	private Integer roleId;

	private Role role;

	public Integer getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserRoleVo(Integer userRoleId, Integer userId, Integer roleId, Role role) {
		super();
		this.userRoleId = userRoleId;
		this.userId = userId;
		this.roleId = roleId;
		this.role = role;
	}

	public UserRoleVo() {
		super();
	}

}
