package cn.edu.imut.jm.user.interfaces.facade.controller.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.imut.jm.user.domain.user.valobj.ResponseVo;
import cn.edu.imut.jm.user.domain.user.valobj.UserLoginVo;

public interface UserServiceRemoteApi {

	UserLoginVo userLogin(@RequestBody String userLogin);

	ResponseVo<String> selectRoleByUserId(@RequestParam("token") String token);

	ResponseVo updateUserLastTime(@RequestBody String token);
}
