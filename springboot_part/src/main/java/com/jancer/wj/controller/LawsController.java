package com.jancer.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.jancer.wj.dao.LawDao;
import com.jancer.wj.vo.LawVo;
import com.jancer.wj.vo.Result;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xerces.dom.DocumentImpl;
import org.elasticsearch.index.snapshots.blobstore.SlicedInputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class LawsController {

    @PostMapping("api/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "D:/test/";
            File dest = new File(filePath + fileName);
            try {
                file.transferTo(dest);
                FileInputStream inputStream = new FileInputStream(dest);
                XWPFDocument document = new XWPFDocument(inputStream);

                List<XWPFParagraph> paragraphs = document.getParagraphs();


                int currentChapter = 0;
                int currentSection = 0;
                for (XWPFParagraph paragraph: paragraphs) {
                    System.out.println(paragraph.getText());
                    System.out.println("---------------------------------------");
                    if (isChapter(paragraph.getText())) {
                        currentChapter ++;
                        //fsdafasdf

                    }

//                    if (is){
//                        currentChapter
//                    }
                }

                System.out.println("上传成功" + fileName);
            } catch (IOException e) {
                System.out.println("上传失败,自动重启");
            }
        }
    }

    boolean isChapter(String paragraph) {
        if (paragraph.split("").length > 0){
            return true;
        }
        return false;
    }

    @CrossOrigin
    @PostMapping(value = "api/index")
    public Result login(@RequestBody String text){
//        System.out.println(map.getLaw_body());
//        String show = map.getLaw_body();
//        map.trim();
//        String show = map.replaceAll("\\\\n","");
//        show=show.replaceAll("\\s*", "");
//        System.out.println(map);
//        JSONObject jo = JSONObject.parseObject(new String(map));
//        String law_body = jo.getString("law_body");

//        LawDao.law_body_split_by_chapter(lawVo.getText());

        System.out.println(text);
        return new Result(200);
    }
}


