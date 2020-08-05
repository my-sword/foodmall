package com.xzb.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xzb.bean.Action;
import com.xzb.bean.Group;
import com.xzb.bean.GroupAction;
import com.xzb.bean.GroupMenu;
import com.xzb.bean.Menu;
import com.xzb.dao.GroupActionDao;
import com.xzb.dao.GroupDao;
import com.xzb.dao.GroupMenuDao;
import com.xzb.dto.ActionDto;
import com.xzb.dto.GroupDto;
import com.xzb.dto.MenuDto;
import com.xzb.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupServiceImpl implements GroupService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private GroupMenuDao groupMenuDao;
	@Autowired
	private GroupActionDao groupActionDao;
	
	@Override
	public List<GroupDto> getList() {
		List<GroupDto> result = new ArrayList<>();
		List<Group> groupList = groupDao.select(new Group());
		for (Group group : groupList) {
			GroupDto groupDto = new GroupDto();
			result.add(groupDto);
			BeanUtils.copyProperties(group, groupDto);
			groupDto.setpId(0);
		}
		return result;
	}

	@Override
	public boolean add(GroupDto groupDto) {
		Group group = new Group();
		BeanUtils.copyProperties(groupDto, group);
		return groupDao.insert(group) == 1;
	}

	@Override
	public GroupDto getById(Long id) {
		GroupDto groupDto = new GroupDto();
		Group group = groupDao.selectById(id);
		BeanUtils.copyProperties(group, groupDto);
		return groupDto;
	}

	@Override
	public boolean modify(GroupDto groupDto) {
		Group group = new Group();
		BeanUtils.copyProperties(groupDto, group);
		return groupDao.update(group) == 1;
	}

	@Override
	public boolean remove(Long id) {
		return groupDao.delete(id) == 1;
	}

	@Override//验证成功时将用户对应的menu和action加入session
	public GroupDto getByIdWithMenuAction(Long id) {
		//GroupDto:pId menuIdList actionIdList menuDtoList actionDtoList
		GroupDto result = new GroupDto();
		List<MenuDto> menuDtoList = new ArrayList<>();
		List<ActionDto> actionDtoList = new ArrayList<>();//id name url menuid method
		//将两个集合对象再装进GroupDto对象中
		result.setMenuDtoList(menuDtoList);
		result.setActionDtoList(actionDtoList);
		//执行事务：数据库匹配查询
		Group group = groupDao.selectMenuListById(id);//id==userDto.getGroupId()
		//返回的是和登录用户匹配的数据 一张以sys_groud的id为准的合并表   将其中的元素分别赋给group（id name listmenu listaction）

		if(group != null) {
			BeanUtils.copyProperties(group, result);//向result添加group存在的属性
			//对List<Menu>重组为List<MenuDto> 一个menu一条数据
			for(Menu menu : group.getMenuList()) {
				MenuDto menuDto = new MenuDto();
				menuDtoList.add(menuDto);
				BeanUtils.copyProperties(menu, menuDto);
				//log.debug(String.valueOf(menu));
			}
			
			for(Action action : group.getActionList()) {
				ActionDto actionDto = new ActionDto();
				actionDtoList.add(actionDto);
				BeanUtils.copyProperties(action, actionDto);
			}
		}
		return result;
	}

	@Override
	@Transactional
	public boolean assignMenu(GroupDto groupDto) {
		groupMenuDao.deleteByGroupId(groupDto.getId());
		groupActionDao.deleteByGroupId(groupDto.getId());
		// 保存为用户组分配的菜单
		if(groupDto.getMenuIdList() != null && groupDto.getMenuIdList().size() > 0) {
			List<GroupMenu> list = new ArrayList<>();
			for(Long menuId : groupDto.getMenuIdList()) {
				if(menuId != null) {
					GroupMenu groupMenu = new GroupMenu();
					groupMenu.setGroupId(groupDto.getId());
					groupMenu.setMenuId(menuId);
					list.add(groupMenu);
				}
			}
			groupMenuDao.insertBatch(list);
		}
		// 保存为用户组分配的动作
		if(groupDto.getActionIdList() != null && groupDto.getActionIdList().size() > 0) {
			List<GroupAction> list = new ArrayList<>();
			for(Long actionId : groupDto.getActionIdList()) {
				if(actionId != null) {
					GroupAction groupAction = new GroupAction();
					groupAction.setGroupId(groupDto.getId());
					groupAction.setActionId(actionId);
					list.add(groupAction);
				}
			}
			groupActionDao.insertBatch(list);
		}
		return true;
	}

}