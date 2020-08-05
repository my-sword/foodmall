package com.xzb.controller.content;

import javax.annotation.Resource;

import com.xzb.constant.DicTypeConst;
import com.xzb.constant.PageCodeEnum;
import com.xzb.dto.BusinessDto;
import com.xzb.service.BusinessService;
import com.xzb.service.DicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/businesses")
public class BusinessesController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private DicService dicService;

	@Resource
	private BusinessService businessService;

	/**
	 * 商户列表
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String search(Model model, BusinessDto dto) {
		model.addAttribute("list", businessService.searchByPage(dto));
		model.addAttribute("searchParam", dto);
		return "/content/businessList";
	}


	/**
	 * 商户添加页初始化--商户列表”添加“
	 */
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addInit(Model model) {
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));//将dic表中的city传入citylist
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		return "/content/businessAdd";
	}

	/**
	 * 商户添加--商户添加页”保存“
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String add(BusinessDto dto,RedirectAttributes attr) {//RedirectAttributes 用于重定向之后还能带参数跳转的的工具类 在这例传递成功与否
		if(businessService.add(dto)) {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_SUCCESS);
			return "redirect:/businesses";
		} else {
			attr.addAttribute(PageCodeEnum.KEY, PageCodeEnum.ADD_FAIL);
			return "redirect:/businesses/addPage";
		}
	}

	/**
	 * 商户修改页初始化--商户列表“修改”
	 *///根据js动态传入id请求
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String modifyInit(Model model, @PathVariable("id") Long id) {
		model.addAttribute("cityList", dicService.getListByType(DicTypeConst.CITY));
		model.addAttribute("categoryList", dicService.getListByType(DicTypeConst.CATEGORY));
		model.addAttribute("modifyObj", businessService.getById(id));
		return "/content/businessModify";
	}


	/**
	 * 商户修改--商户修改页”保存“
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String modify(@PathVariable("id") Long id, BusinessDto dto) {
		System.out.println(id);
		return "/content/businessModify";
	}
	/**
	 * 删除商户
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String remove(@PathVariable("id") Long id) {
		log.debug("删除用户");
		return "redirect:/businesses";
	}


}