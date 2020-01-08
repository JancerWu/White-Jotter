package com.jancer.wj.dao;

import com.jancer.wj.pojo.Chapters;
import com.jancer.wj.pojo.Laws;

import com.jancer.wj.pojo.Sections;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectionsDao extends JpaRepository<Sections,Integer> {
    Sections findById(String id);
    Sections findBySectionContent(String sectionContent);

}
