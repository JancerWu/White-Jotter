package com.jancer.wj.dao;


import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.List;
import java.util.regex.Pattern;

public class LawDao {

    /*提交法律名字到数据库表laws，返回该法律的id*/
    public int addLaw2Base(String lawName){
        int id ;
        double d = Math.random();
        id = (int)(d*100);

        //执行插入操作返回id
        System.out.println("-----插入表laws的内容-----");
        System.out.println("法律id：" + id + "\n标题：" + lawName);
        System.out.println("----- 本 条 执 行 完 毕 -----");
        return id;
    }

    /*判断此行是章*/
    public boolean isChapter(String text){
        boolean flag = false;
        String rgex = "第(.{1,4})章";
        Pattern p = Pattern.compile(rgex);
        if (p.matcher(text).find()){
            flag = true;
        }
        return flag;
    }

    /*判断此行是条*/
    public boolean isSection(String text){
        boolean flag = false;
        String rgex = "第(.{1,6})条";
        Pattern p = Pattern.compile(rgex);
        if (p.matcher(text).find()){
            flag = true;
        }
        return flag;
    }

    /*提交条到数据库:条内容，章id*/
    public int save2Base(String type, String currentText, int upgradeId) {
        int id = 0; //返回的插入到数据库的条id或者章id
        if (type.equals("section_insert")){
            //执行插入操作：upgradeId 章id；currentText 条内容。
            System.out.println("-----插入表sections的内容-----");
            System.out.println("章id：" + upgradeId + "\n条内容：" + currentText);
            System.out.println("----- 本 条 执 行 完 毕 -----");
            return id;
        }
        if (type.equals("chapter_insert")){
            //执行插入操作：upgradeId 法律id；currentText 章名。
            System.out.println("-----插入表chapters的内容-----");
            System.out.println("法律id：" + upgradeId + "\n标题：" + currentText);
            System.out.println("----- 本 条 执 行 完 毕 -----");
            return id;
        }
        return -1;
    }





    //拆分出章
    public static void law_body_split_by_chapter (String law_body){

        String rgex = "第(.{1,4})章";
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


    public boolean runsave(int law_id, List<XWPFParagraph> paragraphs,LawDao dao) {
        boolean flag = false;
        int currentChapter = 0; //当前章
        String currentChapterTittle = ""; //当前章的标题
        int currentSection = 0; //当前条
        String currentSectionText = ""; //当前节的内容
        int chapter_id = 0,section_id = 0;

        //遍历所有段，如果不是空段，进行处理
        for (XWPFParagraph paragraph: paragraphs) {
            if (!paragraph.isEmpty()){
                //如果是章
                if (dao.isChapter(paragraph.getText())) {
                    currentChapter ++; //建立新的章
                    currentChapterTittle = paragraph.getText(); //当前章名称更新
                    //提交章的标题和法律id到数据库
                    chapter_id = dao.save2Base("chapter_insert", currentChapterTittle,law_id);
                    if (chapter_id <= 0){
                        //章插入出错
                        flag = false;
                        break;
                    }else
                        flag = true;
                        continue; //进行下一个循环
                }
                //如果是新的条
                else if (dao.isSection(paragraph.getText())){
                    //把上一条的全部内容提交
                    section_id = dao.save2Base("section_insert", currentSectionText, chapter_id);
                    if (section_id <= 0){
                        //节插入出错
                        flag = false;
                        break;
                    }else {
                        currentSection++; //节号更新
                        currentSectionText = paragraph.getText(); //清空原条内容，更新为新条内容
                        flag = true;
                        continue;
                    }
                }
                //该段落 既不是新的章标题，也不是新的条，对上一条内容进行追加
                else {
                    currentSectionText +=  paragraph.getText();
                }
            }
        }
        return flag;
    }
}
