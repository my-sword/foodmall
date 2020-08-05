package com.xzb.dao;

import java.util.List;

import com.xzb.bean.Dic;

public interface DicDao {
    List<Dic> select(Dic dic);
}