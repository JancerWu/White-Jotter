package com.jancer.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.jancer.wj.dao.LawDao;
import com.jancer.wj.dao.LawsDao;
import com.jancer.wj.dao.TestDao;
import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;
import com.jancer.wj.pojo.Sections;
import com.jancer.wj.service.LawsService;
import com.jancer.wj.service.UserService;
import com.jancer.wj.vo.LawJson;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println(text);
        JSONObject json = JSONObject.parseObject(text);
        String law_tittle = json.getString("law_tittle");
        String law_body = json.getString("law_body");
        System.out.println(law_tittle + law_body);

        int law_id = LawDao.lawDao.addLaw2Base(law_tittle);
        String law_body_list[]  = law_body.split("\\n");

        int currentChapter = 0; //当前章
        String currentChapterTittle = ""; //当前章的标题
        int currentSection = 0; //当前条
        String currentSectionText = ""; //当前节的内容

        int chapter_id = 0;
//                section_id = 0;

        for (int i = 0; i < law_body_list.length ; i++){
            if (!law_body_list[i].isEmpty()){
                //如果是章
                if (LawDao.lawDao.isChapter(law_body_list[i])) {
                    //把上一条的全部内容提交
                    LawDao.lawDao.save2Base("section_insert", currentSectionText, chapter_id);
                    currentChapter ++; //建立新的章
                    currentSection = 0; //章内section清零
                    currentChapterTittle = law_body_list[i]; //当前章名称更新
                    //提交章的标题和法律id到数据库
                    chapter_id = LawDao.lawDao.save2Base("chapter_insert", currentChapterTittle,law_id);
                    continue; //进行下一个循环
                }
                //如果是新的条
                else if (LawDao.lawDao.isSection(law_body_list[i])){
                    //把上一条的全部内容提交
                    if (currentSection!=0){
                        LawDao.lawDao.save2Base("section_insert", currentSectionText, chapter_id);
                    }
                    currentSection ++; //节号更新
                    currentSectionText = law_body_list[i]; //清空原条内容，更新为新条内容
                    //如果是最后一条，先提交
                    if (i==(law_body_list.length-1)){
                        LawDao.lawDao.save2Base("section_insert", currentSectionText, chapter_id);
                    }
                    continue;
                }
                //该段落 既不是新的章标题，也不是新的条，对上一条内容进行追加
                else {
                    currentSectionText +=  law_body_list[i];
                }

            }
        }
        return new Result(200);
    }
//    @GetMapping("api/test")
//    public LawJson getList(){
//        LawDao lawDao = new LawDao();
//
//        //id为9的法律的章节
//        List<Chapters> chapters_list = lawDao.getAllChaptersByLaw_Id(28);
//        List<Map> map_list = new ArrayList<>();
//        for (int i = 0; i<chapters_list.size(); i++){
//            int chapter_id = chapters_list.get(i).getId();
//            List<Sections> sections_list = lawDao.getAllSectionByChapter_Id(chapter_id);
//            Map map_chapter = new HashMap();
//            map_chapter.put("label",chapters_list.get(i).getChapterTittle()); //章标题存入map
//            List<Map> map_section_list = new ArrayList<>();
//            for (int j=0;j<sections_list.size();j++){
//                //章内所有的条存入
//                Map map_section = new HashMap();
//                map_section.put("label",sections_list.get(j).getSectionContent());
//                map_section_list.add(map_section);
//            }
//            map_chapter.put("children",map_section_list);
//            map_list.add(map_chapter);
//        }
//
//        for (int i = 0; i<map_list.size(); i++){
//            System.out.println(map_list.get(i));
//        }
//        LawJson lawJson = new LawJson();
//        lawJson.setMap_list(map_list);
//        return lawJson;
//    }

    @CrossOrigin
    @PostMapping(value = "api/show")
    public LawJson postList(@RequestBody JSONObject id){
        System.out.println(id);
        System.out.println(id.getIntValue("value"));
        LawDao lawDao = new LawDao();
        //找相应法律的id的章节
        List<Chapters> chapters_list = lawDao.getAllChaptersByLaw_Id(id.getIntValue("value"));
        List<Map> map_list = new ArrayList<>();
        for (int i = 0; i<chapters_list.size(); i++){
            int chapter_id = chapters_list.get(i).getId();
            List<Sections> sections_list = lawDao.getAllSectionByChapter_Id(chapter_id);
            Map map_chapter = new HashMap();
            map_chapter.put("label",chapters_list.get(i).getChapterTittle()); //章标题存入map
            List<Map> map_section_list = new ArrayList<>();
            for (int j=0;j<sections_list.size();j++){
                //章内所有的条存入
                Map map_section = new HashMap();
                map_section.put("label",sections_list.get(j).getSectionContent());
                map_section_list.add(map_section);
            }
            map_chapter.put("children",map_section_list);
            map_list.add(map_chapter);
        }

        for (int i = 0; i<map_list.size(); i++){
            System.out.println(map_list.get(i));
        }
        LawJson lawJson = new LawJson();
        lawJson.setMap_list(map_list);
        return lawJson;
//        JSONObject jsonObject = JSONObject.parseObject("{label: \"agvgh\"}, {label: \"sds\"}");
//        System.out.println(jsonObject);
//        return jsonObject;
    }
}

