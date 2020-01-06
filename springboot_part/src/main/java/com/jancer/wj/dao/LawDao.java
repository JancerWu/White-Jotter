package com.jancer.wj.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LawDao {

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

}
