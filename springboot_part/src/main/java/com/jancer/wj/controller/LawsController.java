package com.jancer.wj.controller;

import com.jancer.wj.dao.LawDao;
import com.jancer.wj.vo.Result;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
public class LawsController {
    /*法律文件上传后，按章拆分为条*/
    @PostMapping("api/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "D:/test/";
            File dest = new File(filePath + fileName);
            LawDao dao = new LawDao();
            int law_id = dao.addLaw2Base(fileName);
            try {
                file.transferTo(dest);
                FileInputStream inputStream = new FileInputStream(dest);
                XWPFDocument document = new XWPFDocument(inputStream);
                List<XWPFParagraph> paragraphs = document.getParagraphs();


                int currentChapter = 0; //当前章
                String currentChapterTittle = ""; //当前章的标题
                int currentSection = 0; //当前条
                String currentSectionText = ""; //当前节的内容

                //遍历所有段，如果不是空段，进行处理
                for (XWPFParagraph paragraph: paragraphs) {
                    if (!paragraph.isEmpty()){
                        //如果是章
                        if (dao.isChapter(paragraph.getText())) {
                            currentChapter ++; //建立新的章
                            currentChapterTittle = paragraph.getText(); //当前章名称更新
                            //提交章的标题和法律id到数据库
                            dao.save2Base("chapter_insert", currentChapterTittle,law_id);
                            continue; //进行下一个循环
                        }
                        //如果是新的条
                        else if (dao.isSection(paragraph.getText())){
                            //把上一条的全部内容提交
                            boolean flag = dao.save2Base("section_insert", currentSectionText, currentChapter);
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


