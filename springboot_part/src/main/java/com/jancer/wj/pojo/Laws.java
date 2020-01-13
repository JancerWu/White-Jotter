package com.jancer.wj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Entity
@Table(name = "laws")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})

@Repository
public class Laws {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "lawTitle")
    String lawTitle;

    @Column(name = "lawState")
    String lawState;

    public String getLawState() {
        return lawState;
    }

    public void setLawState(String lawState) {
        this.lawState = lawState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLawTitle() {
        return lawTitle;
    }

    public void setLawTitle(String law_name) {
        this.lawTitle = law_name;
    }
}

