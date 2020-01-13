package com.jancer.wj.controller;


import com.alibaba.fastjson.JSONObject;
import com.jancer.wj.dao.ChaptersDao;
import com.jancer.wj.dao.LawDao;
import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.dao.SectionsDao;
import com.jancer.wj.pojo.Laws;
import com.jancer.wj.pojo.Sections;
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

    @Autowired
    ChaptersDao chaptersDao;

    @Autowired
    SectionsDao sectionsDao;

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

    @CrossOrigin
    @PostMapping(value = "/api/searchSections")
    @ResponseBody
    public List<Sections> ShowSections(@RequestBody JSONObject id) {
        int chapter_id = id.getIntValue("chapter_id");
        System.out.println(id.getIntValue("chapter_id"));
        return sectionsDao.findAllByChapterId(chapter_id);
    }




}
