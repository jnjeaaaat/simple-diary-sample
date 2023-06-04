package com.example.demo.model.jpaTest;

import com.example.demo.src.test.JpaTestRepository;
import com.example.demo.src.test.model.JpaTest;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JpaTestRepositoryTest {

    @Autowired
    JpaTestRepository jpaTestRepository;

    @After(value = "execution(* com.test.controller.TestController.*(..))")
    public void cleanup() {
        jpaTestRepository.deleteAll();
    }

    @Test
    public void jpa_저장_테스트() {
        String title = "jpa 테스트 1";
        String contents = "asdfasdfasdf";

        JpaTest jpaTest = jpaTestRepository.save(JpaTest.builder()
                .title(title)
                .contents(contents)
                .build()
        );
    }
}
