package cn.edu.imut.jm.user.interfaces.facade.controller.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.imut.infrastructrue.util.ResponseVo;
import cn.edu.imut.jm.user.domain.user.entity.Role;
import cn.edu.imut.jm.user.domain.user.entity.User;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;

@RequestMapping("/user")
public interface UserServiceRemoteApi {

	@RequestMapping(value = "/get-users", method = { RequestMethod.GET })
	ResponseVo<User> selectUsers(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize,
			@RequestParam("isDel") boolean isDel);

	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	UserLoginVo userLogin(@RequestBody String userLogin);

	@RequestMapping(value = "/validator-username", method = { RequestMethod.GET })
	ResponseVo validatorUserName(@RequestParam("userName") String userName);

	@RequestMapping(value = "/get-info", method = { RequestMethod.GET })
	ResponseVo<String> selectRoleByUserId(@RequestParam("token") String token);

	@RequestMapping(value = "/get-role", method = { RequestMethod.GET })
	ResponseVo<Role> selectRole(@RequestParam("userId") Integer userId);

	@RequestMapping(value = "/get-roles", method = { RequestMethod.GET })
	ResponseVo<Role> selectRoles();

	@RequestMapping(value = "/insert-user", method = { RequestMethod.POST })
	ResponseVo insertUser(@RequestBody String json);

	@RequestMapping(value = "/upload/user-img", method = { RequestMethod.POST, RequestMethod.GET })
	ResponseVo insertUserImg(@RequestParam("userId") Integer userId, @RequestParam("file") MultipartFile userImage);

	@RequestMapping(value = "/reset-pwd", method = { RequestMethod.PUT })
	ResponseVo resetUserPwd(@RequestBody String json);

	@RequestMapping(value = "/update-user", method = { RequestMethod.PUT })
	ResponseVo updateUser(@RequestBody String json);

	@RequestMapping(value = "/update-del", method = { RequestMethod.PUT })
	ResponseVo updateUserDel(@RequestBody String json);

	@RequestMapping(value = "/del-user", method = { RequestMethod.DELETE })
	ResponseVo deleteUser(@RequestBody String json);

	@RequestMapping(value = "/update-muldel", method = { RequestMethod.PUT })
	ResponseVo updateMultipleUserDel(@RequestBody String json);

	@RequestMapping(value = "/muldel-user", method = { RequestMethod.DELETE })
	ResponseVo deleteMultipleUser(@RequestBody String json);
}
