package com.xzb.controller.system;

import java.util.List;

import com.xzb.constant.PageCodeEnum;
import com.xzb.dto.MenuDto;
import com.xzb.dto.MenuForMoveDto;
import com.xzb.dto.MenuForZtreeDto;
import com.xzb.dto.PageCodeDto;
import com.xzb.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单相关
 */
@RestController
@RequestMapping("/menus")
public class MenusController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MenuService menuService;
	
	/**
	 * 获取菜单列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<MenuForZtreeDto> getList() {
		return menuService.getZtreeList();
	}


	/**
	 * 新增菜单
	 */
	@RequestMapping(method = RequestMethod.POST)
	public PageCodeDto add(MenuDto menuDto) {
		PageCodeDto result;
		if(menuService.add(menuDto)) {
			result = new PageCodeDto(PageCodeEnum.ADD_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.ADD_FAIL);
		}
		return result;
	}
	
	/**
	 * 菜单排序
	 */
	@RequestMapping(value="/{dropNodeId}/{targetNodeId}/{moveType}",method = RequestMethod.PUT)
	public PageCodeDto order(MenuForMoveDto menuForMoveDto) {
		PageCodeDto result;
		if(menuService.order(menuForMoveDto)) {
			result = new PageCodeDto(PageCodeEnum.ORDER_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.ORDER_FAIL);
		}
		return result;
	}
	
	/**
	 * 根据主键获取菜单dto
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.GET)
	public MenuDto getById(@PathVariable("id") Long id) {
		//
		//log.debug("\n权限菜单....");
		return menuService.getById(id);
	}
	
	/**
	 * 修改菜单
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.PUT)
	public PageCodeDto modify(MenuDto menuDto) {
		PageCodeDto result;
		if(menuService.modify(menuDto)) {
			result = new PageCodeDto(PageCodeEnum.MODIFY_SUCCESS);
		} else {
			result = new PageCodeDto(PageCodeEnum.MODIFY_FAIL);
		}
		return result;
	}
	
	/**
	 * 删除菜单
	 */
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)
	public PageCodeDto remove(@PathVariable("id")Long id) {
		PageCodeDto result;
		MenuDto menuDto = new MenuDto();
		menuDto.setParentId(id);
		List<MenuDto> list = menuService.getList(menuDto);
		log.debug("\n失败remove");

		if(list.size() > 0) {
			result = new PageCodeDto(PageCodeEnum.SUB_MENU_EXISTS);
		} else {
			if(menuService.remove(id)) {
				result = new PageCodeDto(PageCodeEnum.REMOVE_SUCCESS);
			} else {
				result = new PageCodeDto(PageCodeEnum.REMOVE_FAIL);
			}
		}
		return result;
	}
}