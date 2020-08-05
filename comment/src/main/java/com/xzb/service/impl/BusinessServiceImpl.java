package com.xzb.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.xzb.constant.CategoryConst;
import com.xzb.bean.Business;
import com.xzb.bean.Page;
import com.xzb.dao.BusinessDao;
import com.xzb.dto.BusinessDto;
import com.xzb.dto.BusinessListDto;
import com.xzb.service.BusinessService;
import com.xzb.util.CommonUtil;
import com.xzb.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BusinessServiceImpl implements BusinessService {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private BusinessDao businessDao;

	@Value("${businessImage.savePath}")
	private String savePath;

	@Value("${businessImage.url}")
	private String url;

	@Override//通过id操作数据库内容
	public BusinessDto getById(Long id) {
		BusinessDto result = new BusinessDto();
		Business business = businessDao.selectById(id);
		BeanUtils.copyProperties(business, result);
		result.setImg(url + business.getImgFileName());
		
		result.setStar(this.getStar(business));
		
		return result;
	}

	@Override//初始化或搜索商户列表页面数据
	public List<BusinessDto> searchByPage(BusinessDto businessDto) {
		List<BusinessDto> result = new ArrayList<>();
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);

		List<Business> list = businessDao.selectByPage(businessForSelect);
		for (Business business : list) {
			BusinessDto businessDtoTemp = new BusinessDto();
			result.add(businessDtoTemp);
			BeanUtils.copyProperties(business, businessDtoTemp);
			//设置Dto定义的Img路径和Star--
			businessDtoTemp.setImg(url + business.getImgFileName());
			businessDtoTemp.setStar(this.getStar(business));//调用本类函数getStar
		}
		return result;
	}


	@Override//前端app 数据返回
	public BusinessListDto searchByPageForApi(BusinessDto businessDto) {
		BusinessListDto result = new BusinessListDto();

		// 组织查询条件
		Business businessForSelect = new Business();
		BeanUtils.copyProperties(businessDto, businessForSelect);
		// 当关键字不为空时，把关键字的值分别设置到标题、副标题、描述中
		// TODO 改进做法：全文检索
		if (!CommonUtil.isEmpty(businessDto.getKeyword())) {
			businessForSelect.setTitle(businessDto.getKeyword());
			businessForSelect.setSubtitle(businessDto.getKeyword());
			businessForSelect.setDesc(businessDto.getKeyword());
		}
		// 当类别为全部(all)时，需要将类别清空，不作为过滤条件
		if (businessDto.getCategory() != null && CategoryConst.ALL.equals(businessDto.getCategory())) {
			businessForSelect.setCategory(null);
		}
		// 前端app页码从0开始计算，这里需要+1
		int currentPage = businessForSelect.getPage().getCurrentPage();
		businessForSelect.getPage().setCurrentPage(currentPage + 1);

		List<Business> list = businessDao.selectLikeByPage(businessForSelect);

		// 经过查询后根据page对象设置hasMore
		Page page = businessForSelect.getPage();
		result.setHasMore(page.getCurrentPage() < page.getTotalPage());

		// 对查询出的结果进行格式化
		for (Business business : list) {
			BusinessDto businessDtoTemp = new BusinessDto();
			result.getData().add(businessDtoTemp);
			BeanUtils.copyProperties(business, businessDtoTemp);
			businessDtoTemp.setImg(url + business.getImgFileName());
			// 为兼容前端mumber这个属性
			businessDtoTemp.setMumber(business.getNumber());
			businessDtoTemp.setStar(this.getStar(business));
		}

		return result;
	}

	@Override
	public boolean add(BusinessDto businessDto) {
		Business business = new Business();
		BeanUtils.copyProperties(businessDto, business);
		
		if (businessDto.getImgFile() != null && businessDto.getImgFile().getSize() > 0) {
			try {
				String fileName = FileUtil.save(businessDto.getImgFile(), savePath);
				business.setImgFileName(fileName);
				// 默认已售数量为0
				business.setNumber(0);
				// 默认评论总次数为0
				business.setCommentTotalNum(0L);
				// 默认评论星星总数为0
				business.setStarTotalNum(0L);
				businessDao.insert(business);
				return true;
			} catch (IllegalStateException | IOException e) {
				// TODO 需要添加日志
				return false;
			}
		} else {
			return false;
		}
	}
	
	private int getStar(Business business) {
		//星数和评论数量不为空
		if(business.getStarTotalNum() != null && business.getCommentTotalNum() != null && business.getCommentTotalNum() != 0) {
			return (int)(business.getStarTotalNum() / business.getCommentTotalNum());
		} else {
			return 0;
		}
	}
}
