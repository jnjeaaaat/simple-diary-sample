package com.example.demo.src.test;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.test.model.JpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/test/jpa")
public class JpaTestController {

    @Autowired
    private final JpaTestService jpaTestService;
    @Autowired
    private final JpaTestRepository jpaTestRepository;

    public JpaTestController (JpaTestService jpaTestService, JpaTestRepository jpaTestRepository) {
        this.jpaTestService = jpaTestService;
        this.jpaTestRepository = jpaTestRepository;
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

    @GetMapping("")
    public BaseResponse<List<JpaTestSpecific>> getData() {
        List<JpaTestSpecific> jpaTest = jpaTestRepository.findAllBy(JpaTestSpecific.class);
        return new BaseResponse<>(jpaTest);
    }
}
