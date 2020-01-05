package com.jancer.wj.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jancer.wj.result.Result;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LawsController {

    @CrossOrigin
    @PostMapping(value = "api/index")
    @ResponseBody
    public Result login(@RequestBody String map) {
        map.trim();
        String show = map.replaceAll("\\\\n","");
        System.out.println(show);
        return new Result(200);
    }
}


