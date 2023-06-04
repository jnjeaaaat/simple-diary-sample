package com.example.demo.src.test;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.test.model.JpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/test/jpa")
public class JpaTestController {

    @Autowired
    private final JpaTestService jpaTestService;

    public JpaTestController (JpaTestService jpaTestService) {
        this.jpaTestService = jpaTestService;
    }

    @PostMapping("")
    public BaseResponse<JpaTest> save(@RequestBody JpaTest jpaTest) {
        try {
            JpaTest jpaTestRes = jpaTestService.save(jpaTest);
            return new BaseResponse<>(jpaTestRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
