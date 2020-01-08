package com.jancer.wj.dao;


import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;
import com.jancer.wj.pojo.Sections;
import com.jancer.wj.pojo.User;
import com.jancer.wj.service.LawsService;
import com.jancer.wj.service.UserService;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.swing.SwingUtilities2;

import javax.annotation.PostConstruct;
import java.util.regex.Pattern;

@Component
public class LawDao {
    @Autowired
    LawsDao lawsDao;
    @Autowired
    ChaptersDao chaptersDao;
    @Autowired
    SectionsDao sectionsDao;

    public static LawDao lawDao;

    @PostConstruct
    public void init(){
        lawDao = this;
    }



    /*提交法律名字到数据库表laws，返回该法律的id*/
    public static int addLaw2Base(String lawName){
//        int id ;
//
//        double d = Math.random();
//        id = (int)(d*100);

        //执行插入操作返回id





        Laws law = new Laws();
        law.setLawTitle(lawName);
        lawDao.lawsDao.save(law);
        int id = lawDao.lawsDao.findByLawTitle(lawName).getId();

        System.out.println("-----插入表laws的内容-----");
        System.out.println("法律id：" + id + "\n标题：" + lawName);
        System.out.println("----- 本 条 执 行 完 毕 -----");

        return id;

    }

    /*判断此行是章*/
    public static boolean isChapter(String text){
        boolean flag = false;
        String rgex = "第(.{1,4})章";
        Pattern p = Pattern.compile(rgex);
        if (p.matcher(text).find()){
            flag = true;
        }
        return flag;
    }
    /*判断此行是条*/
    public static boolean isSection(String text){
        boolean flag = false;
        String rgex = "第(.{1,6})条";
        Pattern p = Pattern.compile(rgex);
        if (p.matcher(text).find()){
            flag = true;
        }
        return flag;
    }

    /*提交条到数据库:条内容，章id*/
    public static int save2Base(String type, String currentText, int upgradeId ) {
        int id = 0; //返回的插入到数据库的条id或者章id
        if (type.equals("section_insert")){
            //执行插入操作：upgradeId 章id；currentText 条内容。
            System.out.println("-----插入表sections的内容-----");
            System.out.println("章id：" + upgradeId + "\n条内容：" + currentText);
            System.out.println("----- 本 条 执 行 完 毕 -----");


            Sections section = new Sections();
            section.setChapterId(upgradeId);
            section.setSectionContent(currentText);
            lawDao.sectionsDao.save(section);



            return 1;
        }
        if (type.equals("chapter_insert")){
            //执行插入操作：upgradeId 法律id；currentText 章名。
            Chapters chapter = new Chapters();
            chapter.setLawId(upgradeId);
            chapter.setChapterTittle(currentText);
            lawDao.chaptersDao.save(chapter);

            System.out.println("-----插入表chapters的内容-----");
            System.out.println("法律id：" + upgradeId + "\n标题：" + currentText);
            System.out.println("----- 本 条 执 行 完 毕 -----");



            return lawDao.chaptersDao.findByChapterTittleAndLawId(currentText, upgradeId).getId();

        }
        return -1;
    }





    //拆分出章
    public static void law_body_split_by_chapter (String law_body){

        String rgex = "第(.*?)章";
        System.out.println(getSubUtil(law_body, rgex));

    }


/**
     * 正则表达式匹配两个指定字符串中间的内容
     * @param soap
     * @return
     */
    public static String[] getSubUtil(String text, String rgex){
        String text_list[] = text.split(rgex);
        for(int i=0;i<text_list.length ;i++){
            System.out.println("iiii:" + i + text_list[i]);

        }
        return text_list;
    }

    //插入laws
    public static boolean insert2laws(String lawName){
        return true;
    }

}
