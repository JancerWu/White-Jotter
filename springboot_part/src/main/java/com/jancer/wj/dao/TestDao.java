package com.jancer.wj.dao;

import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;
import jdk.internal.dynalink.beans.StaticClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Component
public class TestDao {
    @Autowired
    LawsDao lawsDao;

    @Autowired
    UserDAO userDAO;

    @Autowired
    ChaptersDao chaptersDao;

    public static TestDao testDao;

    @PostConstruct
    public void init(){
        testDao = this;
    }

    public static void test(){
//        System.out.println("test:out"+lawsDao.toString());
        System.out.println("test:out"+testDao.userDAO.toString());

        String chaptername = "test";
        Chapters chapter = new Chapters();
        chapter.setLawId(4);
        chapter.setChapterTittle(chaptername);
        testDao.chaptersDao.save(chapter);
        System.out.println(testDao.chaptersDao.findByChapterTittle(chaptername).getId());
    }
}
