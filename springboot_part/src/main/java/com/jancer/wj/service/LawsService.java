package com.jancer.wj.service;

import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.pojo.Laws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LawsService {
    @Autowired
    LawsDao lawsDao;

    public boolean isExist(String  id) {
        Laws law = getById(id);
        return null!=law;
    }

    public Laws getById(String id) {
        return lawsDao.findById(id);
    }
    public void add(Laws law) {
        lawsDao.save(law);
    }
}
