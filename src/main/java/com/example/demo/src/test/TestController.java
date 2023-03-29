package com.example.demo.src.test;

import com.example.demo.config.BaseResponse;
import com.example.demo.src.test.model.JwtList;
import com.example.demo.src.test.model.ListDataTest;
import com.example.demo.src.test.model.TestDate;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.utils.ValidationRegex.isRegexBirth;

@RestController
@RequestMapping("/test")
public class TestController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TestDao testDao;
    private final JwtService jwtService;
    @Autowired
    public TestController(TestDao testDao, JwtService jwtService) {
        this.testDao = testDao;
        this.jwtService = jwtService;
    }

    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
//        trace, debug 레벨은 Console X, 파일 로깅 X
//        logger.trace("TRACE Level 테스트");
//        logger.debug("DEBUG Level 테스트");

//        info 레벨은 Console 로깅 O, 파일 로깅 X
        logger.info("INFO Level 테스트");
//        warn 레벨은 Console 로깅 O, 파일 로깅 O
        logger.warn("Warn Level 테스트");
//        error 레벨은 Console 로깅 O, 파일 로깅 O (app.log 뿐만 아니라 error.log 에도 로깅 됨)
//        app.log 와 error.log 는 날짜가 바뀌면 자동으로 *.gz 으로 압축 백업됨
        logger.error("ERROR Level 테스트");

        return "테스트완료";
    }

    @ResponseBody
    @PostMapping("/date")
    public boolean isDate(@RequestBody TestDate testDate) {
        boolean isDate = isRegexBirth(testDate.getDate());
        System.out.println(isDate);
        return isDate;
    }

    @ResponseBody
    @PostMapping("/list")
    public BaseResponse<ListDataTest> postListTest(@RequestBody ListDataTest listDataTest){
        return new BaseResponse<>(listDataTest);
    }

    @ResponseBody
    @GetMapping("/jwt")
    public BaseResponse<List<JwtList>> getJwtList() {
        List<JwtList> jwtList = new ArrayList<>();
        String jwt = "";
        for (int i = 1; i <= testDao.getUserCount(); i++) {
            jwt = jwtService.createJwt(i);
            jwtList.add(new JwtList(i, jwt));
        }

        return new BaseResponse<>(jwtList);
    }
}
