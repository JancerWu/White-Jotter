package com.jancer.wj.controller;

import com.jancer.wj.dao.LawDao;
import com.jancer.wj.pojo.Laws;
import com.jancer.wj.service.LawsService;
import com.jancer.wj.vo.Result;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.beans.factory.annotation.Autowired;
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
    @PostMapping(value = "api/upload")
    /*法律文件上传后，按章拆分为条*/
    public void upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String filePath = "D:/test/";
            File dest = new File(filePath + fileName);
            LawDao dao = new LawDao();
            int law_id = dao.addLaw2Base(fileName); //法律名添加到数据，返回数据库中的id
            try {
                file.transferTo(dest);
                FileInputStream inputStream = new FileInputStream(dest);
                XWPFDocument document = new XWPFDocument(inputStream);
                List<XWPFParagraph> paragraphs = document.getParagraphs();

                boolean flag = dao.runsave(law_id, paragraphs, dao); //运行法律条目分割保存到数据库，如果出现错误停止插入，返回flase反之返回true

                System.out.println("上传成功" + fileName);
                Laws law_test = new Laws();
                law_test.setLaw_name("test");
                lawsService.add(law_test);
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
        Laws law_test = new Laws();
        law_test.setLaw_name("test");
        lawsService.add(law_test);

        System.out.println(text);
        return new Result(200);
    }
}


