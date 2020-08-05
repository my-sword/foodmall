package com.xzb.controller.content;

import javax.servlet.http.HttpServletRequest;

import com.xzb.constant.PageCodeEnum;
import com.xzb.dto.AdDto;
import com.xzb.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller//使用它标记的类就是一个SpringMvc Controller对象，分发处理器会扫描使用该注解的类的方法，并检测该方法是否使用了@RequestMapping注解。
@RequestMapping("/ad")//请求地址执行命令  RequestMapping是一个用来处理请求地址映射的注解，可用于类或方法上。用于类上，表示类中的所有响应请求的方法都是以该地址作为父路径。
public class AdController {

	@Autowired//自动装配bean(定义)
	private AdService adService;

	/**
	 * 广告管理页初始化(点广告管理菜单进入的第一个页面)
	 */
	//根据登录时绑定单击菜单事件请求地址为sys_menu的url进入此请求
	@RequestMapping
	public String init(Model model, HttpServletRequest request) {
		AdDto adDto = new AdDto();//接收前台的数据封装
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}


	/**
	 * 查询
	 */
	@RequestMapping("/search")
	public String search(Model model, AdDto adDto) {
		model.addAttribute("list", adService.searchByPage(adDto));
		model.addAttribute("searchParam", adDto);
		return "/content/adList";
	}

	/**
	 * 删除
	 */
	@RequestMapping("/remove")
	public String remove(@RequestParam("id") Long id,Model model) {//@RequestParam("id")重定义参数名称
		if(adService.remove(id)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.REMOVE_FAIL);
		}
		return "forward:/ad";
	}

	/**
	 * 新增页初始化
	 */
	@RequestMapping("/addInit")
	public String addInit() {
		return "/content/adAdd";
	}

	/**
	 * 新增
	 */
	@RequestMapping("/add")
	public String add(AdDto adDto, Model model) {
		if (adService.add(adDto)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
		}
		return "/content/adAdd";
	}

	/**
	 * 修改页初始化
	 */
	@RequestMapping("/modifyInit")
	public String modifyInit(Model model, @RequestParam("id") Long id) {//只接收id
		model.addAttribute("modifyObj", adService.getById(id));
		return "/content/adModify";
	}

	/**
	 * 修改
	 */
	@RequestMapping("/modify")
	public String modify(Model model, AdDto adDto) {
		model.addAttribute("modifyObj", adDto);
		//记录执行是否成功。页面载入的时${pageCode.msg}调用
		if (adService.modify(adDto)) {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_SUCCESS);
		} else {
			model.addAttribute(PageCodeEnum.KEY, PageCodeEnum.MODIFY_FAIL);
		}
		return "/content/adModify";
	}
}
