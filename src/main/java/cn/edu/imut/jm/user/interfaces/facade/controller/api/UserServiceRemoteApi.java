package cn.edu.imut.jm.user.interfaces.facade.controller.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.imut.infrastructrue.util.ResponseVo;
import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;

@RequestMapping("/user")
public interface UserServiceRemoteApi {

	@RequestMapping(value = "/get-users", method = { RequestMethod.GET })
	ResponseVo<User> selectUsers(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	UserLoginVo userLogin(@RequestBody String userLogin);

	@RequestMapping(value = "/get-info", method = { RequestMethod.GET })
	ResponseVo<String> selectRoleByUserId(@RequestParam("token") String token);

	@RequestMapping(value = "/get-role", method = { RequestMethod.GET })
	ResponseVo<Role> selectRole(@RequestParam("token") String token);
}
