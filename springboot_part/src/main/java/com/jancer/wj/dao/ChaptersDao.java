package com.jancer.wj.dao;

import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChaptersDao extends JpaRepository<Chapters,Integer> {
    Chapters findById(String id);
    Chapters findByChapterTittle(String chapterTittle);
    Chapters findByChapterTittleAndLawId(String chapterTittle,int LawId);

}
