package com.jancer.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.jancer.wj.dao.LawDao;
import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.dao.TestDao;
import com.jancer.wj.pojo.Laws;
import com.jancer.wj.service.LawsService;
import com.jancer.wj.service.UserService;
import com.jancer.wj.vo.LawVo;
import com.jancer.wj.vo.Result;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xerces.dom.DocumentImpl;
import org.elasticsearch.index.snapshots.blobstore.SlicedInputStream;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    LawsService lawsService;

    @Autowired
    LawsDao lawsDao;



    @PostMapping(value = "api/upload")
    /*法律文件上传后，按章拆分为条*/
    public void upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "D:/test/";
            File dest = new File(filePath + fileName);
//            LawDao dao = new LawDao();
//            int law_id = dao.addLaw2Base(fileName);
            int law_id = LawDao.lawDao.addLaw2Base(fileName);
            try {
                file.transferTo(dest);
                FileInputStream inputStream = new FileInputStream(dest);
                XWPFDocument document = new XWPFDocument(inputStream);
                List<XWPFParagraph> paragraphs = document.getParagraphs();
                for(int i=0;i<paragraphs.size();i++){
                    System.out.println(paragraphs.get(i).getText());
                }

                int currentChapter = 0; //当前章
                String currentChapterTittle = ""; //当前章的标题
                int currentSection = 0; //当前条
                String currentSectionText = ""; //当前节的内容

                int chapter_id = 0;
//                section_id = 0;

                //遍历所有段，如果不是空段，进行处理
                for (XWPFParagraph paragraph: paragraphs) {
                    if (!paragraph.isEmpty()){
                        //如果是章
                        if (LawDao.lawDao.isChapter(paragraph.getText())) {
                            //把上一条的全部内容提交
                            LawDao.lawDao.save2Base("section_insert", currentSectionText, chapter_id);
                            currentChapter ++; //建立新的章
                            currentSection = 0; //章内section清零
                            currentChapterTittle = paragraph.getText(); //当前章名称更新
                            //提交章的标题和法律id到数据库
                            chapter_id = LawDao.lawDao.save2Base("chapter_insert", currentChapterTittle,law_id);
                            continue; //进行下一个循环
                        }
                        //如果是新的条
                        else if (LawDao.lawDao.isSection(paragraph.getText())){
                            //把上一条的全部内容提交
                            if (currentSection!=0){
                                LawDao.lawDao.save2Base("section_insert", currentSectionText, chapter_id);
                            }
                            currentSection ++; //节号更新
                            currentSectionText = paragraph.getText(); //清空原条内容，更新为新条内容
                            continue;
                        }
                        //该段落 既不是新的章标题，也不是新的条，对上一条内容进行追加
                        else {
                            currentSectionText +=  paragraph.getText();
                        }

                    }
//                    System.out.println(paragraph.getText());
//                    System.out.println("---------------------------------------");


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

//        Laws law = new Laws();
//        law.setLawTitle("test2");
//        lawsDao.save(law);
//
//        System.out.println(text);
//        System.out.println(lawsDao.findByLawTitle("test2").getId());
//              System.out.println("test:in"+lawsDao.toString());
        TestDao.test();


        return new Result(200);
    }
}


