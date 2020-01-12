package com.jancer.wj.controller;


import com.jancer.wj.dao.LawDao;
import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.pojo.Laws;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Max;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    LawsDao lawsDao;

    @CrossOrigin
    @GetMapping(value = "/api/show")
    @ResponseBody
    public List<Laws> Search() {
         return lawsDao.findAll();
    }
//
    @CrossOrigin
    @PostMapping(value = "/api/showLaw")
    @ResponseBody
    public List<Laws> ShowSearch() {
        return lawsDao.findAll();
    }


}
