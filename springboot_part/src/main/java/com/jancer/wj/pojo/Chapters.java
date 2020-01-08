package com.jancer.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "chapters")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

public class Chapters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "lawId")
    int lawId;

    @Column(name = "chapterTittle")
    String chapterTittle;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLawId() {
        return lawId;
    }

    public void setLawId(int lawId) {
        this.lawId = lawId;
    }

    public void setChapterTittle(String chapterTittle) {
        this.chapterTittle = chapterTittle;
    }

    public String getChapterTittle() {
        return chapterTittle;
    }
}

