package com.jancer.wj.dao;

import com.jancer.wj.pojo.Laws;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface LawsDao extends JpaRepository<Laws,Integer> {
    Laws findById(String id);
    Laws findByLawTitle(String lawTitle);

}
