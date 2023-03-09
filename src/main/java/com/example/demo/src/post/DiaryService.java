package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.post.model.PostDiaryReq;
import com.example.demo.src.post.model.PostDiaryRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class DiaryService {
    @Autowired
    private final DiaryDao diaryDao;
    private final DiaryProvider diaryProvider;
    private final JwtService jwtService; // JWT부분은 7주차에 다루므로 모르셔도 됩니다!

    public DiaryService(DiaryDao diaryDao, DiaryProvider diaryProvider, JwtService jwtService) {
        this.diaryDao = diaryDao;
        this.jwtService = jwtService;
        this.diaryProvider = diaryProvider;
    }

    /**
     * [post] createPost
     */
    public PostDiaryRes createPost(PostDiaryReq postDiaryReq) throws BaseException {
        try {
            PostDiaryRes postDiaryRes = new PostDiaryRes(diaryDao.createPost(postDiaryReq)+" 번째 일기입니다.");
            return postDiaryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
