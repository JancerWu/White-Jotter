package com.jancer.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "sections")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Sections {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "chapterId")
    int chapterId;

    @Column(name = "sectionContent")
    String sectionContent;

    @Column(name = "sectionState")
    String sectionState;

    public String getSectionState() {
        return sectionState;
    }

    public void setSectionState(String sectionState) {
        this.sectionState = sectionState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public void setSectionContent(String sectionContent) {
        this.sectionContent = sectionContent;
    }

    public String getSectionContent() {
        return sectionContent;
    }
}

