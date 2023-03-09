package com.example.demo.src.diaryImg;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/diaries/imgs")
public class DiaryImgController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final DiaryImgProvider diaryImgProvider;
    @Autowired
    private final DiaryImgService diaryImgService;
    @Autowired
    private final JwtService jwtService;

    public DiaryImgController (DiaryImgProvider diaryImgProvider, DiaryImgService diaryImgService, JwtService jwtService) {
        this.diaryImgProvider = diaryImgProvider;
        this.diaryImgService = diaryImgService;
        this.jwtService = jwtService;
    }


}
