package com.xzb.service.impl;

import java.util.List;
import javax.annotation.Resource;
import com.xzb.bean.Dic;
import com.xzb.dao.DicDao;
import com.xzb.service.DicService;
import org.springframework.stereotype.Service;

@Service
public class DicServiceImpl implements DicService {
    
    @Resource
    private DicDao dicDao;
    
    @Override
    public List<Dic> getListByType(String type) {
        //创建字典类
        Dic dic = new Dic(); //type code name weight <!-- weight权重 -->
        dic.setType(type);   //type=="httpMethod"
        return dicDao.select(dic);
        //name      type
        //POST      1
        //DELETE    2
        //PUT       3
        //GET       4
    }
}
