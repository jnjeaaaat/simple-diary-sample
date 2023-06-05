package com.example.demo.src.test;

import com.example.demo.src.test.model.JpaTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaTestRepository extends JpaRepository<JpaTest, Long> {
//    List<JpaTestSpecific> findAllBy();
    <T> List<T> findAllBy(Class<T> type);
}
