package com.xzb.controller.system;

import java.util.List;

import com.xzb.constant.PageCodeEnum;
import com.xzb.dto.PageCodeDto;
import com.xzb.dto.UserDto;
import com.xzb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 */
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UserService userService;

	/**
	 * 获取用户列表
	 */
	@RequestMapping(method = RequestMethod.GET)//若没有设置ajax{type : "POST"}则默认是GET方式
	public List<UserDto> getList() {
		return userService.getList();
	}

	/**
	 * 新增用户
	 */
	@RequestMapping(method = RequestMethod.POST)//用户不能输入Url地址以get的形式获取数据，只能通过系统内部请求。
	public PageCodeDto add(UserDto userDto) {
		//如果添加成功则 成功字段信息  根据字段信息返回数据 前台通过data.msg调用
		PageCodeDto result;
		if(userService.add(userDto)) {
			result = new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.USERNAME_EXISTS);
		}
		return result;
	}
	
	/**
	 * 根据主键获取用户dto
	 * 如单击菜单的名字
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)//设置只能通过get请求方式执行，比如数据库action的url中对应的四种操作
	public UserDto getById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)//PUT、DELETE是POST衍生的一种方式需要在ajax中的data: {'_method':'DELETE'设置
	public PageCodeDto modify(UserDto userDto) {
		PageCodeDto result;
		if(userService.modify(userDto)) {
			result = new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.USERNAME_EXISTS);
		}
		return result;
	}


	/**
	 * 删除用户
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public PageCodeDto remove(@PathVariable("id")Long id) {//"_method" : "DELETE"
		PageCodeDto result;
		if(userService.remove(id)) {
			result = new PageCodeDto(PageCodeEnum.REMOVE_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.REMOVE_FAIL);
		}
		return result;
	}
}

