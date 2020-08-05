package com.xzb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.xzb.dao.ReportDao;
import com.xzb.dto.echarts.Option;
import com.xzb.dto.echarts.Serie;
import com.xzb.service.OrderReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderReportServiceImpl implements OrderReportService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ReportDao reportDao;
	
	@Override
	public Option count() {
		Option option = new Option();
		List<Map<String, String>> list = reportDao.countOrder();
		// 类别
		Set<String> categoryNameSet = new TreeSet<>();
		// 类别+时间为KEY，数量为VALUE
		Map<String,Long> countMap = new HashMap<String,Long>();
		for(Map<String, String> map : list) {
			categoryNameSet.add(map.get("categoryName"));
			countMap.put(map.get("categoryName") + map.get("hour"), Long.valueOf(map.get("count")));
		}
		// 设置参数中线条的分类
		option.getLegend().setData(new ArrayList<>(categoryNameSet));
		// 设置参数的X轴坐标
		List<String> hours = new ArrayList<String>();
		for(int i = 0; i <= 23; i++) {
			hours.add(String.format("%02d", i));
		}
		option.getxAxis().setData(hours);
		// 设置线条的名称和数值
		for(String categoryName : option.getLegend().getData()) {
			Serie serie = new Serie();
			option.getSeries().add(serie);
			serie.setName(categoryName);
			serie.setType("line");
			for(String hour : hours) {
				serie.getData().add(countMap.get(categoryName + hour) == null ? Long.valueOf(0) : countMap.get(categoryName + hour));
			}//List<Long> data
		}
		return option;
	}
}