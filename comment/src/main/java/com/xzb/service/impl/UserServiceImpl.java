package com.xzb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xzb.bean.User;
import com.xzb.dao.UserDao;
import com.xzb.dto.UserDto;
import com.xzb.service.UserService;
import com.xzb.util.CommonUtil;
import com.xzb.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;//在Resource.Mapper定义的接口映射(interface和mapper文件名相同) Mybatis的特性(通过jdk的类创建一个继承对象，简化了)
	//登录验证
	public boolean validate(UserDto userDto) {//UserDto继承User，CommonUtil为通用工具:判断是否空、随机数、UUId和session
		if (userDto != null && !CommonUtil.isEmpty(userDto.getName()) && !CommonUtil.isEmpty(userDto.getPassword())) {
			User user = new User();
			BeanUtils.copyProperties(userDto, user);//对象属性拷贝（源对象，目标）
			List<User> list = userDao.select(user);
			if(list.size() == 1) {//如果存在对应的账户密码
				BeanUtils.copyProperties(list.get(0),userDto);//将id等属性给userDto
				return true;
			}
			return false;
		}
		return false;
	}

	@Override//查询用户组
	public List<UserDto> getList() {
		List<UserDto> result = new ArrayList<>();
		List<User> userList = userDao.select(new User());
		log.debug("\n用户");

		//将user封装成userdto赋予 result
		for (User user : userList) {
			UserDto userDto = new UserDto();
			result.add(userDto);
			BeanUtils.copyProperties(user, userDto);
			userDto.setpId(0);
		}
		return result;
	}

	@Override//新增用户
	public boolean add(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		user.setPassword(MD5Util.getMD5(userDto.getPassword()));//将密码转MD5
		return userDao.insert(user) == 1;//如果添加数据成功(用户名、账号和密码一致)
	}

	@Override//通过id返回用户信息(bean必须是封装类：返回空等)
	public UserDto getById(Long id) {
		UserDto userDto = new UserDto();
		User user = userDao.selectById(id);
		BeanUtils.copyProperties(user, userDto);
		return userDto;
	}

	@Override//修改用户
	public boolean modify(UserDto userDto) {
		User user = new User();
		BeanUtils.copyProperties(userDto, user);
		if(!CommonUtil.isEmpty(userDto.getPassword())) {
			user.setPassword(MD5Util.getMD5(userDto.getPassword()));
		}
		return userDao.update(user) == 1;
	}

	@Override//删除用户
	public boolean remove(Long id) {
		return userDao.delete(id) == 1;
	}
}