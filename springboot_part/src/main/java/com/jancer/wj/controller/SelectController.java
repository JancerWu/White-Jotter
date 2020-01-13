package com.jancer.wj.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jancer.wj.dao.ChaptersDao;
import com.jancer.wj.dao.LawDao;
import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.constraints.Max;
import java.util.List;

@Controller
public class SelectController {
    @Autowired
    ChaptersDao chaptersDao;

    @CrossOrigin
    @PostMapping(value = "/api/selectLaw")
    @ResponseBody
    public List<Chapters> showChapters(@RequestBody JSONObject lawId) {
        System.out.println(lawId);
        return chaptersDao.findAllByLawId(lawId.getIntValue("valueLaw"));
    }





}
