package com.xzb.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.xzb.bean.Ad;
import com.xzb.dao.AdDao;
import com.xzb.dto.AdDto;
import com.xzb.service.AdService;
import com.xzb.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdServiceImpl implements AdService {

	@Autowired
	private AdDao adDao;

	//保存的指定路径
	@Value("${adImage.savePath}")
	private String adImageSavePath;

	@Value("${adImage.url}")
	private String adImageUrl;

	@Override
	// TODO 可以改成获取失败详细原因
	public boolean add(AdDto adDto) {
		Ad ad = new Ad();
		ad.setTitle(adDto.getTitle());
		ad.setLink(adDto.getLink());
		ad.setWeight(adDto.getWeight());
		if (adDto.getImgFile() != null && adDto.getImgFile().getSize() > 0) {
			String fileName = System.currentTimeMillis() + "_" + adDto.getImgFile().getOriginalFilename();
			File file = new File(adImageSavePath + fileName);
			File fileFolder = new File(adImageSavePath);
			if (!fileFolder.exists()) {
				fileFolder.mkdirs();
			}
			try {
				adDto.getImgFile().transferTo(file);
				ad.setImgFileName(fileName);
				adDao.insert(ad);
				return true;
			} catch (IllegalStateException | IOException e) {
				// TODO 需要添加日志
				return false;
			}
		} else {
			return false;
		}
	}

	//查询广告功能
	public List<AdDto> searchByPage(AdDto adDto) {
		List<AdDto> result = new ArrayList<AdDto>();
		Ad condition = new Ad();
		BeanUtils.copyProperties(adDto, condition);
		//转换：数据库是根据bean原类数据配置的
		//再转换：前端传输需要封装的额外的信息，比如图片等不便在数据库中保存，只保存路径即可 而传输需要文件名、数据等
		List<Ad> adList = adDao.selectByPage(condition);
		for (Ad ad : adList) {
			AdDto adDtoTemp = new AdDto();
			result.add(adDtoTemp);
			adDtoTemp.setImg(adImageUrl + ad.getImgFileName());
			BeanUtils.copyProperties(ad, adDtoTemp);
		}
		return result;
	}

	@Override//根据id查某条数据
	public AdDto getById(Long id) {
		AdDto result = new AdDto();
		Ad ad = adDao.selectById(id);
		BeanUtils.copyProperties(ad, result);
		result.setImg(adImageUrl + ad.getImgFileName());
		return result;
	}

	@Override//修改数据库和传入文件
	public boolean modify(AdDto adDto) {
		Ad ad = new Ad();
		BeanUtils.copyProperties(adDto, ad);
		String fileName = null;
		//将上传的图片文件保存在指定文件下
		if (adDto.getImgFile() != null && adDto.getImgFile().getSize() > 0) {
			try {
				fileName = FileUtil.save(adDto.getImgFile(), adImageSavePath);
				ad.setImgFileName(fileName);
			} catch (IllegalStateException | IOException e) {
				// TODO 需要添加日志
				return false;
			}
		}
		//修改数据库
		int updateCount = adDao.update(ad);
		if (updateCount != 1) {
			return false;
		}
		//保存文件操作成功则 删除原文件
		if (fileName != null) {
			return FileUtil.delete(adImageSavePath + adDto.getImgFileName());
		}
		return true;
	}
	
	@Override//删除文件
	public boolean remove(Long id) {
		Ad ad = adDao.selectById(id);
		int deleteRows = adDao.delete(id);
		FileUtil.delete(adImageSavePath + ad.getImgFileName());
		return deleteRows == 1;
	}
}
